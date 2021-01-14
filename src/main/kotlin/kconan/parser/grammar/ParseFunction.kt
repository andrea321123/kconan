// ParseFunction.kt
// Version 1.0.2

package kconan.parser.grammar

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.parser.token.tokenToTreeToken
import kconan.token.Token
import kconan.token.TokenType

// fun_declaration = FUN ?ID? COLON type OPEN_PARENTHESIS parameters CLOSE_PARENTHESIS OPEN_CURLY_BRACKET {statement} CLOSE_CURLY_BRACKET;
fun parseFunction(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.FUNCTION,
            "", list[i].line, list[i].column))

    if (list[i++].token != TokenType.FUN_KEYWORD) {
        return ParsingResult(false, head, --i)
    }

    // first child is identifier
    expect("Expected identifier", TokenType.IDENTIFIER, i, list)
    head.add(Ast(treeFromIndex(TreeTokenType.IDENTIFIER, i++, list)))

    expect("Expected '(' symbol", TokenType.OPENING_PARENTHESIS, i++, list)

    // second child is parameters
    val result = parseParameters(i, list)
    if (!result.result) {
        throw Error(ErrorType.COMPILE_ERROR, "Expected parameters",
        list[i].line, list[i].column)
    }
    i = result.index
    head.add(result.tree)

    expect("Expected ')' symbol", TokenType.CLOSING_PARENTHESIS, i++, list)

    expect("Expected ':' symbol", TokenType.COLON, i++, list)

    // third child is var type
    expectType("Expected type declaration", i, list)
    head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i++, list)))

    expect("Expected '{' symbol", TokenType.OPENING_CURLY_BRACKET, i++, list)

    // next children are statement
    i = parseStatements(head, i, list)

    expect("Expected '}' symbol", TokenType.CLOSING_CURLY_BRACKET, i++, list)

    return ParsingResult(true, head, i)
}

fun parseStatements(head: Ast, i: Int, list: ArrayList<Token>): Int {
    var i = i
    var result = parseStatement(i, list)

    while (result.result) {
        // we update the tree
        i = result.index
        head.add(result.tree)

        // we read the next statement (with updated index i)
        result = parseStatement(i, list)
    }

    return i
}