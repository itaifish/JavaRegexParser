package main.java.state_machine;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Vertex {
  private List<Edge> edges;
  private boolean finalState;
  /* value used exclusively for debugging to differentiate vertices*/
  private int id;

  public Vertex(int id) {
    this(new LinkedList<>(), false, id);
  }

  public Vertex(final List<Edge> edges, final boolean finalState, final int id) {
    this.edges = edges;
    this.finalState = finalState;
    this.id = id;
  }

  public Set<Vertex> followAllNonEpsilonValidEdges(final char character) {
    final Set<Character> legalCharacters = new HashSet<>();
    legalCharacters.add(character);
    return this.followAllNonEpsilonValidEdges(legalCharacters);
  }

  public Set<Vertex> followAllNonEpsilonValidEdges(final Set<Character> legalCharacters) {
    final Set<Vertex> validDestinations = new HashSet<>();
    for (Edge edge : edges) {
      if (legalCharacters.contains(edge.getCharacter())) {
        validDestinations.add(edge.getDestination());
      }
    }
    return validDestinations;
  }

  public Set<Vertex> followAllEpsilonEdges() {
    final Set<Vertex> validDestinations = new HashSet<>();
    for (Edge edge : edges) {
      if (edge.getCharacter() == 0) {
        validDestinations.add(edge.getDestination());
      }
    }
    return validDestinations;
  }



  public List<Edge> getEdges() {
    return edges;
  }

  public boolean isFinalState() {
    return finalState;
  }

  public void setFinalState(boolean finalState) {
    this.finalState = finalState;
  }

  public String toString() {
    return this.toString("", new HashSet<>());
  }

  private String toString(final String indentation, final Set<Edge> alreadyVisited) {
    final StringBuilder result = new StringBuilder();
    edges.forEach(
        edge -> {
          final String additionalIndent = /* edge.getCharacter() == 0 ? "" : */ "\t";
          if (edge.getDestination() != null && !alreadyVisited.contains(edge)) {
            //if(edge.getCharacter() == 0) {
              alreadyVisited.add(edge);
            //}
            final Character output = edge.getCharacter() == 0 ? 'ε' : edge.getCharacter();
            result
                .append(indentation)
                .append(this.toIndividualString())
                .append(" --")
                .append(output)
                .append("--> ").append(edge.getDestination().toIndividualString()).append("\n")
                .append(edge.getDestination().toString(indentation + additionalIndent, alreadyVisited));
          }
        });
    return result.toString();
  }

  private String toIndividualString() {
    return (this.finalState ? "F" : "V") + this.id;
  }
}
