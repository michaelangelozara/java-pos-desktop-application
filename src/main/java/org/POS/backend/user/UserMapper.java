package org.POS.backend.user;

import org.POS.backend.code_generator.CodeGeneratorService;
import org.POS.backend.global_variable.GlobalVariable;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;

public class UserMapper {

    private CodeGeneratorService codeGeneratorService;

    public UserMapper(){
        this.codeGeneratorService = new CodeGeneratorService();
    }

    public User toUser(AddUserRequestDto dto){
        User user = new User();
        user.setName(dto.name());
        user.setEmployeeId(this.codeGeneratorService.generateProductCode(GlobalVariable.USER_PREFIX));
        user.setContactNumber(dto.contactNumber());
        user.setRole(dto.role());
        user.setUsername(dto.username());
        user.setPassword(BCrypt.hashpw(dto.password(), BCrypt.gensalt()));
        user.setEmail(dto.email());
        user.setStatus(dto.status());
        user.setCreatedAt(LocalDate.now());
        return user;
    }

    public User toUpdatedUser(User user, UpdateUserRequestDto dto){
        user.setName(dto.name());
        user.setRole(dto.role());
        if(!dto.password().isEmpty()){
            String encryptedPassword = BCrypt.hashpw(dto.password(), BCrypt.gensalt(10));
            user.setPassword(encryptedPassword);
        }
        user.setEmail(dto.email());
        user.setContactNumber(dto.contactNumber());
        user.setStatus(dto.status());

        return user;
    }

    public UserResponseDto toUserResponseDto(User user){
        return new UserResponseDto(
                user.getId(),
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
