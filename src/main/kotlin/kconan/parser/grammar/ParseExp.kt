// ParseExp.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.parser.token.tokenToTreeToken

import kconan.token.Token
import kconan.token.TokenType

// exp = ?ID?;
// exp = number;
fun parseExp(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.EXP, "",
            list[i].line, list[i].column)
    )

    if (list[i].token == TokenType.IDENTIFIER) {
        head.add(Ast(treeFromIndex(TreeTokenType.IDENTIFIER, i, list)))
        return ParsingResult(true, head, ++i)
    }
    if (list[i].token == TokenType.INTEGER_CONSTANT ||
        list[i].token == TokenType.FLOAT_CONSTANT) {
        head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i, list)))
        return ParsingResult(true, head, ++i)
    }

    return ParsingResult(false, head, i)
}