// ParseNumberTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.lexer.doLexing
import kconan.parser.token.TreeTokenType

import kotlin.test.Test
import kotlin.test.assertEquals

class ParseNumberTest {
    @Test
    fun parseNumberTest() {
        var test = parseNumber(5, doLexing("var a: u64 = 3.0;"))
        assertEquals(TreeTokenType.FLOAT_CONSTANT, test.tree.children[0].head.token)
    }
}