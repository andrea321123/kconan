// Main.kt
// Version 1.0.1

package kconan

import kconan.interpreter.Interpreter
import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse
import kconan.semantic.convert
import kconan.semantic.resolveNames

fun main(args: Array<String>) {
    val source = readFile(args[0])
    val tree = parse(doLexing(source)).tree
    resolveNames(tree)
    convert(tree)
    val interpreter = Interpreter(tree)
    interpreter.run()

}