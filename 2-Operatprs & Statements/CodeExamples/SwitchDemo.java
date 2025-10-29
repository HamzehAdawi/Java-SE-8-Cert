// Demonstrates switch usage on String, int, and enum, with fall-through and default placement
public class SwitchDemo {
    enum Year { FRESHMAN, SOPHOMORE, JUNIOR, SENIOR }

    static void switchOnInteger(Integer i) {
        // If i is null, auto-unboxing inside switch throws NullPointerException
        switch (i) {
            case 1: System.out.println("one"); break;
            default: System.out.println("other");
        }
    }

    public static void main(String[] args) {
        // String switch with fall-through and default in the middle
        String year = "Senior";
        switch (year) {
            case "Freshman":
            default:
            case "Sophomore":
            case "Junior":
                System.out.println("See you next year");
                break;
            case "Senior":
                System.out.println("Congratulations");
        }

        // Enum switch
        Year y = Year.SOPHOMORE;
        switch (y) {
            case FRESHMAN:
            case SOPHOMORE:
            case JUNIOR:
                System.out.println("Underclassman");
                break;
            case SENIOR:
                System.out.println("Upperclassman");
                break;
        }

        // Integer switch
        int grade = 85;
        switch (grade / 10) { // 8
            case 10: case 9:
                System.out.println("A");
                break;
            case 8:
                System.out.println("B");
                break;
            case 7:
                System.out.println("C");
                break;
            default:
                System.out.println("Below average");
        }

        // Wrapper null on switch -> NullPointerException
        try {
            switchOnInteger(null);
        } catch (NullPointerException npe) {
            System.out.println("Switch on null Integer throws NullPointerException");
        }
    }
}
