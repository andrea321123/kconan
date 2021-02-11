// ReturnException.kt
// Version 1.0.0

package kconan.interpreter

import java.lang.Exception

// Exception thrown when a statement return a value
class ReturnException(val returnValue: Int): Exception() {
}