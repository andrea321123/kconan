// ParseParametersTest.kt
// Version 1.0.0

package kconan.parser.grammar

import kconan.error.Error
import kconan.lexer.doLexing
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseParametersTest {
    @Test
    fun parseParametersTest1() {
        val test = parseParameters(3, doLexing("fun foo(a:u64, b:u32, c:u16, d:u8): u64 {}"))
        assertEquals(13, test.tree.size())
    }
    @Test
    fun parseParametersTest2() {
        val test = parseParameters(3, doLexing("fun foo(): u64 {}"))
        assertEquals(1, test.tree.size())
    }
    @Test
    fun parseParametersTest3() {
        try {
            parseParameters(3, doLexing("fun foo(a:u64, :u64): u64 {}"))
            assert(false)
        } catch (e: Error) {
            assert(e.toString().contains("parameter"))
        }
    }
}