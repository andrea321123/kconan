// ParseReturnTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.io.readFile
import kconan.lexer.doLexing
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseReturnTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

    @Test
    fun parseWhileTest() {
        val source = doLexing(readFile("${conanSourcesDirectory}if1.cn"))
        var test = parseReturn(31, source)
        assertEquals(11, test.tree.size())
    }
}