
# Chapter 6: Exceptions

## Topics covered
- Throw vs. throws
- Types of exceptions (checked, runtime, error)
- Using try-catch-finally (ordering multiple catch blocks; nested try-catch)
- Runtime exceptions (unchecked)
- Checked exceptions (handle or declare)
- Errors (unchecked)
- Declaring exceptions with throws
- Overridden methods and exceptions (narrowing checked exceptions)
- Printing exceptions (stack trace vs. message)
- Control flow and finally
- Try-with-resources and suppressed exceptions
- Multi-catch (Java 7+) and catch ordering

## Code examples
- Checked exceptions and throws: [CodeExamples/CheckedAndThrowsDemo.java](CodeExamples/CheckedAndThrowsDemo.java)
- Unchecked/runtime exceptions: [CodeExamples/RuntimeExceptionsDemo.java](CodeExamples/RuntimeExceptionsDemo.java)
- Multi-catch and ordering: [CodeExamples/MultiCatchDemo.java](CodeExamples/MultiCatchDemo.java)
- Try-with-resources and suppressed: [CodeExamples/TryWithResourcesSuppressedDemo.java](CodeExamples/TryWithResourcesSuppressedDemo.java)
- Catch rethrow and finally flow: [CodeExamples/CatchAndFinallyFlowDemo.java](CodeExamples/CatchAndFinallyFlowDemo.java)

## Throw vs. throws
- throw: the statement used to actually throw an exception at a specific point in code.
- throws: a method declaration clause indicating that the method may throw the listed checked exception(s).

## Types of exceptions (Java 8)

| Type                 | How to recognize                                 | Must handle or declare? | Okay to catch?         |
|----------------------|---------------------------------------------------|--------------------------|------------------------|
| Checked exception    | Extends Exception but is not a RuntimeException   | Yes                      | Yes                    |
| Runtime exception    | Extends RuntimeException                          | No (unchecked)           | Yes                    |
| Error                | Extends Error                                     | No (unchecked)           | Yes (discouraged)      |

## Using try-catch-finally
Use try to wrap code that may throw, with optional catch and/or finally.

```java
try {
   // Code that might throw an exception
   someRiskyOperation();
} catch (Exception e) {
   // Handle the exception
   System.out.println("An exception occurred: " + e.getMessage());
}
```

If an exception is thrown that matches a catch clause, that catch runs; otherwise execution continues normally past the try.

finally block: code that always runs, whether an exception occurs or not.

```java
try {
   // Code that might throw an exception
   someRiskyOperation();
} catch (Exception e) {
   // Handle the exception
   System.out.println("An exception occurred: " + e.getMessage());
} finally {
   // Code that will always run
}
```

Note: A try must be followed by at least one of catch or finally (or be a try-with-resources). Catch is not required if there is a finally, and finally is not required if there is at least one catch.

### Ordering multiple catch blocks
When using multiple catch blocks in the same try, catch more specific (subclass) exceptions before broader (superclass) exceptions, otherwise the broader one will make the specific one unreachable (compile-time error).

```java
try {
   throw new IllegalArgumentException("Invalid input!");
} catch (Exception e) {
   System.out.println("Caught Exception: " + e.getMessage());
}
// ❌ Unreachable: IllegalArgumentException is a subclass of Exception
catch (IllegalArgumentException e) {
   System.out.println("Caught IllegalArgumentException: " + e.getMessage());
}
```

Note: Error and Exception are siblings under Throwable. Their order relative to each other doesn’t cause reachability issues unless you also catch Throwable.

### Nested try-catch
You can nest try-catch blocks to handle different scopes separately.

```java
try {
   try {
      int a = 5 / 0;  // throws ArithmeticException
   } catch (ArithmeticException e) {
      System.out.println("Caught inside inner try");
   }
} catch (Exception e) {
   System.out.println("Caught in outer try");
}
```

## Runtime exceptions (unchecked)
- Extend RuntimeException.
- Do NOT have to be handled or declared.
- Can be thrown by the programmer or by the JVM.

Common subclasses:
- NullPointerException — JVM: access through a null reference
- ArithmeticException — JVM: divide by zero
- ArrayIndexOutOfBoundsException — JVM: invalid array index
- IllegalArgumentException — Programmer: illegal method argument
- ClassCastException — JVM: invalid cast
- NumberFormatException — Programmer: failed string-to-number parse

## Checked exceptions
- Extend Exception (but are not RuntimeException).
- MUST be handled or declared.

Common subclasses:
- FileNotFoundException — file does not exist
- IOException — I/O problems (read/write)
- SQLException — database access errors
- ClassNotFoundException — class not found during loading/reflection

✅ If a method throws a CHECKED exception (e.g., Exception, IOException), callers must either use a try-catch or add a compatible throws clause. For UNCHECKED exceptions (RuntimeException or Error), no handling or declaration is required.

### Declaring and handling a checked exception

```java
// Custom checked exception
public class MyCheckedException extends Exception {
   public MyCheckedException(String message) {
      super(message);
   }
}

public class Demo {
   public static void riskyMethod() throws MyCheckedException {
      throw new MyCheckedException("Something went wrong!");
   }
}

// If we simply call it without handling, we get a compiler error
public class Main1 {
   public static void main(String[] args) {
      Demo.riskyMethod(); // ❌ Compiler error: unhandled exception
   }
}

// Option 1: try-catch
public class Main2 {
   public static void main(String[] args) {
      try {
         Demo.riskyMethod();
      } catch (MyCheckedException e) {
         System.out.println("Caught: " + e.getMessage());
      }
   }
}

// Option 2: declare throws
public class Main3 {
   public static void main(String[] args) throws MyCheckedException {
      Demo.riskyMethod();
   }
}
```

## Errors (unchecked)
- Extend Error.
- Typically thrown by the JVM.
- Do not have to be handled or declared (and generally should not be caught in application code).

Common Errors:
- StackOverflowError — stack exhausted (e.g., infinite recursion)
- ExceptionInInitializerError — failure in static initialization
- NoClassDefFoundError — class not found at runtime after successful compile

## Declaring exceptions with throws
For unchecked exceptions (RuntimeException and Error), the compiler does not require a throws clause (you may declare them, but it’s optional).

For methods that declare a checked exception like IOException, you must follow these rules:

```java
public void ohNo() throws IOException {
   // WHAT CAN BE HERE
}
```

You CAN throw:
- The same exception (IOException itself)
- Any subclass of IOException (narrower checked exceptions)
- Any unchecked exceptions (RuntimeException / Error)

## Overridden methods and exceptions
For checked exceptions, an overriding method may declare the same exceptions, a subset, or more specific (narrower) exceptions—or none at all. It may not declare broader checked exceptions than the method it overrides. Unchecked exceptions (RuntimeException, Error) may be added freely.

Keep in mind: RuntimeException is a subclass of Exception, but it is unchecked.

## Printing exceptions
- e.printStackTrace(): full info with stack trace
- System.out.println(e): exception type + message
- System.out.println(e.getMessage()): message only

```java
try {
   int x = 1 / 0;
} catch (ArithmeticException e) {
   e.printStackTrace();                // 1. Full stack trace
   System.out.println(e);              // 2. Exception type + message
   System.out.println(e.getMessage()); // 3. Message only
}
```

## Control flow and finally
✅ Once an exception is thrown and is not caught by any matching catch in the call stack, the thread terminates with that exception. A finally block still runs but does not prevent termination.

✅ Finally caveat: Even in the case of an uncaught exception, finally executes before the exception propagates out of the method.

✅ Rule for catching checked exceptions:
- A catch clause for a checked exception is only valid if the try block may throw that exception type (directly or via called methods). Otherwise, it’s a compile-time error (exception is never thrown in the body of the corresponding try statement).

✅ Be careful: exceptions thrown inside a catch block are not handled by later catch clauses of the same try. Finally still runs. If finally throws, its exception replaces any pending one.

```java
public static void main(String[] args) {
   System.out.print("a");
   try {
      System.out.print("b");
      throw new IllegalArgumentException();
   } catch (IllegalArgumentException e) {
      System.out.print("c");
      throw new RuntimeException("1"); // not handled by the next catch
   } catch (RuntimeException e) {
      System.out.print("d");          // unreachable for the thrown above
      throw new RuntimeException("2");
   } finally {
      System.out.print("e");
      throw new RuntimeException("3"); // replaces the pending RuntimeException("1")
   }
}
```






## Summary
- throw is used to actually throw an exception; throws declares potential checked exceptions on a method.
- Checked exceptions (non-RuntimeException) must be handled or declared; runtime exceptions and errors are unchecked.
- Catch blocks must be ordered from specific (subclass) to general (superclass); multi-catch uses the | operator.
- try requires at least one of catch or finally (or try-with-resources); finally always runs before unwinding and does not prevent termination.
- Overriding can only narrow checked exceptions (or remove them); unchecked exceptions may be added.
- try-with-resources automatically closes resources; close() exceptions are suppressed and accessible via getSuppressed().
- Printing: printStackTrace() gives full stack; println(e) prints type+message; getMessage() prints message only.





