package dao;

import lombok.Getter;
import model.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerRepository {

    @Getter
    private static final PlayerRepository instance = new PlayerRepository();
    private final Map<String, Player> players = new HashMap<>();
    private static int idCounter = 1;

    private PlayerRepository() {
    }

//   // public Player findOrCreate(String name) {
//        return players.computeIfAbsent(name, n -> new Player(idCounter++, n));
//    }

    public Collection<Player> findAll() {
        return players.values();
    }
}
