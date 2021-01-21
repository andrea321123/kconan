// ParseStatementTest.kt
// Version 1.0.2

package kconan.parser.grammar

import kconan.lexer.doLexing
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ParseStatementTest {
    @Test
    fun parseStatementTest1() {
        val varAssignSource = "a = 45.3;"
        var test = parseStatement(0, doLexing(varAssignSource))
        assertEquals(5, test.tree.size())
        assertEquals(4, test.index)

        test = parseStatement(0, doLexing(": = 43.5 +;"))
        assertFalse(test.result)
    }

    @Test
    fun parseStatementTest2() {
        val source = "foo(bar);"
        val test = parseStatement(0, doLexing(source))

        assertEquals(5, test.tree.size())
    }

    @Test
    fun parseStatementTest3() {
        val source = "var a: i64 = 453;"
        val test = parseStatement(0, doLexing(source))
        assertEquals(6, test.tree.size())
    }
}