package uz.bilol.website.domain.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.bilol.website.domain.entity.BaseEntity;

import java.util.List;

@Entity(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoleEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String role;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<PermissionEntity> permissions;
}
