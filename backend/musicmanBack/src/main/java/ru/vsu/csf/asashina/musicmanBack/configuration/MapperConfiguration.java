package ru.vsu.csf.asashina.musicmanBack.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vsu.csf.asashina.musicmanBack.mapper.RoleMapper;
import ru.vsu.csf.asashina.musicmanBack.mapper.UserMapper;
import ru.vsu.csf.asashina.musicmanBack.mapper.VerificationMapper;

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
}
