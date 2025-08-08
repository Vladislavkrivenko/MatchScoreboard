package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "firstPlayer")
    private List<Matches> matchesFirstPlayer;

    @OneToMany(mappedBy = "secondPlayer")
    private List<Matches> matchesSecondPlayer;

    @OneToMany(mappedBy = "winner")
    private Collection<Matches> matches;

}
