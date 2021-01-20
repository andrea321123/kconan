// ParseIf.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.io.readFile
import kconan.lexer.doLexing
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseIfTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"
    @Test
    fun parseWhileTest() {
        val source = doLexing(readFile("${conanSourcesDirectory}if1.cn"))
        var test = parseIf(7, source)
        assertEquals(57, test.tree.size())
    }
}