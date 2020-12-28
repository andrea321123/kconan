// Token.kt
// Version 1.0.1

package kconan.token

// Data class containing information about a token
data class Token(val token: TokenType,
                 val value: String,
                 val line: Long,
                 val column: Long) {
    override fun toString(): String {
        var returnString = "$line: $column: ${tokenToInfo[token]}"

        // if token is a constant or an identifier, we also add the
        // value or the identifier (are contained in value)
        if (token == TokenType.TRUE_CONSTANT ||
            token == TokenType.FALSE_CONSTANT ||
            token == TokenType.NULL_CONSTANT ||
            token == TokenType.STRING_CONSTANT ||
            token == TokenType.CHAR_CONSTANT ||
            token == TokenType.INTEGER_CONSTANT ||
            token == TokenType.FLOAT_CONSTANT ||
            token == TokenType.IDENTIFIER) {
            returnString += ": $value"
        }

        return returnString
    }
}

private val tokenToInfo = mapOf<TokenType, String>(
    TokenType.IF_KEYWORD to "if keyword",
    TokenType.ELSE_KEYWORD to "else keyword",
    TokenType.WHILE_KEYWORD to "while keyword",
    TokenType.FOR_KEYWORD to "for keyword",

    TokenType.RETURN_KEYWORD to "return keyword",
    TokenType.BREAK_KEYWORD to "break keyword",
    TokenType.CONTINUE_KEYWORD to "continue keyword",

    TokenType.IMPORT_KEYWORD to "import keyword",
    TokenType.AS_KEYWORD to "as keyword",

    TokenType.THIS_KEYWORD to "this keyword",

    TokenType.VAR_KEYWORD to "var keyword",
    TokenType.FUN_KEYWORD to "fun keyword",
    TokenType.STRUCT_KEYWORD to "struct keyword",

    TokenType.I8_TYPE to "i8 type",
    TokenType.U8_TYPE to "u8 type",
    TokenType.I16_TYPE to "i16 type",
    TokenType.U16_TYPE to "u16 type",
    TokenType.I32_TYPE to "i32 type",
    TokenType.U32_TYPE to "u32 type",
    TokenType.I64_TYPE to "i64 type",
    TokenType.U64_TYPE to "u64 type",
    TokenType.F32_TYPE to "f32 type",
    TokenType.F64_TYPE to "f64 type",
    TokenType.CHAR_TYPE to "char type",

    TokenType.TRUE_CONSTANT to "true constant",
    TokenType.FALSE_CONSTANT to "false constant",
    TokenType.NULL_CONSTANT to "null constant",
    TokenType.INTEGER_CONSTANT to "integer constant",
    TokenType.FLOAT_CONSTANT to "float constant",
    TokenType.STRING_CONSTANT to "string constant",
    TokenType.CHAR_CONSTANT to "char constant",

    TokenType.LOGICAL_AND to "logical and",
    TokenType.LOGICAL_OR to "logical or",
    TokenType.LOGICAL_NOT to "logical not",

    TokenType.BITWISE_AND to "bitwise and",
    TokenType.BITWISE_OR to " bitwise or",
    TokenType.BITWISE_XOR to "bitwise xor",
    TokenType.BITWISE_NOT to "bitwise not",

    TokenType.COMMA to "comma",
    TokenType.PERIOD to "period",
    TokenType.SEMICOLON to "semicolon",
    TokenType.COLON to "colon",
    TokenType.OPENING_CURLY_BRACKET to "opening curly bracket",
    TokenType.CLOSING_CURLY_BRACKET to "closing curly bracket",
    TokenType.OPENING_PARENTHESIS to "opening parenthesis",
    TokenType.CLOSING_PARENTHESIS to "closing parenthesis",
    TokenType.OPENING_SQUARE_BRACKET to "opening square bracket",
    TokenType.CLOSING_SQUARE_BRACKET to "closing square bracket",

    TokenType.ASSIGN to "assignment",

    TokenType.ADDITION to "addition operator",
    TokenType.SUBTRACTION to "subtraction operator",
    TokenType.MULTIPLICATION to "multiplication operator",
    TokenType.DIVISION to "division operator",
    TokenType.REMAINDER to "remainder operator",

    TokenType.EQUALS_TO to "equals operator",
    TokenType.NOT_EQUALS_TO to "not equals operator",
    TokenType.GREATER_THAN to "greater than",
    TokenType.GREATER_OR_EQUALS to "greater or equals",
    TokenType.LESS_THAN to "less than",
    TokenType.LESS_OR_EQUALS to "less or equals",

    TokenType.INCREMENT to "increment operator (prefix or postfix)",
    TokenType.DECREMENT to "decrement operator (prefix or postfix)",
    TokenType.ASSIGNMENT_BY_SUM to "assignment by sum",
    TokenType.ASSIGNMENT_BY_DIFFERENCE to "assignment by difference",
    TokenType.ASSIGNMENT_BY_PRODUCT to "assignment by product",
    TokenType.ASSIGNMENT_BY_QUOTIENT to "assignment by quotient",
    TokenType.ASSIGNMENT_BY_REMAINDER to "assignment by remainder",

    TokenType.IDENTIFIER to "identifier")