package battleship;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa o resultado de um jogo para o Scoreboard.
 */

public class GameScore {
    private String playerName;
    private String date;
    private int totalShots;
    private int hits;
    private int sunkShips;
    private boolean victory;



    public GameScore() {
    }

    /**
     * Construtor com parâmetros para registar um novo jogo.
     */
    public GameScore(String playerName, int totalShots, int hits, int sunkShips, boolean victory) {
        this.playerName = playerName;
        this.totalShots = totalShots;
        this.hits = hits;
        this.sunkShips = sunkShips;
        this.victory = victory;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    // Getters e Setters necessários para o Jackson ler/escrever JSON
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalShots() {
        return totalShots;
    }

    public void setTotalShots(int totalShots) {
        this.totalShots = totalShots;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getSunkShips() {
        return sunkShips;
    }

    public void setSunkShips(int sunkShips) {
        this.sunkShips = sunkShips;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    @Override
    public String toString() {
        String resultado = victory ? "Vitória" : "Derrota";
        return String.format("[%s] %s | %s | Tiros: %d | Acertos: %d | Afundados: %d",
                date, playerName, resultado, totalShots, hits, sunkShips);
    }
}


