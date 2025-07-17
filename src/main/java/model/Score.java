package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
            return;
        }
        if (isTiebreak) {
            if (player == 1) {
                tiebreakPointsPlayer1++;
            } else {
                tiebreakPointsPlayer2++;
            }
            if (isTiebreakOver()) {
                winSetFromTiebreak();
            }
        }

        if (pointsPlayer1 == 40 && pointsPlayer2 == 40) {
            handleDeuce(player);
            return;
        }

        if (player == 1) {
            pointsPlayer1 = nextPoint(pointsPlayer1);
        } else {
            pointsPlayer2 = nextPoint(pointsPlayer2);
        }
        checkGameWin();
    }

    private void winSetFromTiebreak() {
        if (tiebreakPointsPlayer1 > tiebreakPointsPlayer2) {
            setsPlayer1++;
        } else {
            setsPlayer2++;
        }
        gamesPlayer1 = 0;
        gamesPlayer2 = 0;
        tiebreakPointsPlayer1 = 0;
        tiebreakPointsPlayer2 = 0;
        isTiebreak = false;
        currentSetNumber++;

        if (setsPlayer1 == maxSetsToWin || setsPlayer2 == maxSetsToWin) {
            matchOver = true;
        }
    }

    private boolean isTiebreakOver() {
        int diff = Math.abs(tiebreakPointsPlayer1 - tiebreakPointsPlayer2);
        return (tiebreakPointsPlayer1 >= 7 || tiebreakPointsPlayer2 >= 7) && diff >= 2;
    }

    private void handleDeuce(int player) {
        String current = player == 1 ? "P1" : "P2";
        String opponent = player == 1 ? "P2" : "P1";
        if (advantagePlayer == null) {
            advantagePlayer = current;
        } else if (advantagePlayer.equals(current)) {
            winGame(player);
            advantagePlayer = null;
        } else {
            advantagePlayer = null;
        }
    }

    private void checkGameWin() {
        if (pointsPlayer1 == 40 && pointsPlayer2 < 40) {
            winGame(1);
        } else if (pointsPlayer2 == 40 && pointsPlayer1 < 40) {
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
        resetPoints();

        if (gamesPlayer1 == 6 && gamesPlayer2 == 6) {
            isTiebreak = true;
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
        } else {
            setsPlayer2++;
        }
        gamesPlayer1 = 0;
        gamesPlayer2 = 0;
        currentSetNumber++;

        if (setsPlayer1 == maxSetsToWin || setsPlayer2 == maxSetsToWin) {
            matchOver = true;
        }
    }

    private void resetPoints() {
        pointsPlayer1 = 0;
        pointsPlayer2 = 0;
        advantagePlayer = null;
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
