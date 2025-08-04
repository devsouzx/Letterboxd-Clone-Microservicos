package com.devsouzx.Film.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "release_info")
public class ReleaseInfo {
    @Id
    @Column(name = "identifier", length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID identifier;

    @Column(name = "date")
    private Date date;

    @Column(name = "country")
    private String country;

    @Column(name = "type")
    private String type;

    @Column(name = "location")
    private String location;

    @Column(name = "classification")
    private String classification;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;
}
