// IdContainerTest.kt
// Version 1.0.2

package kconan.semantic

import kotlin.test.Test
import kotlin.test.assertFalse

class ScopeContainerTest {
    @Test
    fun scopeContainerTest() {
        val test = ScopeContainer<String>()
        test.add("foo")
        test.add("bar")
        test.push()
        test.add("foo")     // shadowing
        test.add("c")
        assert(test.contains("c"))
        assert(test.contains("foo"))
        test.pop()
        assertFalse(test.contains("c"))
        assert(test.contains("foo"))
    }
}