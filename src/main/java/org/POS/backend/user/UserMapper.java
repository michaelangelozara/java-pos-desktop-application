package org.POS.backend.user;

import java.time.LocalDate;
import java.util.List;

public class UserMapper {

    public User toUser(AddUserRequestDto dto){
        User user = new User();
        user.setName(dto.name());
        user.setEmployeeId(dto.userId());
        user.setContactNumber(dto.contactNumber());
        user.setRole(dto.role());
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        user.setStatus(dto.status());
        user.setCreatedAt(LocalDate.now());
        return user;
    }

    public User toUpdatedUser(UpdateUserRequestDto dto){
        User user = new User();
        user.setId(dto.userId());
        user.setName(dto.name());
//        user.setDesignation(dto.designation());
//        user.setEmployeeId(dto.employeeId());
//        user.setContactNumber(dto.contactNumber());
//        user.setSalary(dto.salary());
//        user.setCommission(dto.commission());
//        user.setBirthDate(dto.birthDate());
//        user.setGender(dto.gender());
//        user.setBloodGroup(dto.bloodGroup());
//        user.setReligion(dto.religion());
//        user.setAppointmentDate(dto.appointmentDate());
//        user.setJoinDate(dto.joinDate());
//        user.setAddress(dto.address());
//        user.setStatus(dto.status());
//        user.setProfilePicture(dto.profilePicture());
//        user.setRole(dto.role());
//        if(dto.isAccountAllowed()){
//            user.setUsername(dto.username());
//            user.setPassword(dto.password());
//        }
        return user;
    }

    public UserResponseDto toUserResponseDto(User user){
        return new UserResponseDto(
                user.getName(),
                user.getEmployeeId(),
                user.getContactNumber(),
                user.getStatus(),
                user.getUsername(),
                user.getRole(),
                user.getEmail()
        );
    }

    public List<UserResponseDto> userResponseDtoList(List<User> users){
        return users
                .stream()
                .map(this::toUserResponseDto)
                .toList();
    }
}
