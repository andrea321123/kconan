// LexerTest.kt
// Version 1.0.6

package kconan.lexer

import kconan.error.Error

import kconan.io.readFile

import kconan.token.TokenType

import kotlin.test.Test
import kotlin.test.assertEquals

class LexerTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

    @Test
    fun doLexingTest1() {
        val string = readFile("${conanSourcesDirectory}helloWorld.cn")
        val test = doLexing(string)
        assertEquals(11, test.size)
        assertEquals("fun", test[0].value)
        assertEquals(TokenType.FUN_KEYWORD, test[0].token)
        assertEquals("Hello, world!", test[7].value)
        assertEquals(6, test[7].line)
        assertEquals(13, test[7].column)
    }

    @Test
    fun doLexingTest2() {
        val string = "var a = 'v'; var b = \"string\"; var c = 15.18;"
        val test = doLexing(string)
        assertEquals(15, test.size)
        assertEquals(TokenType.CHAR_CONSTANT, test[3].token)
        assertEquals(TokenType.STRING_CONSTANT, test[8].token)
        assertEquals("string", test[8].value)
        assertEquals(TokenType.FLOAT_CONSTANT, test[13].token)
        assertEquals("15.18", test[13].value)
    }

    @Test
    fun doLexingTest3() {
        val string = "var b = 'cv';"
        try {
            val test = doLexing(string)
            assert(false)
        } catch (e: Error) {
            assert(e.toString().contains("Expected '"))
        }
    }

    @Test
    fun doLexingTest4() {
        val string = readFile("${conanSourcesDirectory}math.cn")
        val test = doLexing(string)
        assertEquals(74, test.size)
        assertEquals(TokenType.ASSIGNMENT_BY_PRODUCT, test[37].token)
    }

    @Test
    fun getSymbolsTokensTest() {
        var string = "(){}++--++="
        var test = getSymbolsTokens(string, 1, 1)
        assertEquals(8, test.size)
        assertEquals("(", test[0].value)
        assertEquals(5, test[4].column)
        assertEquals("--", test[5].value)
        assertEquals(1, test[6].line)
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