package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityDoesNotExistException;
import ru.vsu.csf.asashina.musicmanBack.exception.PasswordsDoNotMatch;
import ru.vsu.csf.asashina.musicmanBack.exception.UserNotVerifiedException;
import ru.vsu.csf.asashina.musicmanBack.mapper.UserMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.CredentialsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.User;
import ru.vsu.csf.asashina.musicmanBack.model.request.UpdateProfileRequest;
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

    public UserDTO getUserByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(
                () -> new EntityDoesNotExistException("Пользователь с данным ником не существует")
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

    public boolean isAdmin(UserDTO user) {
        return roleService.isAdmin(user.getRoles());
    }

    @Transactional
    public CredentialsDTO getCredentials(UserDTO user) {
        return userMapper.toCredentialsDTOFromDTO(user);
    }

    @Transactional
    public void updateProfile(UserDTO user, UpdateProfileRequest request) {
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new EntityAlreadyExistsException("Пользователь с данным никнеймом уже существует");
        }
        User entity = userMapper.toEntityFromDTO(user);
        userMapper.updateEntity(request, entity);
        userRepository.save(entity);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.delete(userRepository.findById(userId).orElseThrow(
                () -> new EntityDoesNotExistException("Пользователь с данным ИД не существует")
        ));
    }
}
