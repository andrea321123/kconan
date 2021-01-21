// ParseFunctionCallTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.error.Error
import kconan.lexer.doLexing
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseFunctionCallTest {
    @Test
    fun parseFunctionCallTest() {
        var source = doLexing("call(43, r, 65 * (6+ v))")
        var test = parseFunctionCall(0, source)

        assertEquals(4, test.tree.children.size)

        source = doLexing("call(sin(90), cos(tan(45 * (3 * 3* 3))))")
        test = parseFunctionCall(0, source)
        assertEquals(3, test.tree.children.size)

        source = doLexing("call()")
        test = parseFunctionCall(0, source)
        assertEquals(2, test.tree.size())

        source = doLexing("call(4; 5)")
        try {
            parseFunctionCall(0, source)
            assert(false)
        } catch (e: Error) {}
    }
}