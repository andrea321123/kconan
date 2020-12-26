// ErrorType.kt
// Version 1.0.0

package kconan.error

// Enum of all different error types:
// INPUT_ERROR: errors regarding the I/O (e.g. missing file)
// COMPILE_ERROR: errors thrown at compile-time
// RUNTIME_ERROR: errors thrown at runtime
enum class ErrorType {
    INPUT_ERROR,
    COMPILE_ERROR,
    RUNTIME_ERROR
}