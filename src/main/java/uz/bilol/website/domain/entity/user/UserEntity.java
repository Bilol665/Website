package uz.bilol.website.domain.entity.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.bilol.website.domain.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity extends BaseEntity implements UserDetails {
    private String username;
    private String firstname;
    private String lastName;
    private String email;
    private String password;
    private Date brithDate;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
    private List<RoleEntity> roles = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private UserState state;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach((role) -> authorities.add(new SimpleGrantedAuthority(role.getRole())));
        List<PermissionEntity> userPermissions = new ArrayList<>();
        roles.forEach((roleEntity -> userPermissions.addAll(roleEntity.getPermissions())));
        userPermissions.forEach((permissionEntity -> authorities.add(new SimpleGrantedAuthority(permissionEntity.getName()))));
        return authorities;
    }
}
