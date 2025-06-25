package edu.pja.mas.s22899.artgallery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Section {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Section Name Is Required.")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "section_identifier", unique = true)
    @NotBlank(message = "Section Identifier Is Required.")
    private String sectionIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Section Status Must Be Specified.")
    private SectionStatus status;

    @Column(name = "end_of_renovation_date")
    private LocalDate endOfRenovation;

    //Associations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curator_manager_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Curator manager;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_id", nullable = false)
    @NotNull(message = "Section Must Belong To A Gallery.")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Gallery gallery;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Usage> usageRecords = new HashSet<>();

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<DailyVisitorLog> visitorLogs = new HashSet<>();


    // Methods For State Management
    public void assignExhibition(Exhibition exhibition) {
        if (this.status != SectionStatus.FREE) {
            throw new IllegalStateException("Error: Section " + name + " Is Not FREE And Cannot Be Assigned. Current Status: " + this.status);
        }
        if (exhibition == null) {
            throw new IllegalArgumentException("Exhibition Cannot Be Null.");
        }
        this.status = SectionStatus.USED;
        Usage usage = Usage.builder()
                .section(this)
                .exhibition(exhibition)
                .dateFrom(exhibition.getStartDate())
                .dateTo(exhibition.getEndDate())
                .build();
        this.addUsageRecord(usage);
        exhibition.addSectionUsage(usage);

        System.out.println("Section '" + name + "' Assigned To Exhibition '" + exhibition.getTitle() + "' And Status Set To USED.");
    }

    public void startRenovation() {
        if (this.status != SectionStatus.USED && this.status != SectionStatus.FREE) {
            throw new IllegalStateException("Section '" + name + "' Cannot Start Renovation From Its Current State: " + this.status);
        }
        this.status = SectionStatus.IN_RENOVATION;
        this.endOfRenovation = null;
    }

    public void makeSectionAvailable() {
        if (this.status != SectionStatus.USED) {
            throw new IllegalStateException("Section '" + name + "' Cannot Be Made Available Directly From State: " + this.status);
        }
        this.status = SectionStatus.FREE;
    }

    public void completeRenovation(LocalDate newEndOfRenovation) {
        if (this.status != SectionStatus.IN_RENOVATION) {
            throw new IllegalStateException("Section '" + name + "' Cannot Complete Renovation As It Is Not In Renovation. Current State: " + this.status);
        }
        this.status = SectionStatus.FREE;
        this.endOfRenovation = newEndOfRenovation;
    }

    public void completeRenovation() {
        completeRenovation(null);
    }

    public void decommissionSection() {
        this.status = SectionStatus.DECOMMISSIONED;
        System.out.println("Section " + name + " Has Been DECOMMISSIONED.");
    }

    public void exhibitionPeriodEnds() {
        if (this.status != SectionStatus.USED) {
            throw new IllegalStateException("Cannot End Exhibition Period For Section '" + this.name +
                    "' Because It Is Not Currently In Use. Status: " + this.status);
        }
        this.status = SectionStatus.FREE;
        System.out.println("Exhibition period ended for Section: '" + name + "'. Status is now FREE.");
    }

    // Methods For Managing Associations

    public void addUsageRecord(Usage usage) {
        if (usage != null && !this.usageRecords.contains(usage)) {
            this.usageRecords.add(usage);
            usage.setSection(this);
        }
    }

    public void removeUsageRecord(Usage usage) {
        if (usage != null && this.usageRecords.remove(usage)) {
            usage.setSection(null);
        }
    }

    public void addVisitorLog(DailyVisitorLog log) {
        if (log != null && !this.visitorLogs.contains(log)) {
            this.visitorLogs.add(log);
            log.setSection(this);
        }
    }

    public void removeVisitorLog(DailyVisitorLog log) {
        if (log != null && this.visitorLogs.remove(log)) {
            log.setSection(null);
        }
    }

    // Derived Attribute Method
    public int getNumberOfVisitors() {
        LocalDate today = LocalDate.now();
        return this.visitorLogs.stream()
                .filter(log -> log.getDate().equals(today))
                .mapToInt(DailyVisitorLog::getVisitorCount)
                .findFirst()
                .orElse(0);
    }
}