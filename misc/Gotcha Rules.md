# Gotcha Rules


### Final keyword
✅ Be careful about final:
- For primitives and String, if it's final you cannot change the value at all.
- For ArrayList, arrays, and other objects: final on a reference means you cannot change the reference, but you can change the object’s contents.
- Final for variables means the variable can be assigned only once. After that, you cannot change its value or reference.

✅ Final quick reference:
- final classes → cannot be extended
- final methods → cannot be overridden
- final variables → value/reference cannot change after first assignment

### Control structures
✅ You can omit the braces of a for loop and if statement in Java; it applies only to the very next line.

✅ For Loop Structure: The for loop has 3 parts: initialization; condition; update. If any of these parts are missing, the loop still compiles but may behave unexpectedly.
For example, missing condition makes it infinite unless broken inside the loop body:

```java
for (int i = 0; ; i++) { /* ... */ }  // Infinite loop unless break
```

### Interfaces and abstract classes
✅ Rules to remember:
- Final methods cannot be overridden; trying to do so causes a compile-time error.
- Private methods in abstract classes are not inherited; declaring a same-signature method in a subclass is not overriding.
- Interfaces in Java 8 and below cannot have private methods.
- Static methods in classes cannot be overridden, only hidden.
- Methods that are not final, private, or static and are visible (public/protected) can be overridden.
- By default, methods in interfaces are public abstract (not in abstract classes).
- Variables in both abstract classes and interfaces can be hidden, not overridden.

✅Calling a method with a null element (say in an array) will cause a runtime exception:

```java
String[] array = new String[2];
// Element at index 1 is still null; calling concat() throws a NullPointerException
array[1].concat("1");
```

### Constructors and access
✅Be careful of constructors and their access modifiers. This could cause a compiler error to extend a class if it does not have a public enough modifier.

### Object creation and references
✅When you do new Object() alone, this means this object and reference type are the same.  

### Operators and precedence
✅ Whenever you see assignment "=" mixed directly within comparison operators like ==, !=, <, or >, be on high alert for potential compiler error. Parentheses may be required due to precedence.

✅In Java the '.' operator has higher precedence than the cast operator. So you have to write it like this:

```java
int k = ((Integer) t).intValue() / 9;  // ✅ correct
```

Otherwise you get a compiler error:

```java
int k = (Integer) t.intValue() / 9;    // ❌ incorrect
```

### Overriding and returns
✅ When overriding a method, the child class's method return type must be either:
Exactly the same as the parent method's return type, or
A subclass (more specific) of the parent method's return type.
This is called covariant return type.

### String utilities
✅For substring (beginIndex, endIndex), the endIndex can be equal to the string’s length to represent the end of the string (exclusive). This means:

```java
String s = "HELLO";
System.out.println(s.substring(2, 5));  // ✅ prints "LLO"
```

### Interface redeclaration
✅ Interfaces can redeclare the same method signature when extending one another without issues. 
This doesn’t require multiple implementations; any class implementing the subinterface provides one single implementation of that method.

### Variable shadowing (scope)
You can declare a local variable in a method with the same name as an instance variable (field).
The local variable shadows the instance variable inside the method scope.
Inside the method, referencing the variable name uses the local variable, not the instance variable.
To access the instance variable when shadowed, use this.variableName.  Before shadowing you would have just used the variable name normal
Example:

```java
class Example {
   int i = 10;  // instance variable

   void test() {
       int i = 5;                   // local variable shadows instance variable
       System.out.println(i);       // prints 5
       System.out.println(this.i);  // prints 10
   }
}
```




### Dangling else clause
If there are nested if-else statements without braces {}, each else is associated with the closest unmatched if.
This can cause confusing behavior, where the else may pair with an inner if instead of the outer one.

### Unreachable code in if vs others
if() is allowed to be unreachable and will not throw any errors.
do-while() is also allowed and will not error because it always runs once, no error.
while()  and for(...; false; ...) will cause unreachable code errors (loop body never runs).


✅An Overridden method can be marked final thus cutting of any overridding past that point
You can mark an overridden method as final in a subclass.
This prevents any further subclass from overriding it.

✅ Java automatically performs widening conversions, but it cannot promote and widen; you'll get a compile error. Also it never narrows types implicitly.

```java
public class WidenBoxDemo {
   public static void main(String[] args) {
       Byte b = 10;
       takeLong(b); // ❌ Compile error
   }

   static void takeLong(Long l) {
       System.out.println("Received: " + l);
   }
}
```


### equals and wrappers
✅ equals() is universally available for all classes
Every class in Java inherits equals(Object obj) from java.lang.Object.
So you can call .equals() on any object, no matter the type.
Whether it gives meaningful results depends on how (or if) it's overridden in that class.

✅ Wrapper classes like Integer, Long, Double .equals() behavior
First compares if the objects are the same (if not false) then if same, it compares the actual values and returns true or false respectively . 
So Integer.equals(Long) returns false, even if values match. Use .intValue() == .longValue() for numeric comparison.

### Command-line args
✅ String[] args is not null when no parameters are passed; it’s an empty array. Accessing a non-existent element throws ArrayIndexOutOfBoundsException.

### Printing/using null strings
✅When a String is null, printing it or concatenating with += shows the literal "null":

```java
public class Example1 {
   public static void main(String[] args) {
       String s = null;
       System.out.println(s); // prints: null
       s += " world";
       System.out.println(s); // prints: null world
   }
}
```


If you call a method on it (e.g., .concat() or .length), you'll get a NullPointerException:

```java
public class Example2 {
   public static void main(String[] args) {
       String s = null;
       System.out.println(s.length());   // throws NullPointerException
       // or
       // s = s.concat("test");        // throws NullPointerException
   }
}
```