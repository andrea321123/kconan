// Lexer.kt
// Version 1.0.3

package kconan.lexer

// Return the index of the first char != ' ' after startIndex
fun skipSpaces(sourceCode: String, startIndex: Int): Int {
    var endIndex = startIndex

    while (endIndex < sourceCode.length && sourceCode[endIndex] == ' ') {
        endIndex++
    }

    return endIndex
}