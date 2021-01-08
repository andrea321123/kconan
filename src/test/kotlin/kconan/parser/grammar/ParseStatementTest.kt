// ParseStatementTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.error.Error
import kconan.lexer.doLexing
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ParseStatementTest {
    @Test
    fun parseStatementTest() {
        val varAssignSource = "a = 45.3;"
        var test = parseStatement(0, doLexing(varAssignSource))
        assertEquals(5, test.tree.size())
        assertEquals(4, test.index)

        test = parseStatement(0, doLexing(": = 43.5 +;"))
        assertFalse(test.result)
    }
}