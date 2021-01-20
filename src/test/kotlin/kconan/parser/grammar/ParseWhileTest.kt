// ParseWhileTest.kt
// Version 1.0.1

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
        assertEquals(16, test.tree.size())
    }
}