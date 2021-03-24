// SemanticAnalysis.kt
// Version 1.0.8

package kconan.semantic

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.token.TreeTokenType
import kconan.parser.token.treeTokenToInfo

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

// kconan 1.0 only supports i32 integer type
// and only f32 float type
// i8, u8, i16, u16, u32, i64, u64, f64 are therefore not supported
// and should raise a compile error
fun unsupportedTypes(ast: Ast) {
    if (unsupportedTypesSet.contains(ast.head.token)) {
        throw Error(ErrorType.COMPILE_ERROR,
                    "${treeTokenToInfo[ast.head.token]} type not supported",
                    ast.head.line,
                    ast.head.column)
    }

    for (i in 0 until ast.children.size) {
        unsupportedTypes(ast.children[i])
    }
}
val unsupportedTypesSet = setOf(
    TreeTokenType.I8_TYPE,
    TreeTokenType.U8_TYPE,
    TreeTokenType.I16_TYPE,
    TreeTokenType.U16_TYPE,
    TreeTokenType.U32_TYPE,
    TreeTokenType.I64_TYPE,
    TreeTokenType.U64_TYPE,
    TreeTokenType.F64_TYPE
)

// Return a SymbolTables data class that holds the
// global variables table and the functions table
fun generateSymbolTables(ast: Ast): SymbolTables {
    var currentNode = ast
    val functionMap = HashMap<String, Ast>()
    val globalVarsMap = HashMap<String, Ast>()
    val localVarsMap = HashMap<String, HashMap <String, TreeTokenType>>()

    while (currentNode.children.size == 2) {
        // one child is a function or a global variable,
        // the other is another program
        val tokenType = currentNode.children[0].head.token
        val identifier = currentNode.children[0].children[0].head.value
        if (tokenType == TreeTokenType.FUNCTION) {
            functionMap[identifier] = currentNode.children[0]
            localVarsMap[identifier] = getLocalVars(currentNode.children[0])
        }
        else {      // it is a variable
            globalVarsMap[identifier] = currentNode.children[0]
        }
        currentNode = currentNode.children[1]
    }

    // we resolve the last part of the program
    val tokenType = currentNode.children[0].head.token
    val identifier = currentNode.children[0].children[0].head.value
    if (tokenType == TreeTokenType.FUNCTION) {
        functionMap[identifier] = currentNode.children[0]
        localVarsMap[identifier] = getLocalVars(currentNode.children[0])
    }
    else {      // it is a variable
        globalVarsMap[identifier] = currentNode.children[0]
    }

    return SymbolTables(globalVarsMap, functionMap, localVarsMap)
}

fun getLocalVars(ast: Ast): HashMap<String, TreeTokenType> {
    val map = HashMap<String, TreeTokenType>()

    // exit condition
    if (ast.head.token == TreeTokenType.VAR_INIT) {
        map[ast.children[0].head.value] = ast.children[1].head.token
        return map
    }

    for (i in 0 until ast.children.size) {
        map += getLocalVars(ast.children[i])
    }
    return map
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
        throw Error(
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