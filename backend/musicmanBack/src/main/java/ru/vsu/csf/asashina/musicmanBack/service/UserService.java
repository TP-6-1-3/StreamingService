package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.exception.PasswordsDoNotMatch;
import ru.vsu.csf.asashina.musicmanBack.exception.UserNotVerifiedException;
import ru.vsu.csf.asashina.musicmanBack.mapper.UserMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserWithSongsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.User;
import ru.vsu.csf.asashina.musicmanBack.model.request.UserSignUpRequest;
import ru.vsu.csf.asashina.musicmanBack.repository.UserRepository;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleService roleService;

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException("Пользователь с данной почтой не существует")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityDoesNotExistException("Пользователь с данным ИД не существует")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserByEmailWithVerificationCheck(String email) {
        UserDTO user = getUserByEmail(email);
        if (!user.getIsVerified()) {
            throw new UserNotVerifiedException("Пользователь не верифицирован");
        }
        return user;
    }

    @Transactional
    public UserDTO registerUser(UserSignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityAlreadyExistsException("Пользователь с данной почтой уже существует");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new EntityAlreadyExistsException("Пользователь с данным никнеймом уже существует");
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordsDoNotMatch("Пароли не совпадают");
        }
        User user = userMapper.toEntityFromRequest(
                request, Set.of(roleService.getUserRole()), BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user = userRepository.save(user);
        return userMapper.toDTOFromEntity(user);
    }

    @Transactional
    public void verifyUserById(Long id) {
        userRepository.verifyUserById(id);
    }

    public UserWithSongsDTO getUserWithSongs(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException("Пользователь не существует с заданным ИД"));
        return userMapper.toWithSongsDTOFromEntity(user);
    }

    @Transactional
    public void updateUserWithSongs(UserWithSongsDTO user) {
        userRepository.save(userMapper.toEntityFromDTO(user));
    }

    public boolean isAdmin(UserDTO user) {
        return roleService.isAdmin(user.getRoles());
    }
}
