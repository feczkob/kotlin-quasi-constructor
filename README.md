# Quasi constructor

## Problem statement
When designing a (container) class in Kotlin that relies on a constructor which accepts a collection of some generic type, it's not easy 
to create a secondary constructor that accepts the same collection of some different type.
This phenomenon is due to type erasure, where the generic type information is removed during compilation,
making it impossible to distinguish between two constructors that accept different generic types.
<details>
  <summary>Type erasure</summary>

Type erasure is a fundamental concept in programming languages with generic types, such as Java and Kotlin. 
It refers to the process by which generic type information is removed or "erased" during compilation and not available at runtime.
For more info see [Java Type erasure](https://www.baeldung.com/java-type-erasure).
</details>

Why would we want to expose another constructor with a collection of a different generic type as an argument?
The answer is for the convenience of the client: If there is a clear way to transfer one type to the other, it would be
beneficial for the client to be able to use the class with the type they are most comfortable with, and not to worry about the conversion between those types. 

So the requirements are:
- The class should have a primary constructor that accepts a collection of some generic type `T`.
- The class should have a secondary constructor that accepts a collection of some different generic type `K`.
- The secondary constructor should be able to convert the collection of the different type to the collection of the primary type (`collection<K> -> collection<T>`).
- The client should be able to use the class with the type they are most comfortable with, without having to know which method to use.

These points are unrealistic in the vast majority of real scenarios, but still, they serve the purpose of easing the burden of the clients,
while making the class more flexible and usable. Luckily, there's a way taking advantage of Kotlin's companion objects and operator overloading to fulfill all requirements.

## Solution
Let's consider a class `ColorStock` that holds a stock of colors of type `Color`, each associated with an `Int` amount.
```kotlin
class ColorStock constructor(private val values: Map<Color, Int>) {

    val colors: Set<Color>
        get() = values.keys

   fun amountFor(color: Color) = values[color] ?: error("Color not found")
}

enum class Color {
    RED, WHITE, GREEN
}
```

This class can be instantiated with a map of `Color` and `Int` values:
```kotlin
val colorStock = ColorStock(mapOf(Color.RED to 10, Color.WHITE to 20, Color.GREEN to 30))
```

So far so good, but let's add a twist: what if another client reads these values from a file, where the colors are represented as strings?
It would be nice if we could spare this client from the effort of converting the strings into enums.
Another examples for this usecase may be reading from database or a Kafka topic, where the enum was stored as string.

### Secondary constructor
First we try to add another constructor to the class, this time taking `Map<String, Int>` as an argument:
```kotlin
class ColorStock constructor(private val values: Map<Color, Int>) {
    ...
    constructor(values: Map<String, Int>) : this(values.mapKeys { Color.valueOf(it.key) })
}
```
However, this code cannot compile:
```
Overload resolution ambiguity. All these functions match.
public constructor ColorStock(values: Map<Color, Int>) defined in com.constructor.quasi.ColorStock
public constructor ColorStock(values: Map<String, Int>) defined in com.constructor.quasi.ColorStock
```
The compiler cannot distinguish between the two constructors, as the generic type information is erased during compilation.

### Factory method
A viable solution however is adding a static method to the `ColorStock` that takes the string color and the amount, and performs the transformation for the client:
```kotlin
class ColorStock constructor(private val values: Map<Color, Int>) {
    ...
    companion object {
        fun fromStringMap(values: Map<String, Int>) = ColorStock(values.mapKeys { valueOf(it.key) })
    }
}
```

This does the job, but now the client needs to know that they should use the `fromStringMap` method instead of the primary constructor.
```kotlin
val colorStock = ColorStock.fromStringMap(mapOf("RED" to 10, "WHITE" to 20, "GREEN" to 30))
```

### Quasi constructor
This is where the "quasi "constructor" comes into play. By using Kotlin's `invoke` operator, we can create a factory method 
that allows for the instantiation of `ColorStock` with `Map<String, Int>` as argument, handling the conversion internally.
```kotlin
class ColorStock constructor(private val values: Map<Color, Int>) {
    ...
    companion object {
       operator fun invoke(values: Map<String, Int>) = ColorStock(values.mapKeys { Color.valueOf(it.key) })
    }
}
```

Then the client can call both methods.
Thanks to the Kotlin conventions, the `invoke` method can be called without the method name:
```kotlin
val first = ColorStock(mapOf(Color.RED to 10, Color.WHITE to 20, Color.GREEN to 30))
val second = ColorStock.invoke(mapOf("RED" to 10, "WHITE" to 20, "GREEN" to 30))
val third = ColorStock(mapOf("RED" to 10, "WHITE" to 20, "GREEN" to 30))
```
Here the first line calls the primary constructor, while the second and third lines call the quasi constructor.

## Summary
This small example demonstrates how to create a constructor in Kotlin which makes it possible to 
instantiate a class with a collection of a different type than its primary constructor. 
This makes it easier for the clients to use the class in a uniform way, without having to worry about the conversion between the types.
To achieve this, we used Kotlin's `invoke` operator and a companion object.

The solution was tailored for Kotlin clients, however we could call the `invoke` method from Java as well:
```java
ColorStock colorStock = ColorStock.Companion.invoke(Map.of("RED", 10, "WHITE", 20, "GREEN", 30));
```

Note that this approach works between any two interchangeable types, not just `Color` and `String`.
For additional constructors with `varargs`, check the [feat/with-varargs]() branch.
For more on the usage, see the tests.
