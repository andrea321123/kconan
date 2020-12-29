// LexerTest.kt
// Version 1.0.4

package kconan.lexer

import kconan.error.Error

import kotlin.test.Test
import kotlin.test.assertEquals

class LexerTest {
    @Test
    fun getSymbolsTokensTest() {
        var string = "(){}++--++="
        var test = getSymbolsTokens(string, 1, 1)
        assertEquals(8, test.size)
        assertEquals("(", test[0].value)
        assertEquals(5, test[4].line)
        assertEquals("--", test[5].value)
        assertEquals(1, test[6].column)
        assertEquals("=", test[7].value)

        string = "()+=($)"
        try {
            test = getSymbolsTokens(string, 1, 1)
            assert(false)
        } catch (e: Error) {
            assert(e.toString().contains("$"))
        }
    }

    @Test
    fun skipSpacesTest() {
        var string = "fun main() {     var foo = 5;     var bar = 3;    }      "
        assertEquals(4, skipSpaces(string, 3))
        assertEquals(11, skipSpaces(string, 11))
        assertEquals(17, skipSpaces(string, 12))
        assertEquals(57, skipSpaces(string, 51))

        string = "fun main() {\n     var foo = 5;}"
        assertEquals(12, skipSpaces(string, 12))
        assertEquals(18, skipSpaces(string, 13))
    }
}