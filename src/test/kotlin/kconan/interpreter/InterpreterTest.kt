// Interpreter.kt
// Version 1.0.2

package kconan.interpreter

import kconan.error.Error
import kconan.error.ErrorType
import kconan.lexer.doLexing
import kconan.parser.grammar.parse
import kconan.semantic.resolveNames
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


    @Test
    fun runtTest3() {
        val source = "var a:i32=32; fun main():i32{return foo(a -1) *2;} fun foo(b: i32):i32{return b-5;}"
        val tree = parse(doLexing(source)).tree
        resolveNames(tree)
        val test = Interpreter(tree)
        assertEquals(52, test.runFunction(test.functionMap["main"]!!, ArrayList()))
    }
}