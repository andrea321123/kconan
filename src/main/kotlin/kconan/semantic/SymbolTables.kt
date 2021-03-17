// SymbolTables.kt
// Version 1.0.0

package kconan.semantic

import kconan.interpreter.Variable
import kconan.parser.Ast

// Hold a map [global var id -> Variable]
// and a map [function name -> function AST]
data class SymbolTables(val globalVars: HashMap<String, Ast>,
                        val functions: HashMap<String, Ast>)
