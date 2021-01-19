// ParseStatement.kt
// Version 1.0.1

package kconan.parser.grammar

import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.token.Token

// statement = var_assign;
// statement = while;
fun parseStatement(i: Int, list: ArrayList<Token>): ParsingResult {
    val head = Ast(TreeToken(TreeTokenType.STATEMENT, "",
        list[i].line, list[i].column))

    // can be a var assign
    var result = parseVarAssign(i, list)
    if (result.result) {
        head.add(result.tree)
        return ParsingResult(true, head, result.index)
    }

    // can be a while loop
    result = parseWhile(i, list)
    if (result.result) {
        head.add(result.tree)
        return ParsingResult(true, head, result.index)
    }

    return ParsingResult(false, head, i)
}