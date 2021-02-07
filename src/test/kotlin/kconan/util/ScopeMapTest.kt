// ScopeMapTest.kt
// Version 1.0.0

package kconan.util

import kconan.parser.token.TreeTokenType
import kotlin.test.Test
import kotlin.test.assertEquals

class ScopeMapTest {
    @Test
    fun scopeMapTest() {
        val test = ScopeMap<TreeTokenType>()
        test.add("a", TreeTokenType.I16_TYPE)
        test.add("b", TreeTokenType.I16_TYPE)
        test.push()
        test.add("b", TreeTokenType.F32_TYPE)
        assertEquals(test.get("b"), TreeTokenType.F32_TYPE)

        test.set("b", TreeTokenType.F64_TYPE)
        assertEquals(test.get("b"), TreeTokenType.F64_TYPE)

        test.pop()
        assertEquals(test.get("b"), TreeTokenType.I16_TYPE)
    }
}