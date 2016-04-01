package cool.lexer

import spock.lang.Specification

class TokenTypeSpec extends Specification {

    def 'keywords should have lexeme'() {
        expect:
        TokenType.CASE.lexeme == 'case'
        TokenType.CLASS.lexeme == 'class'
    }

    def 'keywords should have as a pattern a lexeme with left anchor and all string should be escaped'() {
        expect:
        TokenType.CASE.pattern == /^\Qcase\E/
        TokenType.CLASS.pattern == /^\Qclass\E/
    }

    def 'markers should have pattern as well and all string should be escaped'() {
        expect:
        TokenType.SEMICOLON.pattern == /^\Q;\E/
        TokenType.ASSIGN.pattern == /^\Q<-\E/
        TokenType.COLON.pattern == /^\Q:\E/ }

    def 'special tokens (ID, INTEGER and STRING) should not have lexemes predefined'() {
        expect:
        TokenType.ID.lexeme == null
        TokenType.INTEGER.lexeme == null
        TokenType.STRING.lexeme == null
    }

    def 'special tokens (ID, INTEGER and STRING) should have patterns'() {
        expect:
        TokenType.ID.pattern != null
        TokenType.INTEGER.pattern != null
        TokenType.STRING.pattern != null
    }
}
