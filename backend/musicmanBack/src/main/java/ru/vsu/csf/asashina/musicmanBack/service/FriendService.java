package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.exception.EntityAlreadyExistsException;
import ru.vsu.csf.asashina.musicmanBack.mapper.UserMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.FriendDTO;
import ru.vsu.csf.asashina.musicmanBack.model.dto.UserDTO;
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

    public Page<FriendDTO> getFriends(Long id, Integer pageNumber, Integer size, String nickname) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size);
        Page<User> pages = userRepository.findAllFriendsByNickname(id, nickname, pageRequest);
        pageUtil.checkPageOutOfRange(pages, pageNumber);
        return pages.map(userMapper::toFriendDTOFromEntity);
    }

    public void addFriend(UserDTO userDTO, String nickname) {
        User user = userMapper.toEntityFromDTO(userDTO);
        User friend = userMapper.toEntityFromDTO(userService.getUserByNickname(nickname));
        if (hasFriend(user.getFriends(), friend.getUserId())) {
            throw new EntityAlreadyExistsException("Пользователь уже добавлен в друзья");
        }
        user.addFriend(friend);
        userRepository.save(user);
    }

    private boolean hasFriend(Set<User> friends, Long friendId) {
        return friends.stream().anyMatch(friend -> friend.getUserId().equals(friendId));
    }
}
