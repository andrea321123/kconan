// TypeResolution.kt
// Version 1.0.3
package kconan.semantic

import kconan.error.Error
import kconan.error.ErrorType
import kconan.parser.Ast
import kconan.parser.grammar.expression.cOperators
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
        val actualType = getType(value.children[2], scopeMap, tables)
        checkSameType(expectedType, actualType,
            value.head.line, value.head.column)
    }

    // check functions return types and function variables type
    for ((_, value) in tables.functions) {
        val expectedType = value.children[2].head.token
        val parameters = value.children[1]

        // add parameters to the scopeMap
        scopeMap.push()
        for (i in 0 until parameters.children.size) {
            scopeMap.add(parameters.children[i].children[0].head.value,
                parameters.children[i].children[1].head.token)
        }

        for (i in 3 until value.children.size) {
            checkReturnType(value.children[i], scopeMap, expectedType, tables)
        }

        scopeMap.pop()
    }
}

// Return the type of an expression and check
// the correctness of all subexpressions
fun getType(exp: Ast, map: ScopeMap<TreeTokenType>, tables: SymbolTables): TreeTokenType {
    if (exp.children.size == 1 &&
            exp.head.token == TreeTokenType.EXP) {
        return getType(exp.children[0], map, tables)
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

    // function calls
    if (exp.head.token == TreeTokenType.FUNCTION_CALL) {
        val functionName = exp.children[0].head.value
        val functionAst = tables.functions[functionName]!!

        // check that each argument have same type of the parameter
        for (i in 1 until exp.children.size) {
            val actualType = getType(exp.children[i], map, tables)
            val expectedType = functionAst.children[1].children[i -1].children[1].head.token
            checkSameType(expectedType, actualType,
                exp.head.line, exp.head.column)
        }

        return functionAst.children[2].head.token
    }

    // operation
    if (exp.children.size == 2) {
        // TODO: check if supported operator on operands
        val type1 = getType(exp.children[0], map, tables)
        val type2 = getType(exp.children[1], map, tables)

        checkSameType(type1, type2, exp.head.line, exp.head.column)

        // Comparison operators (==, >= ...) return i32
        if (cOperators.contains(exp.head.token)) {
            return TreeTokenType.I32_TYPE
        }

        return type1
    }

    return exp.head.token
}

// Check the if the return type of the function is satisfied by every "return" statement.
// It also check the type of each expression in the function
fun checkReturnType(ast: Ast,
                    map: ScopeMap<TreeTokenType>,
                    expectedType: TreeTokenType,
                    tables: SymbolTables) {
    // var init
    if (ast.head.token == TreeTokenType.VAR_INIT) {
        val varType = ast.children[1].head.token
        val actualType = getType(ast.children[2], map, tables)
        checkSameType(varType, actualType,
            ast.head.line, ast.head.column)
        map.add(ast.children[0].head.value, ast.children[1].head.token)
    }
    // var assign
    else if (ast.head.token == TreeTokenType.VAR_ASSIGN) {
        val varType = map.get(ast.children[0].head.value)!!
        val actualType = getType(ast.children[1], map, tables)
        checkSameType(varType, actualType,
            ast.head.line, ast.head.column)
    }
    // return
    else if (ast.head.token == TreeTokenType.RETURN) {
        checkSameType(expectedType, getType(ast.children[0], map, tables),
            ast.head.line, ast.head.column)
    }
    // if
    else if (ast.head.token == TreeTokenType.IF) {
        checkSameType(TreeTokenType.I32_TYPE, getType(ast.children[0], map, tables),
            ast.head.line, ast.head.column)

        val ifBody = ast.children[1]
        map.push()
        for (i in 0 until ifBody.children.size) {
            checkReturnType(ifBody.children[i], map, expectedType, tables)
        }
        map.pop()

        // eventually check the else
        if (ast.children.size == 3) {
            val elseBody = ast.children[2]
            map.push()
            for (i in 0 until elseBody.children.size) {
                checkReturnType(elseBody.children[i], map, expectedType, tables)
            }
            map.pop()
        }
    }
    // while
    else if (ast.head.token == TreeTokenType.WHILE) {
        checkSameType(TreeTokenType.I32_TYPE, getType(ast.children[0], map, tables),
            ast.head.line, ast.head.column)

        val body = ast.children[1]
        map.push()
        for (i in 0 until body.children.size) {
            checkReturnType(body.children[i], map, expectedType, tables)
        }
        map.pop()
    }
    // function call
    else if (ast.head.token == TreeTokenType.FUNCTION_CALL) {
        getType(ast, map, tables)
    }
    else {
        for (i in 0 until ast.children.size) {
            checkReturnType(ast.children[i], map, expectedType, tables)
        }
    }
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