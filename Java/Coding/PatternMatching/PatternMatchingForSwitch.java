package PatternMatching;

public class PatternMatchingForSwitch {
    public static void main(String[] args) {
        // Old way
        String day = "Monday";

        switch (day) {
            case "Monday":
                System.out.println("First day of the week");
                break;

            case "Saturday", "Sunday":
                System.out.println("Weekend");
                break;
            default:
                System.out.println("Weekday");
                break;
        }

        //Modern Way
        switch(day){
            case "Monday" -> System.out.println("First day of the week");
            case "Saturday", "Sunday" -> System.out.println("Weekend");
            default -> System.out.println("Weekday");
        }
    }
}
