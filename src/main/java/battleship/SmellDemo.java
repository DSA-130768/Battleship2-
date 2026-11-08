// 130768 - Simão Sousa
package battleship;

/**
 * Long Method smell fixed: the report is built by small, focused methods.
 */
public class SmellDemo {

    public String describeFleet(int galleons, int frigates, int caravels, int barges, int carracks) {
        int total = galleons + frigates + caravels + barges + carracks;
        int size = galleons * 5 + frigates * 4 + caravels * 2 + barges + carracks * 3;
        return header()
                + counts(galleons, frigates, caravels, barges, carracks)
                + totals(total, size)
                + rating(size);
    }

    private String header() {
        return "Fleet report\n------------\n";
    }

    private String counts(int galleons, int frigates, int caravels, int barges, int carracks) {
        return "Galleons: " + galleons + "\nFrigates: " + frigates + "\nCaravels: " + caravels
                + "\nBarges: " + barges + "\nCarracks: " + carracks + "\n";
    }

    private String totals(int total, int size) {
        return "Total ships: " + total + "\nTotal cells: " + size + "\n";
    }

    private String rating(int size) {
        if (size > 30) {
            return "Rating: Huge\n";
        }
        if (size > 20) {
            return "Rating: Large\n";
        }
        if (size > 10) {
            return "Rating: Medium\n";
        }
        return "Rating: Small\n";
    }
}
