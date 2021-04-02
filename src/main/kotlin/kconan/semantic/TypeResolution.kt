// TypeResolution.kt
// Version 1.0.1
package kconan.semantic

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.token.TreeTokenType
import kconan.util.ScopeMap

fun typeResolution(tables: SymbolTables) {
    // add global vars to the scope
    val scopeMap = ScopeMap<TreeTokenType>()
    for ((key, value) in tables.globalVars) {
        scopeMap.add(key, value.children[1].head.token)
    }

    // check global variables type
    for ((_, value) in tables.globalVars) {
        val expectedType = value.children[1].head.token
        val actualType = getType(value.children[2], scopeMap)
        checkSameType(expectedType, actualType,
            value.head.line, value.head.column)
    }

    // TODO: check functions return types and function variables type
}

// Return the type of an expression and check
// the correctness of all subexpressions
fun getType(exp: Ast, map: ScopeMap<TreeTokenType>): TreeTokenType {
    if (exp.children.size == 1 &&
            exp.head.token == TreeTokenType.EXP) {
        return getType(exp.children[0], map)
    }

    // literals
    if (exp.head.token == TreeTokenType.INTEGER_CONSTANT) {
        return TreeTokenType.I32_TYPE
    }
    if (exp.head.token == TreeTokenType.FLOAT_CONSTANT) {
        return TreeTokenType.F32_TYPE
    }

    // variables
    if (exp.head.token == TreeTokenType.IDENTIFIER) {
        return map.get(exp.head.value)
            ?: throw Error(
                ErrorType.RUNTIME_ERROR,
                "${exp.head.value} is not in scope",
                exp.head.line,
                exp.head.column
            )
    }

    // TODO: check function calls

    // operation
    if (exp.children.size == 2) {
        // TODO: check if supported operator on operands
        val type1 = getType(exp.children[0], map)
        val type2 = getType(exp.children[1], map)

        checkSameType(type1, type2, exp.head.line, exp.head.column)

        // TODO: Comparison operators (==, >= ...) should return i32
        return type1
    }

    return exp.head.token
}

private fun checkSameType(type1: TreeTokenType,
                          type2: TreeTokenType,
                          line: Int,
                          column: Int) {
    if (type1 != type2) {
        throw Error(ErrorType.RUNTIME_ERROR,
            "$type1 type expected; found $type2",
            line,
            column)
    }
}