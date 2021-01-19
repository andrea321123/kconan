// ParseWhileTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.io.readFile
import kconan.lexer.doLexing
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseWhileTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"
    @Test
    fun parseWhileTest() {
        val source = doLexing(readFile("${conanSourcesDirectory}while1.cn"))
        var test = parseWhile(14, source)

        test = parseProgram(0, source)
        assertEquals(56, test.tree.size())
    }
}