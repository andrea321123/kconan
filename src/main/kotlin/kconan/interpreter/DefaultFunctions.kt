// DefaultFunctions.kt
// Version 1.0.1

package kconan.interpreter

val defaultFunctionSet = setOf(
    "print",
    "println",
    "input"
)

// map function -> number of arguments
val defaultFunctionsArguments = mapOf(
    "print" to 1,
    "println" to 1,
    "input" to 0
)

// run the function
fun runDefaultFunction(name: String, arguments: ArrayList<Int>): Int {
    return when (name) {
        "print" -> defaultPrint(arguments)
        "println" -> defaultPrintln(arguments)
        "input" -> defaultInput(arguments)

        else -> 0
    }
}

fun defaultPrint(arguments: ArrayList<Int>): Int {
    print(arguments[0])
    return 1
}

fun defaultPrintln(arguments: ArrayList<Int>): Int {
    println(arguments[0])
    return 1
}

fun defaultInput(arguments: ArrayList<Int>): Int {
    return Integer.parseInt(readLine())   // because kconan 1.0 only supports i32 type
}