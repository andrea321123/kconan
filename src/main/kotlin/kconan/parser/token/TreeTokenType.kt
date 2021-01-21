// TreeTokenType.kt
// Version 1.0.13

package kconan.parser.token

enum class TreeTokenType {
    PROGRAM,
    VAR_INIT,
    NUMERIC_TYPE,
    EXP,
    NUMBER,
    OPERATOR,
    FUNCTION_CALL,

    IF,
    WHILE,

    IF_BODY,
    ELSE_BODY,

    RETURN,

    VAR_ASSIGN,
    STATEMENT,
    FUNCTION,
    PARAMETER,
    PARAMETERS,

    IDENTIFIER,
    INTEGER_CONSTANT,
    FLOAT_CONSTANT,

    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    REMAINDER,

    EQUALS_TO,
    NOT_EQUALS_TO,
    GREATER_THAN,
    GREATER_OR_EQUALS,
    LESS_THAN,
    LESS_OR_EQUALS,

    I8_TYPE,
    U8_TYPE,
    I16_TYPE,
    U16_TYPE,
    I32_TYPE,
    U32_TYPE,
    I64_TYPE,
    U64_TYPE,
    F32_TYPE,
    F64_TYPE,
}