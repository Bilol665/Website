package uz.bilol.website.domain.entity.user;

import jakarta.persistence.Entity;
import lombok.*;
import uz.bilol.website.domain.entity.BaseEntity;

@Entity(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PermissionEntity extends BaseEntity {
    private String name;
}
