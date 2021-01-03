// TokenToTreeToken.kt
// Version 1.0.1

package kconan.parser

import kconan.token.TokenType

val tokenToTreeToken = mapOf(
    TokenType.U8_TYPE to TreeTokenType.U8_TYPE,
    TokenType.I8_TYPE to TreeTokenType.I8_TYPE,
    TokenType.U16_TYPE to TreeTokenType.U16_TYPE,
    TokenType.I16_TYPE to TreeTokenType.I16_TYPE,
    TokenType.U32_TYPE to TreeTokenType.U32_TYPE,
    TokenType.I32_TYPE to TreeTokenType.I32_TYPE,
    TokenType.U64_TYPE to TreeTokenType.U64_TYPE,
    TokenType.I64_TYPE to TreeTokenType.I64_TYPE,
    TokenType.F32_TYPE to TreeTokenType.F32_TYPE,
    TokenType.F64_TYPE to TreeTokenType.F64_TYPE,

    TokenType.IDENTIFIER to TreeTokenType.IDENTIFIER,
    TokenType.INTEGER_CONSTANT to TreeTokenType.INTEGER_CONSTANT,
    TokenType.FLOAT_CONSTANT to TreeTokenType.FLOAT_CONSTANT)