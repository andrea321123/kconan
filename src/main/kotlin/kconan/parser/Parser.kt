// Parser.kt
// Version 1.0.3

package kconan.parser

import kconan.error.Error
import kconan.error.ErrorType

import kconan.token.Token
import kconan.token.TokenType
import kconan.token.*

class Parser (val list: ArrayList<Token>) {
    // program = var_init;
    // program = var_init program;
    fun parseProgram(i: Int): ParsingResult {
        var i = i
        var firstLoop = true
        val head = Ast(TreeToken(TreeTokenType.PROGRAM,
            "", list[i].line, list[i].column))

        // first child is a var initialization
        var result = parseVarInit(i)
        if (!result.result) {
            return ParsingResult(false, head, result.index)
        }
        head.add(Ast(treeFromIndex(TreeTokenType.VAR_INIT, i)))

        i = result.index

        // second child is a program
        result = parseProgram(i)
        if (result.result) {
            head.add(Ast(treeFromIndex(TreeTokenType.PROGRAM, i)))
            i = result.index
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

    // exp = ?ID?;
    // exp = number;
    fun parseExp(i: Int): ParsingResult {
        var i = i
        val head = Ast(TreeToken(TreeTokenType.EXP, "",
                list[i].line, list[i].column))

        if (list[i].token == TokenType.IDENTIFIER) {
            head.add(Ast(treeFromIndex(TreeTokenType.IDENTIFIER, i)))
            return ParsingResult(true, head, ++i)
        }
        if (list[i].token == TokenType.INTEGER_CONSTANT ||
                list[i].token == TokenType.FLOAT_CONSTANT) {
            head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i)))
            return ParsingResult(true, head, ++i)
        }

        return ParsingResult(false, head, i)
    }

    // number = ?INT? | ?FLOAT?;
    fun parseNumber(i: Int): ParsingResult {
        val head = Ast(TreeToken(TreeTokenType.NUMBER,
            "", list[i].line, list[i].column))

        if (list[i].token == TokenType.INTEGER_CONSTANT ||
                list[i].token == TokenType.FLOAT_CONSTANT) {
            head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i)))
            return ParsingResult(true, head, i +1)
        }

        return ParsingResult(false, head, i)
    }

    // operator = ADDITION | SUBTRACTION | MULTIPLICATION | DIVISION;
    fun parseOperator(i: Int): ParsingResult {
        val head = Ast(TreeToken(TreeTokenType.OPERATOR, "",
                list[i].line, list[i].column))

        if (operators.contains(list[i].token)) {
            head.add(Ast(treeFromIndex(tokenToTreeToken[list[i].token]!!, i)))
            return ParsingResult(true, head, i +1)
        }

        return ParsingResult(false, head, i)
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