// Interpreter.kt
// Version 1.0.2

package kconan.interpreter

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.token.TreeTokenType
import kconan.semantic.getFunctionAst
import kconan.util.ScopeMap

// kconan 1.0:
// - has support only for i32 types
// - every function must have a return type
// - if, else and while must have {} brackets
// - doesn't check if each function always reach a return statement

// Class that allows to interpret an ast
class Interpreter(val ast: Ast) {
    private val functionList = getFunctionAst(ast)
    private val scope = getGlobalVariables()

    // it executes the program (starting from the main() function)
    fun run(ast: Ast) {
        for (i in functionList) {
            if (i.children[0].head.value == "main") {
                runFunction(i)
                return
            }
        }
    }

    //In kconan 1.0, all functions must return an i32 (kotlin Int)
    // The return value will be given by throwing a ReturnException
    fun runFunction(ast: Ast): Int {
        // a function creates a scope with the parameters
        scope.push()
        for (i in ast.children[1].children) {
            scope.add(i.head.value, 0)
        }

        // then we run each statement of the function
        try {
            for (i in 3 until ast.children.size) {
                // ast.children[i] is a statement node,
                // we have to execute the children of the statement
                walkAst(ast.children[i].children[0])
            }
        } catch (e: ReturnException) {
            return e.returnValue
        }

        // if we arrive here, the function hasn't returned any value;
        // a runtime error should be thrown
        throw Error(ErrorType.RUNTIME_ERROR,
            "Function ${ast.children[0].head.value} didn't return any value",
            ast.head.line, ast.head.column)
    }

    fun walkAst(ast: Ast) {
        // if the statement is a return, throw a ReturnException
        if (ast.head.token == TreeTokenType.RETURN) {
            val returnValue = solveExp(ast.children[0])
            throw ReturnException(returnValue)
        }
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