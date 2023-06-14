import React, {useState} from 'react'
import {HeaderWrapper} from "../../../entities/headerWrapper";
import {PersonalHeader} from "../../../features/personalHeader";
import {AddMusicCard, HomeComponent, HomeLayout} from "./styled";
import {request} from "../../../shared/libs/request";
import {AddSongRequest} from "../../../shared/api/songs/addSongs";
import {AddSingerRequest} from "../../../shared/api/singers/addSinger";
import {AddGenreRequest} from "../../../shared/api/genres/addGenre";

export const Songs = (): React.ReactElement<void, string> => {

    const onAddSong = () => {
        AddSongRequest(songFormData);
    }

    const onAddSinger = () => {
        AddSingerRequest(singerFormData);
    }

    const onAddGenre = () => {
        console.log(genreFormData)
        AddGenreRequest(genreFormData);
    }

    const handleAudioFileChange = (e: any) => {
        const selectedFile = e.target.files[0];
        setSongFormData((prevData) => ({
            ...prevData,
            file: selectedFile,
        }));
    };

    const handleImageFileChange = (e: any) => {
        const selectedFile = e.target.files[0];
        setSongFormData((prevData) => ({
            ...prevData,
            picture: selectedFile,
        }));
    };


    const [songFormData, setSongFormData] = useState({
        title: "",
        duration: "",
        singerId: 0,
        genreIds: [],
        file: null,
        picture: null,
        createdYear: 0,
        album: ""
    });

    const [singerFormData, setSingerFormData] = useState({
        fullName: '',
        description: ''
    })

    const [genreFormData, setGenreFormData] = useState({
        name: ""
    });

    const onSongHandleChange = (e: any) => {
        const { name, value } = e.target;
        if (name === 'genreIds') {
            const singerId = parseInt(value.trim(), 10)
            setSongFormData((prevData) => ({
                ...prevData,
                [name]: singerId,
            }));
        } else {
            setSongFormData((prevData) => ({
                ...prevData,
                [name]: value,
            }));
        }
    };

    const onSingerHandleChange = (e: any) => {
        const { name, value } = e.target;
        setSingerFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };
    const onGenreHandleChange = (e: any) => {
        const { name, value } = e.target;
        setGenreFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };




    const handleFileChange = (e: any) => {
        const selectedFile = e.target.files[0];
        setSongFormData((prevData) => ({
            ...prevData,
            file: selectedFile,
        }));
    };


    const onDeleteSong = () => {}

    console.log(songFormData)
    console.log(songFormData.singerId)

    return (
        <HomeComponent>

            <HeaderWrapper>
                <PersonalHeader />
            </HeaderWrapper>

            <HomeLayout>
                <AddMusicCard>
                    <p>Добавить композицию</p>
                    <input type="text" placeholder="Название" name="title" value={songFormData.title} onChange={onSongHandleChange} />
                    {/*<input type="text" placeholder="Продолжительность" name="duration" value={songFormData.duration} onChange={onSongHandleChange}/>*/}
                    <input type="text"  placeholder="Id исполнителя" name="singerId" value={songFormData.singerId} onChange={onSongHandleChange}/>
                    <input type="text" placeholder="Id жанра" name="genreIds" value={songFormData.genreIds} onChange={onSongHandleChange}/>
                    <input type="text" placeholder="album" name="album" value={songFormData.album} onChange={onSongHandleChange}/>

                    {/*<input type="text" placeholder="createdYear" name="createdYear" value={songFormData.createdYear} onChange={onSongHandleChange}/>*/}
                    Track
                    <input type="file" placeholder="mp3"  name="file" accept=".mp3"  onChange={handleAudioFileChange}/>
                    Img
                    <input type="file" placeholder=".png" name="picture" accept="image/png"  onChange={handleImageFileChange}/>
                    <button onClick={onAddSong}>Добавить</button>
                </AddMusicCard>

                <AddMusicCard>
                    <p>Добавить исполнителя</p>
                    <input type="text" placeholder="Имя" name="fullName" value={singerFormData.fullName} onChange={onSingerHandleChange}/>
                    <input type="text" placeholder="Описание" name="description" value={singerFormData.description} onChange={onSingerHandleChange}/>
                    <button onClick={onAddSinger}>Добавить</button>
                </AddMusicCard>

                <AddMusicCard>
                    <p>Добавить жанр</p>
                    <input type="text" placeholder="Название" name="name" value={genreFormData.name} onChange={onGenreHandleChange}/>

                    <button onClick={onAddGenre}>Добавить</button>
                </AddMusicCard>


            </HomeLayout>
        </HomeComponent>
    )
}