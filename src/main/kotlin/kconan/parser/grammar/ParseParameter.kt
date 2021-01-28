// ParseParameter.kt
// Version 1.0.1

package kconan.parser.grammar

import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.parser.token.tokenToTreeToken
import kconan.token.Token
import kconan.token.TokenType

// parameter = ?ID? COLON numeric_type
// Children:
// 0: id
// 1: type
fun parseParameter(i: Int, list: ArrayList<Token>): ParsingResult {
    val head = Ast(
        TreeToken(
            TreeTokenType.PARAMETER, "",
            list[i].line, list[i].column)
    )
    var i = i

    // first child is identifier
    if (list[i].token == TokenType.IDENTIFIER) {
        head.add(Ast(treeFromIndex(TreeTokenType.IDENTIFIER, i++, list)))
    }
    else {
        return ParsingResult(false, head, i)
    }

    expect("Expected ':' symbol", TokenType.COLON, i++, list)

    // second child is var type
    expectType("Expected type declaration", i, list)
    head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i++, list)))

    return ParsingResult(true, head, i)
}