// Demonstrates do-while semantics and labeled break/continue
public class LoopsAndLabelsDemo {
    public static void main(String[] args) {
        System.out.println("-- do-while runs at least once --");
        int i = 0;
        do {
            System.out.println(i);
            i++;
        } while (i < 3); // required semicolon

        System.out.println("\n-- labeled break and continue --");
        outer:
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (c == 1) {
                    System.out.println("continue outer at r=" + r + ", c=" + c);
                    continue outer; // jump to next r
                }
                if (r == 2 && c == 2) {
                    System.out.println("break outer at r=" + r + ", c=" + c);
                    break outer; // exit both loops
                }
                System.out.println("r=" + r + ", c=" + c);
            }
        }
    }
}
