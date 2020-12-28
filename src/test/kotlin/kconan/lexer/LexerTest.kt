// LexerTest.kt
// Version 1.0.3

package kconan.lexer

import kotlin.test.Test
import kotlin.test.assertEquals

class LexerTest {
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