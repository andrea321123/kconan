// CleanTree.kt
// Version 1.0.0

package kconan.parser.grammar.expression

import kconan.lexer.doLexing
import kconan.parser.Ast
import kconan.parser.token.TreeTokenType

// Remove all expression nodes that have only 1 child and
// the child is another expression
fun removeRedundant(ast: Ast) {
    // algorithm must be applied only on exp nodes
    if (ast.head.token != TreeTokenType.EXP) {
        return
    }

    if (ast.children.size == 1 &&
        ast.children[0].head.token == TreeTokenType.EXP) {
        ast.children = ast.children[0].children

        // redundant depth can be higher than 1
        removeRedundant(ast)
    }

    // if it has more than one node, call removeRedundant on all nodes
    if (ast.children.size != 1) {
        for (i in ast.children) {
            removeRedundant(i)
        }
    }
}