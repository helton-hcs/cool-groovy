package cool.lexer
import spock.lang.Specification

class ReaderSpec extends Specification {

    def 'it should match when is supposed to'() {
        when:
        Reader reader = new Reader(content: 'this is my text')

        then:
        reader.match(/this/) != null
    }

    def 'it should not match when is not supposed to'() {
        when:
        Reader reader = new Reader(content: 'this is my text')

        then:
        reader.match(/something/) == null
    }

    def 'it should match correctly'() {
        when:
        Reader reader = new Reader(content: 'this is my text')
        Match match = reader.match(/[a-z]+/)

        then:
        match != null
        match.row == 1
        match.column == 1
        match.text == 'this'
        reader.index == 4
        reader.row == 1
        reader.column == 5
    }

    def 'it should match a long text'() {
        when:
        def text = """this is a
long
text 12345
56767"""
        Reader reader = new Reader(content: text)
        Match match = reader.match(/([a-z]|\s)+/)

        then:
        match != null
        match.row == 1
        match.column == 1
        match.text == """this is a
long
text """
        //reader.index == 4
        reader.row == 3
        reader.column == 6
    }
}
