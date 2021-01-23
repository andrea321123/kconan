// CleanTreeTest.kt
// Version 1.0.0

package kconan.parser.grammar.expression

import kconan.lexer.doLexing

import kotlin.test.Test
import kotlin.test.assertEquals

class CleanTreeTest {
    @Test
    fun removeRedundantTest() {
        var test = parseC(0, doLexing("3==4+54/2;")).tree
        removeRedundant(test)
        assertEquals(14, test.size())

        test = parseC(0, doLexing("3;")).tree
        removeRedundant(test)
        assertEquals(2, test.size())
    }
}