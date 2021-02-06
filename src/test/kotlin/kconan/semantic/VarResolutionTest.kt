// VarResolutionTest.kt
// Version 1.0.0

package kconan.semantic

import kconan.error.Error
import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse
import kotlin.test.Test

class VarResolutionTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

    @Test
    fun resolveVarNamesTest1() {
        val source = readFile("${conanSourcesDirectory}scope.cn")
        val tree = parse(doLexing(source)).tree
        val list = getFunctionAst(tree)
        val container = getGlobalVarDeclarations(tree)

        for (i in list) {
            resolveVarNames(i, container)
        }
    }

    @Test
    fun resolveVarNamesTest2() {
        val source = readFile("${conanSourcesDirectory}math2.cn")
        val tree = parse(doLexing(source)).tree
        val list = getFunctionAst(tree)
        val container = getGlobalVarDeclarations(tree)

        for (i in list) {
            resolveVarNames(i, container)
        }
    }

    @Test
    fun resolveVarNamesTest3() {
        val source = "fun a():u64{var a: i32 = 4; var a: i8 = 5;}"
        val tree = parse(doLexing(source)).tree
        val list = getFunctionAst(tree)
        val container = getGlobalVarDeclarations(tree)

        try {
            for (i in list) {
                resolveVarNames(i, container)
            }
            assert(false)
        } catch (e: Error) {
            assert(e.toString().contains("multiple times"))
        }
    }

    @Test
    fun resolveVarNamesTest4() {
        val source = "fun a():u64{var d: i32 = 4; var a: i8 = c +1;}"
        val tree = parse(doLexing(source)).tree
        val list = getFunctionAst(tree)
        val container = getGlobalVarDeclarations(tree)

        try {
            for (i in list) {
                resolveVarNames(i, container)
            }
            assert(false)
        } catch (e: Error) {
            assert(e.toString().contains("c not defined"))
        }
    }
}