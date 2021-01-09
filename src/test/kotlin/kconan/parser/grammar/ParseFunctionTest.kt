// parseFunctionTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.error.Error
import kconan.lexer.doLexing

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ParseFunctionTest {
    @Test
    fun parseFunction1() {
        var test = parseFunction(0, doLexing("fun foo(): f64 { a = 100;}"))
        assertEquals("foo", test.tree.children[0].head.value)

        test = parseFunction(0, doLexing("var foo(): f64 { a = 100;}"))
        assertFalse(test.result)

        try {
            parseFunction(0, doLexing("fun foo(): af64 { a = 100;}"))
            assert(false)
        } catch(e: Error) {
            assert(e.toString().contains("type declaration"))
        }
    }
}