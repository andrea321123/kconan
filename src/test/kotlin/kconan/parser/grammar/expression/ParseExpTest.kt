// ParseExpTest.kt
// Version 1.0.8

package kconan.parser.grammar.expression

import kconan.error.Error
import kconan.lexer.doLexing
import kconan.parser.token.TreeTokenType
import kotlin.test.assertEquals

import kotlin.test.Test

class ParseExpTest {
    @Test
    fun parseExpTest1() {
        var test = parseExp(5, doLexing("var a: u64 = 3;"))
        assertEquals(TreeTokenType.EXP, test.tree.head.token)

        test = parseExp(5, doLexing("var a: u64 = variable;"))
        assertEquals(TreeTokenType.EXP, test.tree.head.token)

        test = parseExp(5, doLexing("var a: u64 = fun;"))
        assert(!test.result)
    }

    @Test
    fun parseExpTest2() {
        var test = parseExp(0, doLexing("43-45%4.433;"))
        assertEquals(10, test.tree.size())
    }

    @Test
    fun parseExpTest3() {
        var test = parseExp(5, doLexing("var a: u64 = fun;"))
        assert(!test.result)

        test = parseExp(0, doLexing("43*(15 + 9)%(5*9);"))

        try {
            parseExp(0, doLexing("43*(54+90;"))
            assert(false)
        } catch (e: Error) {

        }
    }

    @Test
    fun parsePrimaryTest() {
        var test = parsePrimary(5, doLexing("var a: u64 = 3;"))
        assertEquals(TreeTokenType.EXP, test.tree.head.token)
        assertEquals("3", test.tree.children[0].head.value)

        test = parsePrimary(5, doLexing("var a: u64 = variable;"))
        assertEquals(TreeTokenType.EXP, test.tree.head.token)
    }

    @Test
    fun parsePTest() {
        var test = parseP(0, doLexing("43*65%3.14;"))
        assertEquals(9, test.tree.size())
        assertEquals(TreeTokenType.MULTIPLICATION, test.tree.children[1].head.token)
    }

    @Test
    fun parseSTest() {
        var test = parseS(0, doLexing("1+2*3+4/5*6;"))
        assertEquals(21, test.tree.size())
        assertEquals(TreeTokenType.ADDITION, test.tree.children[1].head.token)

        test = parseS(0, doLexing("1*2-3 == 5;"))
        assertEquals(11, test.tree.size())
        assertEquals(5, test.index)
    }

    @Test
    fun parseCTest() {
        var test = parseC(0, doLexing("4;"))
        assertEquals(1, test.index)

        test = parseC(0, doLexing("34*4 != 5;"))
        assertEquals(TreeTokenType.NOT_EQUALS_TO, test.tree.children[1].head.token)
    }
}