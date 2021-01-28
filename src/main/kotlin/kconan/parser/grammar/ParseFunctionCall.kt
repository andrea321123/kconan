// ParseFunctionCall.kt
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

// function_call = ?ID? "(" exp ")";
// function_call = ?ID? "(" ")";
// function_call = ?ID? "(" {exp ","} exp);
// Children:
// 0: id
// [1..n]: exp
fun parseFunctionCall(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.FUNCTION_CALL,
            "", list[i].line, list[i].column)
    )

    // first child is an identifier
    if (list[i].token != TokenType.IDENTIFIER) {
        return ParsingResult(false, head, i)
    }
    head.add(Ast(treeFromIndex(TreeTokenType.IDENTIFIER, i++, list)))

    if (list[i++].token != TokenType.OPENING_PARENTHESIS) {
        return ParsingResult(false, head, i)
    }

    var result = parseExp(i, list)

    // next children are expressions (arguments)
    if (result.result) {
        head.add(result.tree)
        i = result.index

        // we add all the following arguments separated by a comma
        while (list[i].token == TokenType.COMMA) {
            result = parseExp(++i, list)

            if (!result.result) {
                throw Error(ErrorType.COMPILE_ERROR, "Expected expression",
                    list[i].line, list[i].column)
            }

            head.add(result.tree)
            i = result.index
        }
    }

    expect("Expected ')' symbol", TokenType.CLOSING_PARENTHESIS,
        i++, list)

    return ParsingResult(true, head, i)
}