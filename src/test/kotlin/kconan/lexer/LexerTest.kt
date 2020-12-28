// LexerTest.kt
// Version 1.0.1

package kconan.lexer

import kotlin.test.Test
import kotlin.test.assertEquals

class LexerTest {
    @Test
    fun readWordTest() {
        var string = "fun main() { println(\"Hello world\")}"
        var test = readWord(string, 0)
        assertEquals("fun", test.first)
        assertEquals(3, test.second)

        test = readWord(string, 4)
        assertEquals("main", test.first)
        assertEquals(8, test.second)

        string = "var _36foo45 = 15.50 + variable+12"
        test = readWord(string, 0)
        assertEquals("var", test.first)
        assertEquals(3, test.second)

        test = readWord(string, 4)
        assertEquals("_36foo45", test.first)
        assertEquals(12, test.second)

        test = readWord(string, 23)
        assertEquals("variable", test.first)
        assertEquals(31, test.second)
    }

    @Test
    fun readSymbolsTest() {
        val string = "fun main() { println(\"Hello world\")}"
        var test = readSymbols(string, 8)
        assertEquals("()", test.first)
        assertEquals(10, test.second)

        test = readSymbols(string, 11)
        assertEquals("{", test.first)
        assertEquals(12, test.second)

        test = readSymbols(string, 20)
        // double quotes are of the next token
        assertEquals("(", test.first)
        assertEquals(21, test.second)

        test = readSymbols(string, 34)
        // double quotes are of the next token
        assertEquals(")}", test.first)
        assertEquals(36, test.second)
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