// Main.kt
// Version 1.0.2

package kconan

import kconan.error.Error
import kconan.interpreter.Interpreter
import kconan.io.readFile
import kconan.lexer.doLexing
import kconan.parser.grammar.parse
import kconan.semantic.convert
import kconan.semantic.generateSymbolTables
import kconan.semantic.resolveNames
import kconan.semantic.typeResolution
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        printHelp()
    }

    if (args[0] == "-h" || args[0] == "--help") {
        printHelp()
    }
    else if (args[0] == "-v" || args[0] == "--version") {
        printVersion()
    }

    try {
        val source = readFile(args[0])
        val tree = parse(doLexing(source)).tree
        resolveNames(tree)
        convert(tree)
        val tables = generateSymbolTables(tree)
        typeResolution(tables)
        val interpreter = Interpreter(tree)
        interpreter.run()
    } catch (e: Error) {
        println(e.toString())
        exitProcess(1)
    }

    exitProcess(0)
}

// Print help information and exit
fun printHelp() {
    println("Usage: kconan [FILE]")
    println("kconan execute a Conan file.")
    println("Options:")
    println("-h, --help         Print this help and exit")
    println("-v, --version      Output version information and exit")
    exitProcess(0)
}

fun printVersion() {
    println("kconan 1.1")
    println("Author: andrea321123 <https://www.github.com/andrea321123>")
    exitProcess(0)
}