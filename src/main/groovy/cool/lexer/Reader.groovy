package cool.lexer

import groovy.transform.Canonical

import java.util.regex.Matcher

@Canonical
class Reader {
    String content
    int index = 0
    @Delegate Position position = new Position(1, 1)

    private Match updatePosition(String matchedText) {
        Match match = new Match(position.row, position.column, matchedText)
        index += matchedText.size()

        int absoluteIndex = 0
        int row = 0
        int column = 0
        for (String line : content.split("\n")) {
            row++
            if (absoluteIndex + line.size() + 1 > index) {
                column = index - absoluteIndex + 1
                break
            }
            absoluteIndex += line.size() + 1 //including EOL (removed by split function)
        }
        position.row = row
        position.column = column
        match
    }

    Match match(pattern) {
        Match match = null
        if (!isDone()) {
            Matcher matcher = remainingText =~ pattern
            if (matcher.find())
                match = updatePosition(matcher.group())
        }
        return match
    }

    Match matchWith(Closure closure) {
        Match match = null
        if (!isDone()) {
            String matchedText = closure(content, index)
            if (matchedText)
                match = updatePosition(matchedText)
        }
        return match
    }

    def getRemainingText() {
        if (!isDone() && content)
            content[index..-1]
    }

    boolean isDone() {
        index >= content.size()
    }
}

