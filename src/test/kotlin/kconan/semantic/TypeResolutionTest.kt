package kconan.semantic

import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse
import kotlin.test.Test

class TypeResolutionTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

    @Test
    fun typeResolutionTest() {
        val source = readFile("${conanSourcesDirectory}types.cn")
        val tree = parse(doLexing(source)).tree
        convert(tree)
        val tables = generateSymbolTables(tree)
        typeResolution(tables)
    }
}