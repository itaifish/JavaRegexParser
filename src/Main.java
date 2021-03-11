import state_machine.StateMachine;

public class Main {
  public static void main(String args[]) {
    final String regex = "a*b*[def]";
    final SimpleRegexEngine regexEngine = new SimpleRegexEngine(regex);
    System.out.println(new StateMachine(regex));
    System.out.println(regexEngine.match("aaabbbbdef")[0]);
  }
}
