// ScannerTest.kt
// Version 1.0.0

package kconan.io

import kconan.error.Error

import kotlin.test.Test
import kotlin.test.assertEquals

class ScannerTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"
    private fun countSubstring(string: String, substring: String): Int =
        string.split(substring).size - 1

    @Test
    fun fileNotFoundScannerTest() {
        try {
            var test = readFile("${conanSourcesDirectory}notFound.cn")
            assert(false)
        } catch (e: Error) {
            assertEquals(1, countSubstring(e.toString(), "notFound.cn"))
        }
    }

    @Test
    fun factorialScannerTest() {
        var test = readFile("${conanSourcesDirectory}factorial.cn")
        assertEquals(142, test.length)
        assertEquals(2, countSubstring(test, "factorial"))
        assertEquals(3, countSubstring(test, "result"))
        assertEquals(10, countSubstring(test, "\n"))
    }

    @Test
    fun bubblesortScannerTest() {
        var test = readFile("${conanSourcesDirectory}bubblesort.cn")
        assertEquals(558, test.length)
        assertEquals(10, countSubstring(test, "sortedVector"))
        assertEquals(4, countSubstring(test, "i64"))
        assertEquals(2, countSubstring(test, "u64"))
    }
}