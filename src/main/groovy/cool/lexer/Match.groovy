package cool.lexer

import groovy.transform.Canonical

@Canonical
class Match {
    int row, column
    String text
}
