package com.example.demo.model.response;

import com.example.demo.annotation.Mask;
import com.example.demo.model.common.CommonConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserResponse {


    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotNull(message = "Gender must not be empty")
    private CommonConstant.GENDER gender;

    @Email(message = "Email invalid format")
    @NotEmpty(message = "Email must bot be empty")
    private String email;

    @NotEmpty(message = "Password must not be empty")
    @Mask
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must have valid format")
    private String password;

}
