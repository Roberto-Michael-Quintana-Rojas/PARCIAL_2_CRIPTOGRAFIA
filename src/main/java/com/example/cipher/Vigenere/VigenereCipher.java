package com.example.cipher.Vigenere;

import com.example.cipher.Cipher;

public class VigenereCipher implements Cipher {

    private String key;

    public VigenereCipher(String key) {
        this.key = key.toUpperCase();
    }

    @Override
    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c < 'A' || c > 'Z') {
                result.append(c);
                continue;
            }

            int shift = key.charAt(j % key.length()) - 'A';
            char encrypted = (char) ((c - 'A' + shift) % 26 + 'A');

            result.append(encrypted);
            j++;
        }

        return result.toString();
    }

    @Override
    public String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c < 'A' || c > 'Z') {
                result.append(c);
                continue;
            }

            int shift = key.charAt(j % key.length()) - 'A';
            char decrypted = (char) ((c - 'A' - shift + 26) % 26 + 'A');

            result.append(decrypted);
            j++;
        }

        return result.toString();
    }
}