// ParseNumber.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.parser.token.tokenToTreeToken

import kconan.token.Token
import kconan.token.TokenType

// number = ?INT? | ?FLOAT?;
fun parseNumber(i: Int, list: ArrayList<Token>): ParsingResult {
    val head = Ast(
        TreeToken(
            TreeTokenType.NUMBER,
            "", list[i].line, list[i].column)
    )

    if (list[i].token == TokenType.INTEGER_CONSTANT ||
        list[i].token == TokenType.FLOAT_CONSTANT) {
        head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i, list)))
        return ParsingResult(true, head, i +1)
    }

    return ParsingResult(false, head, i)
}