// ParseParameterTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.lexer.doLexing
import kconan.parser.token.TreeTokenType
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseParameterTest {
    @Test
    fun parseParameterTest() {
        val test = parseParameter(3, doLexing("fun foo(i: u64): u64 {}"))
        assertEquals(3, test.tree.size())
        assertEquals(TreeTokenType.U64_TYPE, test.tree.children[1].head.token)
    }
}