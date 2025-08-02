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
@Table(name = "actor_role")
public class ActorRole {
    @Id
    @Column(name = "identifier", length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID identifier;
    private String actorName;
    private String characterName;
    private UUID movieID;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
