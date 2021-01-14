// parseFunctionTest.kt
// Version 1.0.2

package kconan.parser.grammar

import kconan.error.Error
import kconan.io.readFile
import kconan.lexer.doLexing

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ParseFunctionTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"
    @Test
    fun parseFunctionTest1() {
        var test = parseFunction(0, doLexing("fun foo(): f64 { a = 100;}"))
        assertEquals("foo", test.tree.children[0].head.value)

        test = parseFunction(0, doLexing("var foo(): f64 { a = 100;}"))
        assertFalse(test.result)

        try {
            parseFunction(0, doLexing("fun foo(): af64 { a = 100;}"))
            assert(false)
        } catch(e: Error) {
            assert(e.toString().contains("type declaration"))
        }
    }

    @Test
    fun parseFunctionTest2() {
        val source = "fun foo(a:i8, b: i8): f32 { a = 32; }"
        val test = parseFunction(0, doLexing(source))

        assertEquals(15, test.tree.size())
    }

    @Test
    fun parseFunctionTest3() {
        val source = readFile("${conanSourcesDirectory}function1.cn")
        val test = parseFunction(0, doLexing(source))
        assertEquals(36, test.tree.size())
    }
}