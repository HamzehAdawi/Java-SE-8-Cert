## Chapter 1 — Java Building Blocks

Quick overview: source file rules (public class and filename), static vs instance (this), instance initializers, primitives vs references and defaults, numeric literals and identifiers, variable declarations and shadowing, access on locals, garbage collection basics, main method and entry, compile/run workflow, packages/imports, and Java benefits.

Concise summary of core Java language concepts and common rules.

## Code Examples

The following small programs illustrate the key concepts in this chapter. You can find them in `1-Building Blocks/CodeExamples/`:

- `HelloWorld.java` — Minimal class with a standard `main` that prints a greeting (compile/run workflow).
- `PublicClassAndFileDemo.java` — One public top-level class per file; matching filename rule; additional package-private class in same file.
- `StaticAndThisDemo.java` — Static vs instance methods/fields and why `this` isn’t available in static context.
- `InstanceInitializerDemo.java` — Instance initializer blocks vs regular blocks inside methods; scope of locals.
- `PrimitivesVsReferencesDemo.java` — Primitive copy vs reference copy, mutations through shared references, and null references.

### Public classes and files
- You can have multiple top-level classes in one `.java` file, but at most one may be declared `public`.
- If a top-level class is `public`, the file name must match that public class (for example `public class Animal` must be in `Animal.java`).

Example:

```java
public class Animal { }
class Animal2 { }
```

### static methods and the `this` reference
- The `static` modifier ties a method or field to the class rather than an instance. Static methods do not have access to the instance `this` reference.
- Call a static method using the class name (e.g., `Zoo.main(args)`) or from inside the class directly.

### Instance initializers
You can use extra braces inside methods or outside methods. Braces outside methods form instance initializers that run when an instance is created:

```java
public class Example {
    public void saySomething() {
        { System.out.println("This is inside the method."); }
    }

    // Instance initializer (runs when a new Example() is created)
    { System.out.println("I am an instance initializer."); }
}
```

### One-public-class rule
- Only one top-level class in a file may be `public`. If you declare more than one `public` top-level class in the same file, you get a compile-time error.

### Primitives vs. references

Primitives: `boolean`, `byte`, `short`, `int`, `long`, `float`, `double`, `char`.

References (objects): variables that hold references to objects (not the object itself), e.g. `String`, `Date`.

Examples:

```java
Date date;
String greeting;
Date date2 = new Date();
String greeting2 = "hello, bud";
```

References may be assigned `null`; primitives cannot.

### Numeric literals and underscores
- You may use underscores in numeric literals to improve readability, e.g. `1_000_000`.
- Underscores cannot appear at the start or end of a literal, next to a decimal point, or before an `L`, `F`, or other suffix.

Floating-point literals: a literal like `1.0` is a `double` by default. To assign directly to a `float` without a cast you must suffix with `f` or `F` (for example `1.0f`).

Integer byte range: `byte` can hold -128 to 127.

### Identifiers (names)
- Identifiers may start with a letter, `$`, or `_`, followed by letters, digits, `$`, or `_`.
- They cannot start with a digit and cannot be Java reserved keywords (case-sensitive: `public` is reserved, but `Public` is a valid identifier — not recommended).
- CamelCase or camelStyle is the common convention.

### Declaring multiple variables

```java
String s1, s2;                // declares two references, both null until initialized
String s3 = "just s3";      // only s3 is initialized here
```

### Variable categories and defaults
- Local variables (inside methods) do NOT get default values — you must initialize them before use.
- Instance variables (non-static fields) and class variables (static fields) do get default values when an object or class is loaded.

Default values for fields:
- `boolean` = `false`
- `byte`, `short`, `int`, `long` = `0` (or `0L` for long)
- `float`, `double` = `0.0` (or `0.0f` for float)
- `char` = `\u0000`
- Object references (including `String`) = `null`

Note: "instance variables" are per-instance fields; "class variables" usually refers to `static` fields (one per class). Both kinds of fields receive defaults; local variables do not.

### Local variable shadowing
Declaring a local variable with the same name as an instance field hides (shadows) the instance field inside that method. Use `this.fieldName` to access the instance field.

### Access modifiers and local variables
- Local variables cannot have access modifiers (`public`, `private`, etc.).
- The `final` modifier can be applied to local variables.

### Garbage collection (GC)
- Java objects live on the heap. The JVM performs garbage collection to free memory of objects that are no longer reachable from live references.
- `System.gc()` is only a suggestion to the JVM; it does not guarantee immediate GC.
- An object is eligible for GC when no live references to it remain (references may go out of scope or be explicitly set to `null`).

### main method and program entry

Java starts program execution from a recognized `main` method signature. The standard signature the JVM looks for is:

```java
public static void main(String[] args)
```

Variants accepted by the compiler (and recognized by the JVM) include:
- `public static void main(String[] args)` (conventional)
- `public static void main(String... args)` (varargs)
- `public static final void main(String[] args)` (final also allowed)

Notes:
- The parameter name (`args`) is arbitrary.
- The parameter type must be a `String[]` (or varargs `String...`) for the JVM to treat it as the program entry point. If the signature differs (for example `long[] args`), the program will compile but the JVM will report `Main method not found` at runtime.
- The return type must be `void`.

### Compiling and running

To compile:

```text
javac Example.java
```

This produces bytecode in `Example.class`.

To run:

```text
java Example
```

Java is platform-independent because source is compiled to JVM bytecode which can run on any OS with a compatible JVM.

### Packages and imports
- The `java.lang` package is automatically imported by the compiler; you don't need to import it explicitly. It contains things like `System`, `String`, `Math`, etc.

### Benefits of Java (summary)
- Object-oriented but supports functional-style features (lambdas, streams).
- Encapsulation via access modifiers helps protect state.
- Platform independence via bytecode + JVM ("write once, run anywhere").
- Robust: automatic garbage collection reduces many memory errors (though it doesn't eliminate all resource-management bugs).
- Simpler than C++ in many ways: no explicit pointers, no operator overloading.
- Runs inside the JVM, which provides a security sandbox and managed runtime.

## Summary
- Source files: at most one public top-level class per file; filename must match the public class.
- Static vs instance: static members belong to the class; no this in static context.
- Instance initializers: brace blocks at class level run on object creation.
- Primitives vs references: primitives hold values; references hold object addresses; references can be null.
- Literals/underscores: underscores allowed with placement rules; floating-point defaults to double; float needs suffix.
- Identifiers: start with letter/$/_; cannot be keywords; case-sensitive.
- Variable declarations: multiple declarations; locals require initialization before use.
- Defaults: instance/static fields get defaults; locals do not.
- Shadowing: local var can hide a field; use this.field to access the field.
- Locals and modifiers: no access modifiers on locals; final is allowed.
- Garbage collection: eligibility when unreachable; System.gc() is only a hint.
- main method: public static void main(String[] args) (or varargs) is the entry point; must return void.
- Compile/run: javac to compile to bytecode; java to run class by name.
- Packages/imports: java.lang auto-imported.
- Benefits of Java: OO, encapsulation, JVM portability, GC, simpler than C++ in many aspects.


