// ParseExpTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.lexer.doLexing
import kconan.parser.token.TreeTokenType
import kotlin.test.assertEquals

import kotlin.test.Test

class ParseExpTest {
    @Test
    fun parseExpTest() {
        var test = parseExp(5, doLexing("var a: u64 = 3;"))
        assertEquals(TreeTokenType.EXP, test.tree.head.token)

        test = parseExp(5, doLexing("var a: u64 = variable;"))
        assertEquals(TreeTokenType.EXP, test.tree.head.token)

        test = parseExp(5, doLexing("var a: u64 = fun;"))
        assert(!test.result)
    }
}