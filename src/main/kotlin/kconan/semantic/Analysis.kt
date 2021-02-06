// SemanticAnalysis.kt
// Version 1.0.3

package kconan.semantic

import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.token.TreeTokenType

fun resolveNames(ast: Ast) {
    resolveFunctionsNames(ast)
    val container = getGlobalVarDeclarations(ast)
    var currentNode = ast

    while (currentNode.children.size == 2) {
        // checkVarNames(currentNode) only inside functions bodies

        currentNode = currentNode.children[1]
    }

    // checkVarNames(currentNode) only if last block is function body
}

// Return a container with all global variables
fun getGlobalVarDeclarations(ast: Ast): ScopeContainer<String> {
    return getGlobalBlocks(ast, TreeTokenType.VAR_INIT)
}

// Return a container with all functions
fun getFunctionDeclarations(ast: Ast): ScopeContainer<String> {
    return getGlobalBlocks(ast, TreeTokenType.FUNCTION)
}

// Add to a IdContainer all blocks that are [type] TreeTokenType
private fun getGlobalBlocks(ast: Ast, type: TreeTokenType): ScopeContainer<String> {
    val container = ScopeContainer<String>()
    var currentNode = ast

    while (currentNode.children.size == 2) {
        // one child is a function or a global variable,
        // the other is another program
        if (currentNode.children[0].head.token == type) {
            addIdentifier(currentNode.children[0].children[0].head.value,
                container)
        }
        currentNode = currentNode.children[1]
    }

    // we resolve the last part of the program
    if (currentNode.children[0].head.token == type) {
        addIdentifier(currentNode.children[0].children[0].head.value,
            container)
    }

    return container
}

fun addIdentifier(value: String, container: ScopeContainer<String>) {
    if (container.lastContains(value)) {
        throw kconan.error.Error(
            ErrorType.COMPILE_ERROR,
            "$value declared multiple times")
    }

    container.add(value)
}