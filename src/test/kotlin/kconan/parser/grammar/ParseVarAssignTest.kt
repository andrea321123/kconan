// ParseVarAssignTest.kt
// Version 1.0.1

package kconan.parser.grammar

import kconan.lexer.doLexing

import kconan.error.Error

import kotlin.test.Test
import kotlin.test.assertEquals

class ParseVarAssignTest {
    @Test
    fun parseVarAssignTest() {
        val varAssignSource = "a = 45.3;"
        var test = parseVarAssign(0, doLexing(varAssignSource))
        assertEquals(4, test.tree.size())
        assertEquals(4, test.index)
        assertEquals("45.3", test.tree.children[1].children[0].head.value)

        try {
            parseVarAssign(0, doLexing("a = : 45.3;"))
            assert(false)
        } catch (e: Error) {
            assert(e.toString().contains("expression"))
            assertEquals(5, e.column)
        }
    }
}