package com.devsouzx.Film.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "crew_members")
public class CrewMember {
    @Id
    @Column(name = "identifier", length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID identifier;
    private String name;
    private String role;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
