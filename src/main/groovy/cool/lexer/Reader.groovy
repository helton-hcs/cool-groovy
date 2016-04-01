package cool.lexer

import groovy.transform.Canonical

import java.util.regex.Matcher

@Canonical
class Reader {
    String content
    int index = 0
    @Delegate Position position = new Position(1, 1)

    Match match(pattern) {
        Match match
        if (!isDone()) {
            Matcher matcher = remainingText =~ pattern
            if (matcher.find()) {
                String matchedText = matcher.group()
                match = new Match(position.row, position.column, matchedText)
                index += matchedText.size()
                position.row += matchedText.findAll("\n").size()
                def lines = matchedText.split("\n")
                if (lines)
                    position.column += lines.last().size()
            }
        }
        return match
    }

    def getRemainingText() {
        if (content)
            content[index..-1]
    }

    boolean isDone() {
        index >= content.size()
    }
}

