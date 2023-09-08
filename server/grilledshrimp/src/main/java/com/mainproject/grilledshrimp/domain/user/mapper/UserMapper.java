package com.mainproject.grilledshrimp.domain.user.mapper;

import com.mainproject.grilledshrimp.domain.user.dto.UserPostDto;
import com.mainproject.grilledshrimp.domain.user.dto.UserResponseDto;
import com.mainproject.grilledshrimp.domain.user.entity.Users;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users userPostDtoToUser(UserPostDto userPostDto);
    UserResponseDto userToUserResponseDto(Users users);
    List<UserResponseDto> usersToUserResponseDtos(List<Users> users);
}
