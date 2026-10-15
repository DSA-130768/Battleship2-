package battleship;

import java.util.Scanner;
import java.util.Locale;
import java.util.ResourceBundle;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * The type Tasks.
 */
public class Tasks {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final List<Long> moveTimes = new ArrayList<>();
    private static long totalTime = 0;
    private static int totalMoves = 0;

    private static ResourceBundle messages =
            ResourceBundle.getBundle("Messages", Locale.forLanguageTag("pt"));

    private static final String GOODBYE_MESSAGE = "menu.goodbye";

    private static final String AJUDA = "ajuda";
    private static final String GERAFROTA = "gerafrota";
    private static final String LEFROTA = "lefrota";
    private static final String DESISTIR = "desisto";
    private static final String RAJADA = "rajada";
    private static final String TIROS = "tiros";
    private static final String MAPA = "mapa";
    private static final String STATUS = "estado";
    private static final String SIMULA = "simula";
    private static final String SCOREBOARD = "scoreboard";
    private static final String IDIOMA = "idioma";

    /**
     * Main game menu.
     */
    public static void menu() {

        IFleet myFleet = null;
        IGame game = null;

        Scanner in = new Scanner(System.in);

        menuHelp();

        System.out.print("> ");
        String command = in.next();

        while (!command.equalsIgnoreCase(DESISTIR)) {

            switch (command.toLowerCase(Locale.ROOT)) {

                case GERAFROTA:
                    myFleet = Fleet.createRandom();
                    game = new Game(myFleet);
                    game.printMyBoard(false, true);
                    break;

                case LEFROTA:
                    myFleet = buildFleet(in);
                    game = new Game(myFleet);
                    game.printMyBoard(false, true);
                    break;

                case STATUS:
                    if (myFleet != null) {
                        myFleet.printStatus();
                    }
                    break;

                case MAPA:
                    if (game != null) {
                        game.printMyBoard(false, true);
                    }
                    break;

                case RAJADA:
                    if (game != null) {

                        Instant startTime = Instant.now();

                        game.readEnemyFire(in);

                        Instant endTime = Instant.now();
                        long moveTime =
                                Duration.between(startTime, endTime).toSeconds();

                        moveTimes.add(moveTime);
                        totalTime += moveTime;
                        totalMoves++;

                        System.out.println(
                                "Tempo da jogada: " + moveTime + " segundos");

                        myFleet.printStatus();
                        game.printMyBoard(true, false);

                        if (game.getRemainingShips() == 0) {
                            printTimerStatistics();
                            game.over();

                            int totalShots = game.getHits()
                                    + game.getInvalidShots()
                                    + game.getRepeatedShots();

                            Scoreboard.saveScore(
                                    new GameScore(
                                            "Jogador",
                                            totalShots,
                                            game.getHits(),
                                            game.getSunkShips(),
                                            true
                                    )
                            );

                            Scoreboard.displayScoreboard();
                            System.exit(0);
                        }
                    }
                    break;

                case SIMULA:
                    if (game != null) {

                        while (game.getRemainingShips() > 0) {
                            game.randomEnemyFire();
                            myFleet.printStatus();
                            game.printMyBoard(true, false);

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                break;
                            }
                        }

                        if (game.getRemainingShips() == 0) {
                            printTimerStatistics();
                            game.over();

                            int totalShots = game.getHits()
                                    + game.getInvalidShots()
                                    + game.getRepeatedShots();

                            Scoreboard.saveScore(
                                    new GameScore(
                                            "Jogador",
                                            totalShots,
                                            game.getHits(),
                                            game.getSunkShips(),
                                            true
                                    )
                            );

                            Scoreboard.displayScoreboard();
                            System.exit(0);
                        }
                    }
                    break;

                case TIROS:
                    if (game != null) {
                        game.printMyBoard(true, true);
                    }
                    break;

                case SCOREBOARD:
                    Scoreboard.displayScoreboard();
                    break;

                case AJUDA:
                    menuHelp();
                    break;

                case IDIOMA:
                    changeLanguage(in);
                    menuHelp();
                    break;

                default:
                    System.out.println(messages.getString("menu.unknown"));
            }

            System.out.print("> ");
            command = in.next();
        }

        System.out.println(messages.getString(GOODBYE_MESSAGE));
    }

    /**
     * Changes the current language.
     */
    private static void changeLanguage(Scanner in) {

        System.out.println("Choose language / Escolha o idioma:");
        System.out.println("pt - Português");
        System.out.println("en - English");
        System.out.print("> ");

        String language = in.next().toLowerCase(Locale.ROOT);

        if (language.equals("pt") || language.equals("en")) {
            setLanguage(language);
            System.out.println(messages.getString("language.changed"));
        } else {
            System.out.println(messages.getString("language.invalid"));
        }
    }

    /**
     * Loads messages for the selected language.
     */
    private static void setLanguage(String language) {
        Locale locale = Locale.forLanguageTag(language);
        messages = ResourceBundle.getBundle("Messages", locale);
    }

    /**
     * Displays menu help.
     */
    public static void menuHelp() {
        System.out.println("======================= "
                + messages.getString("menu.title")
                + " =========================");

        System.out.println(messages.getString("menu.instructions"));
        System.out.println("- " + GERAFROTA + ": "
                + messages.getString("menu.fleet"));
        System.out.println("- " + LEFROTA + ": "
                + messages.getString("menu.loadfleet"));
        System.out.println("- " + STATUS + ": "
                + messages.getString("menu.status"));
        System.out.println("- " + MAPA + ": "
                + messages.getString("menu.map"));
        System.out.println("- " + RAJADA + ": "
                + messages.getString("menu.fire"));
        System.out.println("- " + SIMULA + ": "
                + messages.getString("menu.simulate"));
        System.out.println("- " + TIROS + ": "
                + messages.getString("menu.shots"));
        System.out.println("- " + SCOREBOARD + ": "
                + messages.getString("menu.scoreboard"));
        System.out.println("- " + IDIOMA + ": "
                + messages.getString("menu.language"));
        System.out.println("- " + DESISTIR + ": "
                + messages.getString("menu.quit"));
        System.out.println("===============================================================");
    }

    /**
     * Builds a fleet using user input.
     */
    public static Fleet buildFleet(Scanner in) {

        assert in != null;

        Fleet fleet = new Fleet();
        int i = 0;

        while (i < Fleet.FLEET_SIZE) {
            IShip s = readShip(in);

            if (s != null) {
                boolean success = fleet.addShip(s);

                if (success) {
                    i++;
                } else {
                    LOGGER.info("Falha na criacao de {} {} {}",
                            s.getCategory(),
                            s.getBearing(),
                            s.getPosition());
                }
            } else {
                LOGGER.info("Navio desconhecido!");
            }
        }

        LOGGER.info(i + " navios adicionados com sucesso!");
        return fleet;
    }

    /**
     * Reads ship data and creates a ship.
     */
    public static Ship readShip(Scanner in) {

        assert in != null;

        String shipKind = in.next();
        Position pos = readPosition(in);
        char c = in.next().charAt(0);
        Compass bearing = Compass.charToCompass(c);

        return Ship.buildShip(shipKind, bearing, pos);
    }

    /**
     * Reads a numeric position.
     */
    public static Position readPosition(Scanner in) {

        assert in != null;

        int row = in.nextInt();
        int column = in.nextInt();

        return new Position(row, column);
    }

    /**
     * Reads a classic position such as A3 or A 3.
     */
    public static IPosition readClassicPosition(@NotNull Scanner in) {

        if (!in.hasNext()) {
            throw new IllegalArgumentException(
                    "Nenhuma posição válida encontrada!");
        }

        String part1 = in.next();
        String part2 = null;

        if (in.hasNextInt()) {
            part2 = in.next();
        }

        String input = (part2 != null) ? part1 + part2 : part1;
        input = input.toUpperCase(Locale.ROOT);

        if (input.matches("[A-Z]\\d+")) {
            char column = input.charAt(0);
            int row = Integer.parseInt(input.substring(1));

            return new Position(column, row);

        } else if (part2 != null
                && part1.matches("[A-Z]")
                && part2.matches("\\d+")) {

            char column = part1.charAt(0);
            int row = Integer.parseInt(part2);

            return new Position(column, row);

        } else {
            throw new IllegalArgumentException(
                    "Formato inválido. Use 'A3', 'A 3' ou similar.");
        }
    }

    /**
     * Prints game timing statistics.
     */
    private static void printTimerStatistics() {

        if (moveTimes.isEmpty()) {
            System.out.println(messages.getString("timer.empty"));
            return;
        }

        long min = moveTimes.stream()
                .mapToLong(Long::longValue)
                .min()
                .orElse(0);

        long max = moveTimes.stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        double average = moveTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);

        System.out.println("\n===== "
                + messages.getString("timer.title")
                + " =====");

        System.out.println(messages.getString("timer.moves") + totalMoves);
        System.out.println(messages.getString("timer.total")
                + totalTime + " seconds");
        System.out.println(messages.getString("timer.min")
                + min + " seconds");
        System.out.println(messages.getString("timer.max")
                + max + " seconds");
        System.out.println(messages.getString("timer.average")
                + average + " seconds");

        System.out.println("=================================\n");
    }
}