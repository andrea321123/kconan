package kconan.parser.grammar

import kconan.lexer.doLexing
import kconan.parser.token.TreeTokenType
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseOperatorTest {
    @Test
    fun parseOperatorTest() {
        var test = parseOperator(6, doLexing("var a: u64 = 3 * 5;"))
        assertEquals(
            TreeTokenType.MULTIPLICATION,
            test.tree.children[0].head.token)

        test = parseOperator(6, doLexing("var a: u64 = 3 / 5;"))
        assertEquals(
            TreeTokenType.DIVISION,
            test.tree.children[0].head.token)
    }
}