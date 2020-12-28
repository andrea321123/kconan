// CleanSourceTest.kt
// Version 1.0.1

package kconan.lexer

import kconan.error.Error
import kconan.io.readFile

import kotlin.test.Test
import kotlin.test.assertEquals

class CleanSourceTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"
    private fun countSubstring(string: String, substring: String): Int =
        string.split(substring).size -1

    @Test
    fun skipStringTest() {
        var test = "This string contains \"another string\" inside"
        assertEquals(37, skipString(test, 21))
        test = "\" string not closed"
        try {
            skipString(test, 0)
            assert(false)
        } catch (e: Error) {
            assertEquals(1, countSubstring(e.toString(), "no closing double quote"))
        }
    }

    @Test
    fun factorialUncommentTest() {
        val test = uncomment(readFile("${conanSourcesDirectory}factorial.cn"))
        assertEquals(126, test.length)
        assertEquals(1, countSubstring(test, "factorial"))
        assertEquals(9, countSubstring(test, "\n"))
        assertEquals(0, countSubstring(test, "//"))
    }

    @Test
    fun helloWorldUncommentTest() {
        val test = uncomment(readFile("${conanSourcesDirectory}helloWorld.cn"))
        assertEquals(60, test.length)
        assertEquals(1, countSubstring(test, "Hello, world!"))
        assertEquals(3, countSubstring(test, "\n"))
    }

    @Test
    fun commentUncommentTest() {
        val test = uncomment(readFile("${conanSourcesDirectory}comment.cn"))
        assertEquals(51, test.length)
        assertEquals(2, countSubstring(test, "//"))
        assertEquals(3, countSubstring(test, "\n"))
    }

    @Test
    fun removeNewlinesTest() {
        var withNewlines = uncomment(readFile("${conanSourcesDirectory}comment.cn"))
        var test = removeNewlines(withNewlines)
        assertEquals(48, test.length)
        assertEquals(0, countSubstring(test, "\n"))
        withNewlines = uncomment(readFile("${conanSourcesDirectory}helloWorld.cn"))
        test = removeNewlines(withNewlines)
        assertEquals(57, test.length)
        assertEquals(0, countSubstring(test, "\n"))
    }
}