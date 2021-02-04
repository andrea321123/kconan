// AnalysisTest.kt
// Version 1.0.1

package kconan.semantic

import kconan.error.Error
import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse

import kotlin.test.Test
import kotlin.test.assertFalse

class AnalysisTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

    @Test
    fun addGlobalDeclarationsTest1() {
        val source = readFile("${conanSourcesDirectory}function2.cn")
        val tree = parse(doLexing(source)).tree
        val test = getGlobalVarDeclarations(tree)
        assert(test.contains("pi"))
        assertFalse(test.contains("foo"))
        assertFalse(test.contains("test"))
    }

    @Test
    fun addGlobalDeclarationsTest2() {
        val source = "var a:u64 = 0; var a:u64 = 0;"
        val tree = parse(doLexing(source)).tree

        try {
            getGlobalVarDeclarations(tree)
            assert(false)
        } catch (e: Error) {
            e.toString().contains("multiple times")
        }
    }
}