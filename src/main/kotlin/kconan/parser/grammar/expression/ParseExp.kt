// ParseExp.kt
// Version 1.0.3

// ParseExp.kt contains all the expression rules:
//  - exp
//  - c
//  - s
//  - p
//  - primary

package kconan.parser.grammar.expression

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.grammar.treeFromIndex
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.parser.token.tokenToTreeToken

import kconan.token.Token
import kconan.token.TokenType

// exp = c;
fun parseExp(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.EXP, "",
            list[i].line, list[i].column)
    )

    if (list[i].token == TokenType.IDENTIFIER) {
        head.add(Ast(treeFromIndex(TreeTokenType.IDENTIFIER, i, list)))
        return ParsingResult(true, head, ++i)
    }
    if (list[i].token == TokenType.INTEGER_CONSTANT ||
        list[i].token == TokenType.FLOAT_CONSTANT) {
        head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i, list)))
        return ParsingResult(true, head, ++i)
    }

    return ParsingResult(false, head, i)
}

// primary = ?ID? | number | ("(" exp ")");
fun parsePrimary(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(
        TreeToken(
            TreeTokenType.EXP, "",
            list[i].line, list[i].column)
    )

    if (list[i].token == TokenType.IDENTIFIER) {
        head.add(Ast(treeFromIndex(TreeTokenType.IDENTIFIER, i, list)))
        return ParsingResult(true, head, ++i)
    }

    if (list[i].token == TokenType.INTEGER_CONSTANT ||
        list[i].token == TokenType.FLOAT_CONSTANT) {
        head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i, list)))
        return ParsingResult(true, head, ++i)
    }

    // TODO: implement parenthesis rule
    return ParsingResult(false, head, i)
}

// p = primary {("*" | "/") primary};
fun parseP(i: Int, list: ArrayList<Token>): ParsingResult {
    var i = i
    val head = Ast(TreeToken(TreeTokenType.EXP, "",
        list[i].line, list[i].column))

    var result = parsePrimary(i, list)
    if (!result.result) {
        return ParsingResult(false, head, i)
    }

    i = result.index
    head.add(result.tree)

    // we read until we don't found any p operator
    while (pOperators.contains(tokenToTreeToken[list[i].token])) {
        head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i, list)))

        // parse next expression
        result = parsePrimary(++i, list)
        if (!result.result) {
            throw Error(ErrorType.COMPILE_ERROR, "Expected expression",
                list[i].line, list[i].column)
        }

        i = result.index
        head.add(result.tree)
    }

    return ParsingResult(true, head, i)
}