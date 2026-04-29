package com.example.cipher.Cesar;

import com.example.cipher.Cipher;

public class CesarCipher implements Cipher {

    private int shift;
    private String key;

    public CesarCipher(int shift) {
        this.shift = shift % 26;
        this.key = null;
    }

    public CesarCipher(String key) {
        this.key = key;
    }

    @Override
    public String encrypt(String text) {
        if (key != null && key.length() > 0) {
            return encryptWithKey(text);
        }
        return encryptWithShift(text, shift);
    }

    @Override
    public String decrypt(String text) {
        if (key != null && key.length() > 0) {
            return decryptWithKey(text);
        }
        return encryptWithShift(text, 26 - shift);
    }

    private String encryptWithShift(String text, int shift) {
        String result = "";

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {

                char base;
                if (c >= 'A' && c <= 'Z') {
                    base = 'A';
                } else {
                    base = 'a';
                }

                char encrypted = (char) ((c - base + shift) % 26 + base);
                result = result + encrypted;

            } else {
                result = result + c;
            }
        }

        return result;
    }

    private String encryptWithKey(String text) {
        String result = "";
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {

                char k = key.charAt(keyIndex % key.length());

                if (k >= 'a' && k <= 'z') {
                    k = (char)(k - 32); // convertir a mayúscula
                }

                int shift = k - 'A';

                char base;
                if (c >= 'A' && c <= 'Z') {
                    base = 'A';
                } else {
                    base = 'a';
                }

                char encrypted = (char) ((c - base + shift) % 26 + base);
                result = result + encrypted;

                keyIndex++;

            } else {
                result = result + c;
            }
        }

        return result;
    }

    private String decryptWithKey(String text) {
        String result = "";
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {

                char k = key.charAt(keyIndex % key.length());

                if (k >= 'a' && k <= 'z') {
                    k = (char)(k - 32);
                }

                int shift = k - 'A';

                char base;
                if (c >= 'A' && c <= 'Z') {
                    base = 'A';
                } else {
                    base = 'a';
                }

                char decrypted = (char) ((c - base - shift + 26) % 26 + base);
                result = result + decrypted;

                keyIndex++;

            } else {
                result = result + c;
            }
        }

        return result;
    }
}
