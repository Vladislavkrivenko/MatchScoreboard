package dao;

import lombok.Getter;
import model.Players;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PlayerRepository {

    @Getter
    private static final PlayerRepository instance = new PlayerRepository();
    private final Map<String, Players> players = new HashMap<>();
    private static int idCounter = 1;

    private PlayerRepository() {
    }

    public Players findOrCreate(String name) {
        return players.computeIfAbsent(name, n -> new Players(idCounter++, n));
    }

    public Collection<Players> findAll() {
        return players.values();
    }
}
