// ParseVarAssign.kt
// Version 1.0.1

package kconan.parser.grammar

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.grammar.expression.parseExp
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType

import kconan.token.Token
import kconan.token.TokenType

// var_assign = ?ID? ASSIGN exp;;
fun parseVarAssign(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.VAR_ASSIGN,
            "", list[i].line, list[i].column))

    // first child is identifier
    if (list[i].token != TokenType.IDENTIFIER) {
        return ParsingResult(false, head, i)
    }
    head.add(Ast(treeFromIndex(TreeTokenType.IDENTIFIER, i++, list)))

    expect("Expected '=' symbol", TokenType.ASSIGN, i++, list)

    // second child is an expression
    val result = parseExp(i, list)
    if (!result.result) {
        throw Error (
            ErrorType.COMPILE_ERROR,
            "Expected expression", list[i].line, list[i].column)
    }
    head.add(result.tree)
    i = result.index


    expect("Expected ';' symbol", TokenType.SEMICOLON, i++, list)

    return ParsingResult(true, head, i)
}