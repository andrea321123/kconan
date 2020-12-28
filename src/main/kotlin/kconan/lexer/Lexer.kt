// Lexer.kt
// Version 1.0.1

package kconan.lexer

// Return the index of the first char != ' ' after startIndex
fun skipSpaces(sourceCode: String, startIndex: Int): Int {
    var endIndex = startIndex

    while (endIndex < sourceCode.length && sourceCode[endIndex] == ' ') {
        endIndex++
    }

    return endIndex
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