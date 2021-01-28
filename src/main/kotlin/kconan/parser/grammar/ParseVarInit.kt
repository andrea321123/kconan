// ParseVarInit.kt
// Version 1.0.1

package kconan.parser.grammar

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.grammar.expression.parseExp
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.parser.token.tokenToTreeToken
import kconan.token.Token
import kconan.token.TokenType

// var_init = VAR ?ID? COLON type ASSIGN exp;;
// children:
// 0: id
// 1: type
// 2: exp
fun parseVarInit(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.VAR_INIT,
            "", list[i].line, list[i].column)
    )

    if (list[i++].token != TokenType.VAR_KEYWORD) {
        return ParsingResult(false, head, --i)
    }

    // first child is identifier
    expect("Expected identifier", TokenType.IDENTIFIER, i, list)
    head.add(Ast(treeFromIndex(TreeTokenType.IDENTIFIER, i++, list)))

    expect("Expected ':' symbol", TokenType.COLON, i++, list)

    // second child is var type
    expectType("Expected type declaration", i, list)
    head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i++, list)))

    expect("Expected '=' symbol", TokenType.ASSIGN, i++, list)

    // third child is an expression
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