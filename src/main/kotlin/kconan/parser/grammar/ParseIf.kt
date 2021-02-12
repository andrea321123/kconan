// ParseIf.kt
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

// if = "if" "(" exp ")" "{" if_body "}" ["else" "{" else_body "}"];
// Children:
// 0: exp
// 1: if body
// [3]: else body
fun parseIf(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.IF,
            "", list[i].line, list[i].column))

    if (list[i++].token != TokenType.IF_KEYWORD) {
        return ParsingResult(false, head, --i)
    }

    expect("Expected '(' symbol", TokenType.OPENING_PARENTHESIS, i++, list)

    // first child is an expression (the condition)
    var result = parseExp(i, list)
    if (!result.result) {
        throw Error(
            ErrorType.COMPILE_ERROR, "Expected expression",
            list[i].line, list[i].column)
    }
    i = result.index
    head.add(result.tree)

    expect("Expected ')' symbol", TokenType.CLOSING_PARENTHESIS, i++, list)
    expect("Expected '{' symbol", TokenType.OPENING_CURLY_BRACKET, i++, list)

    // next child is if body
    result = parseIfBody(i, list)
    i = result.index
    head.add(result.tree)

    expect("Expected '}' symbol", TokenType.CLOSING_CURLY_BRACKET, i++, list)

    // eventually there is an else statement
    if (list[i++].token != TokenType.ELSE_KEYWORD) {
        return ParsingResult(true, head, --i)
    }

    expect("Expected '{' symbol", TokenType.OPENING_CURLY_BRACKET, i++, list)

    // next child is else body
    result = parseElseBody(i, list)
    i = result.index
    head.add(result.tree)

    expect("Expected '}' symbol", TokenType.CLOSING_CURLY_BRACKET, i++, list)

    return ParsingResult(true, head, i)
}

fun parseIfBody(i: Int, list: ArrayList<Token>): ParsingResult {
    return parseBody(i, list, TreeTokenType.IF_BODY)
}

fun parseElseBody(i: Int, list: ArrayList<Token>): ParsingResult {
    return parseBody(i, list, TreeTokenType.ELSE_BODY)
}

// Parse 0 or more statements under a node with [headType] TreeTokenType
private fun parseBody(i: Int,
                      list: ArrayList<Token>,
                      headType: TreeTokenType): ParsingResult {
    val head = Ast(
        TreeToken(
            headType,
            "", list[i].line, list[i].column))
    val i = parseStatements(head, i, list)
    return ParsingResult(true, head, i)
}