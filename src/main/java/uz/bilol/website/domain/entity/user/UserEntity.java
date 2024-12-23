package uz.bilol.website.domain.entity.user;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bilol.website.domain.entity.BaseEntity;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity {
    // NMA GAAAAPPPP!!!
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String password;
}
