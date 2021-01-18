// OperatorGroups.kt
// Version 1.0.1

package kconan.parser.grammar.expression

import kconan.parser.token.TreeTokenType

val pOperators = setOf(
    TreeTokenType.MULTIPLICATION,
    TreeTokenType.DIVISION,
    TreeTokenType.REMAINDER)

val sOperators = setOf(
    TreeTokenType.ADDITION,
    TreeTokenType.SUBTRACTION)

val cOperators = setOf(
    TreeTokenType.EQUALS_TO,
    TreeTokenType.NOT_EQUALS_TO,
    TreeTokenType.GREATER_THAN,
    TreeTokenType.GREATER_OR_EQUALS,
    TreeTokenType.LESS_THAN,
    TreeTokenType.LESS_OR_EQUALS)

