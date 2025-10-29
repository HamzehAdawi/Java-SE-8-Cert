# Chapter 2: Operators and Statements
Quick overview: ternary operator, numeric promotion and casting, ++/--, compound assignments, assignment expressions, relational/logical operators, equality (== vs equals), if/else, switch, do-while, and operator precedence.

## Code Examples

The following small programs illustrate the key concepts in this chapter. You can find them in `2-Operatprs & Statements/CodeExamples/`:

- `TernaryDemo.java` — Ternary basics, right-associativity, single-branch evaluation, and numeric type promotion.
- `NumericPromotionDemo.java` — Promotions and casts, constant expressions, integer division, overflow wrap, modulo with negatives, and shift promotions.
- `IncrementDecrementDemo.java` — Prefix vs postfix semantics, valid targets (variables/array elements), and why method results aren’t incrementable.
- `CompoundAssignmentDemo.java` — Implicit cast in `op=`, LHS evaluated once, and overflow behavior.
- `LogicalOperatorsDemo.java` — Short-circuit (&&, ||) vs non-short-circuit (&, |), XOR (^), and NaN equality caveat.
- `EqualityDemo.java` — `==` vs `.equals()` for primitives/objects; String pool nuance; arrays/StringBuilder; `Objects.equals` for null safety.
- `SwitchDemo.java` — Switch on String/int/enum, fall-through, `default` anywhere, case constants, and wrapper null NPE.
- `LoopsAndLabelsDemo.java` — do-while’s at-least-once behavior; labeled `break`/`continue` across nested loops.
- `OperatorPrecedenceDemo.java` — Precedence rules and how parentheses change evaluation across arithmetic, logical, and bitwise.

## Ternary Operator (?:)
The ternary operator is a compact if-else expression:
condition ? valueIfTrue : valueIfFalse

- It is an expression (not a statement), so it must produce a value where one is expected.
- It is right-associative (parses from right to left), so nested forms work without extra parentheses.

Example:
```java
int result = a > b ? a : b > 15 ? 20 : 30;     // parsed as a > b ? a : (b > 15 ? 20 : 30)
// Equivalent with explicit parentheses:
int result2 = (a > b) ? a : ((b > 15) ? 20 : 30);
```

Using it as a standalone statement is not allowed:
```java
(score >= 60) ? "Passed" : "Failed"; // ❌ not a valid statement
return (score >= 60) ? "Passed" : "Failed";    // ✅ valid (expression used in return)
```

Additional tips:
- Only one of the second or third operands is evaluated (no double evaluation).
- Type rules: second and third operands must be compatible.
  - Numeric types are promoted to a common type:
    ```java
    int a = 1; long b = 2L;
    var v = cond ? a : b; // v is long
    ```
  - With boxed types, both sides must be convertible to a common type (may involve unboxing/boxing). Ambiguity can cause a compile error.
- The condition must be boolean; there is no truthy/falsy in Java.

---

## Numeric Promotion and Casting
General rules:
- If two operands have different numeric types, the smaller type is promoted to the larger.
- If one operand is integral and the other is floating-point, the integral is promoted to the floating-point type.
- For binary arithmetic, byte, short, and char are first promoted to int. The result type is based on the promoted operands, not the receiving variable.
- After promotion, the result has the same type as the promoted operands.

Examples:
```java
byte b1 = 10;
byte b2 = 20;
// b1 + b2 is int; direct assignment to byte fails:
byte b3 = b1 + b2;        // ❌ compile error
byte b4 = (byte)(b1 + b2); // ✅ explicit cast required

byte x = 3;
long result = x + 4;       // ✅ x promoted to int, then result widened to long
```

Widening chains:
- byte → short → int → long → float → double
- char → int → long → float → double
Note: char does not widen to short.

Quick tip:
- Widening conversions are implicit; narrowing conversions require an explicit cast.
- Promotion happens in:
  - Arithmetic expressions
  - Variable assignments (via implicit widening or explicit narrowing)
  - Method arguments
  - Return statements

Important method call note:
- Java will not combine a widening primitive conversion and a boxing conversion in one step when selecting methods (no “widening + boxing” together implicitly).

More nuances:
- Constant expressions of int type can assign to byte/short/char without a cast if within range:
  ```java
  byte ok = 10 + 20;   // ✅ constant expression within range
  int a = 10; byte nope = a + 20; // ❌ not a constant expression
  ```
- Integer division truncates toward zero:
  ```java
  System.out.println(7 / 3);  // 2
  System.out.println(-7 / 3); // -2
  ```
- Overflow wraps around silently (two’s complement):
  ```java
  int big = Integer.MAX_VALUE;
  System.out.println(big + 1); // wraps to Integer.MIN_VALUE
  ```
- Modulo works with negatives; sign follows the dividend:
  ```java
  System.out.println(-7 % 3);  // -1
  ```
- Shift promotions: operands are promoted to int (or long if left is long) before shifting.

---

## Increment and Decrement (Unary ++ and --)
- Postfix: x++ returns the current value, then increments.
- Prefix: ++x increments first, then returns the new value.
- They can only apply to variables (including array elements or fields), not to literals or method return values.

Examples:
```java
int x = 1;
int a = x++; // a=1, x=2
int b = ++x; // x=3, b=3

getValue()++; // ❌ not allowed: result of a method call is not a variable
arr[i]++;     // ✅ allowed: array element is a variable
```

Tip:
- Avoid using ++ on the same variable multiple times in one expression—it’s legal but easy to misread.

---

## Compound Assignment Operators (+=, -=, *=, etc.)
- E1 op= E2 is roughly equivalent to: E1 = (T) ((E1) op (E2)), where T is the type of E1.
- The cast back to the left-hand type is implicit with compound assignments.

Examples:
```java
int x = 1;
long L = 2L;

x = x * L;   // ❌ result is long; requires cast to assign to int
x *= L;      // ✅ implicit narrowing cast to int is applied
```

More to know:
- Overflow during the implicit cast still wraps:
  ```java
  short s = 30_000;
  s += 30_000; // wraps within short range
  ```
- The left-hand side is evaluated only once (useful with array/field/index expressions).

---

## Assignment Expressions Return a Value
Assignments evaluate to the assigned value:
```java
long x = 10;
long y = (x = 3);  // parentheses optional here
// Equivalent:
long y2 = x = 3;   // x becomes 3, y2 becomes 3
```

---

## Relational and Logical Operators
Relational: >, >=, <, <=, ==, !=

Boolean logical:
- & and | (non-short-circuit): both sides are always evaluated.
- && and || (short-circuit):
  - For &&, if the left side is false, the right side is not evaluated.
  - For ||, if the left side is true, the right side is not evaluated.
- ^ (xor): true if operands differ; false if the same.

Examples:
```java
if (false & someCheck()) { } // someCheck() is still called
if (true  | someCheck()) { } // someCheck() is still called

if (cond1 && cond2) { } // cond2 may be skipped if cond1 is false
if (cond1 || cond2) { } // cond2 may be skipped if cond1 is true
```

Floating-point caveat:
- NaN is not equal to anything, including itself:
  ```java
  double d = Double.NaN;
  System.out.println(d == d);    // false
  System.out.println(d != d);    // true
  ```

---

## Equality: '==' vs '.equals()'
Primitives:
- Use == (compares actual values).

Objects (including String):
- '==' compares references (same object?).
- '.equals()' compares contents (when overridden to do so).

Examples:
```java
int a = 5, b = 5;
System.out.println(a == b); // true

String s1 = "hello";
String s2 = "hello";
String s3 = new String("hello");

System.out.println(s1 == s2);      // true (same interned literal)
System.out.println(s1 == s3);      // false (different objects)
System.out.println(s1.equals(s3)); // true (same contents)
```

String pool nuance:
```java
String x = "Hello";
String y = "He";
y += "llo";
System.out.println(y == x); // false: concatenation creates a new object
```

Arrays and StringBuilder:
- Arrays: both '==' and '.equals()' compare references.
- Use Arrays.equals(arr1, arr2) for content comparison.
- StringBuilder does not override equals; both '==' and '.equals()' compare references.

Collections (e.g., ArrayList):
- '.equals()' compares contents element-by-element.
- '==' compares references.

Example:
```java
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};

System.out.println(a == b);               // false
System.out.println(java.util.Arrays.equals(a, b)); // true
```

Key takeaways:
- Use '==' for primitives.
- Use '.equals()' for content equality of objects.
- '==' is true for objects only when they are the exact same instance.

Null-safety tips:
- Calling obj.equals(...) when obj is null throws NullPointerException; prefer:
  - java.util.Objects.equals(a, b)
  - "literal".equals(variable)

For nested arrays, use Arrays.deepEquals.

---

## if, else if, else
- Only one block in an if-else-if-else chain executes.
- Separate if statements are all evaluated independently.

Beware accidental assignment:
```java
if (x = someBool) { }   // ❌ compile error (not boolean variable assignment to condition)
if (x == someValue) { } // ✅ comparison
```

Tips:
- Always use braces in conditionals in real codebases to prevent bugs during edits.
- Variables declared in a block are scoped to that block.

---

## switch Statement
Structure:
```java
switch (expr) {
  case VALUE_1:
    // ...
    break;
  case VALUE_2:
    // ...
    break;
  default:
    // ...
}
```

Supported types (Java 8):
- byte, short, char, int (and their wrappers)
- String
- enum types
- Not allowed: boolean, long (and their wrappers)

Behavior:
- The expression is evaluated and control jumps to the matching case.
- Without break, execution “falls through” to subsequent cases.
- default can appear anywhere.

Case labels must be compile-time constants:
- Literals are fine.
- final variables only if initialized with a compile-time constant.
- Method return values are not compile-time constants.
- Method parameters (even if final) are not compile-time constants.

Example (default in the middle and fall-through):
```java
String year = "Senior";
switch (year) {
  case "Freshman":
  default:
  case "Sophomore":
  case "Junior":
    System.out.print("See you next year");
    break;
  case "Senior":
    System.out.print("Congratulations");
}
```

More switch tips:
- Multiple labels can share a block:
  ```java
  switch (ch) {
    case 'a': case 'e': case 'i': case 'o': case 'u':
      isVowel = true; break;
    default:
      isVowel = false;
  }
  ```
- String switch is case-sensitive; switching on null throws NullPointerException.
- Wrapper types are unboxed; a null expression triggers NullPointerException.
- Enums are great in switch:
  ```java
  switch (day) {
    case MONDAY: case TUESDAY: /*...*/ break;
    default: break;
  }
  ```

---

## do-while Loop
- The body runs at least once; the condition is checked after the body.
- Note the required semicolon after the while condition.

Example:
```java
int i = 0;
do {
  System.out.println(i);
  i++;
} while (i < 3); // ← semicolon required
```

Related:
- break exits the nearest loop; continue jumps to the next iteration.
- Labeled break/continue can target outer loops.

---

## Operator Precedence (high → low)
- Unary: ++ -- + (unary) - (unary) ! ~
- Multiplicative: * / %
- Additive: + -
- Shift: << >> >>>
- Relational: < <= > >= instanceof
- Equality: == !=
- Bitwise AND: &
- Bitwise XOR: ^
- Bitwise OR: |
- Logical AND: &&
- Logical OR: ||
- Ternary: ? :
- Assignment: = += -= *= /= %= &= ^= |= <<= >>= >>>=

## Summary
- Ternary (?:): expression form, right-associative, type compatibility, only one branch evaluated.
- Numeric promotion/casting: integral promotion to int, widening vs narrowing, constant expressions, integer division, overflow wrap, modulo with negatives, shift promotions.
- ++/--: prefix vs postfix semantics; only valid on variables (not literals or method results).
- Compound assignments: implicit cast back to LHS type; LHS evaluated once; overflow still wraps.
- Assignment expressions: return the assigned value; can be nested/used in expressions.
- Relational/logical: &, | (non-short-circuit) vs &&, || (short-circuit); ^ (xor); NaN comparisons.
- Equality: '==' for primitives/references; '.equals()' for content (when overridden); String pool nuances; Arrays.equals/Arrays.deepEquals; Objects.equals for null safety; collections compare by contents.
- if/else-if/else: single executed branch; scope of variables; prefer braces; avoid assignment-in-condition mistakes.
- switch: supported types (byte/short/char/int, wrappers, String, enum); case constants; fall-through; default anywhere; null handling; enums fit well.
- do-while: executes at least once; trailing semicolon; break/continue and labels.
- Operator precedence: unary → multiplicative → additive → shift → relational → equality → bitwise → logical → ternary → assignment.
