package ru.vsu.csf.asashina.musicmanBack.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.asashina.musicmanBack.exception.*;
import ru.vsu.csf.asashina.musicmanBack.mapper.UserMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.CredentialsDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.User;
import ru.vsu.csf.asashina.musicmanBack.model.request.UpdateProfileRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.UploadPhotoProfileRequest;
import ru.vsu.csf.asashina.musicmanBack.model.request.UserSignUpRequest;
import ru.vsu.csf.asashina.musicmanBack.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleService roleService;

    @Value("${users.directory}")
    private String usersDirectoryPath;

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

    @Transactional
    public File getFileFromSystem(String nickname) {
        UserDTO user = getUserByNickname(nickname);
        var photo = new File(usersDirectoryPath.concat("/").concat(Long.toString(user.getUserId())));
        if (!photo.exists()) {
            throw new EntityDoesNotExistException("Фотографии пользователя не существует");
        }
        return photo;
    }

    @Transactional
    public void uploadProfilePicture(UserDTO user, UploadPhotoProfileRequest request) throws IOException {
        validateUsersPhotoFile(request.getFile());
        String filePath = usersDirectoryPath.concat("/").concat(Long.toString(user.getUserId()));
        File directory = new File(filePath);
        request.getFile().transferTo(directory);
    }

    private void validateUsersPhotoFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileException("Вы не можете загрузить пустой файл");
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).contains(".png")
                && !Objects.requireNonNull(file.getOriginalFilename()).contains(".jpeg")
                && !Objects.requireNonNull(file.getOriginalFilename()).contains(".jpg")) {
            throw new FileException("Тип файла должен быть .png, или .jpeg, или .jpg");
        }
    }

    @PostConstruct
    private void checkIsPathValid() {
        try {
            Paths.get(usersDirectoryPath);
        } catch (InvalidPathException e) {
            log.error("Произошла ошибка при чтении директории с пользовательскими фотографиями: {}", e.getMessage(), e);
        }
    }
}
