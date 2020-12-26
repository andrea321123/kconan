// Error.kt
// Version 1.0.0

package kconan.error

// Represent an error message from kconan
// error format:
// kconan: [error type]: ([line]: [column]: )[message]
class Error(private val errorType: ErrorType,
            private val info: String): Exception() {
    private var line: Long = 0
    private var column: Long = 0

    constructor(errorType: ErrorType,
                info: String,
                line: Long): this(errorType, info) {
        this.line = line
    }
    constructor(errorType: ErrorType,
                info: String,
                line: Long,
                column: Long): this(errorType, info, line) {
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

        if (line != 0L) {
            returnString += "$line: "
        }

        if (column != 0L) {
            returnString += "$column: "
        }

        return returnString + info
    }
}