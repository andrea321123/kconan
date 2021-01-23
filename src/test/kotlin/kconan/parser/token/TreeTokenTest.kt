// TreeTokenTest.kt
// Version 1.0.0

package kconan.parser.token

import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType
import kotlin.test.Test
import kotlin.test.assertEquals

class TreeTokenTest {
    @Test
    fun treeTokenTest() {
        var test = TreeToken(TreeTokenType.IDENTIFIER, "foo", 10, 1)
        assertEquals(TreeTokenType.IDENTIFIER, test.token)
        assertEquals(10, test.line)
        assertEquals(1, test.column)
        assertEquals("foo", test.value)

        test = TreeToken(TreeTokenType.NUMBER, "", 245, 15)
        assertEquals(TreeTokenType.NUMBER, test.token)
        assertEquals(245, test.line)
        assertEquals(15, test.column)
        assertEquals("", test.value)
    }
}