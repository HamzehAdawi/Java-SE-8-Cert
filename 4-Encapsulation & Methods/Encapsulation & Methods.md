# Chapter 4: Methods and Encapsulation


- Access control: public/protected/default/private and nested vs top‑level rules.
- Method modifiers: static/final/abstract (+ synchronized/native/strictfp overview).
- Varargs: rules, overloading interactions, and null specificity.
- Static members: access rules, hiding vs overriding, static initializers, initialization order.
- Overloading: precedence (exact → widening → boxing → varargs) and common ambiguities.
- Constructors: chaining, visibility, super(), and final field assignment.
- Initialization order: class load vs instance creation, with/without inheritance.
- Encapsulation and immutability: private/final fields, defensive copies, unmodifiable wrappers.
- Lambdas and functional interfaces: syntax, effectively final, java.util.function, method references.
- Predicates: removeIf and composition.

## Code Examples

Small runnable programs are in `4-Encapsulation & Methods/CodeExamples/`:

- `StaticAndInitializationOrderDemo.java` — Shows static initializer vs static field order, instance field/blocks, and constructor order across multiple instances.
- `OverloadingAndVarargsResolutionDemo.java` — Demonstrates overload selection: widening vs boxing vs varargs, null ambiguity, and specificity.
- `ConstructorsChainingAndVisibilityDemo.java` — this(...) chaining, super(...) calls, and final field initialization.
- `EncapsulationImmutableDefensiveCopyDemo.java` — Defensive copying of Date/arrays, proving immutability of exposed state.
- `LambdaAndPredicateDemo.java` — Lambda basics, method references, Predicate composition, and removeIf in action.

## Access modifiers (from least restrictive to most)
- public — accessible by any class in any package.
- protected — accessible within the same package and by subclasses (even in other packages). From outside the package, access is via inheritance.
- default (no modifier, a.k.a. package‑private) — accessible only within the same package.
- private — accessible only within the same class.

Notes and gotchas:
- Top‑level classes can only be public or package‑private (no protected/private for top‑level types).
- Nested types (classes/interfaces declared inside another class) may use any access modifier.
- protected outside the package: access must be through a subclass reference (not just any instance).

## Optional specifiers (method non‑access modifiers)
- static
- abstract
- final

Order: Modifiers can appear in any order before the return type, but some combinations are illegal.

Additional method modifiers you may see:
- synchronized — acquires the monitor of the receiver/class before executing the method.
- native — method implemented in another language (JNI); no body in Java.
- strictfp — enforces strict floating‑point rules for consistent results across platforms.

Examples:
```java
// ✅ valid (order doesn’t matter)
public static final void good1() {}
protected abstract void good2();

// ❌ invalid combinations
// abstract + static
// abstract + final
// abstract + private
```

🔶 Final can be a parameter modifier
Only final is allowed on parameters; it prevents reassignment inside the method.
```java
public void example(final int x) {
        // x = 5; // ❌ compile error
}
```

## Working with Varargs
Varargs are arrays under the hood. Two rules:
- Only one varargs per method.
- It must be the last parameter.

Correct ✅
```java
void print(String... messages) {}
void greet(String greeting, String... names) {}
```

Incorrect ❌
```java
void invalid(String... names, int... ages) {}
void invalid(String... names, String title) {}
```

Passing values: individual args or an array.
```java
void method(String... xs) { /* use xs like an array */ }

String a = "one", b = "two", c = "three";
String[] arr = {"one", "two", "three"};

method(a, b, c);
method(arr);
```

Access like an array—check length first:
```java
static void printFirst(int... nums) {
        if (nums.length > 0) System.out.println(nums[0]);
}
```

Overloading interactions with varargs:
```java
void m(String s) {}
void m(Object... os) {}

m("hi");      // picks m(String)
m();           // picks m(Object...) with an empty array
m((String) null); // picks m(String); null is compatible with both but String is more specific
```

## Designing static methods and fields
- Instance methods can access instance and static members.
- Static methods can only access static members unless they create/use an instance.
- There is exactly one copy of each static field per class; all instances share it.

Example (static shared state):
```java
class Koala { public static int count = 0; }

class Demo {
    public static void main(String[] args) {
        Koala k1 = new Koala();
        System.out.println(Koala.count); // preferred
        System.out.println(k1.count);    // compiles, but still refers to Koala.count

        k1 = null;
        System.out.println(k1.count);    // still ok: resolved to Koala.count at compile time

        Koala koala1 = new Koala();
        Koala koala2 = new Koala();
        koala1.count = 6;    // writes the same static field
        koala2.count = 5;    // overwrites it
        System.out.println(Koala.count); // 5
    }
}
```

### Static initializers
Run once when the class is initialized (typically at first use). Static fields and static blocks run in the order they appear.
```java
class MyClass {
    static final int staticValue;
    static {
        System.out.println("Static initializer running...");
        staticValue = 42;
    }
    public static void main(String[] args) {
        System.out.println(staticValue);
    }
}
```

Extras:
- You cannot reference instance members from a static initializer without creating an instance first.
- Reading a static field before it’s explicitly assigned in a static block yields its default value (0, null, false, etc.).
- Static methods are hidden (not overridden) in subclasses; resolution is based on the reference type at compile time.

## Static imports
Use static import to omit the class name when calling static members.
```java
import static java.util.Arrays.asList;
import static java.util.Arrays.*; // all public static members

// Without static import:
java.util.Arrays.asList(1, 2, 3);
// With static import:
asList(1, 2, 3);
```
Notes:
- Name clashes from multiple static imports can make an unqualified call ambiguous; qualify with the class name in that case.
- Regular imports still allow static calls when prefixed with the class name.
 - java.lang is auto‑imported (classes like String, System, Math), but their static members still require class qualification unless statically imported.

## Overloading Methods
Method overloading = same name, different parameter list (in one class or across inheritance).

Must differ by at least one:
- number of parameters
- parameter types
- parameter order (types)

Doesn’t matter for overloading:
- return type
- access modifier
- exceptions thrown

Resolution order (no exact match): exact → widening → boxing/unboxing → varargs.
```java
class OverloadExample {
    static void print(String s) { System.out.println("String: " + s); }
    static void print(Object o) { System.out.println("Object: " + o); }
    public static void main(String[] args) {
        print("hello"); // String
        print(123);     // int autoboxes to Integer → matches Object
    }
}
```

More tie‑breakers and pitfalls:
```java
void f(long x) {}
void f(Integer x) {}
// f(5) calls f(long) — primitive widening is preferred over boxing

void g(int... xs) {}
void g(Integer x) {}
// g(5) calls g(Integer) — boxing is preferred over varargs

void h(Long x) {}
void h(Integer x) {}
// h(null) is ambiguous — both are equally specific reference types

// The compiler will not do widening + boxing (or unboxing + widening) in one step.
```

## ✅ Constructors
- Can be public, protected, package‑private (default), or private.
- Cannot be static or final.
- No return type.
- If you don’t declare any constructor, the compiler provides a default no‑arg constructor.

Overloading and chaining with this(...):
```java
class Hamster {
    private final String color;
    private final int weight;

    Hamster(int weight) { this(weight, "brown"); }
    Hamster(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }
}
```

Constructor inheritance rules:
- If you don’t call this(...) or super(...), the compiler inserts super() as the first statement.
- If the parent has no no‑arg constructor, you must call an existing super(...).

More constructor notes:
- this(...) and super(...) are mutually exclusive and must be the first statement in the constructor.
- All final fields must be definitely assigned by the end of every constructor.
- Private constructors are common in utility classes (to prevent instantiation) and singletons.

## Order of Initialization
- Class load: superclass static fields/blocks → subclass static fields/blocks (textual order in each).
- Instance creation: superclass instance fields/blocks → superclass constructor → subclass instance fields/blocks → subclass constructor.

Quick trace (no superclass):
```java
class InitOrder {
    static int s = print("0. static field");
    int i = print("2. instance field");
    static { print("1. static block"); }
    { print("3. instance block"); }
    InitOrder() { print("4. constructor"); }
    static int print(String msg) { System.out.println(msg); return 0; }
    public static void main(String[] args) { new InitOrder(); }
}
```

With inheritance:
```java
class A {
    static int sa = print("A: static");
    int ia = print("A: instance");
    A() { print("A: ctor"); }
}
class B extends A {
    static int sb = print("B: static");
    int ib = print("B: instance");
    B() { print("B: ctor"); }
    static int print(String s) { System.out.println(s); return 0; }
    public static void main(String[] args) { new B(); }
}
// Output order:
// A: static
// B: static
// A: instance
// A: ctor
// B: instance
// B: ctor
```

## Encapsulating data
- Make instance variables private to restrict access.
- Provide public getters/setters to control changes.
- Naming: getX()/setX(); for booleans use isX() or getX().

Defensive copying examples (arrays and Date):
```java
class Person {
    private final java.util.Date dob;     // mutable type!
    private final int[] scores;           // mutable array!

    Person(java.util.Date dob, int[] scores) {
        this.dob = new java.util.Date(dob.getTime()); // copy
        this.scores = scores.clone();                 // copy
    }
    public java.util.Date getDob() {
        return new java.util.Date(dob.getTime());     // return a copy
    }
    public int[] getScores() {
        return scores.clone();                        // return a copy
    }
}
```

## Creating Immutable Classes
- Keep fields private and final.
- Don’t provide setters.
- Defensively copy mutable inputs/outputs (arrays, collections, Date).

Tips:
- Consider making the class final to prevent subclass mutation holes.
- For collections, wrap with `Collections.unmodifiableList(...)` (and still defensively copy the input!).
- Avoid leaking `this` from the constructor (e.g., by starting threads that capture a partially constructed object).

## Writing Simple Lambdas
Lambdas are a concise way to supply an implementation for a functional interface (an interface with a single abstract method).

Syntax
```java
(parameters) -> expression
(parameters) -> { statements }
```

Example
```java
interface Tester { boolean test(Animal animal); }

Tester isFeline = a -> a.isFeline();
Tester isCanine = a -> a.isCanine();

// boolean result = isFeline.test(cat);
```

Two parameters
```java
interface Calculator { int apply(int a, int b); }
Calculator add = (a, b) -> a + b;
Calculator subtract = (a, b) -> a - b;
```

Understanding the idea
```java
interface Climb { boolean isTooHigh(int height, int limit); }
class Climber {
    static void check(Climb climb, int height) {
        if (climb.isTooHigh(height, 10)) System.out.println("too high");
        else System.out.println("ok");
    }
    public static void main(String[] args) {
        check((h, l) -> h > l, 5);
    }
}
```
Takeaway: a lambda provides the body for the single abstract method; types often infer; braces/return needed only for multi‑statement bodies.

More lambda essentials:
- Variables captured from the enclosing scope must be effectively final.
- You can annotate your interface with `@FunctionalInterface` to enforce a single abstract method.
- Common built‑ins (java.util.function): Predicate<T>, Consumer<T>, Function<T,R>, Supplier<T>, UnaryOperator<T>, BinaryOperator<T>, BiPredicate<T,U>, BiFunction<T,U,R>, BiConsumer<T,U>.
- Method references are a shorthand: `String::isEmpty`, `System.out::println`, `Integer::parseInt`.

## Predicate and removeIf
Predicate<T> (java.util.function) represents a boolean test of T.
```java
java.util.function.Predicate<Animal> isFeline = animal -> animal.isFeline();
java.util.function.Predicate<Car> isElectric = car -> car.isElectric();
```

Using removeIf on a List
```java
import java.util.*;

List<String> names = new ArrayList<>(List.of("Alice", "Bob", "Alex"));
names.removeIf(name -> name.startsWith("A")); // removes Alice and Alex
```
Why it works: removeIf expects a Predicate<T>—lambdas make simple filtering easy and readable.

Composing predicates:
```java
import static java.util.function.Predicate.*;

var startsWithA = (java.util.function.Predicate<String>) s -> s.startsWith("A");
var endsWithX = (java.util.function.Predicate<String>) s -> s.endsWith("x");

var combined = startsWithA.and(endsWithX).negate();
// Use combined with removeIf/stream.filter as needed
```

## Summary of What Was Covered

You learned the practical rules and gotchas for encapsulation and methods in Java, including:
- How to pick and reason about access/optional modifiers.
- How varargs and overloading interact (and why some calls are ambiguous).
- How static state behaves across instances and when/why initializers run.
- How constructors chain and what the compiler inserts for you.
- The exact order Java uses to initialize classes and objects.
- How to protect state (encapsulation) and design immutable types safely.
- How to write and compose lambdas and use Predicate with collections.

Use the provided examples to see each concept in action and as quick templates during study.

