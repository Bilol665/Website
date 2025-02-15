package uz.bilol.website.domain.entity.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;
import uz.bilol.website.domain.entity.BaseEntity;

@Entity(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ImageEntity extends BaseEntity {
    private String name;
//    @Lob
    @Column(name = "data",columnDefinition = "BYTEA")
    private byte[] data;
}
