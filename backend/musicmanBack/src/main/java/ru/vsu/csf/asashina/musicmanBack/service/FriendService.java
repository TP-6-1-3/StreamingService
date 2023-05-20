package ru.vsu.csf.asashina.musicmanBack.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.vsu.csf.asashina.musicmanBack.mapper.UserMapper;
import ru.vsu.csf.asashina.musicmanBack.model.dto.FriendDTO;
import ru.vsu.csf.asashina.musicmanBack.model.entity.User;
import ru.vsu.csf.asashina.musicmanBack.repository.UserRepository;
import ru.vsu.csf.asashina.musicmanBack.utils.PageUtil;

@Service
@AllArgsConstructor
public class FriendService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PageUtil pageUtil;

    public Page<FriendDTO> getFriends(Long id, Integer pageNumber, Integer size, String nickname) {
        PageRequest pageRequest = pageUtil.createPageRequest(pageNumber, size);
        Page<User> pages = userRepository.findAllFriendsByNickname(id, nickname, pageRequest);
        pageUtil.checkPageOutOfRange(pages, pageNumber);
        return pages.map(userMapper::toFriendDTOFromEntity);
    }
}
