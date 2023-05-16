package ru.vsu.csf.asashina.musicmanBack.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vsu.csf.asashina.musicmanBack.mapper.*;

@Configuration
public class MapperConfiguration {

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }

    @Bean
    public RoleMapper roleMapper() {
        return RoleMapper.INSTANCE;
    }

    @Bean
    public VerificationMapper verificationMapper() {
        return VerificationMapper.INSTANCE;
    }

    @Bean
    public GenreMapper genreMapper() {
        return GenreMapper.INSTANCE;
    }

    @Bean
    public SingerMapper singerMapper() {
        return SingerMapper.INSTANCE;
    }

    @Bean
    public SongMapper songMapper() {
        return SongMapper.INSTANCE;
    }
}
