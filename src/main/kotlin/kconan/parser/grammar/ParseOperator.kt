// ParseOperator.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.parser.token.tokenToTreeToken
import kconan.token.Token
import kconan.token.operators

// operator = ADDITION | SUBTRACTION | MULTIPLICATION | DIVISION;
// Children:
// 0: operator type
fun parseOperator(i: Int, list: ArrayList<Token>): ParsingResult {
    val head = Ast(
        TreeToken(
            TreeTokenType.OPERATOR, "",
            list[i].line, list[i].column)
    )

    if (operators.contains(list[i].token)) {
        head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i, list)))
        return ParsingResult(true, head, i +1)
    }

    return ParsingResult(false, head, i)
}