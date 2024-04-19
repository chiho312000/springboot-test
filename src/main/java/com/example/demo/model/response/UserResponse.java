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
@SuperBuilder
public class UserResponse {


    @NotEmpty
    private String name;

    @NotNull
    private CommonConstant.GENDER gender;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Mask
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String password;

}
