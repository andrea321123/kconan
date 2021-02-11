// Interpreter.kt
// Version 1.0.1

package kconan.interpreter

import kconan.parser.Ast
import kconan.parser.token.TreeTokenType
import kconan.semantic.ScopeContainer
import kconan.semantic.addIdentifier
import kconan.semantic.getFunctionAst
import kconan.util.ScopeMap

// kconan 1.0:
// - has support only for i32 types
// - every function must have a return type
// - if, else and while must have {} brackets
// - doesn't check if each function always reach a return statement

// Class that allows to interpret an ast
class Interpreter(val ast: Ast) {
    private val scope = getGlobalVariables()
    private val functionList = getFunctionAst(ast)

    fun run(ast: Ast) {
    }

    // Add to a ScopeMap<Integer> all global variables
    private fun getGlobalVariables(): ScopeMap<Int> {
        val map = ScopeMap<Int>()
        var currentNode = ast

        while (currentNode.children.size == 2) {
            // one child is a function or a global variable,
            // the other is another program
            if (currentNode.children[0].head.token == TreeTokenType.VAR_INIT) {
                map.add(currentNode.children[0].children[0].head.value,
                    solveExp(currentNode.children[0].children[2].children[0]))
            }
            currentNode = currentNode.children[1]
        }

        // we resolve the last part of the program
        if (currentNode.children[0].head.token == TreeTokenType.VAR_INIT) {
            map.add(currentNode.children[0].children[0].head.value,
                solveExp(currentNode.children[0].children[0].children[2]))
        }

        return map
    }

    // Evaluates an expression
    fun solveExp(ast: Ast): Int {
        if (ast.head.token == TreeTokenType.INTEGER_CONSTANT) {
            return Integer.parseInt(ast.head.value)
        }
        if (ast.children.size == 1) {
            return solveExp(ast.children[0])
        }
        if (ast.head.token == TreeTokenType.IDENTIFIER) {
            return scope.get(ast.head.value)!!
        }

        // if arrive here we have a situation of number operator number ... number
        var i = 1
        var tmp = solveExp(ast.children[0])     // store the temporary result
        while (i < ast.children.size) {
            tmp = operator(tmp,
                ast.children[i],
                solveExp(ast.children[i +1]))
            i += 2
        }

        return tmp
    }

    // apply the [operator] on [value1] and [value2]
    private fun operator(value1: Int, operator: Ast, value2: Int): Int {
        return when (operator.head.token) {
            TreeTokenType.ADDITION -> value1 + value2
            TreeTokenType.SUBTRACTION -> value1 - value2
            TreeTokenType.MULTIPLICATION -> value1 * value2
            TreeTokenType.DIVISION -> value1 / value2
            TreeTokenType.REMAINDER -> value1 % value2

            else -> 0
        }
    }
}