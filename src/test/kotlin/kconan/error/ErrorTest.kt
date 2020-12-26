// ErrorTest.kt
// Version 1.0.0

package kconan.error

import kotlin.test.Test
import kotlin.test.assertEquals

class ErrorTest {
    private fun throwInputError() {
        throw Error(ErrorType.INPUT_ERROR, "File not found: src.cn")
    }
    private fun throwCompileError() {
        throw Error(ErrorType.COMPILE_ERROR, "Compile error", 12, 15)
    }
    private fun throwRuntimeError() {
        throw Error(ErrorType.RUNTIME_ERROR, "Null pointer error", 32)
    }

    @Test
    fun errorTest() {
        var test = Error(ErrorType.RUNTIME_ERROR, "info", 21)
        assertEquals("kconan: Runtime error: 21: info", test.toString())

        test = Error(ErrorType.COMPILE_ERROR, "info", 15, 18)
        assertEquals("kconan: Compile error: 15: 18: info", test.toString())

        test = Error(ErrorType.INPUT_ERROR, "info")
        assertEquals("kconan: Input error: info", test.toString())
    }

    @Test
    fun inputErrorTest() {
        try {
            throwInputError()
            assert(false)
        } catch (e: Error) {
            assertEquals("kconan: Input error: File not found: src.cn",
                e.toString())
        }
    }

    @Test
    fun compileErrorTest() {
        try {
            throwCompileError()
            assert(false)
        } catch (e: Error) {
            assertEquals("kconan: Compile error: 12: 15: Compile error",
                e.toString())
        }
    }

    @Test
    fun runtimeErrorTest() {
        try {
            throwRuntimeError()
            assert(false)
        } catch (e: Error) {
            assertEquals("kconan: Runtime error: 32: Null pointer error",
                e.toString())
        }
    }
}