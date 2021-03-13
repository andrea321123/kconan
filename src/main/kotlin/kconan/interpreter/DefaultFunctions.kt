// DefaultFunctions.kt
// Version 1.0.2

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
fun runDefaultFunction(name: String, arguments: ArrayList<Variable>): Variable {
    return when (name) {
        "print" -> defaultPrint(arguments)
        "println" -> defaultPrintln(arguments)
        "input" -> defaultInput(arguments)

        else -> Variable(VarTypeEnum.I32, 0)
    }
}

fun defaultPrint(arguments: ArrayList<Variable>): Variable {
    print(arguments[0].value as Int)
    return Variable(VarTypeEnum.I32, 1)
}

fun defaultPrintln(arguments: ArrayList<Variable>): Variable {
    println(arguments[0].value as Int)
    return Variable(VarTypeEnum.I32, 1)
}

fun defaultInput(arguments: ArrayList<Variable>): Variable {
    return Variable(VarTypeEnum.I32, Integer.parseInt(readLine())) // In kconan 1.0, return type is i32
}