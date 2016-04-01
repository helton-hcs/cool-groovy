package cool.lexer

import groovy.transform.Canonical

@Canonical
class Token {
    TokenType type
    @Delegate Position position
    def lexeme

    def getLexeme() {
        lexeme ?: type.lexeme
    }
}
