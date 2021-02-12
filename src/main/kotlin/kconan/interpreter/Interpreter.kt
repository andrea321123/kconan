// Interpreter.kt
// Version 1.0.5

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
// main function() cannot have arguments

// Class that allows to interpret an ast
class Interpreter(val ast: Ast) {
    private val functionList = getFunctionAst(ast)
    val functionMap = createFunctionMap(functionList)

    private val scope = getGlobalVariables()


    // it executes the program (starting from the main() function)
    fun run() {
        for (i in functionList) {
            if (i.children[0].head.value == "main") {
                runFunction(i, ArrayList())
                return
            }
        }
    }

    //In kconan 1.0, all functions must return an i32 (kotlin Int)
    // The return value will be given by throwing a ReturnException
    fun runFunction(ast: Ast, arguments: ArrayList<Int>): Int {
        // a function creates a scope with the arguments
        scope.push()
        for (i in 0 until ast.children[1].children.size) {
            // here we pair tha parameter name with the actual argument
            scope.add(ast.children[1].children[i].children[0].head.value,
                arguments[i])
        }

        // then we run each statement of the function
        try {
            for (i in 3 until ast.children.size) {
                // ast.children[i] is a statement node,
                // we have to execute the children of the statement
                walkAst(ast.children[i].children[0])
            }
        } catch (e: ReturnException) {
            scope.pop()
            return e.returnValue
        }

        scope.pop()
        // if we arrive here, the function hasn't returned any value;
        // a runtime error should be thrown
        throw Error(ErrorType.RUNTIME_ERROR,
            "Function ${ast.children[0].head.value} didn't return any value",
            ast.head.line, ast.head.column)
    }

    private fun walkAst(ast: Ast) {
        when (ast.head.token) {
            TreeTokenType.RETURN -> {
                // if the statement is a return, throw a ReturnException
                val returnValue = solveExp(ast.children[0])
                throw ReturnException(returnValue)
            }
            TreeTokenType.IF -> {
                val condition = solveExp(ast.children[0])

                if (condition == 1) {
                    walkBlock(ast.children[1])
                }

                // we eventually run the else body
                if (condition == 0 && ast.children.size == 3) {
                    walkBlock(ast.children[2])
                }
            }
            TreeTokenType.WHILE -> {
                var condition = solveExp(ast.children[0])

                while (condition == 1) {
                    scope.push()
                    try {
                        for (i in 1 until ast.children.size) {
                            walkAst(ast.children[i])
                        }
                    } catch (e: ReturnException) {
                        // if a value is returned from the block,
                        // we still have to pop the scope,
                        // then throw the exception
                        scope.pop()
                        throw e
                    }
                    scope.pop()

                    // we chek the condition
                    condition = solveExp(ast.children[0])
                }
            }
            TreeTokenType.VAR_ASSIGN -> {
                scope.set(ast.children[0].head.value, solveExp(ast.children[1]))
            }
            TreeTokenType.VAR_INIT -> {
                scope.add(ast.children[0].head.value, solveExp(ast.children[2]))
            }
            else -> {
                // if we arrive there, we call walk ast for each fo the children
                for (i in ast.children) {
                    walkAst(i)
                }
            }
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
        if (ast.head.token == TreeTokenType.FUNCTION_CALL) {
            return runFunction(functionMap[ast.children[0].head.value]!!,
                createArgumentList(ast))
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

            TreeTokenType.EQUALS_TO -> if (value1 == value2) 1 else 0
            TreeTokenType.NOT_EQUALS_TO -> if (value1 != value2) 1 else 0
            TreeTokenType.LESS_OR_EQUALS -> if (value1 <= value2) 1 else 0
            TreeTokenType.LESS_THAN -> if (value1 < value2) 1 else 0
            TreeTokenType.GREATER_OR_EQUALS -> if (value1 >= value2) 1 else 0
            TreeTokenType.GREATER_THAN -> if (value1 > value2) 1 else 0

            else -> 0
        }
    }

    // we create a map <function id, function AST>
    private fun createFunctionMap(functionList: ArrayList<Ast>): HashMap<String, Ast> {
        val map = HashMap<String, Ast>()

        for (i in functionList) {
            map[i.children[0].head.value] = i
        }

        return map
    }

    private fun createArgumentList(functionCall: Ast): ArrayList<Int> {
        val list = ArrayList<Int>()
        for (i in 1 until functionCall.children.size) {
            list.add(solveExp(functionCall.children[i]))
        }
        return list
    }

    // run a if/else block (that creates a new scope)
    private fun walkBlock(block: Ast) {
        scope.push()
        try {
            walkAst(block)
        } catch (e: ReturnException) {
            // if a value is returned from the block,
            // we still have to pop the scope,
            // then throw the exception
            scope.pop()
            throw e
        }

        scope.pop()
    }
}