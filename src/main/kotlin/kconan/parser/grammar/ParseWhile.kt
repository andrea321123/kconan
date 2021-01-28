// ParseWhile.kt
// Version 1.0.0

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

// while = WHILE "(" exp ")" "{" {statement} "}"
// Children:
// 0: exp
// [1..n]: statements
fun parseWhile(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.WHILE,
            "", list[i].line, list[i].column)
    )

    if (list[i++].token != TokenType.WHILE_KEYWORD) {
        return ParsingResult(false, head, --i)
    }

    expect("Expected '(' symbol", TokenType.OPENING_PARENTHESIS, i++, list)

    // first child is an expression (the condition)
    val result = parseExp(i, list)
    if (!result.result) {
        throw Error(ErrorType.COMPILE_ERROR, "Expected expression",
        list[i].line, list[i].column)
    }
    i = result.index
    head.add(result.tree)

    expect("Expected ')' symbol", TokenType.CLOSING_PARENTHESIS, i++, list)
    expect("Expected '{' symbol", TokenType.OPENING_CURLY_BRACKET, i++, list)

    // next children are statements
    i = parseStatements(head, i, list)

    expect("Expected '}' symbol", TokenType.CLOSING_CURLY_BRACKET, i++, list)

    return ParsingResult(true, head, i)
}