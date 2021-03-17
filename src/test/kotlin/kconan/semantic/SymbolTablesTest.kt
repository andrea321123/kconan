// SymbolTablesTest.kt
// Version 1.0.0

package kconan.semantic

import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse
import kotlin.test.Test
import kotlin.test.assertEquals

class SymbolTablesTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

    @Test
    fun symbolTablesTest1() {
        var tree = parse(doLexing(readFile("${conanSourcesDirectory}scope.cn")))
        resolveNames(tree.tree)
        val test = generateSymbolTables(tree.tree)
        assertEquals(2, test.functions.size)
        assertEquals(2, test.globalVars.size)
    }
}