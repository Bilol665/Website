package uz.bilol.website.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bilol.website.domain.converter.EmailOrUsername;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@EmailOrUsername
public class LoginDto {
    private String email;
    @NotBlank(message = "Password cannot be blank!")
    private String password;
    private String username;
}
