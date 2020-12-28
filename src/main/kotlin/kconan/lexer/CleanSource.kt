// CleanSource.kt
// Version 1.0.1

package kconan.lexer

import kconan.error.Error
import kconan.error.ErrorType

// Remove comments from source code
// comments are single-line and start with "//"
fun uncomment(sourceCode: String): String {
    var i = 0
    var newSourceCode = "" + sourceCode

    while (i < newSourceCode.length -1) {
        // we skip strings because strings contains "//"
        if (newSourceCode[i] == '\"') {
            i = skipString(newSourceCode, i)
        }
        if (newSourceCode[i] == '/' && newSourceCode[i +1] == '/') {
            // remove all text between "//" and "\n" (inclusive)
            val startIndex = i
            var endIndex = i-- +2

            // reach the newline character
            while (endIndex < newSourceCode.length &&
                newSourceCode[endIndex] != '\n') {
                endIndex++
            }

            // if comment is at the end of file, we don't need to add 1 at endIndex
            if (endIndex != newSourceCode.length) {
                endIndex++
            }

            newSourceCode = newSourceCode.removeRange(startIndex, endIndex)
        }
        i++
    }

    return newSourceCode
}

// return the index of the first character after the string at [startIndex]
// sourceCode[startIndex] must be '"' (double quote)
fun skipString(sourceCode: String, startIndex: Int): Int {
    var endIndex = startIndex + 1
    while (endIndex < sourceCode.length) {
        if (sourceCode[endIndex] == '\\') {
            endIndex++
        }

        if (sourceCode[endIndex] == '\"') {
            return  endIndex +1
        }

        endIndex++
    }

    // if arrive here, no closing " is found
    throw Error(ErrorType.COMPILE_ERROR, "no closing double quote")
}

// Remove all "\n'
fun removeNewlines(sourceCode: String): String {
    return sourceCode.replace("\n", "")
}