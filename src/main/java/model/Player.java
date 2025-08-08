package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString(exclude = {"matchesFirstPlayer", "matchesSecondPlayer", "matches"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Player")
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
