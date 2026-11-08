// 130768 - Simão Sousa
package battleship;

/**
 * Deliberate Long Method smell, used to test the Qodana quality gate.
 */
public class SmellDemo {

    public String describeFleet(int galleons, int frigates, int caravels, int barges, int carracks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Fleet report\n");
        sb.append("------------\n");
        int total = 0;
        total += galleons;
        total += frigates;
        total += caravels;
        total += barges;
        total += carracks;
        sb.append("Galleons: ").append(galleons).append('\n');
        sb.append("Frigates: ").append(frigates).append('\n');
        sb.append("Caravels: ").append(caravels).append('\n');
        sb.append("Barges: ").append(barges).append('\n');
        sb.append("Carracks: ").append(carracks).append('\n');
        int size = 0;
        size += galleons * 5;
        size += frigates * 4;
        size += caravels * 2;
        size += barges;
        size += carracks * 3;
        sb.append("Total ships: ").append(total).append('\n');
        sb.append("Total cells: ").append(size).append('\n');
        int largest = 0;
        if (galleons > 0) {
            largest = 5;
        } else if (frigates > 0) {
            largest = 4;
        } else if (carracks > 0) {
            largest = 3;
        } else if (caravels > 0) {
            largest = 2;
        } else if (barges > 0) {
            largest = 1;
        }
        sb.append("Largest ship size: ").append(largest).append('\n');
        double avg = total == 0 ? 0 : (double) size / total;
        sb.append("Average size: ").append(avg).append('\n');
        String rating;
        if (size > 30) {
            rating = "Huge";
        } else if (size > 20) {
            rating = "Large";
        } else if (size > 10) {
            rating = "Medium";
        } else {
            rating = "Small";
        }
        sb.append("Rating: ").append(rating).append('\n');
        int warships = galleons + frigates;
        int merchants = caravels + barges + carracks;
        sb.append("Warships: ").append(warships).append('\n');
        sb.append("Merchants: ").append(merchants).append('\n');
        boolean balanced = Math.abs(warships - merchants) <= 1;
        sb.append("Balanced: ").append(balanced).append('\n');
        int freeCells = 100 - size;
        sb.append("Free board cells: ").append(freeCells).append('\n');
        boolean valid = total > 0 && size <= 100;
        sb.append("Valid fleet: ").append(valid).append('\n');
        sb.append("------------\n");
        sb.append("End of report\n");
        return sb.toString();
    }
}
