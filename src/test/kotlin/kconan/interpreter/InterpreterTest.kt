// Interpreter.kt
// Version 1.0.1

package kconan.interpreter

import kconan.lexer.doLexing
import kconan.parser.grammar.parse
import kotlin.test.Test
import kotlin.test.assertEquals

class InterpreterTest {
    @Test
    fun solveExpTest1() {
        val source = "fun main(): i32 { return 32+ 65+ 3;}"
        val tree = parse(doLexing(source)).tree
        val test = Interpreter(tree)
        val exp = tree.children[0].children[3].children[0].children[0]
        assertEquals(100, test.solveExp(exp))
    }

    @Test
    fun solveExpTest2() {
        val source = "var a: i32 = 32; fun main(): i32 { return a+ 65+ 3;}"
        val tree = parse(doLexing(source)).tree
        val test = Interpreter(tree)
        val exp = tree.children[1].children[0].children[3].children[0].children[0]
        assertEquals(100, test.solveExp(exp))
    }
}