// ExpSolver.kt
// Version 1.0.3

package kconan.interpreter

import kconan.parser.Ast
import kconan.parser.grammar.expression.operators
import kconan.parser.token.TreeTokenType

// Class that solves AST expression (will be embedded in Interpreter class)
class ExpSolver(val interpreter: Interpreter) {
    // Evaluates an expression
    fun solveExp(ast: Ast): Variable {
        if (ast.head.token == TreeTokenType.INTEGER_CONSTANT) {
            return Variable(VarTypeEnum.I32, Integer.parseInt(ast.head.value))
        }
        if (ast.head.token == TreeTokenType.FLOAT_CONSTANT) {
            return Variable(VarTypeEnum.F32, ast.head.value.toFloat())
        }
        if (ast.head.token == TreeTokenType.FUNCTION_CALL) {
            return interpreter.runFunction(interpreter.functionMap[ast.children[0].head.value]!!,
                interpreter.createArgumentList(ast))
        }
        if (ast.head.token == TreeTokenType.IDENTIFIER) {
            return interpreter.scope.get(ast.head.value)!!
        }
        if (operators.contains(ast.head.token)) {
            return operator(solveExp(ast.children[0]),
                ast,
                solveExp(ast.children[1]))
        }
        else {
            return solveExp(ast.children[0])
        }
    }

    // apply the [operator] on [value1] and [value2]
    private fun operator(var1: Variable, operator: Ast, var2: Variable): Variable {
        val value1 = var1.value
        val value2 = var2.value

        when (operator.head.token) {
            TreeTokenType.ADDITION -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> Variable(VarTypeEnum.I8, (value1 as Byte) + (value2 as Byte))
                    VarTypeEnum.I32 -> Variable(VarTypeEnum.I32,(value1 as Int) + (value2 as Int))
                    VarTypeEnum.I64 -> Variable(VarTypeEnum.I64,(value1 as Long) + (value2 as Long))
                    VarTypeEnum.F32 -> Variable(VarTypeEnum.I64,(value1 as Float) + (value2 as Float))
                }
            }
            TreeTokenType.SUBTRACTION -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> Variable(VarTypeEnum.I8, (value1 as Byte) - (value2 as Byte))
                    VarTypeEnum.I32 -> Variable(VarTypeEnum.I32,(value1 as Int) - (value2 as Int))
                    VarTypeEnum.I64 -> Variable(VarTypeEnum.I64,(value1 as Long) - (value2 as Long))
                    VarTypeEnum.F32 -> Variable(VarTypeEnum.I64,(value1 as Float) - (value2 as Float))
                }
            }
            TreeTokenType.MULTIPLICATION -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> Variable(VarTypeEnum.I8, (value1 as Byte) * (value2 as Byte))
                    VarTypeEnum.I32 -> Variable(VarTypeEnum.I32,(value1 as Int) * (value2 as Int))
                    VarTypeEnum.I64 -> Variable(VarTypeEnum.I64,(value1 as Long) * (value2 as Long))
                    VarTypeEnum.F32 -> Variable(VarTypeEnum.I64,(value1 as Float) * (value2 as Float))
                }
            }
            TreeTokenType.DIVISION -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> Variable(VarTypeEnum.I8, (value1 as Byte) / (value2 as Byte))
                    VarTypeEnum.I32 -> Variable(VarTypeEnum.I32,(value1 as Int) / (value2 as Int))
                    VarTypeEnum.I64 -> Variable(VarTypeEnum.I64,(value1 as Long) / (value2 as Long))
                    VarTypeEnum.F32 -> Variable(VarTypeEnum.I64,(value1 as Float) / (value2 as Float))
                }
            }
            TreeTokenType.REMAINDER -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> Variable(VarTypeEnum.I8, (value1 as Byte) % (value2 as Byte))
                    VarTypeEnum.I32 -> Variable(VarTypeEnum.I32,(value1 as Int) % (value2 as Int))
                    VarTypeEnum.I64 -> Variable(VarTypeEnum.I64,(value1 as Long) % (value2 as Long))
                    VarTypeEnum.F32 -> Variable(VarTypeEnum.I64,(value1 as Float) % (value2 as Float))
                }
            }

            TreeTokenType.EQUALS_TO -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) == (value2 as Byte))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) == (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) == (value2 as Long))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.F32 -> {
                        if ((value1 as Float) == (value2 as Float))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                }
            }
            TreeTokenType.NOT_EQUALS_TO -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) != (value2 as Byte))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) != (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) != (value2 as Long))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.F32 -> {
                        if ((value1 as Float) != (value2 as Float))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                }
            }
            TreeTokenType.LESS_OR_EQUALS -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) <= (value2 as Byte))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) <= (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) <= (value2 as Long))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.F32 -> {
                        if ((value1 as Float) <= (value2 as Float))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                }
            }
            TreeTokenType.LESS_THAN -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) < (value2 as Byte))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) < (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) < (value2 as Long))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.F32 -> {
                        if ((value1 as Float) < (value2 as Float))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                }
            }
            TreeTokenType.GREATER_OR_EQUALS -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) >= (value2 as Byte))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) >= (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) >= (value2 as Long))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.F32 -> {
                        if ((value1 as Float) >= (value2 as Float))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                }
            }
            TreeTokenType.GREATER_THAN -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) > (value2 as Byte))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) > (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) > (value2 as Long))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.F32 -> {
                        if ((value1 as Float) > (value2 as Float))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                }
            }

            else -> return Variable(VarTypeEnum.I32, 0)
        }
    }

}