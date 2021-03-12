package test.java;

import static org.junit.jupiter.api.Assertions.*;

import main.java.SimpleRegexEngine;
import main.java.SimpleRegexEngine.RegexResult;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class SimpleRegexEngineText {
  @Test
  void testRegexMatches() {
    String regex = "a*[01]";
    SimpleRegexEngine regexEngine = new SimpleRegexEngine(regex);
    RegexResult result = regexEngine.match("aaa0");
    try {
      assertNotNull(result);
      assertEquals(0, result.getLineStart());
      assertEquals(4, result.getLineEnd());
      result = regexEngine.match("bbbaa00");
      assertNotNull(result);
      assertEquals(3, result.getLineStart());
      assertEquals(6, result.getLineEnd());
      result = regexEngine.match("abcd2");
      assertNull(result);

      regexEngine = new SimpleRegexEngine("[123456789]+[1234567890]*");
      result = regexEngine.match("354");
      assertNotNull(result);
      assertEquals(0, result.getLineStart());
      assertEquals(3, result.getLineEnd());
      result = regexEngine.match("1005letters");
      assertNotNull(result);
      assertEquals(0, result.getLineStart());
      assertEquals(4, result.getLineEnd());
    } catch (AssertionFailedError e) {
      e.printStackTrace();
      System.out.println(regexEngine.getStateMachine());
      if (result != null) {
        System.out.println(result.toString());
      }
      fail();
    }
  }
}
