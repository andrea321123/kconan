// ParseParameters.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.parser.token.tokenToTreeToken
import kconan.token.Token
import kconan.token.TokenType

// parameters = {parameter COMMA} parameter
// parameters = ?EMPTY?
// Children:
// [0..n]: parameter
fun parseParameters(i: Int, list: ArrayList<Token>): ParsingResult {
    val head = Ast(
        TreeToken(
            TreeTokenType.PARAMETERS, "",
            list[i].line, list[i].column)
    )
    var i = i

    // first handle ?EMPTY? rule
    if (list[i].token == TokenType.CLOSING_PARENTHESIS) {
        return ParsingResult(true, head, i)
    }


    if (!parseParameter(i, list).result) {
        throw Error(ErrorType.COMPILE_ERROR, "Expected parameter",
            list[i].line, list[i].column)

    }

    // children are parameter
    while (true) {
        val result = parseParameter(i++, list)
        if (!result.result) {
            throw Error(ErrorType.COMPILE_ERROR, "Expected parameter",
                list[i].line, list[i].column)
        }
        head.add(result.tree)
        i = result.index

        try {
            expect("Expected ',' symbol", TokenType.COMMA, i++, list)
        } catch (e: Error) {
            break
        }
    }

    return ParsingResult(true, head, --i)
}