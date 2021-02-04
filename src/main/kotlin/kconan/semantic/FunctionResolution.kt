// FunctionResolution.kt
// Version 1.0.1

package kconan.semantic

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.token.TreeTokenType

fun resolveFunctionsNames(ast: Ast) {
    val container = getFunctionDeclarations(ast)

    checkFunctionIdDeclared(ast, container)
    checkFunctionCallArgumentsNumber(ast)
}

// Throw an error if there is a function call with different number
// of arguments compared to the actual function's parameters
fun checkFunctionCallArgumentsNumber(ast: Ast) {
    // we create a map function name -> number of parameters
    val map = createMap(ast)

    checkArgumentsNumber(ast, map)
}

private fun checkArgumentsNumber(ast: Ast, map: HashMap<String, Int>) {
    // if we find a function call, check that its arguments size == map[id]
    if (ast.head.token == TreeTokenType.FUNCTION_CALL) {
        val expectedParameters = map[ast.children[0].head.value]
        val actualArguments = ast.children.size -1

        if (expectedParameters != actualArguments) {
            throw Error(ErrorType.COMPILE_ERROR,
                "${ast.children[0].head.value} has $actualArguments arguments" +
                    ", but $expectedParameters expected",
                    ast.head.line, ast.head.column)
        }
    }

    // we call the function on all the children
    for (i in 0 until ast.children.size) {
        checkArgumentsNumber(ast.children[i], map)
    }
}

private fun createMap(ast: Ast): HashMap<String, Int> {
    val map = HashMap<String, Int>()

    if (ast.head.token == TreeTokenType.FUNCTION) {
        map[ast.children[0].head.value] = ast.children[1].children.size
    }

    // we apply the function on all the children
    for (i in 0 until ast.children.size) {
        map += createMap(ast.children[i])
    }

    return map
}

// Throw an error if there are any function call with a non declared id
fun checkFunctionIdDeclared(ast: Ast, container: ScopeContainer<String>) {
    // we only need to check function calls
    if (ast.head.token == TreeTokenType.FUNCTION_CALL) {
        if (!container.contains(ast.children[0].head.value)) {
            throw Error(ErrorType.COMPILE_ERROR,
            "${ast.children[0].head.value} function undefined",
                ast.children[0].head.line,
                ast.children[0].head.column)
        }
    }

    // we call checkFunctionIdDeclared on every child
    for (i in 0 until ast.children.size) {
        checkFunctionIdDeclared(ast.children[i], container)
    }
}