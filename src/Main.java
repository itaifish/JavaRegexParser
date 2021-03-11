public class Main {
  public static void main(String args[]) {
    final String regex = "a*[01]";
    final SimpleRegexEngine regexEngine = new SimpleRegexEngine(regex);
    System.out.println(regexEngine.match("Xxdsdsasdaaaaaaaaaaaaaaa0"));
  }
}
