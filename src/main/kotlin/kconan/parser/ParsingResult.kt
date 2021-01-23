// ParsingResult.kt
// Version 1.0.0

package kconan.parser

// Return type of all parse...() functions. It has information about:
// - result: if the parsing had success or not
// - tree: the actual Abstract Syntax Tree
// - index: the index of the first token of the list not parsed
data class ParsingResult(val result: Boolean,
                            val tree: Ast,
                            val index: Int) {}
