// SemanticAnalysis.kt
// Version 1.0.0

package kconan.semantic

import kconan.error.ErrorType
import kconan.parser.Ast

fun resolveNames() {

}

// Add to a IdContainer all function and global variables declaration
fun addGlobalDeclarations(ast: Ast): ScopeContainer<String> {
    val container = ScopeContainer<String>()
    var currentNode = ast

    while (currentNode.children.size == 2) {
        // one child is a function or a global variable,
        // the other is another program
        addIdentifier(currentNode.children[0].children[0].head.value,
            container)
        currentNode = currentNode.children[1]
    }

    // we resolve the last part of the program
    addIdentifier(currentNode.children[0].children[0].head.value,
        container)

    return container
}

private fun addIdentifier(value: String, container: ScopeContainer<String>) {
    if (container.lastContains(value)) {
        throw kconan.error.Error(
            ErrorType.COMPILE_ERROR,
            "$value declared multiple times")
    }

    container.add(value)
}