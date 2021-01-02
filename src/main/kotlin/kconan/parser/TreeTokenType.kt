// TreeTokenType.kt
// Version 1.0.1

package kconan.parser

enum class TreeTokenType {
    PROGRAM,
    VAR_DECLARATION,
    NUMERIC_TYPE,
    EXP,
    NUMBER,
    OPERATOR,

    IDENTIFIER,
    INTEGER_CONSTANT,
    FLOAT_CONSTANT,

    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION
}