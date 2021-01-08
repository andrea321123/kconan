// Token.kt
// Version 1.0.4

package kconan.parser.token

// Data class containing information about a tree token
data class TreeToken(val token: TreeTokenType,
                     val value: String,
                     val line: Int,
                     val column: Int) {
    override fun toString(): String {
        var returnString = "$line: $column: ${treeTokenToInfo[token]}"

        // if token is a constant or an identifier, we also add the
        // value or the identifier (are contained in value)
        if (token == TreeTokenType.INTEGER_CONSTANT ||
                token == TreeTokenType.FLOAT_CONSTANT ||
                token == TreeTokenType.IDENTIFIER
        ) {
            returnString += ": $value"
        }

        return returnString
    }
}

private val treeTokenToInfo = mapOf(
    TreeTokenType.PROGRAM to "program",
    TreeTokenType.VAR_INIT to "variable initialization",
    TreeTokenType.NUMERIC_TYPE to "numeric type",
    TreeTokenType.EXP to "expression",
    TreeTokenType.NUMBER to "number",
    TreeTokenType.OPERATOR to "operator",

    TreeTokenType.VAR_ASSIGN to "var assign",
    TreeTokenType.STATEMENT to "statement",

    TreeTokenType.ADDITION to "addition",
    TreeTokenType.SUBTRACTION to "subtraction",
    TreeTokenType.MULTIPLICATION to "multiplication",
    TreeTokenType.DIVISION to "division",

    TreeTokenType.IDENTIFIER to "identifier",
    TreeTokenType.INTEGER_CONSTANT to "integer constant",
    TreeTokenType.FLOAT_CONSTANT to "float constant",

    TreeTokenType.I8_TYPE to "i8 type",
    TreeTokenType.U8_TYPE to "u8 type",
    TreeTokenType.I16_TYPE to "i16 type",
    TreeTokenType.U16_TYPE to "u16 type",
    TreeTokenType.I32_TYPE to "i32 type",
    TreeTokenType.U32_TYPE to "u32 type",
    TreeTokenType.I64_TYPE to "i64 type",
    TreeTokenType.U64_TYPE to "u64 type",
    TreeTokenType.F32_TYPE to "f32 type",
    TreeTokenType.F64_TYPE to "f64 type")