// DefaultFunctions.kt
// Version 1.0.0

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
fun runDefaultFunction() {

}

fun defaultPrint(): Int {
    return 1
}

fun defaultPrintln(): Int {
    return 1
}

fun defaultInput(): Int {
    return 1
}