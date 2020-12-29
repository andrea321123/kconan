// Error.kt
// Version 1.0.2

package kconan.error

// Represent an error message from kconan
// error format:
// kconan: [error type]: ([line]: [column]: )[message]
class Error(val errorType: ErrorType,
            val info: String): Exception() {
    var line: Int = 0
    var column: Int = 0

    constructor(errorType: ErrorType,
                info: String,
                line: Int): this(errorType, info) {
        this.line = line
    }
    constructor(errorType: ErrorType,
                info: String,
                line: Int,
                column: Int): this(errorType, info, line) {
        this.column = column
    }

    // return a formatted error message
    override fun toString(): String {
        var returnString = "kconan: "

        var errorTypeString = when (errorType) {
            ErrorType.INPUT_ERROR -> "Input error: "
            ErrorType.COMPILE_ERROR -> "Compile error: "
            ErrorType.RUNTIME_ERROR -> "Runtime error: "
        }
        returnString += errorTypeString

        if (line != 0) {
            returnString += "$line: "
        }

        if (column != 0) {
            returnString += "$column: "
        }

        return returnString + info
    }
}