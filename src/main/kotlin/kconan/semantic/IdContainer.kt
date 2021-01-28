// IdContainer
// Version 1.0.1

package kconan.semantic

import java.util.HashSet
import kotlin.collections.ArrayList

// Data structure used to resolve all variable and function names
// Internally it uses an ArrayList<HashSet> as a stack where the last set is the inner scope
// The outer scope is the global scope
// A new scope can be created by function, if, and while
// Note that conan allows shadowing
class IdContainer<T> {
    private val stack = ArrayList<HashSet<T>>()

    init {
        push()
    }

    // create a new scope on top of the stack
    public fun push() {
        stack.add(HashSet())
    }

    // delete the inner scope from the stack
    public fun pop() {
        stack.removeAt(stack.size -1)
    }

    // add an identifier to the last scope
    public fun add(id: T) {
        stack.last().add(id)
    }

    // check if an identifier is present on the stack
    public fun contains(id: String): Boolean {
        // search for the id from the last scope to the global scope
        for (i in stack.size -1 downTo 0) {
            if (stack[i].contains(id)) {
                return true
            }
        }
        return false
    }
}