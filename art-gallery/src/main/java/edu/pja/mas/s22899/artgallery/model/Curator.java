package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@SuperBuilder
public class Curator extends GalleryMember {
    // Attributes
    @PositiveOrZero(message = "Experience Must Be Zero Or Positive.")
    @Min(value = 0, message = "Years Of Experience Cannot Be Negative.")
    @Max(value = 100, message = "Years Of Experience Seems Too High, Max 100.")
    @Column(name = "experience_years")
    private int experienceYears;

    @Size(max = 100, message = "Specialization Must Be At Most 100 Characters.")
    private String specialization;

    // Associations

    // One-to-many relationship with Section
    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Section> managedSections = new HashSet<>();

    // Many-to-many relationship with TemporaryExhibition
    @ManyToMany(mappedBy = "externalCurators", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<TemporaryExhibition> temporaryExhibitionsServed = new HashSet<>();


    // Methods
    public void addManagedSection(Section section) {
        if (section != null && !this.managedSections.contains(section)) {
            this.managedSections.add(section);
            section.setManager(this);
        }
    }

    public void removeManagedSection(Section section) {
        if (section != null && this.managedSections.contains(section)) {
            this.managedSections.remove(section);
            if (section.getManager() == this) {
                section.setManager(null);
            }
        }
    }

    public void addTemporaryExhibitionServed(TemporaryExhibition exhibition) {
        if (exhibition != null && !this.temporaryExhibitionsServed.contains(exhibition)) {
            this.temporaryExhibitionsServed.add(exhibition);
            exhibition.getExternalCurators().add(this);
        }
    }

    public void removeTemporaryExhibitionServed(TemporaryExhibition exhibition) {
        if (exhibition != null && this.temporaryExhibitionsServed.contains(exhibition)) {
            this.temporaryExhibitionsServed.remove(exhibition);
            exhibition.getExternalCurators().remove(this);
        }
    }
}
