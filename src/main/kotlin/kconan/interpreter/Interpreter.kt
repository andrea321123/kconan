// Interpreter.kt
// Version 1.0.9

package kconan.interpreter

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.token.TreeToken
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
    val expSolver = ExpSolver(this)

    private val functionList = getFunctionAst(ast)
    val functionMap = createFunctionMap(functionList)
    val scope = getGlobalVariables()

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
    fun runFunction(ast: Ast, arguments: ArrayList<Variable>): Variable {
        // we first check if it is a default function
        if (ast.head.token == TreeTokenType.DEFAULT_FUNCTION) {
            return runDefaultFunction(ast.head.value, arguments)
        }

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
                val returnValue = expSolver.solveExp(ast.children[0])
                throw ReturnException(returnValue)
            }
            TreeTokenType.IF -> {
                val condition = expSolver.solveExp(ast.children[0])

                if ((condition.value as Int) == 1) {
                    walkBlock(ast.children[1])
                }
                // we eventually run the else body
                else if (ast.children.size == 3) {
                    walkBlock(ast.children[2])
                }
            }
            TreeTokenType.WHILE -> {
                var condition = expSolver.solveExp(ast.children[0])

                while ((condition.value as Int) == 1) {
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
                    condition = expSolver.solveExp(ast.children[0])
                }
            }
            TreeTokenType.VAR_ASSIGN -> {
                scope.set(ast.children[0].head.value, expSolver.solveExp(ast.children[1]))
            }
            TreeTokenType.VAR_INIT -> {
                scope.add(ast.children[0].head.value, expSolver.solveExp(ast.children[2]))
            }
            TreeTokenType.FUNCTION_CALL -> {
                runFunction(functionMap[ast.children[0].head.value]!!,
                    createArgumentList(ast))
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
    private fun getGlobalVariables(): ScopeMap<Variable> {
        val map = ScopeMap<Variable>()
        var currentNode = ast

        while (currentNode.children.size == 2) {
            // one child is a function or a global variable,
            // the other is another program
            if (currentNode.children[0].head.token == TreeTokenType.VAR_INIT) {
                map.add(currentNode.children[0].children[0].head.value,
                    expSolver.solveExp(currentNode.children[0].children[2].children[0]))
            }
            currentNode = currentNode.children[1]
        }

        // we resolve the last part of the program
        if (currentNode.children[0].head.token == TreeTokenType.VAR_INIT) {
            map.add(currentNode.children[0].children[0].head.value,
                expSolver.solveExp(currentNode.children[0].children[0].children[2]))
        }

        return map
    }



    // we create a map <function id, function AST>
    private fun createFunctionMap(functionList: ArrayList<Ast>): HashMap<String, Ast> {
        val map = HashMap<String, Ast>()

        for (i in functionList) {
            map[i.children[0].head.value] = i
        }

        // we add default functions to the map
        for (i in defaultFunctionSet) {
            val ast = Ast(TreeToken(TreeTokenType.DEFAULT_FUNCTION, i, 0, 0))
            map[i] = ast
        }
        return map
    }

    fun createArgumentList(functionCall: Ast): ArrayList<Variable> {
        val list = ArrayList<Variable>()
        for (i in 1 until functionCall.children.size) {
            list.add(expSolver.solveExp(functionCall.children[i]))
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