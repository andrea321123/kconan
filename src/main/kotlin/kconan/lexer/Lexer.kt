// Lexer.kt
// Version 1.0.5

package kconan.lexer

import kconan.error.Error
import kconan.error.ErrorType

import kconan.token.Token

// Return an ArrayList of tokens made of all the symbols in the string
fun getSymbolsTokens(symbolsString: String, line: Int, column: Int):
        ArrayList<Token> {
    // exit conditions
    if (symbolsString.length == 1) {
        if (size1Symbols.contains(symbolsString)) {
            val list = ArrayList<Token>()
            list.add(Token(symbolsToToken[symbolsString]!!,
                symbolsString, line, column))
            return list
        }

        throw Error(ErrorType.COMPILE_ERROR,
            "Unknown symbol: ${symbolsString[0]}", line, column)

    }
    if (symbolsString.isEmpty()) {
        return ArrayList()      // return an empty list
    }

    // since Conan operators aren't longer than 2 chars,
    // we check at first size 2 operators
    val firstTwo = symbolsString.substring(0, 2)
    if (size2Symbols.contains(firstTwo)) {
        val otherChars = symbolsString.substring(2, symbolsString.length)
        val list = ArrayList<Token>()
        list.add(Token(symbolsToToken[firstTwo]!!, firstTwo, line, column))
        list.addAll(getSymbolsTokens(otherChars, line, column +2))
        return list
    }
    else {
        // we treat the first char as a size 1 operator,
        // and we call getSymbolsToken() on other characters
        val firstChar = symbolsString.substring(0, 1)
        val otherChars = symbolsString.substring(1, symbolsString.length)
        val list = getSymbolsTokens(firstChar, line, column)
        list.addAll(getSymbolsTokens(otherChars, line, column +1))
        return list
    }
}

// Return the index of the first char != ' ' after startIndex
fun skipSpaces(sourceCode: String, startIndex: Int): Int {
    var endIndex = startIndex

    while (endIndex < sourceCode.length && sourceCode[endIndex] == ' ') {
        endIndex++
    }

    return endIndex
}