// Readers.kt
// Version 1.0.0

package kconan.lexer

import kconan.error.Error
import kconan.error.ErrorType

// Return a pair:
// first: string read starting from [startIndex] (sourceCode[startIndex] = ")
// second: index of the first char after closing "
// string is collected without opening and closing quotes
fun readString(sourceCode: String, startIndex: Int): Pair<String, Int> {
    var endIndex = startIndex + 1
    var string = ""
    while (endIndex < sourceCode.length) {
        if (sourceCode[endIndex] == '\\') {
            string += sourceCode[endIndex++]
        }

        if (sourceCode[endIndex] == '\"') {
            return  Pair(string, endIndex +1)
        }

        string += sourceCode[endIndex]
        endIndex++
    }

    // if arrive here, no closing " is found
    throw Error(ErrorType.COMPILE_ERROR, "no closing double quote")
}

// Return a pair:
// first: word read starting from [startIndex]
// second: index of the first char after the word
// A word can contain only [a..z][A..Z][0..9][_]; first letter can't be [0..9]
fun readWord(sourceCode: String, startIndex: Int): Pair<String, Int> {
    return read(sourceCode, startIndex, validLetters)
}

// Return a pair:
// first: symbols read starting from [startIndex]
// second: index of the first char after the symbols
fun readSymbols(sourceCode: String, startIndex: Int): Pair<String, Int> {
    return read(sourceCode, startIndex, symbolsList)
}

private fun read(sourceCode: String, startIndex: Int,
                 set: Set<Char>): Pair<String, Int>{
    var endIndex = startIndex +1
    var word = "" + sourceCode[startIndex]

    while (endIndex < sourceCode.length &&
        set.contains(sourceCode[endIndex])) {
        word += sourceCode[endIndex++]
    }

    return Pair(word, endIndex)
}