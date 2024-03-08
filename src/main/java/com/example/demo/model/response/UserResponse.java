package com.example.demo.model.response;

import com.example.demo.model.common.CommonConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

}
