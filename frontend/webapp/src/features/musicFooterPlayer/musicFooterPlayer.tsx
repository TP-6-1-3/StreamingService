import React from 'react';
import axios from 'axios';
import { Slider } from '@mui/material';
import { MusicFooterPlayerEqualizerIcon } from "../../entities/icons/musicFooterPlayerEqualizer"
import { MusicFooterPlayerHeartIcon } from "../../entities/icons/musicFooterPlayerHeart"
import { MusicFooterPlayerLeftIcon } from "../../entities/icons/musicFooterPlayerLeft"
import { MusicFooterPlayerPlayIcon } from "../../entities/icons/musicFooterPlayerPlay"
import { MusicFooterPlayerRightIcon } from "../../entities/icons/musicFooterPlayerRight"
import { EqualizerLines, MusicFooterPlayerEqualizer, MusicFooterPlayerEqualizerContainer, MusicFooterPlayerSlider, MusicPlayerFooterBasisActions, MusicPlayerFooterContainer, MusicPlayerFooterImageContainer, MusicPlayerFooterTime, MusicPlayerFooterTrackInfo } from "./styled"
import { MusicFooterPlayerPauseIcon } from '../../entities/icons/musicFooterPlayerPause';
import { $currentTrack, $currentTrackAudioObj, $currentTrackIsPaused, $currentTrackSongId, setCurrentTrackAudioObjFx, setCurrentTrackIsPausedFx, setNextTrackFx, setPrevTrackFx } from '../../shared/stores/tracks';
import { useStore } from 'effector-react';
import { Equalizer } from '../../shared/libs/equalizer';
import { ISong } from '../../shared/api/songs/getSongs';
import { $userCredentials } from '../../shared/stores/user';

const MusicEqualizer = React.memo(({ showEqualizer, setShowEqualizer, audioContext, audioBuffer }: any) => {
    const audioObj = useStore($currentTrackAudioObj);
    const [equalizer, setEqualizer] = React.useState<any>(null);
    const [hasEdit, setHasEdit] = React.useState<boolean>(false);

    React.useEffect(() => {
        if (audioContext && audioBuffer && hasEdit) {
            const equalizer = new Equalizer(audioContext);
            equalizer.initialize(audioBuffer);

            setEqualizer(equalizer);
        }
    }, [hasEdit, audioContext]);

    const onInput = (index: any, value: any) => {
        const gain = Number(value);
        equalizer.setGain(index, gain);
    }

    const neeededRowEqualize = 3;
    const renderEqualizeLines = new Array(neeededRowEqualize).fill('').map((_, key) => {
        return (
            <Slider
                min={-10}
                max={10}
                step={0.1}
                defaultValue={0}
                orientation="vertical"
                onChange={(_, value) => {
                    if (!hasEdit) {
                        setHasEdit(true);
                    } else { 
                        onInput(key + 1, value);
                    }
                }}
            />
        )
    })

    return (<div>
        <div onClick={() => setShowEqualizer(!showEqualizer)}><MusicFooterPlayerEqualizerIcon /></div>
        {showEqualizer ? <MusicFooterPlayerEqualizerContainer>
            <span>Эквалайзер</span>

            <EqualizerLines>
                {renderEqualizeLines}
            </EqualizerLines>
        </MusicFooterPlayerEqualizerContainer> : null}
    </div>)
})

const SliderComponent = React.memo(({ duration, stopStartInterval, updateTrackTime, currentDuration, changeTimeTrack }: any) => {
    return (
        <Slider
            min={0}
            max={duration}
            value={currentDuration}
            onMouseUp={(_) => {
                stopStartInterval(currentDuration);
                changeTimeTrack(currentDuration);
            }}
            onChange={(_, value: any) => {
                updateTrackTime(value);
            }}
        />
    )
});

export const MusicPlayerFooter = () => {
    const currentTrackIsPaused = useStore($currentTrackIsPaused);
    const currentTrack = useStore($currentTrack);
    const userCredentials = useStore($userCredentials);
    const currentTrackSongId = useStore($currentTrackSongId);

    const [showEqualizer, setShowEqualizer] = React.useState<any>(false);
    const [int, setInt] = React.useState<any>(0);
    const [initialPlayer, setInitialPlayer] = React.useState<any>(false);
    const [musicHasStarted, setMusicHasStarted] = React.useState(false);

    const [duration, setDuration] = React.useState(0);
    const [currentDuration, setCurrentDuration] = React.useState(0);
    const [minutes, setMinutes] = React.useState(0);
    const [seconds, setSeconds] = React.useState(0);

    const audioObj: any = useStore($currentTrackAudioObj);
    const setAudioObj = setCurrentTrackAudioObjFx;

    const [audioContext, setAudioContext] = React.useState<any>(null);
    const [audioBuffer, setAudioBuffer] = React.useState<any>(null);

    console.log(currentTrackSongId);

    React.useEffect(() => {
        clearInterval(int);
        setMinutes(0);
        setSeconds(0);
        console.log('changed audio')

        if (!currentTrack) return;
        if (audioObj) {
            audioObj.stop();
            audioObj.disconnect();
        }

        loadAudioBuffer(currentTrack);
    }, [currentTrackSongId]);

    const stopStartInterval = (currentDuration: number) => {
        clearInterval(int);

        let time = currentDuration;
        const inter = setInterval(() => {
            updateTrackTime(time + 1);
            time = time + 1;
        }, 1000);

        setInt(inter);
    }

    const updateTrackTime = (duration: number) => {
        const minutes = Math.floor(duration / 60);
        const seconds = duration - (minutes * 60);

        // setDuration(duration);
        setCurrentDuration(duration);
        setMinutes(minutes);
        setSeconds(seconds);
    }

    const loadAudioBuffer = async (currentTrack: ISong) => {
        console.log('Start')

        if (!currentTrack.url) return;
        if (!userCredentials) return;

        const response = await axios.get(currentTrack.url, {
            responseType: 'arraybuffer',
            headers: {
                Authorization: `Bearer ${userCredentials?.accessToken}`
            }
        });
        const audioContext = new AudioContext();
        const audioBufferSourceNode = audioContext.createBufferSource();
        const decodedData = await audioContext.decodeAudioData(response.data);

        audioBufferSourceNode.buffer = decodedData;

        audioBufferSourceNode.connect(audioContext.destination);

        console.log(audioContext, decodedData)

        setDuration(decodedData.duration);
        setCurrentDuration(0);
        stopStartInterval(0);

        setAudioObj(audioBufferSourceNode);
        setAudioContext(audioContext);

        setAudioBuffer(decodedData);

        audioBufferSourceNode.start();
        setMusicHasStarted(true);
    };

    React.useEffect(() => {
        if (initialPlayer) {
            if (!musicHasStarted) {
                if (currentTrack) {
                    loadAudioBuffer(currentTrack);
                }
            } else {
                if (audioContext) {
                    if (currentTrackIsPaused) {
                        audioContext.suspend()
                        clearInterval(int);
                    } else {
                        audioContext.resume()
                        stopStartInterval(currentDuration);
                    }
                }
            }
        }
    }, [currentTrackIsPaused, musicHasStarted, initialPlayer]);

    const changeTimeTrack = (seconds: number) => {
        const currentTime = audioContext.currentTime;

        audioObj.stop(currentTime);
        audioObj.disconnect();

        const audioBufferSourceNode = audioContext.createBufferSource();
        audioBufferSourceNode.buffer = audioBuffer;
        audioBufferSourceNode.connect(audioContext.destination);
        // startTime = context.currentTime;
        const startTime = audioContext.currentTime;

        audioBufferSourceNode.start(currentTime, currentTime - startTime + seconds);

        setAudioObj(audioBufferSourceNode);

        setCurrentDuration(seconds);
    }

    const renderIcon = currentTrackIsPaused ? <MusicFooterPlayerPlayIcon /> : <MusicFooterPlayerPauseIcon />;
    const time = `${minutes}:${seconds}`;

    return (
        <MusicPlayerFooterContainer>
            <MusicPlayerFooterImageContainer>
                <img src="/assets/music.png" alt="" />
            </MusicPlayerFooterImageContainer>

            <MusicPlayerFooterBasisActions>
                <div onClick={() => setPrevTrackFx()}><MusicFooterPlayerLeftIcon /></div>
                <div onClick={() => {
                    if (!initialPlayer) setInitialPlayer(true);

                    setCurrentTrackIsPausedFx(!currentTrackIsPaused)
                }}>{renderIcon}</div>
                <div onClick={() => setNextTrackFx()}><MusicFooterPlayerRightIcon /></div>
            </MusicPlayerFooterBasisActions>

            <MusicPlayerFooterTime>{time}</MusicPlayerFooterTime>

            <MusicPlayerFooterTrackInfo>
                <span>{currentTrack ? currentTrack.singer.fullName : ''}</span>
                <span>{currentTrack ? currentTrack.title : ''}</span>
            </MusicPlayerFooterTrackInfo>

            <MusicFooterPlayerHeartIcon />

            <MusicFooterPlayerEqualizer>
                <MusicEqualizer {...{ showEqualizer, setShowEqualizer, audioContext, audioBuffer }} />
            </MusicFooterPlayerEqualizer>

            <MusicFooterPlayerSlider>
                {duration ? <SliderComponent {...{ duration, stopStartInterval, updateTrackTime, currentDuration, changeTimeTrack }} /> : null}
            </MusicFooterPlayerSlider>
        </MusicPlayerFooterContainer >
    )
}