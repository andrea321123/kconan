// Scanner.kt
// Version 1.0.0

package kconan.io

import kconan.error.Error
import kconan.error.ErrorType

import java.io.File
import java.io.FileNotFoundException

// Return the content of source file at location [path]
fun readFile(path: String): String {
    try {
        // read all file
        val inputStream = File(path).inputStream()
        return inputStream.bufferedReader().use { it.readText() }
    } catch (e: FileNotFoundException) {
        throw Error(ErrorType.INPUT_ERROR, "File not found: $path")
    }
}