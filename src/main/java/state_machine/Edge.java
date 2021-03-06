package main.java.state_machine;

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

  public void setCharacter(char character) {
    this.character = character;
  }

  public void setDestination(Vertex destination) {
    this.destination = destination;
  }
}
