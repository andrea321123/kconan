package kconan

import kconan.interpreter.Interpreter
import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse
import kconan.semantic.resolveNames

fun main(args: Array<String>) {
    val source = readFile("src/test/resources/conan-files/fibonacciI32.cn")
    val tree = parse(doLexing(source)).tree
    resolveNames(tree)
    val test = Interpreter(tree)
    val mainReturnValue = test.runFunction(test.functionMap["main"]!!, ArrayList())
    println(mainReturnValue)

}