// ReturnException.kt
// Version 1.0.1

package kconan.interpreter

import java.lang.Exception

// Exception thrown when a statement return a value
class ReturnException(val returnValue: Variable): Exception() {
}