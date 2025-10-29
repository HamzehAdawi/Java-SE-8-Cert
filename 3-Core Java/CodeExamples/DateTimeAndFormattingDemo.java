// Demonstrates: java.time creation and manipulation, Period vs chaining on dates,
// and formatting via DateTimeFormatter (ISO, localized, custom). Also shows a
// runtime exception when applying a date-time formatter to a date-only object.
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DateTimeAndFormattingDemo {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2025, 1, 1);
        System.out.println("Start date:    " + date);
        System.out.println("Plus 1 year:   " + date.plusYears(1));
        System.out.println("Minus 21 days: " + date.minusDays(21));

    // Period factory methods are not chainable as sums (last call wins)
    Period p = Period.ofYears(1);
    p = Period.ofMonths(2); // overwrites previous; effectively "P2M"
    System.out.println("Period after ofYears then ofMonths: " + p);
    System.out.println("date.plus(p):  " + date.plus(p)); // adds 2 months

        // Preferred: chain on the date/time object
        System.out.println("date.plusMonths(2).plusYears(1): " + date.plusMonths(2).plusYears(1));

        // Formatting
        LocalDateTime now = LocalDateTime.now();
        System.out.println("ISO_DATE_TIME: " + now.format(DateTimeFormatter.ISO_DATE_TIME));

        DateTimeFormatter shortDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        System.out.println("Localized short date: " + LocalDate.now().format(shortDate));

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
        System.out.println("Custom pattern: " + now.format(pattern));

        // Applying a date-time formatter to a date-only object throws an exception
        try {
            DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
            String bad = LocalDate.now().format(shortDateTime); // will throw
            System.out.println("Should not print: " + bad);
        } catch (UnsupportedTemporalTypeException e) {
            System.out.println("Caught UnsupportedTemporalTypeException when formatting LocalDate with date-time formatter.");
        }
    }
}
