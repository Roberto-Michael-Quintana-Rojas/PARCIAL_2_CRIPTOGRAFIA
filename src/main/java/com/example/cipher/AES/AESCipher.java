package com.example.cipher.AES;

import com.example.cipher.Cipher;

public class AESCipher implements Cipher {

    // S-Box AES estándar
    private static final int[] S_BOX = {
        0x63,0x7c,0x77,0x7b,0xf2,0x6b,0x6f,0xc5,0x30,0x01,0x67,0x2b,0xfe,0xd7,0xab,0x76,
        0xca,0x82,0xc9,0x7d,0xfa,0x59,0x47,0xf0,0xad,0xd4,0xa2,0xaf,0x9c,0xa4,0x72,0xc0,
        0xb7,0xfd,0x93,0x26,0x36,0x3f,0xf7,0xcc,0x34,0xa5,0xe5,0xf1,0x71,0xd8,0x31,0x15,
        0x04,0xc7,0x23,0xc3,0x18,0x96,0x05,0x9a,0x07,0x12,0x80,0xe2,0xeb,0x27,0xb2,0x75,
        0x09,0x83,0x2c,0x1a,0x1b,0x6e,0x5a,0xa0,0x52,0x3b,0xd6,0xb3,0x29,0xe3,0x2f,0x84,
        0x53,0xd1,0x00,0xed,0x20,0xfc,0xb1,0x5b,0x6a,0xcb,0xbe,0x39,0x4a,0x4c,0x58,0xcf,
        0xd0,0xef,0xaa,0xfb,0x43,0x4d,0x33,0x85,0x45,0xf9,0x02,0x7f,0x50,0x3c,0x9f,0xa8,
        0x51,0xa3,0x40,0x8f,0x92,0x9d,0x38,0xf5,0xbc,0xb6,0xda,0x21,0x10,0xff,0xf3,0xd2,
        0xcd,0x0c,0x13,0xec,0x5f,0x97,0x44,0x17,0xc4,0xa7,0x7e,0x3d,0x64,0x5d,0x19,0x73,
        0x60,0x81,0x4f,0xdc,0x22,0x2a,0x90,0x88,0x46,0xee,0xb8,0x14,0xde,0x5e,0x0b,0xdb,
        0xe0,0x32,0x3a,0x0a,0x49,0x06,0x24,0x5c,0xc2,0xd3,0xac,0x62,0x91,0x95,0xe4,0x79,
        0xe7,0xc8,0x37,0x6d,0x8d,0xd5,0x4e,0xa9,0x6c,0x56,0xf4,0xea,0x65,0x7a,0xae,0x08,
        0xba,0x78,0x25,0x2e,0x1c,0xa6,0xb4,0xc6,0xe8,0xdd,0x74,0x1f,0x4b,0xbd,0x8b,0x8a,
        0x70,0x3e,0xb5,0x66,0x48,0x03,0xf6,0x0e,0x61,0x35,0x57,0xb9,0x86,0xc1,0x1d,0x9e,
        0xe1,0xf8,0x98,0x11,0x69,0xd9,0x8e,0x94,0x9b,0x1e,0x87,0xe9,0xce,0x55,0x28,0xdf,
        0x8c,0xa1,0x89,0x0d,0xbf,0xe6,0x42,0x68,0x41,0x99,0x2d,0x0f,0xb0,0x54,0xbb,0x16
    };

    // S-Box inversa
    private static final int[] INV_S_BOX = new int[256];

    // Constantes de ronda
    private static final int[] RCON = {
        0x00,0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80,0x1b,0x36
    };

    static {
        for (int i = 0; i < 256; i++) {
            INV_S_BOX[S_BOX[i]] = i;
        }
    }

    private final byte[] keyBytes;

    public AESCipher(String key) {
        // Normaliza la clave a exactamente 16 bytes (AES-128)
        byte[] raw = key.getBytes();
        keyBytes = new byte[16];
        for (int i = 0; i < 16; i++) {
            keyBytes[i] = i < raw.length ? raw[i] : 0;
        }
    }

    // ─── Operaciones GF(2^8) ───────────────────────────────────────────────

    private int gmul(int a, int b) {
        int p = 0;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) != 0) p ^= a;
            boolean hiBit = (a & 0x80) != 0;
            a = (a << 1) & 0xFF;
            if (hiBit) a ^= 0x1b;
            b >>= 1;
        }
        return p;
    }

    // ─── Key Expansion ────────────────────────────────────────────────────

    private int[][] keyExpansion() {
        int nk = 4, nr = 10;
        int[][] w = new int[4 * (nr + 1)][4];

        for (int i = 0; i < nk; i++) {
            w[i] = new int[]{
                keyBytes[4*i]   & 0xFF,
                keyBytes[4*i+1] & 0xFF,
                keyBytes[4*i+2] & 0xFF,
                keyBytes[4*i+3] & 0xFF
            };
        }

        for (int i = nk; i < 4 * (nr + 1); i++) {
            int[] temp = w[i-1].clone();
            if (i % nk == 0) {
                // RotWord
                int t = temp[0];
                temp[0] = temp[1]; temp[1] = temp[2];
                temp[2] = temp[3]; temp[3] = t;
                // SubWord
                for (int j = 0; j < 4; j++) temp[j] = S_BOX[temp[j]];
                temp[0] ^= RCON[i / nk];
            }
            for (int j = 0; j < 4; j++) {
                w[i][j] = w[i-nk][j] ^ temp[j];
            }
        }
        return w;
    }

    // ─── Operaciones de bloque ────────────────────────────────────────────

    private int[][] bytesToState(byte[] block) {
        int[][] state = new int[4][4];
        for (int c = 0; c < 4; c++)
            for (int r = 0; r < 4; r++)
                state[r][c] = block[r + 4*c] & 0xFF;
        return state;
    }

    private byte[] stateToBytes(int[][] state) {
        byte[] out = new byte[16];
        for (int c = 0; c < 4; c++)
            for (int r = 0; r < 4; r++)
                out[r + 4*c] = (byte) state[r][c];
        return out;
    }

    private void addRoundKey(int[][] state, int[][] w, int round) {
        for (int c = 0; c < 4; c++)
            for (int r = 0; r < 4; r++)
                state[r][c] ^= w[round*4 + c][r];
    }

    private void subBytes(int[][] state) {
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                state[r][c] = S_BOX[state[r][c]];
    }

    private void invSubBytes(int[][] state) {
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                state[r][c] = INV_S_BOX[state[r][c]];
    }

    private void shiftRows(int[][] state) {
        for (int r = 1; r < 4; r++) {
            int[] row = state[r].clone();
            for (int c = 0; c < 4; c++)
                state[r][c] = row[(c + r) % 4];
        }
    }

    private void invShiftRows(int[][] state) {
        for (int r = 1; r < 4; r++) {
            int[] row = state[r].clone();
            for (int c = 0; c < 4; c++)
                state[r][c] = row[(c - r + 4) % 4];
        }
    }

    private void mixColumns(int[][] state) {
        for (int c = 0; c < 4; c++) {
            int s0 = state[0][c], s1 = state[1][c],
                s2 = state[2][c], s3 = state[3][c];
            state[0][c] = gmul(0x02,s0)^gmul(0x03,s1)^s2^s3;
            state[1][c] = s0^gmul(0x02,s1)^gmul(0x03,s2)^s3;
            state[2][c] = s0^s1^gmul(0x02,s2)^gmul(0x03,s3);
            state[3][c] = gmul(0x03,s0)^s1^s2^gmul(0x02,s3);
        }
    }

    private void invMixColumns(int[][] state) {
        for (int c = 0; c < 4; c++) {
            int s0 = state[0][c], s1 = state[1][c],
                s2 = state[2][c], s3 = state[3][c];
            state[0][c] = gmul(0x0e,s0)^gmul(0x0b,s1)^gmul(0x0d,s2)^gmul(0x09,s3);
            state[1][c] = gmul(0x09,s0)^gmul(0x0e,s1)^gmul(0x0b,s2)^gmul(0x0d,s3);
            state[2][c] = gmul(0x0d,s0)^gmul(0x09,s1)^gmul(0x0e,s2)^gmul(0x0b,s3);
            state[3][c] = gmul(0x0b,s0)^gmul(0x0d,s1)^gmul(0x09,s2)^gmul(0x0e,s3);
        }
    }

    // ─── Cifrado / Descifrado de un bloque de 16 bytes ───────────────────

    private byte[] encryptBlock(byte[] block) {
        int[][] w = keyExpansion();
        int[][] state = bytesToState(block);
        addRoundKey(state, w, 0);
        for (int round = 1; round < 10; round++) {
            subBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state, w, round);
        }
        subBytes(state);
        shiftRows(state);
        addRoundKey(state, w, 10);
        return stateToBytes(state);
    }

    private byte[] decryptBlock(byte[] block) {
        int[][] w = keyExpansion();
        int[][] state = bytesToState(block);
        addRoundKey(state, w, 10);
        for (int round = 9; round >= 1; round--) {
            invShiftRows(state);
            invSubBytes(state);
            addRoundKey(state, w, round);
            invMixColumns(state);
        }
        invShiftRows(state);
        invSubBytes(state);
        addRoundKey(state, w, 0);
        return stateToBytes(state);
    }

    // ─── Padding PKCS#7 ──────────────────────────────────────────────────

    private byte[] applyPadding(byte[] data) {
        int pad = 16 - (data.length % 16);
        byte[] padded = new byte[data.length + pad];
        System.arraycopy(data, 0, padded, 0, data.length);
        for (int i = data.length; i < padded.length; i++) padded[i] = (byte) pad;
        return padded;
    }

    private byte[] removePadding(byte[] data) {
        int pad = data[data.length - 1] & 0xFF;
        byte[] result = new byte[data.length - pad];
        System.arraycopy(data, 0, result, 0, result.length);
        return result;
    }

    // ─── Helpers hex ─────────────────────────────────────────────────────

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02X", b));
        return sb.toString();
    }

    private byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2)
            data[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
        return data;
    }

    // ─── API pública ──────────────────────────────────────────────────────

    @Override
    public String encrypt(String text) {
        byte[] data = applyPadding(text.getBytes());
        byte[] output = new byte[data.length];
        for (int i = 0; i < data.length; i += 16) {
            byte[] block = new byte[16];
            System.arraycopy(data, i, block, 0, 16);
            byte[] enc = encryptBlock(block);
            System.arraycopy(enc, 0, output, i, 16);
        }
        return bytesToHex(output);
    }

    @Override
    public String decrypt(String hexText) {
        byte[] data = hexToBytes(hexText);
        byte[] output = new byte[data.length];
        for (int i = 0; i < data.length; i += 16) {
            byte[] block = new byte[16];
            System.arraycopy(data, i, block, 0, 16);
            byte[] dec = decryptBlock(block);
            System.arraycopy(dec, 0, output, i, 16);
        }
        return new String(removePadding(output));
    }
}