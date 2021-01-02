// Parser.kt
// Version 1.0.0

package kconan.parser

import kconan.error.Error
import kconan.error.ErrorType

import kconan.token.Token
import kconan.token.TokenType
import kconan.token.*

class Parser (val list: ArrayList<Token>) {
    // program = var_init;
    // program = program var_init;
    fun parseProgram(i: Int): ParsingResult {
        var i = i
        var firstLoop = true
        val head = Ast(TreeToken(TreeTokenType.PROGRAM,
            "", list[i].line, list[i].column))

        while (true) {
            var result = parseVarInit(i)

            // if we don't parse a var init
            if (!result.result) {
                if (firstLoop) {
                    throw Error(ErrorType.COMPILE_ERROR,
                        "Expected var initialization",
                        list[i].line, list[i].column)
                }
                else {
                    break
                }
            }

            head.add(result.tree)
            i = result.index
            firstLoop = false
        }

        return ParsingResult(true, head, i)
    }

    // var_init = VAR ?ID? COLON numeric_type ASSIGN exp;;
    fun parseVarInit(i: Int): ParsingResult {
        var i = i
        val head = Ast(TreeToken(TreeTokenType.VAR_INIT,
            "", list[i].line, list[i].column))

        if (list[i++].token != TokenType.VAR_KEYWORD) {
            return ParsingResult(false, head, --i)
        }

        // first child is identifier
        expect("Expected identifier", TokenType.IDENTIFIER, i)
        head.add(Ast(treeFromIndex(TreeTokenType.IDENTIFIER, i++)))

        expect("Expected ':' symbol", TokenType.COLON, i++)

        // second child is var type
        expectType("Expected type declaration", i)
        head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i++)))

        expect("Expected '=' symbol", TokenType.ASSIGN, i++)

        // third child is an expression
        val result = parseExp(i)
        if (!result.result) {
            throw Error (ErrorType.COMPILE_ERROR,
                "Expected expression", list[i].line, list[i].column)
        }
        head.add(result.tree)
        i = result.index

        expect("Expected ';' symbol", TokenType.SEMICOLON, i++)

        return ParsingResult(true, head, i)
    }

    fun parseExp(i: Int): ParsingResult {
        // TODO: Implement method
    }

    // throw an error if the token at [index] isn't [tokenType]
    private fun expect(errorMessage: String,
                       tokenType: TokenType,
                       index: Int) {
        if (list[index].token != tokenType) {
            throw Error(ErrorType.COMPILE_ERROR,
                errorMessage, list[index].line, list[index].column)
        }
    }

    // throw an error if the token at [index] isn't a type
    private fun expectType(errorMessage: String, index: Int) {
        if (!types.contains(list[index].token)) {
            throw Error(ErrorType.COMPILE_ERROR,
                    errorMessage, list[index].line, list[index].column)
        }
    }

    // return a new Tree token with value, line, column from list[index]
    private fun treeFromIndex(token: TreeTokenType, index: Int): TreeToken {
        return TreeToken(token,
            list[index].value, list[index].line, list[index].column)
    }
}