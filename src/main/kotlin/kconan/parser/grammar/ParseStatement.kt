// ParseStatement.kt
// Version 1.0.5

package kconan.parser.grammar

import kconan.parser.Ast
import kconan.parser.ParsingResult
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kconan.token.Token
import kconan.token.TokenType

// statement = function_call;;
// statement = var_assign;
// statement = var_init;
// statement = while;
// statement = if;
// statement = return;
fun parseStatement(i: Int, list: ArrayList<Token>): ParsingResult {
    val head = Ast(TreeToken(TreeTokenType.STATEMENT, "",
        list[i].line, list[i].column))

    // can be a function call
    // note that function call must be checked before var assign
    var result = parseFunctionCall(i, list)
    if (result.result) {
        head.add(result.tree)
        expect("Expected ';' symbol", TokenType.SEMICOLON,
            result.index, list)
        return ParsingResult(true, head, result.index +1)
    }

    // can be a var init
    result = parseVarInit(i, list)
    if (result.result) {
        head.add(result.tree)
        return ParsingResult(true, head, result.index)
    }

    // can be a var assign
    result = parseVarAssign(i, list)
    if (result.result) {
        head.add(result.tree)
        return ParsingResult(true, head, result.index)
    }

    // can be a while loop
    result = parseWhile(i, list)
    if (result.result) {
        head.add(result.tree)
        return ParsingResult(true, head, result.index)
    }

    // can be an if statement
    result = parseIf(i, list)
    if (result.result) {
        head.add(result.tree)
        return ParsingResult(true, head, result.index)
    }

    // can be a return statement
    result = parseReturn(i, list)
    if (result.result) {
        head.add(result.tree)
        return ParsingResult(true, head, result.index)
    }

    return ParsingResult(false, head, i)
}