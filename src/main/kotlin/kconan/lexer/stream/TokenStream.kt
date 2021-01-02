// TokenStream.kt
// Version 1.0.1

package kconan.lexer.stream

import kconan.token.Token

// Stream of tokens; tokens can be obtained using next() method
class TokenStream(private val list: ArrayList<Token>) {
    val size = list.size
    var index = 0

    fun next(): Token {
        if (index < size) {
            return list[index++]
        }

        throw EndStreamException()
    }
}