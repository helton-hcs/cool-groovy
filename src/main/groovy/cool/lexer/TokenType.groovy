package cool.lexer

import cool.exceptions.SyntaxError

import java.util.regex.Pattern

enum TokenType {
    //order is important to try to find the longest match (using regex boundaries to handle that)
    BLANK(pattern:/^[\t\s\n]+/, skip:true),
    SINGLE_LINE_COMMENT(pattern:/^\-\-(.*?)(\n|$)/, skip:true),
    MULTI_LINE_COMMENT(pattern:/^\(\*(.|\n)*?\*\)/, skip:true, patternClosure:{ //the patternClosure avoid StackOverflow produced by complex regexes
        String content, int index ->
            int id = index
            if ((content[id..-1].size() > 1) && (content[id..(id+1)] == '(*')) {
                id += 2
                while ((content[id..-1].size() > 1) && (content[id..(id+1)] != '*)'))
                    id++
                if ((content[id..-1].size() > 1) && (content[id..(id+1)] == '*)')) //in case of unbalanced multiline comment
                    id += 2
                else
                    throw new SyntaxError("Unbalanced multiline comment")
                return content[index..id]
            }
    }),

    CASE(lexeme:'case', keyword:true),
    CLASS(lexeme:'class', keyword:true),
    ELSE(lexeme:'else', keyword:true),
    ESAC(lexeme:'esac', keyword:true),
    FALSE(lexeme:'false', keyword:true),
    FI(lexeme:'fi', keyword:true),
    IF(lexeme:'if', keyword:true),
    IN(lexeme:'in', keyword:true),
    INHERITS(lexeme:'inherits', keyword:true),
    ISVOID(lexeme:'isvoid', keyword:true),
    LET(lexeme:'let', keyword:true),
    LOOP(lexeme:'loop', keyword:true),
    NEW(lexeme:'new', keyword:true),
    NOT(lexeme:'not', keyword:true),
    OF(lexeme:'of', keyword:true),
    POOL(lexeme:'pool', keyword:true),
    THEN(lexeme:'then', keyword:true),
    TRUE(lexeme:'true', keyword:true),
    WHILE(lexeme:'while', keyword:true),

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
    DOT(lexeme:'.'),

    INTEGER(pattern:/^(0|([1-9][0-9]*))/),
    ID(pattern:/^[A-Za-z_][A-Za-z0-9_]*/),
    STRING(pattern:/^\"(\\.|[^"])*\"/)

    String lexeme
    String pattern
    boolean skip = false
    boolean keyword = false
    Closure patternClosure

    def getPattern() {
        if (pattern)
            pattern
        else {
            if (keyword)
                /^\b${Pattern.quote(lexeme)}\b/
            else
                /^${Pattern.quote(lexeme)}/
        }
    }
}