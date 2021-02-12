// Interpreter.kt
// Version 1.0.5

package kconan.interpreter

import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse
import kconan.semantic.resolveNames
import kotlin.test.Test
import kotlin.test.assertEquals

class InterpreterTest {
    private val conanSourcesDirectory = "src/test/resources/conan-files/"

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
    fun runTest3() {
        val source = "var a:i32=32; fun main():i32{return foo(a -1) *2;} fun foo(b: i32):i32{return b-5;}"
        val tree = parse(doLexing(source)).tree
        resolveNames(tree)
        val test = Interpreter(tree)
        assertEquals(52, test.runFunction(test.functionMap["main"]!!, ArrayList()))
    }

    @Test
    fun runTest4() {
        val source = readFile("${conanSourcesDirectory}/doubleFactorial.cn")
        val tree = parse(doLexing(source)).tree
        resolveNames(tree)
        val test = Interpreter(tree)
        assertEquals(479001600, test.runFunction(test.functionMap["main"]!!, ArrayList()))
    }

    @Test
    fun runTest5() {
        val source = readFile("${conanSourcesDirectory}/fibonacciI32.cn")
        val tree = parse(doLexing(source)).tree
        resolveNames(tree)
        val test = Interpreter(tree)
        assertEquals(55, test.runFunction(test.functionMap["main"]!!, ArrayList()))
    }

    @Test
    fun runTest6() {
        val source = readFile("${conanSourcesDirectory}/variables.cn")
        val tree = parse(doLexing(source)).tree
        resolveNames(tree)
        val test = Interpreter(tree)
        assertEquals(24, test.runFunction(test.functionMap["main"]!!, ArrayList()))
    }
}