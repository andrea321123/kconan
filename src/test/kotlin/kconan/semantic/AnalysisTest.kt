// AnalysisTest.kt
// Version 1.0.3

package kconan.semantic

import kconan.error.Error
import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class AnalysisTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

    @Test
    fun getGlobalVarDeclarationTest1() {
        val source = readFile("${conanSourcesDirectory}function2.cn")
        val tree = parse(doLexing(source)).tree
        val test = getGlobalVarDeclarations(tree)
        assert(test.contains("pi"))
        assertFalse(test.contains("foo"))
        assertFalse(test.contains("test"))
    }

    @Test
    fun getGlobalVarDeclarationTest2() {
        val source = "var a:u64 = 0; var a:u64 = 0;"
        val tree = parse(doLexing(source)).tree

        try {
            getGlobalVarDeclarations(tree)
            assert(false)
        } catch (e: Error) {
            e.toString().contains("multiple times")
        }
    }

    @Test
    fun getFunctionDeclarationsTest1() {
        val source = readFile("${conanSourcesDirectory}function2.cn")
        val tree = parse(doLexing(source)).tree
        val test = getFunctionDeclarations(tree)
        assert(test.contains("foo"))
        assert(test.contains("bar"))
        assertFalse(test.contains("a"))
    }

    @Test
    fun getFunctionDeclarationsTest2() {
        val source = "fun a():u32{} fun a():u16{}"
        val tree = parse(doLexing(source)).tree

        try {
            getFunctionDeclarations(tree)
            assert(false)
        } catch (e: Error) {
            e.toString().contains("multiple times")
        }
    }

    @Test
    fun getFunctionAstTest() {
        val source = readFile("${conanSourcesDirectory}/scope.cn")
        val list = getFunctionAst(parse(doLexing(source)).tree)
        assertEquals(2, list.size)
    }
}