package battleship;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Gestor do Scoreboard que persiste e carrega o histórico de jogos em formato JSON com Jackson.
 */

public class Scoreboard {

    private static final String FILE_PATH = "scoreboard.json";
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Carrega a lista de resultados a partir do ficheiro JSON.
     */
    public static List<GameScore> loadScores() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(file, new TypeReference<List<GameScore>>() {});
        } catch (IOException e) {
            System.err.println("Erro ao ler o ficheiro de scoreboard: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Guarda um novo resultado de jogo no ficheiro JSON.
     */
    public static void saveScore(GameScore newScore) {
        List<GameScore> scores = loadScores();
        scores.add(newScore);

        try {
            mapper.writeValue(new File(FILE_PATH), scores);
            System.out.println("-> Resultado guardado com sucesso no Scoreboard (scoreboard.json)!");
        } catch (IOException e) {
            System.err.println("Erro ao gravar no scoreboard: " + e.getMessage());
        }
    }

    /**
     * Apresenta na consola o histórico e o ranking dos melhores jogos.
     */
    public static void displayScoreboard() {
        List<GameScore> scores = loadScores();

        System.out.println("\n=========================== SCOREBOARD ===========================");
        if (scores.isEmpty()) {
            System.out.println("Ainda não existem jogos registados. Jogue uma partida primeiro!");
            System.out.println("==================================================================\n");
            return;
        }

        // Ordenar por vitórias primeiro e, depois, por menor número de tiros (melhor eficácia)
        scores.sort(Comparator
                .comparing(GameScore::isVictory).reversed()
                .thenComparing(GameScore::getTotalShots));

        System.out.printf("%-4s | %-19s | %-12s | %-9s | %-6s | %-7s | %-9s%n",
                "Pos", "Data/Hora", "Jogador", "Estado", "Tiros", "Acertos", "Afundados");
        System.out.println("------------------------------------------------------------------");

        int pos = 1;
        for (GameScore score : scores) {
            String estado = score.isVictory() ? "Vitória" : "Derrota";
            System.out.printf("%-4d | %-19s | %-12s | %-9s | %-6d | %-7d | %-9d%n",
                    pos++,
                    score.getDate(),
                    score.getPlayerName(),
                    estado,
                    score.getTotalShots(),
                    score.getHits(),
                    score.getSunkShips());
        }
        System.out.println("==================================================================\n");
    }
}
