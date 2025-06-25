package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "permanent_exhibitions")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class PermanentExhibition extends Exhibition {
    // Attributes
    @NotBlank(message = "Electronic Catalogue Link Is Required For Permament Exhibitions.")
    @Column(name = "electronic_catalogue_link")
    private String electronicCatalogueLink;

    // Methods
    @Override
    public void closeExhibition() {
        System.out.println("Closing Permanent Exhibition '" + getTitle() + "'.");

        for (Usage usage : this.getSectionUsages()) {
            Section section = usage.getSection();
            if (section != null) {
                section.startRenovation();
            }
        }
        System.out.println("All Sections For Permanent Exhibition '" + getTitle() + "' Have Been Moved To Renovation Status.");
    }
}