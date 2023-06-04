import React from 'react';
import axios from 'axios';
import { Slider } from '@mui/material';
import { MusicFooterPlayerEqualizerIcon } from "../../entities/icons/musicFooterPlayerEqualizer"
import { MusicFooterPlayerHeartIcon } from "../../entities/icons/musicFooterPlayerHeart"
import { MusicFooterPlayerLeftIcon } from "../../entities/icons/musicFooterPlayerLeft"
import { MusicFooterPlayerPlayIcon } from "../../entities/icons/musicFooterPlayerPlay"
import { MusicFooterPlayerRightIcon } from "../../entities/icons/musicFooterPlayerRight"
import { MusicFooterPlayerEqualizer, MusicFooterPlayerEqualizerContainer, MusicFooterPlayerSlider, MusicPlayerFooterBasisActions, MusicPlayerFooterContainer, MusicPlayerFooterImageContainer, MusicPlayerFooterTime, MusicPlayerFooterTrackInfo } from "./styled"
import { MusicFooterPlayerPauseIcon } from '../../entities/icons/musicFooterPlayerPause';
import { $currentTrack, $currentTrackIsPaused, setCurrentTrackIsPausedFx } from '../../shared/stores/tracks';
import { useStore } from 'effector-react';

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

    const [int, setInt] = React.useState<any>(0);
    const [musicHasStarted, setMusicHasStarted] = React.useState(false);

    const [duration, setDuration] = React.useState(0);
    const [currentDuration, setCurrentDuration] = React.useState(0);
    const [minutes, setMinutes] = React.useState(0);
    const [seconds, setSeconds] = React.useState(0);

    const [audioObj, setAudioObj] = React.useState<any>(null);
    const [audioContext, setAudioContext] = React.useState<any>(null);
    const [audioBuffer, setAudioBuffer] = React.useState<any>(null);

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

    const loadAudioBuffer = async () => {
        console.log('Start')
        const response = await axios.get('/gspd.mp3', {
            responseType: 'arraybuffer',
            headers: {
                Authorization: `Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiQURNSU4iLCJVU0VSIl0sImlzcyI6Im11c2ljbWFuLXNlcnZlciIsIm5pY2tuYW1lIjoiYWRtaW4iLCJmdWxsTmFtZSI6ImFkbWluIGFkbWluIiwiZXhwIjoxNjg2Mjk1OTA1LCJlbWFpbCI6ImFkbWluQG1haWwuY29tIn0.p75bEYutAaW-8blNisy27xLW7TYzgxm7RtBU8xxwbk8`
            }
        });
        const audioContext = new AudioContext();
        const audioBufferSourceNode = audioContext.createBufferSource();
        const decodedData = await audioContext.decodeAudioData(response.data);

        audioBufferSourceNode.buffer = decodedData;

        audioBufferSourceNode.connect(audioContext.destination);

        console.log(audioContext, decodedData)

        setDuration(decodedData.duration);

        setAudioObj(audioBufferSourceNode);
        setAudioContext(audioContext);

        setAudioBuffer(decodedData);

        audioBufferSourceNode.start();
        setMusicHasStarted(true);
    };

    // React.useEffect(() => {
    //     loadAudioBuffer();
    // }, [])

    React.useEffect(() => {
        if (!musicHasStarted) {
            loadAudioBuffer()
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
    }, [currentTrackIsPaused, musicHasStarted]);

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
                <MusicFooterPlayerLeftIcon />
                <div onClick={() => setCurrentTrackIsPausedFx(!currentTrackIsPaused)}>{renderIcon}</div>
                <MusicFooterPlayerRightIcon />
            </MusicPlayerFooterBasisActions>

            <MusicPlayerFooterTime>{time}</MusicPlayerFooterTime>

            <MusicPlayerFooterTrackInfo>
                <span>{currentTrack ? currentTrack.singer.fullName : ''}</span>
                <span>{currentTrack ? currentTrack.title : ''}</span>
            </MusicPlayerFooterTrackInfo>

            <MusicFooterPlayerHeartIcon />

            <MusicFooterPlayerEqualizer>
                <MusicFooterPlayerEqualizerIcon />

                <MusicFooterPlayerEqualizerContainer>
                    <span>Эквалайзер</span>
                </MusicFooterPlayerEqualizerContainer>
            </MusicFooterPlayerEqualizer>

            <MusicFooterPlayerSlider>
                {duration ? <SliderComponent {...{ duration, stopStartInterval, updateTrackTime, currentDuration, changeTimeTrack }} /> : null}
            </MusicFooterPlayerSlider>
        </MusicPlayerFooterContainer >
    )
}