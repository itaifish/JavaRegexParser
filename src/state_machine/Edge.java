package state_machine;

import java.util.Set;

public class Edge {

  private char character;
  private Vertex destination;

  /**
   *
   * @param character The character to match with, or the '0' character (ascii code 0) to allow all matches
   * @param destination Destination vertex of the Edge
   */
  public Edge(final char character, final Vertex destination) {
    this.character = character;
    this.destination = destination;
  }

  public char getCharacter() {
    return character;
  }

  public Vertex getDestination() {
    return destination;
  }

  public boolean validate(final Set<Character> characterSetToValidateAgainst) {
    return character == 0 || characterSetToValidateAgainst.contains(character);
  }

  public void setCharacter(char character) {
    this.character = character;
  }

  public void setDestination(Vertex destination) {
    this.destination = destination;
  }
}
