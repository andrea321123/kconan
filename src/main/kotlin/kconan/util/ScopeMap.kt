// ScopeMap.kt
// Version 1.0.0

package kconan.util

import java.util.HashSet

// Data structure used to map each variable in its own scope to a <T> object
// (it can be its type or its value)
// The only difference with ScopeContainer is that ScopeContainer uses HashSet,
// ScopeMap use a map
class ScopeMap<T> {
    private val stack = ArrayList<HashMap<String, T>>()

    init {
        push()
    }

    // create a new scope on top of the stack
    fun push() {
        stack.add(HashMap())
    }

    // delete the inner scope from the stack
    fun pop() {
        stack.removeAt(stack.size -1)
    }

    // add an identifier to the last scope
    fun add(key: String, value: T) {
        stack.last()[key] = value
    }

    // check if an identifier is present on the stack
    fun contains(key: String): Boolean {
        // search for the id from the last scope to the global scope
        for (i in stack.size -1 downTo 0) {
            if (stack[i].containsKey(key)) {
                return true
            }
        }
        return false
    }

    fun get(key: String): T? {
        // search for the id from the last scope to the global scope
        for (i in stack.size -1 downTo 0) {
            if (stack[i].containsKey(key)) {
                return stack[i][key];
            }
        }

        return null;
    }

    fun set(key: String, value: T) {
        // search for the id from the last scope to the global scope
        // and it updates only the first occurrence
        for (i in stack.size -1 downTo 0) {
            if (stack[i].containsKey(key)) {
                stack[i][key] = value;
                return
            }
        }
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