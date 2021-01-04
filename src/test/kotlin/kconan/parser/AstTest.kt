// AstTest.kt
// Version 1.0.0

package kconan.parser

import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kotlin.test.Test
import kotlin.test.assertEquals

class AstTest {
    @Test
    fun sizeTest() {
        // create by hand the AST of the program "var foo : u4 = 3 + 9
        val n11 = Ast(TreeToken(TreeTokenType.IDENTIFIER, "foo", 1, 5))
        val n121 = Ast(TreeToken(TreeTokenType.U64_TYPE, "",1, 11))
        val n13111 = Ast(TreeToken(TreeTokenType.INTEGER_CONSTANT, "3", 1, 17))
        val n1321 = Ast(TreeToken(TreeTokenType.ADDITION, "", 1, 19))
        val n13311 = Ast(TreeToken(TreeTokenType.INTEGER_CONSTANT, "9", 1, 21))

        val n12 = Ast(TreeToken(TreeTokenType.NUMERIC_TYPE, "", 1, 11))
        n12.add(n121)
        val n1311 = Ast(TreeToken(TreeTokenType.NUMBER, "", 1, 17))
        n1311.add(n13111)
        val n131 = Ast(TreeToken(TreeTokenType.EXP, "", 1, 17))
        n131.add(n1311)
        val n132 = Ast(TreeToken(TreeTokenType.OPERATOR, "", 1, 19))
        n132.add(n1321)
        val n1331 = Ast(TreeToken(TreeTokenType.NUMBER, "", 1, 21))
        n1331.add(n13311)
        val n133 = Ast(TreeToken(TreeTokenType.EXP, "", 1, 21))
        n133.add(n1331)
        val n13 = Ast(TreeToken(TreeTokenType.EXP, "", 1, 17))
        n13.add(n131)
        n13.add(n132)
        n13.add(n133)

        val n1 = Ast(TreeToken(TreeTokenType.VAR_INIT, "", 1, 1))
        n1.add(n11)
        n1.add(n12)
        n1.add(n13)

        val head = Ast(TreeToken(TreeTokenType.PROGRAM, "", 1, 1))
        head.add(n1)

        assertEquals(14, head.size())
    }
}