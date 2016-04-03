package cool.lexer

import cool.exceptions.SyntaxError

class Lexer {

    def static tokenize(String source) {
        Reader reader = new Reader(content:source)
        def tokens = []

        while (!reader.isDone()) {
            Match match = null
            for (TokenType type in TokenType.values()) {
                if (type.patternClosure)
                    match = reader.matchWith(type.patternClosure)
                else
                    match = reader.match(type.pattern)
                if (match) {
                    if (!type.skip) {
                        Token token = new Token(type, new Position(match.row, match.column))
                        if ([TokenType.ID, TokenType.INTEGER, TokenType.STRING].contains(type))
                            token.lexeme = match.text
                        tokens << token
                    }
                    break;
                }
            }
            if (!match && !reader.isDone()) //if it didn't match any text and it still have text available to parse
                throw new SyntaxError("No match found. Remaining text = <${reader.remainingText}>")
        }

        tokens
    }

}
