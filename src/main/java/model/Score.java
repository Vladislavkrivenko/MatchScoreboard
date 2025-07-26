package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private String advantagePlayer = null;
    private int setsPlayer1 = 0;
    private int setsPlayer2 = 0;

    private int gamesPlayer1 = 0;
    private int gamesPlayer2 = 0;

    private int pointsPlayer1 = 0;
    private int pointsPlayer2 = 0;

    private int currentSetNumber = 1;
    private int maxSetsToWin = 2;

    private int tiebreakPointsPlayer1 = 0;
    private int tiebreakPointsPlayer2 = 0;

    private boolean isTiebreak = false;
    private boolean matchOver = false;

    public void pointWonBy(int player) {
        if (isGameOver()) {
            log.info("Point ignored: Match is already over");
            return;
        }
        log.info("Point won by Player {}", player);

        if (isTiebreak) {
            if (player == 1) {
                tiebreakPointsPlayer1++;
                log.info("Tiebreak point: Player1 now has {}", tiebreakPointsPlayer1);
            } else {
                tiebreakPointsPlayer2++;
                log.info("Tiebreak point: Player2 now has {}", tiebreakPointsPlayer2);
            }
            if (isTiebreakOver()) {
                log.info("Tiebreak finished");
                winSetFromTiebreak();
            }
            return;
        }

        if (pointsPlayer1 == 40 && pointsPlayer2 == 40) {
            log.info("Deuce");
            handleDeuce(player);
            return;
        }

        if (player == 1) {
            pointsPlayer1 = nextPoint(pointsPlayer1);
            log.info("Player1 scored. Points: {}", pointsPlayer1);
        } else {
            pointsPlayer2 = nextPoint(pointsPlayer2);
            log.info("Player2 scored Points: {}", pointsPlayer2);
        }
        checkGameWin();
    }

    private void winSetFromTiebreak() {
        if (tiebreakPointsPlayer1 > tiebreakPointsPlayer2) {
            setsPlayer1++;
            log.info("Player1 won the tiebreak and the set. Sets now: {}–{}", setsPlayer1, setsPlayer2);
        } else {
            setsPlayer2++;
            log.info("Player2 won the tiebreak and the set. Sets now: {}–{}", setsPlayer1, setsPlayer2);
        }
        resetGamesAndTiebreak();
        currentSetNumber++;

        if (setsPlayer1 == maxSetsToWin || setsPlayer2 == maxSetsToWin) {
            matchOver = true;
            log.info("Match over! Winner: {}", setsPlayer1 > setsPlayer2 ? 1 : 2);
        }
    }

    private void resetGamesAndTiebreak() {
        gamesPlayer1 = 0;
        gamesPlayer2 = 0;
        tiebreakPointsPlayer1 = 0;
        tiebreakPointsPlayer2 = 0;
        isTiebreak = false;
    }

    private boolean isTiebreakOver() {
        int diff = Math.abs(tiebreakPointsPlayer1 - tiebreakPointsPlayer2);
        return (tiebreakPointsPlayer1 >= 7 || tiebreakPointsPlayer2 >= 7) && diff >= 2;
    }

    private void handleDeuce(int player) {
        String current = player == 1 ? "Player1" : "Player2";
        if (advantagePlayer == null) {
            advantagePlayer = current;
            log.info("{} gets Advantage", current);
        } else if (advantagePlayer.equals(current)) {
            log.info("{} wins game from Advantage", current);
            winGame(player);
            advantagePlayer = null;
        } else {
            log.info("Advantage lost. Back to Deuce");
            advantagePlayer = null;
        }
    }

    private void checkGameWin() {
        if (pointsPlayer1 == 40 && pointsPlayer2 < 40) {
            winGame(1);
            log.info("Game Win Player for Player1");
        } else if (pointsPlayer2 == 40 && pointsPlayer1 < 40) {
            log.info("Game Win Player for Player2");
            winGame(2);
        }
    }

    private void winGame(int player) {

        if (isTiebreak) {
            return;
        }
        if (player == 1) {
            gamesPlayer1++;
        } else {
            gamesPlayer2++;
        }
        log.info("Player{} wins the game. Games: {}–{}", player, gamesPlayer1, gamesPlayer2);

        resetPoints();

        if (gamesPlayer1 == 6 && gamesPlayer2 == 6) {
            isTiebreak = true;
            log.info("Tiebreak started");
        } else if (isSetWon()) {
            winSet();
        }
    }

    private boolean isSetWon() {
        int diff = Math.abs(gamesPlayer1 - gamesPlayer2);
        return (gamesPlayer1 >= 6 || gamesPlayer2 >= 6) && diff >= 2;
    }

    private void winSet() {
        if (gamesPlayer1 > gamesPlayer2) {
            setsPlayer1++;
            log.info("Player1 wins the set. Sets: {}–{}", setsPlayer1, setsPlayer2);
        } else {
            setsPlayer2++;
            log.info("Player2 wins the set. Sets: {}–{}", setsPlayer1, setsPlayer2);
        }
        gamesPlayer1 = 0;
        gamesPlayer2 = 0;
        currentSetNumber++;

        if (setsPlayer1 == maxSetsToWin || setsPlayer2 == maxSetsToWin) {
            matchOver = true;
            log.info("Match over! Final score — Player1: {} sets, Player2: {} sets", setsPlayer1, setsPlayer2);
        }
    }

    private void resetPoints() {
        pointsPlayer1 = 0;
        pointsPlayer2 = 0;
        advantagePlayer = null;
        log.debug("Points reset. New game starts.");
    }

    private int nextPoint(int current) {
        return switch (current) {
            case 0 -> 15;
            case 15 -> 30;
            case 30 -> 40;
            default -> current;
        };
    }

    private boolean isGameOver() {
        return matchOver;
    }
}
