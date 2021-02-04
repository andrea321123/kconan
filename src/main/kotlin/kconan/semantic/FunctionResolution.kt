// FunctionResolution.kt
// Version 1.0.0

package kconan.semantic

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.token.TreeTokenType

fun resolveFunctions(ast: Ast) {
    val container = getFunctionDeclarations(ast)

    checkFunctionIdDeclared(ast, container)
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