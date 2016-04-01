package cool.lexer

class Lexer {

    def tokenize(String source) {
        Reader reader = new Reader(content:source)
        def tokens = []

        while (!reader.isDone()) {
            for (TokenType type in TokenType.values()) {
                Match match = reader.match(type.pattern)
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
        }

        tokens
    }

}
