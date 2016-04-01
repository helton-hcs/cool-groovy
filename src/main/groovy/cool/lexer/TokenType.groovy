package cool.lexer

import java.util.regex.Pattern

enum TokenType {
    //order is important to try to find the longest match (example: <inherits> should come before <in>)
    BLANK(pattern:/^[\t\s\n]+/, skip:true),
    COMMENT(pattern:/^\(\*(.*?)\*\)/, skip:true),

    CASE(lexeme:'case'),
    CLASS(lexeme:'class'),
    ELSE(lexeme:'else'),
    ESAC(lexeme:'esac'),
    FALSE(lexeme:'false'),
    FI(lexeme:'fi'),
    IF(lexeme:'if'),
    INHERITS(lexeme:'inherits'),
    IN(lexeme:'in'),
    ISVOID(lexeme:'isvoid'),
    LET(lexeme:'let'),
    LOOP(lexeme:'loop'),
    NEW(lexeme:'new'),
    NOT(lexeme:'not'),
    OF(lexeme:'of'),
    POOL(lexeme:'pool'),
    THEN(lexeme:'then'),
    TRUE(lexeme:'true'),
    WHILE(lexeme:'while'),

    ASSIGN(lexeme:'<-'),
    LEQ(lexeme:'<='),
    GEQ(lexeme:'>='),
    LT(lexeme:'<'),
    EQ(lexeme:'='),
    GT(lexeme:'>'),
    PLUS(lexeme:'+'),
    MINUS(lexeme:'-'),
    TIMES(lexeme:'*'),
    DIVIDE(lexeme:'/'),
    NEGATE(lexeme:'~'),
    LEFT_PAREN(lexeme:'('),
    RIGHT_PAREN(lexeme:')'),
    LEFT_BRACE(lexeme:'{'),
    RIGHT_BRACE(lexeme:'}'),
    SEMICOLON(lexeme:';'),
    COMMA(lexeme:','),
    COLON(lexeme:':'),

    INTEGER(pattern:/^[1-9][0-9]*/),
    ID(pattern:/^[A-Za-z_][A-Za-z0-9_]*/),
    STRING(pattern:/^\"(\\.|[^"])*\"/)

    String lexeme
    String pattern
    boolean skip = false

    def getPattern() {
        if (pattern)
            pattern
        else
            /^${Pattern.quote(lexeme)}/
    }
}