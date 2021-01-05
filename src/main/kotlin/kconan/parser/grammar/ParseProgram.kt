// ParseProgram.kt
// Version 1.0.2

package kconan.parser.grammar

import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.token.Token

import kconan.error.Error
import kconan.error.ErrorType

import java.lang.IndexOutOfBoundsException

// program = var_init;
// program = var_init program;
fun parseProgram(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.PROGRAM,
            "", list[i].line, list[i].column)
    )

    // first child must be var init
    var result: ParsingResult
    try {
        result = parseVarInit(i, list)
    }
    catch (e: IndexOutOfBoundsException) {
        throw Error(ErrorType.COMPILE_ERROR, "Expected var init",
        list[i].line, list[i].column)
    }

    if (!result.result) {
        throw Error(
            ErrorType.COMPILE_ERROR, "Expected var init",
            list[i].line, list[i].column)
    }
    i = result.index
    head.add(result.tree)

    // check if there are other var init
    if (i == list.size) {
        return ParsingResult(true, head, i)
    }
    result = parseProgram(i, list)
    head.add(result.tree)
    return ParsingResult(true, head, i)
}