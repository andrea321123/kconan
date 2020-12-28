// Symbols.kt
// Version 1.0.0

package kconan.lexer

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
val validLetters = validFirstLetters + setOf(
    '0','1','2','3','4','5','6','7','8','9',)

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
    "++","--","+=","-=","*=","%=")

// set all of words used by conan (not usable for identifiers)
val conanWords = setOf(
    "if","else","while","for",
    "return","break","continue",
    "import","as","this",
    "var","fun","struct",
    "i8","u8","i16","u16","i32","u32","i64","u64","f32","f64",
    "true","false","null",
    "and","or","not")