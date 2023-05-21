package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.exception.NoFriendException;
import ru.vsu.csf.asashina.musicmanBack.mapper.UserMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.FriendDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.user.UserDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.User;
import ru.vsu.csf.asashina.musicmanBack.repository.UserRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;

import java.util.Set;

@Service
@AllArgsConstructor
public class FriendService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserService userService;

    private final PageUtil pageUtil;

    @Transactional
    public Page<FriendDTO> getFriends(Long id, Integer pageNumber, Integer size, String nickname) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size);
        Page<User> pages = userRepository.findAllFriendsByNickname(id, nickname, pageRequest);
        pageUtil.checkPageOutOfRange(pages, pageNumber);
        return pages.map(userMapper::toFriendDTOFromEntity);
    }

    @Transactional
    public void addFriend(UserDTO userDTO, String nickname) {
        User friend = userMapper.toEntityFromDTO(userService.getUserByNickname(nickname));
        if (userRepository.isFriend(userDTO.getUserId(), friend.getUserId())) {
            throw new EntityAlreadyExistsException("Пользователь уже добавлен в друзья");
        }
        userRepository.addFriend(userDTO.getUserId(), friend.getUserId());
    }

    private boolean hasFriend(Set<User> friends, Long friendId) {
        return !CollectionUtils.isEmpty(friends)
                && friends.stream().anyMatch(friend -> friend.getUserId().equals(friendId));
    }

    @Transactional
    public void deleteFriend(UserDTO userDTO, String nickname) {
        User friend = userMapper.toEntityFromDTO(userService.getUserByNickname(nickname));
        if (!userRepository.isFriend(userDTO.getUserId(), friend.getUserId())) {
            throw new NoFriendException("Пользователя не было в друзьях");
        }
        userRepository.deleteFriend(userDTO.getUserId(), friend.getUserId());
    }

    public boolean hasFriend(UserDTO userDTO, UserDTO friend) {
        return userRepository.isFriend(userDTO.getUserId(), friend.getUserId());
    }
}
