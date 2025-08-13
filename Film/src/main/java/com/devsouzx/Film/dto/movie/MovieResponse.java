package com.devsouzx.Film.dto.movie;

import com.devsouzx.Film.database.model.ActorRole;
import com.devsouzx.Film.database.model.CrewMember;
import com.devsouzx.Film.database.model.ReleaseInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {
    private UUID identifier;
    private String title;
    private String slug;
    private String releaseYear;
    private String synopsis;
    private String tagline;
    private Integer runtime;

    private String country;
    private String primaryLanguage;
    private String trailerUrl;
    private String posterUrl;
    private String backdropUrl;

    private Integer tmdbID;

    private List<String> spokenLanguages;
    private List<String> alternativeTitles;
    private List<String> studios;
    private List<String> genres;
    private List<String> themes;
    private List<String> whereToWatch;

    private List<ActorRole> cast;
    private List<CrewMember> crew;
    private List<ReleaseInfo> releases;
}
