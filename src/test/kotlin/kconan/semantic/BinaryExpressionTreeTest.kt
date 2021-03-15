// BinaryExpressionTree.kt
// Version 1.0.0

package kconan.semantic

import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse

import kotlin.test.Test
import kotlin.test.assertEquals

class BinaryExpressionTreeTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

    @Test
    fun convertTest1() {
        val source = readFile("${conanSourcesDirectory}math2.cn")
        val tree = parse(doLexing(source)).tree
        resolveNames(tree)
        convert(tree)
        assertEquals(138, tree.size())
    }
}