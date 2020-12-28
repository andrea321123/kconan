// LexerTest.kt
// Version 1.0.0

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
        var string = "fun main() { println(\"Hello world\")}"
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
}