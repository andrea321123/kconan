// ParseVarInitTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.lexer.doLexing
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseVarInitTest {
    @Test
    fun parseVarInit() {
        var test = parseVarInit(0, doLexing("var a: u64 = 3;"))
        assertEquals(5, test.tree.size())
    }
}