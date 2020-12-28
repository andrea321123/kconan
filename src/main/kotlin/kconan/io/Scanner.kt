// Scanner.kt
// Version 1.0.1

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
        val sourceCode = inputStream.bufferedReader().use { it.readText() }
        return sourceCode.replace("\r", "")
    } catch (e: FileNotFoundException) {
        throw Error(ErrorType.INPUT_ERROR, "File not found: $path")
    }
}