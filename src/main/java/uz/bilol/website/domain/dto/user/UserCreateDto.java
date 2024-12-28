package uz.bilol.website.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateDto {
    @NotBlank(message = "Username cannot be blank!")
    private String username;
    @NotBlank(message = "Firstname cannot be blank!")
    private String firstname;
    private String lastName;
    private String email;
    @NotBlank(message = "Password cannot be blank!")
    private String password;
    @NotNull(message = "Date cannot be null!")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Birth date must be in the format yyyy-MM-dd"
    )
    @JsonProperty("birth-date")
    private String brithDate;
}
