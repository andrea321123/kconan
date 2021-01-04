// ParseProgram.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.token.Token

// program = var_init;
fun parseProgram(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.PROGRAM,
            "", list[i].line, list[i].column)
    )

    var result = parseVarInit(i, list)
    if (!result.result) {
        return ParsingResult(false, head, result.index)
    }
    head.add(result.tree)
    return ParsingResult(true, head, result.index)
}