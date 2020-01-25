package com.bohdan.service.impl;

import com.bohdan.UserRepository;
import com.bohdan.io.entity.UserEntity;
import com.bohdan.service.UserService;
import com.bohdan.shared.Utils;
import com.bohdan.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Override
    public UserDto createUser(UserDto user) {

        UserEntity storedUserDetails = userRepository.findByEmail(user.getEmail());
        if  (storedUserDetails != null) {
            throw new RuntimeException("Record already exist");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setEncryptedPassword("test");
        String publicUserId = utils.generateUseerId(30);
        userEntity.setUserId(publicUserId);

        storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);
        return returnValue;
    }
}
