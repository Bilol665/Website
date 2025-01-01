package uz.bilol.website.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bilol.website.domain.entity.user.RoleEntity;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {

    RoleEntity findByName(String name);

}
