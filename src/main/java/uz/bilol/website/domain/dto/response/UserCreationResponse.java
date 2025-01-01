package uz.bilol.website.domain.dto.response;

import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserCreationResponse {
    private String username;
    private String firstname;
    private String lastName;
    private String email;
    private Date brithDate;
}
