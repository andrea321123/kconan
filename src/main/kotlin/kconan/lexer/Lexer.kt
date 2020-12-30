// Lexer.kt
// Version 1.0.6

package kconan.lexer

import kconan.error.Error
import kconan.error.ErrorType

import kconan.token.Token
import kconan.token.TokenType

// Return an ArrayList of tokens based on source code
fun doLexing(sourceCode: String): ArrayList<Token> {
    var line = 1
    var column = 1
    var i = 0       // i refers to the char that we're analyzing

    val tokenList = ArrayList<Token>()
    val source = uncomment(sourceCode)

    while (i < source.length) {
        // skip possible ' ' characters
        val spaces = skipSpaces(source, i) - i
        column += spaces
        i += spaces
        if (i >= source.length) {
            break
        }

        // if we find a newline, update line and column
        if (source[i] == '\n') {
            line++
            column = 1
            i++
            continue
        }

        // next element can be:
        // - identifier
        // - keyword
        // - symbols
        // - number
        // - char
        // - string
        val firstChar = source[i]
        var newI: Int
        when {
            digits.contains(firstChar) -> {     // number
                val triple = readNumber(source, i)
                tokenList.add(Token(triple.third, triple.first, line, column))
                newI = triple.second
            }
            symbolsList.contains(firstChar) -> {    // symbols
                val pair = readSymbols(source, i)
                tokenList.addAll(getSymbolsTokens(pair.first, line, column))
                newI = pair.second
            }
            firstChar == '\'' -> {      // char
                try {
                    val pair = readChar(source, i)
                    tokenList.add(Token(TokenType.CHAR_CONSTANT, pair.first, line, column))
                    newI = pair.second
                } catch (e: Error) {
                    throw Error(e.errorType, e.info, line, column)
                }
            }
            firstChar == '\"' -> {      // string
                try {
                    val pair = readString(source, i)
                    tokenList.add(Token(TokenType.STRING_CONSTANT, pair.first, line, column))
                    newI = pair.second
                } catch (e: Error) {
                    throw Error(e.errorType, e.info, line, column)
                }
            }
            validFirstLetters.contains(firstChar) -> {      // word
                val pair = readWord(source, i)
                val word = pair.first
                if (conanWords.contains(word)) {        // conan word
                    tokenList.add(Token(keywordToToken[word]!!, word, line, column))
                } else {                                  // identifier
                    tokenList.add(Token(TokenType.IDENTIFIER, word, line, column))
                }
                newI = pair.second
            }

            // if nothing matches, throw an error
            else -> {
                throw Error(
                    ErrorType.COMPILE_ERROR,
                    "Unknown symbol: $firstChar", line, column
                )
            }
        }

        // update index and column
        column += newI - i
        i = newI
    }

    return tokenList
}

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