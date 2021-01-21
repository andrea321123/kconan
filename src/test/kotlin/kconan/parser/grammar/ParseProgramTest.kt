// ParseProgramTest.kt
// Version 1.0.5

package kconan.parser.grammar

import kconan.error.Error
import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.token.TreeTokenType
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
            assert(e.toString().contains("Expected ':'"))
        }
    }

    @Test
    fun parseProgramTest2() {
        val test = parseProgram(0,
            doLexing(readFile("${conanSourcesDirectory}varInit.cn")))

        assertEquals(18, test.tree.size())
    }

    @Test
    fun parseProgramTest3() {
        val source = readFile("${conanSourcesDirectory}function2.cn")
        val test = parseProgram(0, doLexing(source))
        assertEquals(TreeTokenType.FUNCTION, test.tree.children[1].children[0].head.token)
    }

    @Test
    fun parseProgramTest4() {
        val source = readFile("${conanSourcesDirectory}fibonacci.cn")
        val test = parseProgram(0, doLexing(source))
        assertEquals(43, test.tree.size())
    }

    @Test
    fun parseProgramTest5() {
        var source = "fun a(): bool {return a == true;}"
        var test = parseProgram(0, doLexing(source))
        assertEquals (test.tree.children[0].children[2].head.token,
            TreeTokenType.BOOL_TYPE)

        source = "fun a(a: char): bool {return a > 'b';}"
        test = parseProgram(0, doLexing(source))
        assertEquals(16, test.tree.size())
    }
}