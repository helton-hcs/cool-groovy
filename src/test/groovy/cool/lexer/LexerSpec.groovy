package cool.lexer

import spock.lang.Specification

class LexerSpec extends Specification {
    def lexer = new Lexer()

    def "it should recognize a empty string"() {
        when:
        def tokens = lexer.tokenize("")

        then:
        tokens == []
    }

    def "it should recognize a CLASS keyword"() {
        when:
        def tokens = lexer.tokenize("class")

        then:
        tokens.size() == 1
        tokens.first() == new Token(type:TokenType.CLASS, position:new Position(1, 1))
    }

    def "it should recognize multiple tokens"() {
        when:
        def tokens = lexer.tokenize("class Foo { }")

        then:
        tokens.size() == 4
        tokens == [new Token(type:TokenType.CLASS, position:new Position(1, 1)),
                   new Token(type:TokenType.ID, position:new Position(1, 7), lexeme:'Foo'),
                   new Token(type:TokenType.LEFT_BRACE, position:new Position(1, 11)),
                   new Token(type:TokenType.RIGHT_BRACE, position:new Position(1, 13))]
    }

    def "it should recognize a text with a string"() {
        when:
        List<Token> tokens = lexer.tokenize("""
            out_string("Hello, World.\n")
        """)

        then:
        tokens.size() == 4
        tokens[0].type == TokenType.ID
        tokens[0].lexeme == 'out_string'

        tokens[1].type == TokenType.LEFT_PAREN

        tokens[2].type == TokenType.STRING
        tokens[2].lexeme == '"Hello, World.\n"'

        tokens[3].type == TokenType.RIGHT_PAREN
    }

    def "it should recognize multiple tokens in multiple lines"() {
        when:
        def tokens = lexer.tokenize(
                """
                    class Main inherits IO {
                      main(): SELF_TYPE {
                        out_string("Hello, World.\n")
                      };
                    };
                """
        )

        then:
        tokens.size() == 19
        tokens.collect { it.type } == [TokenType.CLASS, TokenType.ID, TokenType.INHERITS, TokenType.ID, TokenType.LEFT_BRACE,
                                       TokenType.ID, TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN, TokenType.COLON, TokenType.ID, TokenType.LEFT_BRACE,
                                       TokenType.ID, TokenType.LEFT_PAREN, TokenType.STRING, TokenType.RIGHT_PAREN,
                                       TokenType.RIGHT_BRACE, TokenType.SEMICOLON,
                                       TokenType.RIGHT_BRACE, TokenType.SEMICOLON]
    }

    def "it should skip whitespaces"() {
        when:
        def tokens = lexer.tokenize("       class")

        then:
        tokens.size() == 1
        tokens.first() == new Token(type:TokenType.CLASS, position:new Position(1, 8))
    }

    def "it should skip blanks"() {
        when:
        def tokens = lexer.tokenize("""

class""")

        then:
        tokens.size() == 1
        tokens.first() == new Token(type:TokenType.CLASS, position:new Position(3, 1))
    }

    def "it should ignore comments"() {
        when:
        def tokens = lexer.tokenize("(* test 123 *) class Foo { } (* another test 456 *)")

        then:
        tokens.size() == 4
        tokens.collect { it.type } == [TokenType.CLASS, TokenType.ID, TokenType.LEFT_BRACE, TokenType.RIGHT_BRACE]
    }

}
