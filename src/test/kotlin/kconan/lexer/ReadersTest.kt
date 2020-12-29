// ReadersTest.kt
// Version 1.0.2

package kconan.lexer

import kconan.token.TokenType

import kotlin.test.Test
import kotlin.test.assertEquals

class ReadersTest {
    @Test
    fun readNumberTest() {
        var string = "val foo = 15+52.435-var+17."
        var test = readNumber(string, 10)
        assertEquals("15", test.first)
        assertEquals(12, test.second)
        assertEquals(TokenType.INTEGER_CONSTANT, test.third)

        test = readNumber(string, 13)
        assertEquals("52.435", test.first)
        assertEquals(19, test.second)
        assertEquals(TokenType.FLOAT_CONSTANT, test.third)

        test = readNumber(string, 13)
        assertEquals("52.435", test.first)
        assertEquals(19, test.second)
        assertEquals(TokenType.FLOAT_CONSTANT, test.third)

        test = readNumber(string, 24)
        assertEquals("17", test.first)
        assertEquals(26, test.second)
        assertEquals(TokenType.INTEGER_CONSTANT, test.third)
    }

    @Test
    fun readCharTest() {
        var string = "foo(\'a\',\'b\',\'\\n\');"
        var test = readChar(string, 4)
        assertEquals("a", test.first)
        assertEquals(7, test.second)

        test = readChar(string, 8)
        assertEquals("b", test.first)
        assertEquals(11, test.second)

        test = readChar(string, 12)
        assertEquals("\\n", test.first)
        assertEquals(16, test.second)
    }

    @Test
    fun readStringTest() {
        var string = "println(\"First string, \" + \"Second \\string\");"
        var test = readString(string, 8)
        assertEquals("First string, ", test.first)
        assertEquals(24, test.second)

        test = readString(string, 27)
        assertEquals("Second \\string", test.first)
        assertEquals(43, test.second)
    }

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
}