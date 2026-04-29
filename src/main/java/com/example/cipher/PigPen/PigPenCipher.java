package com.example.cipher.PigPen;

import com.example.cipher.Cipher;
import java.util.HashMap;
import java.util.Map;

public class PigPenCipher implements Cipher {

  private static final Map<Character, String> ENCRYPT_MAP = new HashMap<>();

  private static final Map<String, Character> DECRYPT_MAP = new HashMap<>();

  static {
    putMapping('A', "┌");
    putMapping('B', "┬");
    putMapping('C', "┐");
    putMapping('D', "├");
    putMapping('E', "┼");
    putMapping('F', "┤");
    putMapping('G', "└");
    putMapping('H', "┴");
    putMapping('I', "┘");

    putMapping('J', "┌•");
    putMapping('K', "┬•");
    putMapping('L', "┐•");
    putMapping('M', "├•");
    putMapping('N', "┼•");
    putMapping('O', "┤•");
    putMapping('P', "└•");
    putMapping('Q', "┴•");
    putMapping('R', "┘•");

    putMapping('S', "╱");
    putMapping('T', "✕");
    putMapping('U', "╲");
    putMapping('V', "V");
    putMapping('W', "╱•");
    putMapping('X', "✕•");
    putMapping('Y', "╲•");
    putMapping('Z', "V•");
  }

  private static void putMapping(char letter, String symbol) {
    ENCRYPT_MAP.put(letter, symbol);
    DECRYPT_MAP.put(symbol, letter);
  }

  @Override
  public String encrypt(String text) {
    return (text == null) ? "" : text.toUpperCase();
  }

  public String toPigpenSymbols(String text) {
    if (text == null || text.isEmpty())
      return "";

    StringBuilder sb = new StringBuilder();
    for (char c : text.toUpperCase().toCharArray()) {
      if (Character.isLetter(c)) {
        sb.append(ENCRYPT_MAP.getOrDefault(c, String.valueOf(c)));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  @Override
  public String decrypt(String text) {
    if (text == null || text.isEmpty())
      return "";

    StringBuilder sb = new StringBuilder();
    int i = 0;

    while (i < text.length()) {
      String symbol = String.valueOf(text.charAt(i));

      if (i + 1 < text.length() && text.charAt(i + 1) == '•') {
        symbol += "•";
        i++;
      }

      Character letter = DECRYPT_MAP.get(symbol);
      if (letter != null) {
        sb.append(letter);
      } else {
        sb.append(text.charAt(i));
      }
      i++;
    }
    return sb.toString();
  }
}