// ParseProgram.kt
// Version 1.0.3

package kconan.parser.grammar

import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.token.Token

import kconan.error.Error
import kconan.error.ErrorType

import java.lang.IndexOutOfBoundsException

// program = var_init | fun_declaration;
// program = (var_init | fun_declaration) program;
fun parseProgram(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    var valid = false
    val head = Ast(
        TreeToken(
            TreeTokenType.PROGRAM,
            "", list[i].line, list[i].column)
    )

    // first child can be var init
    var result: ParsingResult
    try {
        result = parseVarInit(i, list)
    }
    catch (e: IndexOutOfBoundsException) {
        throw Error(ErrorType.COMPILE_ERROR, "Expected var init",
        list[i].line, list[i].column)
    }
    if (result.result) {
        valid = true
        i = result.index
    }

    if (!valid) {
        // first child can be a fun_declaration
        try {
            result = parseFunction(i, list)
        }
        catch (e: IndexOutOfBoundsException) {
            throw Error(ErrorType.COMPILE_ERROR, "Expected function declaration",
                list[i].line, list[i].column)
        }
        if (result.result) {
            valid = true
            i = result.index
        }
    }

    // if we didn't found a var_init or a fun_declaration,
    // throw an error
    if (!valid) {
        throw Error(ErrorType.COMPILE_ERROR, "Invalid input")
    }

    head.add(result.tree)

    // check if we are at the end of file
    if (i == list.size) {
        return ParsingResult(true, head, i)
    }

    result = parseProgram(i, list)
    head.add(result.tree)
    return ParsingResult(true, head, result.index)
}