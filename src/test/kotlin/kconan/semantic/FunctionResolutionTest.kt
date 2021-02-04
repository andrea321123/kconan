// FunctionResolutionTest.kt
// Version 1.0.0

package kconan.semantic

import kconan.error.Error
import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse
import kotlin.test.Test
import kotlin.test.assertFalse

class FunctionResolutionTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

    @Test
    fun resolveFunctionTest1() {
        val source = readFile("${conanSourcesDirectory}scope.cn")
        val tree = parse(doLexing(source)).tree
        resolveFunctions(tree)
    }

    @Test
    fun resolveFunctionTest2() {
        val source = "fun a():u32{return b();} fun b():u16{return d(a());}"
        val tree = parse(doLexing(source)).tree
        try {
            resolveFunctions(tree)
        } catch (e: Error) {
            assert(e.toString().contains("d function"))
        }
    }
}