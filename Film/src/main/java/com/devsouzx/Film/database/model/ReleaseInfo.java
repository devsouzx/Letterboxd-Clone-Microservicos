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
    private Date date;
    private String country;
    private String type;
    private String location;
    private String classification;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
