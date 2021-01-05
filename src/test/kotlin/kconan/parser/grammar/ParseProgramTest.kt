// ParseProgramTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.error.Error
import kconan.io.readFile
import kconan.lexer.doLexing
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseProgramTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

    @Test
    fun parseProgramTest1() {
        var test = parseProgram(0, doLexing("var a: u8 = 3;"))
        assertEquals(6, test.tree.size())

        try {
            test = parseProgram(0, doLexing("fun foo() {}"))
            assert(false)
        } catch(e: Error) {
            assert(e.toString().contains("Expected var init"))
        }
    }

    @Test
    fun parseProgramTest2() {
        val test = parseProgram(0,
            doLexing(readFile("${conanSourcesDirectory}varInit.cn")))

        assertEquals(18, test.tree.size())
    }
}