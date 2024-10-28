package org.POS.backend.user;

import org.POS.backend.department.Department;

import java.util.List;

public class UserMapper {

    public User toUser(AddUserRequestDto dto, Department department){
        User user = new User();
        user.setName(dto.name());
        user.setDesignation(dto.designation());
        user.setEmployeeId(dto.employeeId());
        user.setContactNumber(dto.contactNumber());
        user.setSalary(dto.salary());
        user.setCommission(dto.commission());
        user.setBirthDate(dto.birthDate());
        user.setGender(dto.gender());
        user.setBloodGroup(dto.bloodGroup());
        user.setReligion(dto.religion());
        user.setAppointmentDate(dto.appointmentDate());
        user.setJoinDate(dto.joinDate());
        user.setAddress(dto.address());
        user.setStatus(dto.status());
        user.setProfilePicture(dto.profilePicture());
        user.setRole(dto.role());
        user.setDepartment(department);
        if(dto.isAccountAllowed()){
            user.setUsername(dto.username());
            user.setPassword(dto.password());
        }

        return user;
    }

    public User toUpdatedUser(UpdateUserRequestDto dto, Department department){
        User user = new User();
        user.setId(dto.userId());
        user.setName(dto.name());
        user.setDesignation(dto.designation());
        user.setEmployeeId(dto.employeeId());
        user.setContactNumber(dto.contactNumber());
        user.setSalary(dto.salary());
        user.setCommission(dto.commission());
        user.setBirthDate(dto.birthDate());
        user.setGender(dto.gender());
        user.setBloodGroup(dto.bloodGroup());
        user.setReligion(dto.religion());
        user.setAppointmentDate(dto.appointmentDate());
        user.setJoinDate(dto.joinDate());
        user.setAddress(dto.address());
        user.setStatus(dto.status());
        user.setProfilePicture(dto.profilePicture());
        user.setRole(dto.role());
        user.setDepartment(department);
        if(dto.isAccountAllowed()){
            user.setUsername(dto.username());
            user.setPassword(dto.password());
        }

        return user;
    }

    public UserResponseDto toUserResponseDto(User user){
        return new UserResponseDto(
                user.getName(),
                user.getDesignation(),
                user.getEmployeeId(),
                user.getContactNumber(),
                user.getSalary(),
                user.getCommission(),
                user.getBirthDate(),
                user.getGender(),
                user.getBloodGroup(),
                user.getReligion(),
                user.getAppointmentDate(),
                user.getJoinDate(),
                user.getAddress(),
                user.getStatus(),
                user.getProfilePicture(),
                user.getUsername(),
                user.getRole()
        );
    }

    public List<UserResponseDto> userResponseDtoList(List<User> users){
        return users
                .stream()
                .map(this::toUserResponseDto)
                .toList();
    }
}
