// TokenTest.kt
// Version 1.0.0

package kconan.token

import kotlin.test.Test
import kotlin.test.assertEquals

class TokenTest {
    @Test
    fun tokenTest() {
        var test = Token(TokenType.IDENTIFIER, "fun", 10, 1)
        assertEquals(TokenType.IDENTIFIER, test.token)
        assertEquals(10, test.line)
        assertEquals(1, test.column)
        assertEquals("fun", test.value)

        test = Token(TokenType.LOGICAL_AND, "and", 245, 15)
        assertEquals(TokenType.LOGICAL_AND, test.token)
        assertEquals(245, test.line)
        assertEquals(15, test.column)
        assertEquals("and", test.value)
    }
}