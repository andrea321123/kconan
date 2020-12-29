// Readers.kt
// Version 1.0.3

package kconan.lexer

import kconan.error.Error
import kconan.error.ErrorType
import kconan.token.TokenType

// Return a triple:
// first: number read starting from [startIndex]
// second: index of the first char after the number
// third: TokenType based on the number (INTEGER_CONSTANT or FLOAT_CONSTANT)
fun readNumber(sourceCode: String, startIndex: Int):
        Triple<String, Int, TokenType> {
    // kconan 1.0 supports only decimal numbers,
    // however Conan language itself supports also binary, octal and hex numbers
    var endIndex = startIndex +1;
    var number = "" + sourceCode[startIndex]
    var decimal = false

    while (endIndex < sourceCode.length &&
            (digits.contains(sourceCode[endIndex]) ||
            sourceCode[endIndex] == '.')) {
        if (sourceCode[endIndex] == '.') {
            // if we already found a ., the number finishes
            if (decimal) {
                break
            }
            else {
                decimal = true
            }
        }

        number += sourceCode[endIndex++]
    }

    // if '.' is last char of the number, we remove the '.'
    if (number[number.length -1] == '.') {
        decimal = false
        number = number.dropLast(1)
        endIndex--
    }

    val token = if (decimal) {
        TokenType.FLOAT_CONSTANT
    }
    else {
        TokenType.INTEGER_CONSTANT
    }

    return Triple(number, endIndex, token)
}

// Return a pair:
// first: char read starting from [startIndex] (sourceCode[startIndex] = ')
// second: index of the first char after closing '
// char is collected without opening and closing quotes
fun readChar(sourceCode: String, startIndex: Int): Pair<String, Int> {
    var endIndex = startIndex +1
    var char: String = ""

    try {
        if (sourceCode[endIndex] == '\\') {
            char += '\\'
            endIndex++
        }

        char += sourceCode[endIndex++]

    } catch (e: StringIndexOutOfBoundsException) {
        throw Error(ErrorType.COMPILE_ERROR, "Expected char")
    }

    if (endIndex >= sourceCode.length || sourceCode[endIndex] != '\'') {
        throw Error(ErrorType.COMPILE_ERROR, "Expected ' symbol")
    }

    return Pair(char, endIndex +1)
}

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