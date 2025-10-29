# Chapter 5: Class Design

## Topics covered
- Single inheritance vs multiple interface implementation
- Inherited member access (protected vs package-private)
- Constructors and super rules (no-arg vs parameterized)
- Overriding vs static method hiding
- Variables and field hiding; final methods
- Abstract classes and interfaces (default/static methods in Java 8)
- Polymorphism and casting (upcast/downcast, instanceof)
- Virtual method invocation; polymorphic parameters

## Code examples
- Constructors and super: [CodeExamples/ConstructorsAndSuperDemo.java](CodeExamples/ConstructorsAndSuperDemo.java)
- Overriding vs static hiding: [CodeExamples/OverridingVsHidingDemo.java](CodeExamples/OverridingVsHidingDemo.java)
- Abstract classes and interfaces: [CodeExamples/AbstractAndInterfaceDemo.java](CodeExamples/AbstractAndInterfaceDemo.java)
- Polymorphism and casting: [CodeExamples/PolymorphismAndCastingDemo.java](CodeExamples/PolymorphismAndCastingDemo.java)
- Field hiding and final methods: [CodeExamples/FieldHidingAndFinalDemo.java](CodeExamples/FieldHidingAndFinalDemo.java)

### Concept-to-example map
- Constructors and super rules → ConstructorsAndSuperDemo.java
- Overriding and method hiding → OverridingVsHidingDemo.java
- Field hiding and final methods → FieldHidingAndFinalDemo.java
- Abstract classes and interfaces → AbstractAndInterfaceDemo.java
- Polymorphism, casting, and virtual methods → PolymorphismAndCastingDemo.java

## Inheritance basics

Java only allows a class to extend one single class.
Java supports single inheritance; it does not allow multiple inheritance of classes.
Java does allow a class to implement multiple interfaces.


If a class extends a parent, it inherits accessible fields and methods from the parent. Instance members still require an instance to access; static members can be accessed via the subclass name (or unqualified within the subclass).
For instance variables, anything with at least protected access is available to subclasses; you do not need to prefix with the parent class name when accessing inherited members.
Be careful: default (package-private) members are accessible ONLY IF the subclass is in the same package. Protected members are accessible to subclasses across packages, but the access must be through the subclass type (not an unrelated reference of the superclass).

All classes inherit from java.lang.Object:
If you do not extend any class, your class implicitly extends java.lang.Object. If you do extend a class, then that class’s ancestor chain ultimately extends java.lang.Object. Therefore, all classes ultimately inherit from java.lang.Object.


## Constructors and super rules
When a class is extended, be careful with constructors—especially when the parent does not define a no-argument constructor. If the parent has only parameterized constructors, then each child constructor must explicitly call one of them via super(...), or the parent must supply a no-arg constructor.

```java
// Parent class with ONLY parameterized constructor
public class Mammal {
   public Mammal(int age) {
       System.out.println("Mammal age: " + age);
   }
}


// Subclass without calling super(int) (NOT satisfying the int value parameter)
public class Dog extends Mammal {
   public Dog() {
       // Compiler inserts super(); here implicitly, which fails because Mammal has no no-arg constructor ❌
   }
}






// FIX: Add a no-arg constructor to Mammal
public class Mammal {
   public Mammal() { /* no-arg */ }

   public Mammal(int age) {
       System.out.println("Mammal age: " + age);
   }
}


// Now this works fine ✅:
public class Dog extends Mammal {
    public Dog() { /* implicit super(); ✅ OK! */ }
}


// OR: Call the parameterized constructor explicitly
public class Dog extends Mammal {
   public Dog() {
       super(5); // ✅ OK!
   }
}
```


Key Takeaways:
- If you define any constructor and expect subclasses to rely on the no-arg path, also define an explicit no-arg constructor in the parent.
- Otherwise, each child constructor must explicitly call a parent constructor via super(...).





### Reviewing constructor rules
- The first statement of every constructor must be either this(...) to call a sibling constructor, or super(...) to call a parent constructor.
- super(...) may not appear after the first statement of the constructor.
- If no super(...) is declared, Java inserts a no-argument super() as the first statement.
- If the parent does not have a no-arg constructor and the child defines no constructors, the compiler inserts a default no-arg constructor in the child that calls super(), which then fails to compile.
- If the parent does not have a no-arg constructor, the compiler requires an explicit call to a parent constructor in each child constructor.
- You cannot have both this() and super() in the same constructor.


## Overriding methods
When you override a method, the child class provides its own implementation of a method with the same signature as the parent.
The return type may be covariant (a subclass of the parent's return type) for reference types.

Simple example of method override:

```java
class Animal {
   public void speak() {
       System.out.println("Animal speaks");
   }
}

class Dog extends Animal {
   @Override
   public void speak() {
       System.out.println("Dog barks");
   }
}

public class Test {
   public static void main(String[] args) {
       Animal a = new Dog();
       a.speak();  // Output: Dog barks
   }
}
```



Keep in mind, if the method is different, then it is not overridden, it has to be exact. 
Otherwise it's just another method, even if it has the same name, that would just be considered method overloading.




### Calling overridden methods
Within a subclass:
- methodName(...) and this.methodName(...) call the most specific implementation available on the current object (i.e., the overridden child version if present).
- super.methodName(...) calls the parent implementation directly.
Note: private methods are not visible to subclasses and cannot be overridden.



Throwing Exceptions in Overridden Methods: For checked exceptions, an overriding method can declare the same exception(s), a subset, or more specific (subtype) exceptions—or declare none at all. It may not declare a broader checked exception than the parent. Unchecked exceptions (RuntimeException and its subclasses) may be added freely.

Additional override rules:
- The access level cannot be more restrictive than the parent (e.g., cannot change public to protected).
- The return type must be covariant (same type or a subtype for reference types).
- Methods marked final cannot be overridden.

### Private methods in inheritance
Cannot be overridden and if you try to do so it will just end up being a method that is unrelated to the parent's method. 
Methods marked “private” cannot be overridden because they are not visible to subclasses.
If a subclass declares a method with the same name and signature, it’s treated as a new, unrelated method (not an override).


## Static methods (hiding)
Static methods are not overridden; they are considered “hidden”. This happens when a subclass defines a static method with the same signature as a static method in the parent.
Both parent and child methods must be static for hiding to happen.
If the parent method is static and the child method is not static, or vice versa, you get a compile-time error.

Unlike an overridden method, the version of a static method used depends on the reference type (left side), not the object type (right side).

```java
class Parent {
   static void show() {
       System.out.println("Parent static show");
   }
}

class Child extends Parent {
   static void show() {
       System.out.println("Child static show");
   }
}

public class Test {
   public static void main(String[] args) {
       Parent p = new Child();
       p.show();  // Output: Parent static show (based on reference type)

       Child c = new Child();
       c.show();  // Output: Child static show
   }
}
```


RULE: Basically if the child class wants to have the same named static method as the static method in the parent class, it has to match everything exactly. That is considered a hidden method.


### Hidden (static) vs. overridden (instance) methods
Static (hidden) methods: depend on the reference type to call the method (left side).
Overridden methods: depend on the object type to call the method (right side).

Example:

```java
class Parent {
   static void staticMethod() {
       System.out.println("Parent static");
   }

   void instanceMethod() {
       System.out.println("Parent instance");
   }
}

class Child extends Parent {
   static void staticMethod() {
       System.out.println("Child static");
   }

   @Override
   void instanceMethod() {
       System.out.println("Child instance");
   }
}

public class Test {
   public static void main(String[] args) {
       Parent p = new Child();

       p.staticMethod();   // Output: "Parent static" (reference type Parent)
       p.instanceMethod(); // Output: "Child instance"  (actual object is Child)
   }
}
```


This also means we need to watch out: if both the parent and child define a hidden static method with the same name, calls to it are resolved at compile time based on where the call appears, not the runtime object.
Example:

```java
class Parent {
   static void myStaticMethod() {
       System.out.println("Parent static method");
   }

   void parentMethod() {
       myStaticMethod();
   }
}

class Child extends Parent {
   static void myStaticMethod() {
       System.out.println("Child static method");
   }

   void childMethod() {
       myStaticMethod();
   }
}

public class Test {
   public static void main(String[] args) {
       Child c = new Child();

       c.parentMethod();  // prints: Parent static method (resolved in Parent)
       c.childMethod();   // prints: Child static method   (resolved in Child)
   }
}
```


## Overloading and casting
✅ For overloaded methods, resolution happens at compile time based on the reference type and parameter types. Casting changes the reference type, not the underlying object.
Think of casting like putting a different label on a box, not changing the box itself.

```java
interface WaterFowl {}
class Bird {}
class Duck extends Bird implements WaterFowl {
    public void quack() { System.out.println("quack!"); }
    public static void main(String[] args) {
        Object object = new Bird();

        // Unsafe downcast: compiles but throws ClassCastException at runtime
        // Duck bad = (Duck) object; // ❌ runtime error

        Object o2 = new Duck();
        if (o2 instanceof Duck) {
            Duck good = (Duck) o2; // ✅ safe cast
            good.quack();
        }
    }
}
```


## Inheritance & method-call rules


✅ Overloaded methods. We look to the reference type to see what overloaded methods we have available. 

If we have a scenario where the reference is parent (supertype) and object is child (subtype):
We have access to only parent overloaded methods
If we send in parent method matching parameters of an overloaded method, the parent method will execute. 
The child-specific overload is not available because the reference type is the parent.
If we send in the child overloaded method params we will have compiler error

If the reference is child and obj is child:
We have access to the parents method and child methods. 
If we send in child method matching params it works, If we send in parent method matching params it works.

KEY: The reference is like the gatekeeper. Overloaded methods are resolved at compile time based on the reference type, not the actual object type.


✅ Overridden methods (@Override) RULES:

    Overridden instance methods:
We look at the object type to determine what overridden method will run.
The reference type must be the same as or a supertype of the object type in order to compile. You cannot assign a parent object to a child reference.

    
✅ Static methods, static variables, and instance variables:
- Static methods and static variables are resolved based on the reference type (compile time).
- Instance variables (fields) are resolved based on the reference type (field hiding), not the runtime object.
- Instance methods are resolved based on the runtime object (overriding).
The reference type must still be the same as or a supertype of the actual object to compile.



## Final methods
Cannot be overridden.
They can, however, be overloaded (same name, different parameters).

## Variables and hiding
There is no variable overriding in Java.
If the child class declares a field with the same name as the parent, the parent's field is hidden. You can access the parent's value (subject to access) with super.fieldName.


## Abstract classes
Abstract classes are classes that are meant to be extended and used as base types, but never instantiated directly. They often declare common state/behavior and can include abstract methods that subclasses must implement.
For example, Abstract Animal class would get implemented by Swan, Monkey, Cat and so on which you can instantiate. 
You specify which classes are abstract by saying abstract 

```java
abstract class Animal {
        protected int age;

        // abstract method
        abstract void makeSound();

        // non-abstract method
        void eat() {
             System.out.println("Animal is eating.");
        }
}

class Swan extends Animal {
        // implemented abstract method
        @Override
        void makeSound() {
             System.out.println("Swan says: Honk!");
        }
}
```




An abstract class can have its own non-abstract methods and variables.
Abstract classes are not required to have any abstract methods.
Abstract classes and methods CANNOT be final.
Abstract methods CANNOT be private.


CORRECT abstract method is like this:
- ✅ abstract void method();

INCORRECT abstract method:
- ❌ abstract void method() {}



Concrete classes: are the non-abstract classes that implement all the abstract methods in an abstract class or interface. 
They must implement all abstract methods otherwise they become abstract themselves and not truly concrete and if they're not marked abstract you'll get compiler error

Extending an abstract class: you don’t have to immediately have a concrete class extend your abstract class—you can have multiple intermediate abstract classes, but each must be labeled abstract, and eventually a concrete class must implement all inherited abstract methods (including those from the topmost parent):

```java
abstract class Animal {
    abstract void makeSound();
}

abstract class Dog extends Animal {
    abstract void fetch();
}

class Labrador extends Dog {
    @Override
    void makeSound() {
         System.out.println("Bark");
    }

    @Override
    void fetch() {
         System.out.println("Fetching...");
    }
}
```

In this example the last concrete class implemented all the abstract methods including makeSound in animal in order to truly be concrete and the compiler is satisfied. 
Of note: this could have changed too, Dog class could have implemented the abstract method makeSound and Labrador would only have had to implement fetch() to be complete 

### Abstract class rules
- Cannot be instantiated.
- Cannot be final.
- Top-level abstract classes cannot be private or protected (they may be public or package-private).
- An abstract class is not required to declare any abstract methods.
- Concrete subclasses must implement all inherited abstract methods.

## Interfaces
A class can implement multiple interfaces (unlike classes, which support only single inheritance).
Cannot be instantiated.
Not required to have methods.
Cannot be final.
Top-level interfaces can be public or package-private (no modifier), but not private/protected.
In Java 8, interface methods are implicitly public; they cannot be protected or private (private interface methods arrive in Java 9+).
Interfaces can contain non-abstract methods marked default or static (Java 8). Default methods use the default keyword and are implicitly public.
An interface can extend another interface (or multiple). An interface cannot implement another interface—only classes implement interfaces.
Note: Subinterfaces may also declare default methods.

```java
interface Flyer {
    void fly();
}

interface Swimmer {
    void swim();
}

// Interface that extends both
interface SuperAnimal extends Flyer, Swimmer {
    void roar();
}

// Concrete class that implements the combined interface
class Dragon implements SuperAnimal {
    public void fly() {
         System.out.println("Dragon is flying!");
    }

    public void swim() {
         System.out.println("Dragon is swimming!");
    }

    public void roar() {
         System.out.println("Dragon roars!");
    }
}
```

Although you do not necessarily need a concrete class with an interface, if you want it implemented at all then the only way you can do so is through a class implementing said interface. Why?
because another interface cannot implement an interface

What If two implemented interfaces have the same exact method? 
Then the concrete class implementing them only needs to implement it once to satisfy both.
If you do have two interfaces with methods that have the same name but something is different. You must make sure they are overloaded and if so, implement it twice. They are considered overloaded if everything's the same but parameters. (pg. 23, parameter and parameter order difference)

### Interface variables
You can have variables in an interface, but they are always implicitly public static final.
They are constants and cannot be reassigned.
The values are accessible by implementing classes and subinterfaces.
Attempting to change them results in a compiler error.

### Interface methods
By default, non-static, non-default interface methods are implicitly public and abstract.

### Interface default methods
A method in an interface marked as default makes a default method. A default method is inherited by implementing classes and may be optionally overridden.
Default methods exist only in interfaces.
Default methods use the default keyword (this is not the same as default/package-private access). 
All default methods are implicitly public in Java 8 and cannot be private/protected.
Default methods are not abstract, static, or final.

```java
interface Animal {
    // Default method
    default void makeSound() {
         System.out.println("Some generic animal sound");
    }
}

class Dog implements Animal {
    // Inherits makeSound() from Animal
}

public class Main {
    public static void main(String[] args) {
         Dog dog = new Dog();
         dog.makeSound();  // Output: Some generic animal sound
    }
}
```

✅ Be Careful, If two interfaces have the same exact default method, and your class is implementing both those interfaces you get a compiler error. 
One way to fix this is implement that method in YOUR class and this will take away the compiler error. 

### Static interface methods
Behave the same as regular class static methods with a few rules:
- Are implicitly public
- Cannot be overridden or inherited by implementing classes
- Can be called like normal static methods without an instance: InterfaceName.staticMethod();

## Polymorphism
You can set the reference type of an object to its interface that it implements or its parent class. When you do this, you gain access to the members exposed by the reference type.
```java
public class Primate {
   public boolean hasHair() {
       return true;
   }
}

public interface HasTail {
   boolean isTailStriped();
}

public class Lemur extends Primate implements HasTail {
   public boolean isTailStriped() {
       return true;
   }

   public int age = 10;
   public static void main(String[] args) {
       Lemur lemur = new Lemur();
       System.out.println(lemur.age);

       // Interface type as reference
       HasTail hasTail = lemur;
       hasTail.isTailStriped();

       // Parent class as reference type
       Primate primate = lemur;
       primate.hasHair();
   }
}
```

Be careful: the references hasTail and primate don't have access to each other's methods or to Lemur-specific fields.

Object vs Reference: Every variable holds a reference to an object. Changing the reference type changes what members are accessible, not the actual object. For example:

```java
public static void main(String[] args) {
    Lemur lemur = new Lemur();

    HasTail hasTail = lemur;   // access to isTailStriped()
    Primate primate = lemur;    // access to hasHair()
    Object obj = lemur;         // access to Object members only
}
```

### Summary 
All objects can be referenced by java.lang.Object
Can have multiple references to one object even different types as long as its implemented or extended 
Reference type determines what methods/fields are accessible
It all depends on the reference type when it comes to objects and access to methods and variables for that object

## Casting objects
You can cast references back to a more specific type to regain access to subclass methods and fields (using the example above). Upcasting changes the reference type, not the object type.

```java
public static void main(String[] args) {
    Lemur lemur = new Lemur();
    Primate primate = lemur;   // upcast (implicit)

    // primate.isTailStriped(); // does not compile: method not visible on Primate

    Lemur backLemur = (Lemur) primate; // downcast (explicit)
    backLemur.isTailStriped();         // Works now
}
```
### 4 rules of object casting in Java

1) Casting from subclass to superclass (upcasting) does NOT require an explicit cast. This is implicit and always safe.

```java
Primate primate = new Lemur(); // implicit upcast
```

2) Casting from superclass to subclass (downcasting) REQUIRES an explicit cast. This can fail at runtime if the object is not actually an instance of the subclass.

```java
Primate primate = new Lemur();
Lemur lemur = (Lemur) primate; // explicit downcast
```

3) The compiler will NOT allow casting between unrelated types.

4) Even if a cast compiles, a ClassCastException may be thrown at runtime if the object being cast is not an instance of the target class. Use instanceof to safely check before downcasting.

```java
Primate primate = new Primate();
// Compiles with explicit cast but fails at runtime:
Lemur lemur = (Lemur) primate; // ClassCastException

// Safe version
if (primate instanceof Lemur) {
    Lemur ok = (Lemur) primate;
}
```


✅ Be careful: downcasting with a parent reference that actually refers to a parent object (not a child) will always result in a runtime error.

```java
class A { }
class AA extends A { }

public class TestClass {
   public static void main(String[] args) {
       A a2 = (AA) new A();  // throws ClassCastException at runtime!

       A a1 = new AA();
       AA aa1 = (AA) a1;     // safe cast
   }
}
```

	

## Virtual methods
Virtual method invocation is the reason overridden methods are selected at runtime based on the actual object type.

public class Bird {
   public void displayInformation() {
       System.out.println("I am bird");
   }
}


public class Peacock extends Bird {
   @Override
   public void displayInformation() {
       System.out.println("I am Peacock");
   }
   public static void main(String[] args) {
        Bird bird = new Peacock();
        bird.displayInformation(); // Will print "I am Peacock"
   }
}
### Takeaway
Static hidden methods: depend on the reference type to call a method (left side).
Overridden methods: depend on the object type to call a method (right side).


## Polymorphic parameters
If a method accepts a parameter typed as a parent, it can accept instances of any subclass.

class Reptile {
   void speak() {
       System.out.println("Reptile sound");
   }
}


class Alligator extends Reptile {
   void speak() {
       System.out.println("Alligator growls");
   }
}


class Crocodile extends Reptile {
   void speak() {
       System.out.println("Crocodile hisses");
   }
}


public class Zoo {
   static void makeReptileSpeak(Reptile r) {
       r.speak();  // Calls the version based on the actual object type
   }


   public static void main(String[] args) {
       Reptile reptile = new Reptile();
       Alligator alligator = new Alligator();
       Crocodile crocodile = new Crocodile();


       makeReptileSpeak(reptile);
       makeReptileSpeak(alligator);
       makeReptileSpeak(crocodile);
   }
}

## Summary
- Java uses single inheritance for classes; classes can implement multiple interfaces.
- Inheritance gives access to accessible members; protected is visible to subclasses (even across packages), while package-private requires same package.
- Constructors must call either this(...) or super(...); if the parent lacks a no-arg constructor, child constructors must explicitly call a parent constructor.
- Overriding applies to instance methods (supports covariant returns); static methods are hidden, resolved by reference/class context.
- Fields are hidden (not overridden). final methods cannot be overridden but can be overloaded.
- Interfaces (Java 8) can declare default and static methods; default methods are public and can be overridden; static methods are called via the interface name.
- Polymorphism allows parent/interface references to point to child objects; method dispatch for instance methods is based on runtime type (virtual methods).
- Casting: upcasting is implicit and safe; downcasting requires an explicit cast and may fail at runtime—use instanceof first.
