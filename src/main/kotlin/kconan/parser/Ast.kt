// Ast.kt
// Version 1.0.0

package kconan.parser

import kconan.parser.token.TreeToken

// Implement an Abstract syntax tree
class Ast(val head: TreeToken) {
    val children: ArrayList<Ast> = ArrayList()

    fun add(child: Ast) {
        children.add(child)
    }

    override fun toString(): String {
        return toStringRec(0)
    }

    // Return the number of treeToken are in the AST
    fun size(): Long {
        // exit condition
        if (children.size == 0) {
            return 1
        }

        var size: Long = 1
        for (i in children) {
            size += i.size()
        }

        return size
    }

    private fun toStringRec(indent: Int): String {
        // we add the indentation
        var returnString = ""
        for (i in 0 until indent) {
            returnString += " "
        }

        returnString += head.toString() + "\n"

        // we call toStringRec for each child with new indent
        for (i in children) {
            returnString += i.toStringRec(indent +2)
        }

        return returnString
    }
}