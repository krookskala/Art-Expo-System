package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "themed_permanent_exhibitions")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ThemedPermanentExhibition extends PermanentExhibition {
    @NotBlank(message = "Theme Description Is Required For Themed Exhibitions.")
    @Column(name = "theme_description")
    private String themeDescription;
}
