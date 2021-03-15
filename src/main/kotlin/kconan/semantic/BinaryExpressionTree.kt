// BinaryExpressionTree.kt
// Version 1.0.0

package kconan.semantic

import kconan.parser.Ast
import kconan.parser.token.TreeToken
import kconan.parser.token.TreeTokenType

// Convert the entire program tree into a new program tree that use only
// binary expression trees
// [ast] mustn't be an expression
fun convert(ast: Ast) {
    for (i in 0 until ast.children.size) {
        if (ast.children[i].head.token == TreeTokenType.EXP) {
            // convert the child into a binary expression
            ast.children[i] = toBinaryExpression(ast.children[i])
        }
        else {      // we call convert() on the child
            convert(ast.children[i])
        }
    }
}

// Return a binary expression tree from an equivalent expression tree into
private fun toBinaryExpression(ast: Ast): Ast {
    if (ast.head.token != TreeTokenType.EXP) {
        for (i in 0 until ast.children.size) {
            ast.children[i] = toBinaryExpression(ast.children[i])
        }
        return ast
    }

    // there are 3 possibilities
    // 1. node has 1 child
    // 2. node has 3 or more children
    when (ast.children.size) {
        1 -> {
            // we call again toBinaryExpression on the child
            // and we return the ast
            ast.children[0] = toBinaryExpression(ast.children[0])
            return ast
        }
        else -> {
            // we create a new Exp node, its child will be the last operator
            // The operator's right child will be the last operand
            // The operator's left child will be the result of
            // toBinaryExpression on a new exp node with the same children
            // of the original node [ast] except the last two
            // (which are the last operator and the last operand)
            val childrenSize = ast.children.size
            val newAst = Ast(TreeToken(TreeTokenType.EXP,
                "",
                ast.head.line,
                ast.head.column))
            val operator = ast.children[childrenSize -2]
            val leftOperand = Ast(TreeToken(TreeTokenType.EXP,
                "",
                ast.head.line,
                ast.head.column))
            for (i in 0 until childrenSize -2) {
                leftOperand.add(ast.children[i])
            }

            operator.add(toBinaryExpression(leftOperand))
            operator.add(toBinaryExpression(ast.children[childrenSize -1]))

            newAst.add(operator)
            return newAst
        }
    }
}