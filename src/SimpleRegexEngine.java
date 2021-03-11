import java.util.ArrayList;
import java.util.Set;
import state_machine.StateMachine;
import state_machine.Vertex;

public class SimpleRegexEngine {

  private final StateMachine regexNFA;

  public SimpleRegexEngine(final String regex) {
    this.regexNFA = new StateMachine(regex);
  }

  public RegexResult match(final String stringToMatch) {
    for(int i = 0; i < stringToMatch.length(); i++) {
      final RegexResult regexResult = this.backtrackMatch(new RegexResult(stringToMatch, 0, i, i), this.regexNFA.getRoot());
      if(regexResult != null) {
        return regexResult;
      }
    }
    return null;
  }

  private RegexResult backtrackMatch(RegexResult result, final Vertex currentState) {
    // If at the end of the match, return the result if in a final state, and null otherwise
    final String matchMe = result.getInitialString();
    if(result.lineEnd >= matchMe.length()) {
      if(currentState.isFinalState()) {
        return result;
      } else {
        return null;
      }
    }
    final char currentCharacter = matchMe.charAt(result.lineEnd);
    final Set<Vertex> validCharacterEdges = currentState.followAllNonEpsilonValidEdges(currentCharacter);
    final Set<Vertex> validEpsilonEdges = currentState.followAllEpsilonEdges();
    if(validCharacterEdges.isEmpty() && validEpsilonEdges.isEmpty()) {
      if(currentState.isFinalState()) {
        return result;
      } else {
        return null;
      }
    }
    for(Vertex nextState: validEpsilonEdges) {
      final RegexResult recursiveMatch = backtrackMatch(new RegexResult(matchMe, result.getLineNumber(), result.getLineStart(), result.getLineEnd()), nextState);
      if(recursiveMatch != null) {
        return recursiveMatch;
      }
    }
    for(Vertex nextState: validCharacterEdges) {
      final RegexResult recursiveMatch = backtrackMatch(new RegexResult(matchMe, result.getLineNumber(), result.getLineStart(), result.getLineEnd()+1), nextState);
      if(recursiveMatch != null) {
        return recursiveMatch;
      }
    }
    return null;
  }

  public class RegexResult {

    private final String initialString;
    private final int lineNumber;
    private final int lineStart;
    private final int lineEnd;

    public RegexResult(final String initialString, final int lineNumber, final int lineStart, final int lineEnd) {
      this.initialString = initialString;
      this.lineNumber = lineNumber;
      this.lineStart = lineStart;
      this.lineEnd = lineEnd;
    }
    public String getInitialString() {
      return initialString;
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

    @Override
    public String toString() {
      return "Found match in string '" + initialString + "':\n" + initialString.substring(lineStart, lineEnd);
    }
  }
}

