// ScopeContainer.kt
// Version 1.0.5

package kconan.semantic

import java.util.HashSet
import kotlin.collections.ArrayList

// Data structure used to resolve all variable and function names
// Internally it uses an ArrayList<HashSet> as a stack where the last set is the inner scope
// The outer scope is the global scope
// A new scope can be created by function, if, and while
// Note that conan allows shadowing
class ScopeContainer<T> {
    private val stack = ArrayList<HashSet<T>>()

    init {
        push()
    }

    // create a new scope on top of the stack
    fun push() {
        stack.add(HashSet())
    }

    // delete the inner scope from the stack
    fun pop() {
        stack.removeAt(stack.size -1)
    }

    // add an identifier to the last scope
    fun add(id: T) {
        stack.last().add(id)
    }

    // check if an identifier is present on the stack
    fun contains(id: T): Boolean {
        // search for the id from the last scope to the global scope
        for (i in stack.size -1 downTo 0) {
            if (stack[i].contains(id)) {
                return true
            }
        }
        return false
    }

    // check if an identifier is present on the last scope
    fun lastContains(id: T): Boolean {
        return stack[stack.size -1].contains(id)
    }

    // return the depth of the stack
    fun depth(): Int {
        return  stack.size
    }

    // return a list of the elements of the scope at depth [depth]
    fun toList(depth: Int): ArrayList<T> {
        return ArrayList(stack[depth])
    }

    override fun toString(): String {
        var str = ""

        for (i in stack.size -1 downTo 0) {
            stack[i].forEach { e -> str += e.toString() + "\n"}
            str += "----------\n"
        }

        return str
    }
}