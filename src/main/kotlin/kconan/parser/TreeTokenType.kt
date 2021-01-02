// TreeTokenType.kt
// Version 1.0.3

package kconan.parser

enum class TreeTokenType {
    PROGRAM,
    VAR_INIT,
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
    DIVISION,

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