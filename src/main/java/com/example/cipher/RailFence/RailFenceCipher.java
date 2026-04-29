package com.example.cipher.RailFence;

import com.example.cipher.Cipher;

public class RailFenceCipher implements Cipher {

    private final int rails;

    public RailFenceCipher(int rails) {
        this.rails = rails;
    }

    @Override
    public String encrypt(String text) {
        if (text == null || text.isEmpty() || rails <= 1 || text.length() <= 1) {
            return text == null ? "" : text;
        }

        StringBuilder[] rows = new StringBuilder[rails];
        for (int i = 0; i < rails; i++) {
            rows[i] = new StringBuilder();
        }

        int currentRail = 0;
        int direction = 1;

        for (char character : text.toCharArray()) {
            rows[currentRail].append(character);

            if (currentRail == 0) {
                direction = 1;
            } else if (currentRail == rails - 1) {
                direction = -1;
            }

            currentRail += direction;
        }

        StringBuilder encryptedText = new StringBuilder();
        for (StringBuilder row : rows) {
            encryptedText.append(row);
        }

        return encryptedText.toString();
    }

    @Override
    public String decrypt(String text) {
        if (text == null || text.isEmpty() || rails <= 1 || text.length() <= 1) {
            return text == null ? "" : text;
        }

        char[][] matrix = new char[rails][text.length()];

        int currentRail = 0;
        int direction = 1;
        for (int column = 0; column < text.length(); column++) {
            matrix[currentRail][column] = '*';

            if (currentRail == 0) {
                direction = 1;
            } else if (currentRail == rails - 1) {
                direction = -1;
            }

            currentRail += direction;
        }

        int index = 0;
        for (int row = 0; row < rails; row++) {
            for (int column = 0; column < text.length(); column++) {
                if (matrix[row][column] == '*' && index < text.length()) {
                    matrix[row][column] = text.charAt(index++);
                }
            }
        }

        StringBuilder decryptedText = new StringBuilder();
        currentRail = 0;
        direction = 1;

        for (int column = 0; column < text.length(); column++) {
            decryptedText.append(matrix[currentRail][column]);

            if (currentRail == 0) {
                direction = 1;
            } else if (currentRail == rails - 1) {
                direction = -1;
            }

            currentRail += direction;
        }

        return decryptedText.toString();
    }
}
