// ParseStatement.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.token.Token

// statement = var_assign;
fun parseStatement(i: Int, list: ArrayList<Token>): ParsingResult {
    val head = Ast(TreeToken(TreeTokenType.STATEMENT, "",
        list[i].line, list[i].column))

    val result = parseVarAssign(i, list)

    if (result.result) {
        head.add(result.tree)
    }

    return ParsingResult(result.result, head, result.index)
}