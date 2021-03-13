// ExpSolver.kt
// Version 1.0.0

package kconan.interpreter

import kconan.parser.Ast
import kconan.parser.token.TreeTokenType
import kconan.util.ScopeMap

// Class that solves AST expression (will be embedded in Interpreter class)
class ExpSolver(val interpreter: Interpreter) {
    // Evaluates an expression
    fun solveExp(ast: Ast): Variable {
        if (ast.head.token == TreeTokenType.INTEGER_CONSTANT) {
            return Variable(VarTypeEnum.I32, Integer.parseInt(ast.head.value))
        }
        if (ast.head.token == TreeTokenType.FUNCTION_CALL) {
            return interpreter.runFunction(interpreter.functionMap[ast.children[0].head.value]!!,
                interpreter.createArgumentList(ast))
        }
        if (ast.children.size == 1) {
            return solveExp(ast.children[0])
        }
        if (ast.head.token == TreeTokenType.IDENTIFIER) {
            return interpreter.scope.get(ast.head.value)!!
        }

        // if arrive here we have a situation of number operator number ... number
        var i = 1
        var tmp = solveExp(ast.children[0])     // store the temporary result
        while (i < ast.children.size) {
            tmp = operator(tmp,
                ast.children[i],
                solveExp(ast.children[i +1]))
            i += 2
        }

        return tmp
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
                }
            }
            TreeTokenType.SUBTRACTION -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> Variable(VarTypeEnum.I8, (value1 as Byte) - (value2 as Byte))
                    VarTypeEnum.I32 -> Variable(VarTypeEnum.I32,(value1 as Int) - (value2 as Int))
                    VarTypeEnum.I64 -> Variable(VarTypeEnum.I64,(value1 as Long) - (value2 as Long))
                }
            }
            TreeTokenType.MULTIPLICATION -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> Variable(VarTypeEnum.I8, (value1 as Byte) * (value2 as Byte))
                    VarTypeEnum.I32 -> Variable(VarTypeEnum.I32,(value1 as Int) * (value2 as Int))
                    VarTypeEnum.I64 -> Variable(VarTypeEnum.I64,(value1 as Long) * (value2 as Long))
                }
            }
            TreeTokenType.DIVISION -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> Variable(VarTypeEnum.I8, (value1 as Byte) / (value2 as Byte))
                    VarTypeEnum.I32 -> Variable(VarTypeEnum.I32,(value1 as Int) / (value2 as Int))
                    VarTypeEnum.I64 -> Variable(VarTypeEnum.I64,(value1 as Long) / (value2 as Long))
                }
            }
            TreeTokenType.REMAINDER -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> Variable(VarTypeEnum.I8, (value1 as Byte) % (value2 as Byte))
                    VarTypeEnum.I32 -> Variable(VarTypeEnum.I32,(value1 as Int) % (value2 as Int))
                    VarTypeEnum.I64 -> Variable(VarTypeEnum.I64,(value1 as Long) % (value2 as Long))
                }
            }

            TreeTokenType.EQUALS_TO -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) == (value2 as Byte))
                            Variable(VarTypeEnum.I8, 1)
                        else
                            Variable(VarTypeEnum.I8, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) == (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) == (value2 as Long))
                            Variable(VarTypeEnum.I64, 1)
                        else
                            Variable(VarTypeEnum.I64, 0)
                    }
                }
            }
            TreeTokenType.NOT_EQUALS_TO -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) != (value2 as Byte))
                            Variable(VarTypeEnum.I8, 1)
                        else
                            Variable(VarTypeEnum.I8, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) != (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) != (value2 as Long))
                            Variable(VarTypeEnum.I64, 1)
                        else
                            Variable(VarTypeEnum.I64, 0)
                    }
                }
            }
            TreeTokenType.LESS_OR_EQUALS -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) <= (value2 as Byte))
                            Variable(VarTypeEnum.I8, 1)
                        else
                            Variable(VarTypeEnum.I8, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) <= (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) <= (value2 as Long))
                            Variable(VarTypeEnum.I64, 1)
                        else
                            Variable(VarTypeEnum.I64, 0)
                    }
                }
            }
            TreeTokenType.LESS_THAN -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) < (value2 as Byte))
                            Variable(VarTypeEnum.I8, 1)
                        else
                            Variable(VarTypeEnum.I8, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) < (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) < (value2 as Long))
                            Variable(VarTypeEnum.I64, 1)
                        else
                            Variable(VarTypeEnum.I64, 0)
                    }
                }
            }
            TreeTokenType.GREATER_OR_EQUALS -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) >= (value2 as Byte))
                            Variable(VarTypeEnum.I8, 1)
                        else
                            Variable(VarTypeEnum.I8, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) >= (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) >= (value2 as Long))
                            Variable(VarTypeEnum.I64, 1)
                        else
                            Variable(VarTypeEnum.I64, 0)
                    }
                }
            }
            TreeTokenType.GREATER_THAN -> {
                return when (var1.type) {
                    VarTypeEnum.I8 -> {
                        if ((value1 as Byte) > (value2 as Byte))
                            Variable(VarTypeEnum.I8, 1)
                        else
                            Variable(VarTypeEnum.I8, 0)
                    }
                    VarTypeEnum.I32 -> {
                        if ((value1 as Int) > (value2 as Int))
                            Variable(VarTypeEnum.I32, 1)
                        else
                            Variable(VarTypeEnum.I32, 0)
                    }
                    VarTypeEnum.I64 -> {
                        if ((value1 as Long) > (value2 as Long))
                            Variable(VarTypeEnum.I64, 1)
                        else
                            Variable(VarTypeEnum.I64, 0)
                    }
                }
            }

            else -> return Variable(VarTypeEnum.I8, 0)
        }
    }

}