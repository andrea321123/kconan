// TokenStreamTest.kt
// Version 1.0.0

package kconan.lexer.stream

import kconan.lexer.doLexing

import kconan.io.readFile

import kotlin.test.Test

class TokenStreamTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"
    @Test
    fun nextTest() {
        val test =
            TokenStream(doLexing(readFile("${conanSourcesDirectory}math.cn")))

        for (i in 0 until test.size) {
            test.next()
        }

        try {
            test.next()
            assert(false)
        } catch (e: EndStreamException) {}
    }
}
