// VarResolution.kt
// Version 1.0.0

package kconan.semantic

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.token.TreeTokenType

// Throw an error if a variable used in the ast is not present in the scope.
// Must be calle inside a block (not in global scope)
fun resolveVarNames(ast: Ast, container: ScopeContainer<String>) {
    when (ast.head.token) {
        // we must not resolve id of functions or function calls
        TreeTokenType.FUNCTION -> {
            container.push()
            for (i in 1 until ast.children.size) {
                resolveVarNames(ast.children[i], container)
            }
            container.pop()
        }
        TreeTokenType.FUNCTION_CALL -> {
            for (i in 1 until ast.children.size) {
                resolveVarNames(ast.children[i], container)
            }
        }

        // var init add a id to the scope after resolving the expression
        TreeTokenType.VAR_INIT -> {
            resolveVarNames(ast.children[2], container)
            addIdentifier(ast.children[0].head.value, container)
        }

        // parameter add a new id to the scope
        TreeTokenType.PARAMETER -> {
            addIdentifier(ast.children[0].head.value, container)
        }

        // if and while create new scopes
        TreeTokenType.IF_BODY, TreeTokenType.ELSE_BODY -> {
            container.push()
            for (i in 0 until ast.children.size) {
                resolveVarNames(ast.children[i], container)
            }
            container.pop()
        }
        TreeTokenType.WHILE -> {
            resolveVarNames(ast.children[0], container)
            container.push()
            for (i in 1 until ast.children.size) {
                resolveVarNames(ast.children[i], container)
            }
            container.pop()
        }

        // identifiers must be checked
        TreeTokenType.IDENTIFIER -> {
            if (!container.contains(ast.head.value)) {
                throw Error(ErrorType.COMPILE_ERROR,
                "${ast.head.value} not defined",
                ast.head.line, ast.head.column)
            }
        }

        // we call the function on eah child
        else -> {
            for (i in 0 until ast.children.size) {
                resolveVarNames(ast.children[i], container)
            }
        }
    }
}

