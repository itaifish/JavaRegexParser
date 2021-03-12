package main.java.state_machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StateMachine {

  private Vertex startVertex;
  private String regex;

  public StateMachine(final String regexToParse) {
    this.regex = regexToParse;
    this.startVertex = buildStateMachine(regexToParse);
  }

  public Vertex getRoot() {
    return this.startVertex;
  }

  /**
   * Builds the state machine
   *
   * @param regexToParse Regex expression to convert to state machine
   * @return {@link Vertex}starting vertex of state machine to set object property
   */
  private Vertex buildStateMachine(String regexToParse) {
    int runningId = 0;
    final Vertex startVertex = new Vertex(runningId++);
    Vertex prevVertex = null;
    Vertex currentVertex = startVertex;
    while (!regexToParse.isEmpty()) {
      final char currentCharacter = regexToParse.charAt(0);
      regexToParse = regexToParse.substring(1);
      final Vertex nextVertex = new Vertex(runningId++);

      switch (currentCharacter) {
        case '[':
          final CharacterSetStringPair remainingRegexAndValidCharacters =
              crawlOrCharacterList(regexToParse);
          regexToParse = remainingRegexAndValidCharacters.getString();

          final List<Edge> edges = new ArrayList<>();
          remainingRegexAndValidCharacters.characterSet.forEach(
              character -> {
                edges.add(new Edge(character, nextVertex));
              });
          currentVertex.getEdges().addAll(edges);
          prevVertex = currentVertex;
          currentVertex = nextVertex;
          break;
        case '*':
          /*
           Allow the previous vertex to skip ahead to the next vertex, and have the current
           vertex have free travel (empty character/epsilon)) to previous vertex. This allows
           for 0 or move of the previous vertex's edges
           (prevVertex) --a-> <-ε--(currentVertex)
           (prevVertex) --ε--> (nextVertex)
          */
          prevVertex.getEdges().add(new Edge((char) 0, nextVertex));
          currentVertex.getEdges().add(new Edge((char) 0, prevVertex));
          currentVertex = nextVertex;
          break;
        case '+':
          /*
           Does not allow the previous vertex to skip ahead to the next vertex, but still has the current
           vertex have free travel (empty character/epsilon)) to previous vertex. This allows
           for 1 or move of the previous vertex's edges
           (prevVertex) --a-> <-ε--(currentVertex) --ε--> (nextVertex)
          */
          currentVertex.getEdges().add(new Edge((char) 0, prevVertex));
          currentVertex.getEdges().add(new Edge((char) 0, nextVertex));
          currentVertex = nextVertex;
          break;
        default:
          /*
           Constructs a basic path to the next vertex
           (currentVertex) --a--> (nextVertex)
          */
          currentVertex.getEdges().add(new Edge(currentCharacter, nextVertex));
          prevVertex = currentVertex;
          currentVertex = nextVertex;
          break;
      }
    }

    if (!prevVertex.followAllEpsilonEdges().contains(currentVertex)) {
      currentVertex.setFinalState(true);
    } else {
      prevVertex.setFinalState(true);
      final Vertex finalCurrentVertex = currentVertex;
      final Edge edgeToRemove =
          prevVertex.getEdges().stream()
              .filter(
                  edge -> edge.getCharacter() == 0 && edge.getDestination() == finalCurrentVertex)
              .findFirst()
              .get();
      prevVertex.getEdges().remove(edgeToRemove);
    }
    return startVertex;
  }

  public String toString() {
    return this.regex + "\n" + this.startVertex.toString();
  }

  private CharacterSetStringPair crawlOrCharacterList(final String regexToCrawl) {
    return this.crawlOrCharacterList(new CharacterSetStringPair(new HashSet<>(), regexToCrawl));
  }

  private CharacterSetStringPair crawlOrCharacterList(
      final CharacterSetStringPair remainingRegexAndCurrentValidOrCharacters) {
    final String remainingRegex = remainingRegexAndCurrentValidOrCharacters.getString();
    final Set<Character> currentValidOrCharacters =
        remainingRegexAndCurrentValidOrCharacters.getCharacterSet();
    if (remainingRegex == null || remainingRegex.isEmpty()) {
      throw new IllegalArgumentException(
          "ERROR: Unable to find closing bracket for 'or' expression");
    }
    // If we find the end of the or list, return the current list of valid characters, and the
    // remaining regex, skipping over the closing bracket of course
    if (remainingRegex.charAt(0) == ']') {
      return new CharacterSetStringPair(currentValidOrCharacters, remainingRegex.substring(1));
    } else {
      currentValidOrCharacters.add(remainingRegex.charAt(0));
      final CharacterSetStringPair newRegexCharacterPair =
          new CharacterSetStringPair(currentValidOrCharacters, remainingRegex.substring(1));
      return this.crawlOrCharacterList(newRegexCharacterPair);
    }
  }

  private class CharacterSetStringPair {

    private Set<Character> characterSet;
    private String string;

    public CharacterSetStringPair(final Set<Character> characters, final String string) {
      this.characterSet = characters;
      this.string = string;
    }

    public Set<Character> getCharacterSet() {
      return characterSet;
    }

    public String getString() {
      return string;
    }
  }
}
