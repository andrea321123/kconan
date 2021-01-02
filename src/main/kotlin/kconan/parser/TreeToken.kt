// Token.kt
// Version 1.0.0

package kconan.parser

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
                token == TreeTokenType.IDENTIFIER) {
            returnString += ": $value"
        }

        return returnString
    }
}

private val treeTokenToInfo = mapOf(
    TreeTokenType.PROGRAM to "program",
    TreeTokenType.VAR_DECLARATION to "variable declaration",
    TreeTokenType.NUMERIC_TYPE to "numeric type",
    TreeTokenType.EXP to "expression",
    TreeTokenType.NUMBER to "number",
    TreeTokenType.OPERATOR to "operator",

    TreeTokenType.ADDITION to "addition",
    TreeTokenType.SUBTRACTION to "subtraction",
    TreeTokenType.MULTIPLICATION to "multiplication",
    TreeTokenType.DIVISION to "division",

    TreeTokenType.IDENTIFIER to "identifier",
    TreeTokenType.INTEGER_CONSTANT to "integer constant",
    TreeTokenType.FLOAT_CONSTANT to "float constant")
