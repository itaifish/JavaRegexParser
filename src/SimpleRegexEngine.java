import state_machine.StateMachine;

public class SimpleRegexEngine {

  public static RegexResult[] parse(final String regex) {
    final StateMachine regexNFA = new StateMachine(regex);

    return null;
  }

  public class RegexResult {
    private final int lineNumber;
    private final int lineStart;
    private final int lineEnd;

    public RegexResult(final int lineNumber, final int lineStart, final int lineEnd) {
      this.lineNumber = lineNumber;
      this.lineStart = lineStart;
      this.lineEnd = lineEnd;
    }

    public int getLineNumber() {
      return lineNumber;
    }

    public int getLineStart() {
      return lineStart;
    }

    public int getLineEnd() {
      return lineEnd;
    }
  }
}

