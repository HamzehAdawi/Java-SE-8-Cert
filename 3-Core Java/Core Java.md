# Chapter 3: Core Java APIs

Quick overview: Strings (immutability, pool, common methods), StringBuilder (mutability and operations), Arrays (declarations, init, sorting/searching), ArrayList (usage, methods, autoboxing pitfall), Wrapper classes (parsing, Number methods, autoboxing), conversions between arrays and lists, and java.time (creation, manipulation, Period vs Duration, formatting).

## Code Examples

The following small programs illustrate the key concepts in this chapter. You can find them in `3-Core Java/CodeExamples/`:

- `StringsAndStringBuilderDemo.java` — String immutability and pool, concatenation rules, StringBuilder mutability and equality.
- `ArraysAndSearchDemo.java` — Arrays.sort, Arrays.binarySearch (with insertion point), and 2D arrays with `Arrays.deepToString`.
- `ArrayListAndAutoboxingDemo.java` — List operations, remove(int) vs remove(Object) autoboxing gotcha, sorting, contains/equals.
- `WrapperParsingAndNumberDemo.java` — Wrapper parsing/valueOf, Number methods (intValue, longValue, etc.), NumberFormatException handling.
- `DateTimeAndFormattingDemo.java` — Creating/manipulating LocalDate/Time/DateTime, Period behavior, and DateTimeFormatter (ISO, localized, custom).

## Strings

- String is immutable. Any “change” creates a new String.
```java
String word = "hello";
word += " world"; // creates a new String "hello world"
System.out.println(word); // hello world
```

- Once a String is involved, remaining + operations are concatenation:
  - "Hello" + 1 + 2 → "Hello12"
  - 1 + 2 + "Hello" → "3Hello"

- Two ways to create:
```java
String s1 = "hello";                 // uses String pool
String s2 = new String("hello");     // forces a new object (slower, avoid unless needed)
```

- String pool:
```java
String a = "hello";
String b = "hello";
System.out.println(a == b); // true (same pooled object)

String c = new String("hello");
System.out.println(a == c); // false (different object)
```

- String + int autoconverts via String.valueOf(int):
```java
String x = "Count: ";
x += 5; // "Count: 5"
```

### Common String methods
- length(), charAt(int), toLowerCase(), toUpperCase()
- equals(), equalsIgnoreCase(), startsWith(), endsWith(), contains()
- indexOf(...)
  - indexOf('a'), indexOf("apple"), indexOf('a', fromIndex)
  - returns -1 if not found
- substring(...) returns a portion of the String (not an index!)
  - substring(start), substring(start, end) where end is exclusive
```java
String text = "HelloWorld";
text.substring(5);      // "World"
text.substring(0, 5);   // "Hello"
"abc".substring(1, 1);  // "" (empty)
"abc".substring(2, 1);  // throws StringIndexOutOfBoundsException
```
- replace(char old, char new), replace(CharSequence old, CharSequence new)
- trim() removes leading/trailing whitespace
- concat(String) returns a new String (remember to save)
```java
String a = "Hello";
a = a.concat(" World"); // "Hello World"
```
- split(String regex) splits into an array:
```java
"1,2,3".split(","); // ["1","2","3"]
```

## StringBuilder

- Mutable sequence of characters; efficient for building strings.
```java
StringBuilder sb1 = new StringBuilder("hello");
StringBuilder sb2 = sb1;     // same reference
sb2.append(" world");
System.out.println(sb1);     // "hello world"
System.out.println(sb1 == sb2); // true
```

- Constructors: new StringBuilder(), new StringBuilder("str"), new StringBuilder(capacity)

- Methods: append(), insert(int, ...), delete(int, int), deleteCharAt(int), reverse(), toString(), plus String-like methods: length(), charAt(), indexOf(), substring()
  - Note: split() is NOT a StringBuilder method.

- Equality:
  - StringBuilder.equals is not overridden; it behaves like == (reference equality).
  - To compare content, compare sb.toString().
```java
String s = "test";
StringBuilder sb = new StringBuilder("test");
s.equals(sb);              // false (different types)
s.equals(sb.toString());   // true
```

- Printing:
  - System.out.println(sb) calls toString() automatically.

## Arrays

- Import utilities:
```java
import java.util.Arrays;     // sort, binarySearch, toString, etc.
```

- Declarations:
```java
int[] a; int a2[]; int[] a3, b3; // a3 and b3 are arrays
```

- Initialization:
```java
int[] x = {1, 2, 3};
int[] y = new int[]{1, 2, 3};
// new int[] requires values; you cannot do just new int[] without size/values
```

- Arrays methods (java.util.Arrays):
  - Arrays.sort(array)
  - Arrays.binarySearch(array, key) — array must be sorted (natural order)
    - If not found: returns r < 0 where insertionPoint = -r - 1

- 2D arrays (all valid):
```java
int[][] vars1;
int vars2[];
int[] vars3[];
int [] vars4 [];
int [] [] vars5;
```

## ArrayList

- Imports:
```java
import java.util.List;
import java.util.ArrayList;
import java.util.Collections; // for sorting
```

- Create:
```java
List<String> names = new ArrayList<>();
// Also valid but less flexible:
// ArrayList<String> names2 = new ArrayList<>();
```

- Generics:
  - E is the conventional type parameter name (e.g., ArrayList<E>).

- Printing:
```java
List<Integer> numbers = new ArrayList<>();
numbers.add(1); numbers.add(2); numbers.add(3);
System.out.println(numbers); // [1, 2, 3]
```

- Methods:
  - add(E), add(int, E)
  - remove(int index), remove(Object o)
  - set(int, E), isEmpty(), size(), clear(), contains(), equals()
  - Sorting:
```java
Collections.sort(names); // natural order; elements must be Comparable
```

### Autoboxing gotcha
```java
List<Integer> numbers = new ArrayList<>();
numbers.add(1);
numbers.add(2);

numbers.remove(1);                    // removes element at index 1 (value 2)
numbers.remove(Integer.valueOf(1));   // removes the Integer value 1
```

## Wrapper Classes

- Primitive → Wrapper:
  - byte→Byte, short→Short, int→Integer, long→Long, float→Float, double→Double, char→Character, boolean→Boolean

- Why: need objects (e.g., in collections) and utility methods (e.g., Integer.parseInt)

- Parsing/valueOf:
```java
int p = Integer.parseInt("123");      // primitive
Integer w = Integer.valueOf("123");   // wrapper
```

- Numeric wrapper constructors with String parse the value; invalid strings throw NumberFormatException:
```java
Long ok = new Long("123");     // OK (ctor form; valueOf preferred)
Long bad = new Long("hi");     // throws NumberFormatException
```

- All numeric wrappers extend Number and provide:
  - intValue(), longValue(), doubleValue(), floatValue(), shortValue(), byteValue()

- Autoboxing/unboxing:
  - Java converts between primitives and wrappers automatically, but be mindful of performance and overload resolution (see remove example above).

## Converting between Array and List

- List → Array:
```java
List<String> list = new ArrayList<>();
list.add("hi"); list.add("hello");
String[] arr = list.toArray(new String[0]); // size 0 is idiomatic
```

- Array → List:
```java
String[] arr = {"a","b","c"};
List<String> list = Arrays.asList(arr); // fixed-size, backed by arr

list.set(0, "z"); // updates both list and arr
// list.add("d"); // throws UnsupportedOperationException

List<String> modifiable = new ArrayList<>(Arrays.asList(arr));
modifiable.add("d"); // OK
```

## Dates and Times (java.time)

- Imports:
```java
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
```

- Never use new with LocalDate/LocalTime/LocalDateTime.
  - Use now() or of(...)

- Current:
```java
LocalDate.now();           // e.g., 2025-06-23
LocalTime.now();           // e.g., 15:45:30.123456789
LocalDateTime.now();       // e.g., 2025-06-23T15:45:30.123456789
```

- Create:
```java
LocalDate d1 = LocalDate.of(2015, Month.JANUARY, 20);
LocalDate d2 = LocalDate.of(2015, 1, 20);

LocalTime t1 = LocalTime.of(6, 15);
LocalTime t2 = LocalTime.of(6, 15, 23);
LocalTime t3 = LocalTime.of(6, 15, 23, 200);

LocalDateTime dt1 = LocalDateTime.of(2015, 1, 20, 6, 15);
LocalDateTime dt2 = LocalDateTime.of(d2, t1);
```

- Manipulation (immutable — save the result):
```java
LocalDate date = LocalDate.of(2025, 1, 1);

LocalDate nextYear  = date.plusYears(1);    // 2026-01-01
LocalDate nextMonth = date.plusMonths(1);   // 2025-02-01
LocalDate plusDays  = date.plusDays(21);    // 2025-01-22

LocalDate prevYear  = date.minusYears(1);   // 2024-01-01
LocalDate prevMonth = date.minusMonths(1);  // 2024-12-01
LocalDate minusDays = date.minusDays(21);   // 2024-12-11
```

- Period (date-based amount; use Duration for time):
```java
LocalDate today = LocalDate.of(2025, 1, 1);
Period oneMonth = Period.ofMonths(1);
LocalDate newDate = today.plus(oneMonth); // 2025-02-01
```
  - Period factory methods aren’t chainable as sums:
    - Period.ofYears(1).ofMonths(2) results in 2 months only (last call wins).
  - You can chain plus/minus on the date/time objects (e.g., date.plusMonths(1).minusDays(2)).

### Formatting Dates/Times

- Extract fields:
```java
LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
int day = date.getDayOfMonth(); // 20
int month = date.getMonthValue(); // 1
Month monthEnum = date.getMonth(); // JANUARY
int year = date.getYear(); // 2020
int dayOfYear = date.getDayOfYear();
```

- DateTimeFormatter:
```java
LocalDateTime now = LocalDateTime.now();

// ISO
String iso = now.format(DateTimeFormatter.ISO_DATE_TIME);

// Localized
DateTimeFormatter shortDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
System.out.println(LocalDate.now().format(shortDate)); // e.g., 6/24/25

DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

// Custom pattern
DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
```

- Applying a formatter expecting time to a date-only object (LocalDate) throws an exception.

- Remember: never use new with LocalDate/LocalTime/LocalDateTime:
```java
// LocalDate date = new LocalDate(); // doesn't compile
LocalDate date = LocalDate.now();
LocalDate date2 = LocalDate.of(2025, 6, 23);
LocalTime time = LocalTime.of(15, 30);
LocalDateTime dt = LocalDateTime.of(date2, time);
```

## Summary
- Strings: immutable; string pool; concatenation rules; key methods (substring, indexOf, replace, trim, split); concat returns new String.
- StringBuilder: mutable; efficient appends/inserts/deletes; reference equality; compare content via toString().
- Arrays: declarations/initialization; Arrays.sort and binarySearch (requires sorted); 2D forms.
- ArrayList: creation via List interface; add/remove/set/contains/equals; Collections.sort; autoboxing remove(int) vs remove(Object).
- Wrapper classes: primitive↔wrapper; parseInt/valueOf; Number methods (intValue, longValue, etc.); NumberFormatException on bad numeric strings.
- Conversions: List↔Array via toArray and Arrays.asList (fixed-size, backed) and copying to a modifiable list.
- Dates/times: use LocalDate/LocalTime/LocalDateTime via now/of; immutable plus/minus; Period for date amounts (Duration for time); DateTimeFormatter for ISO, localized, and patterns.




