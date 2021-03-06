package test.java;

import static org.junit.jupiter.api.Assertions.*;

import main.java.SimpleRegexEngine;
import main.java.SimpleRegexEngine.RegexResult;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class SimpleRegexEngineTest {
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
      result = regexEngine.match("b1005letters");
      assertNotNull(result);
      assertEquals(1, result.getLineStart());
      assertEquals(5, result.getLineEnd());
      result = regexEngine.match("010");
      assertNotNull(result);
      assertEquals(1, result.getLineStart());
      assertEquals(3, result.getLineEnd());
      result = regexEngine.match("999999999 is a large number. so is five.");
      assertNotNull(result);
      assertEquals(0, result.getLineStart());
      assertEquals(9, result.getLineEnd());
      result = regexEngine.match("0xd");
      assertNull(result);

      regexEngine = new SimpleRegexEngine("[abcdefghij]*q*r*s*t+u*");
      result = regexEngine.match("aqqqqrrrrsssst");
      assertNotNull(result);
      assertEquals(0, result.getLineStart());
      assertEquals(14, result.getLineEnd());
      result = regexEngine.match("jt");
      assertNotNull(result);
      assertEquals(0, result.getLineStart());
      assertEquals(2, result.getLineEnd());

      regexEngine = new SimpleRegexEngine("[123456789]+[1234567890]*more");
      result = regexEngine.match("354more");
      assertNotNull(result);
      assertEquals(0, result.getLineStart());
      assertEquals(7, result.getLineEnd());

      regexEngine = new SimpleRegexEngine("[a*+b]c");
      result = regexEngine.match("*c");
      assertNotNull(result);
      assertEquals(0, result.getLineStart());
      assertEquals(2, result.getLineEnd());

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
