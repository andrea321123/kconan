// ParserTest.kt
// Version 1.0.0

package kconan.parser

import kconan.lexer.doLexing
import kconan.parser.token.TreeTokenType

import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {
    @Test
    fun parseNumberTest() {
        var test = Parser(doLexing("var a: u64 = 3.0;"))
        assertEquals(TreeTokenType.FLOAT_CONSTANT, test.parseNumber(5).tree.children[0].head.token)
    }

    @Test
    fun parseExpTest() {
        var test = Parser(doLexing("var a: u64 = 3;"))
        assertEquals(TreeTokenType.EXP, test.parseExp(5).tree.head.token)

        test = Parser(doLexing("var a: u64 = variable;"))
        assertEquals(TreeTokenType.EXP, test.parseExp(5).tree.head.token)

        test = Parser(doLexing("var a: u64 = fun;"))
        assert(!test.parseExp(5).result)
    }

    @Test
    fun parseOperatorTest() {
        var test = Parser(doLexing("var a: u64 = 3 * 5;"))
        assertEquals(
            TreeTokenType.MULTIPLICATION,
            test.parseOperator(6).tree.children[0].head.token)

        test = Parser(doLexing("var a: u64 = 3 / 5;"))
        assertEquals(
            TreeTokenType.DIVISION,
            test.parseOperator(6).tree.children[0].head.token)
    }

    @Test
    fun parseVarInit() {
        var test = Parser(doLexing("var a: u64 = 3;"))
        assertEquals(5, test.parseVarInit(0).tree.size())
    }
}