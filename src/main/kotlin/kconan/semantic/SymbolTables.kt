// SymbolTables.kt
// Version 1.0.1

package kconan.semantic

import kconan.interpreter.Variable
import kconan.parser.Ast
import kconan.parser.token.TreeTokenType

// Hold a map [global var id -> Variable]
// and a map [function name -> function AST]
// and a map [function name -> map [ local var id -> type]]
data class SymbolTables(val globalVars: HashMap<String, Ast>,
                        val functions: HashMap<String, Ast>,
                        val localVars: HashMap<String, HashMap<String, TreeTokenType>>)