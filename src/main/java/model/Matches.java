package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "matches")
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "firstPlayer", referencedColumnName = "id")
    private Players firstPlayer;

    @ManyToOne
    @JoinColumn(name = "secondPlayer", referencedColumnName = "id")
    private Players secondPlayer;

    @ManyToOne
    @JoinColumn(name = "winner", referencedColumnName = "id")
    private Players winner;
}
