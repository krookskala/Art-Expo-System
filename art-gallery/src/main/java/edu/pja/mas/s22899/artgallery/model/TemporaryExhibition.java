package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "temporary_exhibitions")
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class TemporaryExhibition extends Exhibition {
    // Attributes
    public static final int MAX_DURATION_IN_MONTHS = 2;

    // Association
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "exhibition_external_curators",
            joinColumns = @JoinColumn(name = "temporary_exhibition_id"),
            inverseJoinColumns = @JoinColumn(name = "curator_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Curator> externalCurators = new HashSet<>();

    // Methods
    public void addExternalCurator(Curator curator) {
        if (curator != null && !this.externalCurators.contains(curator)) {
            this.externalCurators.add(curator);
            curator.getTemporaryExhibitionsServed().add(this);
        }
    }

    public void removeExternalCurator(Curator curator) {
        if (curator != null && this.externalCurators.remove(curator)) {
            curator.getTemporaryExhibitionsServed().remove(this);
        }
    }

    @Override
    public void closeExhibition() {
        System.out.println("Closing Temporary Exhibition '" + getTitle() + "'.");
        for (Usage usage : this.getSectionUsages()) {
            Section section = usage.getSection();
            if (section != null) {
                section.makeSectionAvailable();
            }
        }
        System.out.println("All Sections For Temporary Exhibition '" + getTitle() + "' Have Been Marked As Free.");
    }
}
