// Symbols.kt
// Version 1.0.4

package kconan.lexer

import kconan.token.TokenType

// set of digits
val digits = setOf(
    '0','1','2','3','4','5','6','7','8','9')

// set of all valid first symbols of a word
val validFirstLetters = setOf(
    'a','b','c','d','e','f','g','h','i','j',
    'k','l','m','n','o','p','q','r','s','t',
    'u', 'v','w','x','y','z',
    'A','B','C','D','E','F','G','H','I','J',
    'K','L','M','N','O','P','Q','R','S','T',
    'U', 'V','W','X','Y','Z',
    '_')

// set of all valid symbols of a word
val validLetters = validFirstLetters + digits

// set of all symbols used (in operators, conditionals, brackets...)
val symbolsList = setOf(
    '&','|','~','!',
    ',','.',';',':','{','}','(',')','[',']',
    '=','+','-','*','/','%',
    '>','<')

// set of all symbols of length 1
val size1Symbols = setOf(
    "&","|","~","!",
    ",",".",";",":","{","}","(",")","[","]",
    "=","+","-","*","/","%",
    ">","<")

// set of all symbols of length 1
val size2Symbols = setOf(
    "==","!=",">=","<=",
    "++","--","+=","-=","*=","/=","%=")

// set all of words used by conan (not usable for identifiers)
val conanWords = setOf(
    "if","else","while","for",
    "return","break","continue",
    "import","as","this",
    "var","fun","struct",
    "i8","u8","i16","u16","i32","u32","i64","u64","f32","f64","bool","char",
    "true","false","null",
    "and","or","not")

// map each symbol to a tokenType
val symbolsToToken = mapOf(
    "&" to TokenType.BITWISE_AND,
    "|" to TokenType.BITWISE_OR,
    "~" to TokenType.BITWISE_XOR,
    "!" to TokenType.BITWISE_NOT,
    "," to TokenType.COMMA,
    "." to TokenType.PERIOD,
    ";" to TokenType.SEMICOLON,
    ":" to TokenType.COLON,
    "{" to TokenType.OPENING_CURLY_BRACKET,
    "}" to TokenType.CLOSING_CURLY_BRACKET,
    "(" to TokenType.OPENING_PARENTHESIS,
    ")" to TokenType.CLOSING_PARENTHESIS,
    "[" to TokenType.OPENING_SQUARE_BRACKET,
    "]" to TokenType.CLOSING_SQUARE_BRACKET,
    "=" to TokenType.ASSIGN,
    "+" to TokenType.ADDITION,
    "-" to TokenType.SUBTRACTION,
    "*" to TokenType.MULTIPLICATION,
    "/" to TokenType.DIVISION,
    "%" to TokenType.REMAINDER,
    "==" to TokenType.EQUALS_TO,
    "!=" to TokenType.NOT_EQUALS_TO,
    ">" to TokenType.GREATER_THAN,
    ">=" to TokenType.GREATER_OR_EQUALS,
    "<" to TokenType.LESS_THAN,
    "<=" to TokenType.LESS_OR_EQUALS,
    "++" to TokenType.INCREMENT,
    "--" to TokenType.DECREMENT,
    "+=" to TokenType.ASSIGNMENT_BY_SUM,
    "-=" to TokenType.ASSIGNMENT_BY_DIFFERENCE,
    "*=" to TokenType.ASSIGNMENT_BY_PRODUCT,
    "/=" to TokenType.ASSIGNMENT_BY_QUOTIENT,
    "%=" to TokenType.ASSIGNMENT_BY_REMAINDER)

// map each Conan word to a tokenType
val keywordToToken = mapOf(
    "if" to TokenType.IF_KEYWORD,
    "else" to TokenType.ELSE_KEYWORD,
    "while" to TokenType.WHILE_KEYWORD,
    "for" to TokenType.FOR_KEYWORD,
    "return" to TokenType.RETURN_KEYWORD,
    "break" to TokenType.BREAK_KEYWORD,
    "continue" to TokenType.CONTINUE_KEYWORD,
    "import" to TokenType.IMPORT_KEYWORD,
    "as" to TokenType.AS_KEYWORD,
    "this" to TokenType.THIS_KEYWORD,
    "var" to TokenType.VAR_KEYWORD,
    "fun" to TokenType.FUN_KEYWORD,
    "struct" to TokenType.STRUCT_KEYWORD,
    "i8" to TokenType.I8_TYPE,
    "u8" to TokenType.U8_TYPE,
    "i16" to TokenType.I16_TYPE,
    "u16" to TokenType.U16_TYPE,
    "i32" to TokenType.I32_TYPE,
    "u32" to TokenType.U32_TYPE,
    "i64" to TokenType.I64_TYPE,
    "u64" to TokenType.U64_TYPE,
    "f32" to TokenType.F32_TYPE,
    "f64" to TokenType.F64_TYPE,
    "bool" to TokenType.BOOL_TYPE,
    "char" to TokenType.CHAR_TYPE,
    "true" to TokenType.TRUE_CONSTANT,
    "false" to TokenType.FALSE_CONSTANT,
    "null" to TokenType.NULL_CONSTANT,
    "and" to TokenType.LOGICAL_AND,
    "or" to TokenType.LOGICAL_OR,
    "not" to TokenType.LOGICAL_NOT)