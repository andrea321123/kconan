// IdContainerTest.kt
// Version 1.0.1

package kconan.semantic

import kconan.semantic.IdContainer
import kotlin.test.Test
import kotlin.test.assertFalse

class IdContainerTest {
    @Test
    fun idContainerTest() {
        val test = IdContainer<String>()
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