// Parser.kt
// Version 1.0.7

package kconan.parser.grammar

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType

import kconan.token.Token
import kconan.token.TokenType
import kconan.token.types

fun parse(list: ArrayList<Token>): ParsingResult {
    return parseProgram(0, list)
}

// throw an error if the token at [index] isn't [tokenType]
fun expect(errorMessage: String,
                   tokenType: TokenType,
                   index: Int,
                   list: ArrayList<Token>) {
    if (list[index].token != tokenType) {
        throw Error(ErrorType.COMPILE_ERROR,
            errorMessage, list[index].line, list[index].column)
    }
}

// throw an error if the token at [index] isn't a type
fun expectType(errorMessage: String,
               index: Int,
               list: ArrayList<Token>) {
    if (!types.contains(list[index].token)) {
        throw Error(ErrorType.COMPILE_ERROR,
            errorMessage, list[index].line, list[index].column)
    }
}

// return a new Tree token with value, line, column from list[index]
fun treeFromIndex(token: TreeTokenType,
                  index: Int,
                  list: ArrayList<Token>): TreeToken {
    return TreeToken(token,
        list[index].value, list[index].line, list[index].column)
}

fun parseStatements(head: Ast, i: Int, list: ArrayList<Token>): Int {
    var i = i
    var result = parseStatement(i, list)

    while (result.result) {
        // we update the tree
        i = result.index
        head.add(result.tree)

        // we read the next statement (with updated index i)
        result = parseStatement(i, list)
    }

    return i
}