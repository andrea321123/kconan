// SemanticAnalysis.kt
// Version 1.0.6

package kconan.semantic

import kconan.error.ErrorType
import kconan.interpreter.defaultFunctionSet
import kconan.parser.Ast
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType

// Check that all variables are defined,
// that there aren't multiple declaration of vars with same id in the same scope and
// that function call arguments number and function parameters number are the same
// for each function call
fun resolveNames(ast: Ast) {
    resolveFunctionsNames(ast)
    val container = getGlobalVarDeclarations(ast)

    for (i in getFunctionAst(ast)) {
        resolveVarNames(i, container)
    }
}

// Return a SymbolTables data class that holds the
// global variables table and the functions table
fun generateSymbolTables(ast: Ast): SymbolTables {
    var currentNode = ast
    val functionMap = HashMap<String, Ast>()
    val variableMap = HashMap<String, Ast>()

    while (currentNode.children.size == 2) {
        // one child is a function or a global variable,
        // the other is another program
        val tokenType = currentNode.children[0].head.token
        val identifier = currentNode.children[0].children[0].head.value
        if (tokenType == TreeTokenType.FUNCTION) {
            functionMap[identifier] = currentNode.children[0]
        }
        else {      // it is a variable
            variableMap[identifier] = currentNode.children[0]
        }
        currentNode = currentNode.children[1]
    }


    // we resolve the last part of the program
    val tokenType = currentNode.children[0].head.token
    val identifier = currentNode.children[0].children[0].head.value
    if (tokenType == TreeTokenType.FUNCTION) {
        functionMap[identifier] = currentNode.children[0]
    }
    else {      // it is a variable
        variableMap[identifier] = currentNode.children[0]
    }

    return SymbolTables(variableMap, functionMap)
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

// Return a list of Ast with FUNCTION as head token
// [ast] must be a program
fun getFunctionAst(ast: Ast): ArrayList<Ast> {
    val list = ArrayList<Ast>()
    var currentNode = ast

    while (currentNode.children.size == 2) {
        if (currentNode.children[0].head.token == TreeTokenType.FUNCTION) {
            list.add(currentNode.children[0])
        }

        currentNode = currentNode.children[1]
    }

    // check last block
    if (currentNode.children[0].head.token == TreeTokenType.FUNCTION) {
        list.add(currentNode.children[0])
    }

    return list
}