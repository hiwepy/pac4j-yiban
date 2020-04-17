package org.pac4j.yiban.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author zd
 */
public class AESDecoder {

    final static String ALGORITHM = "AES";

    final static String TRANSFORM = "AES/CBC/NoPadding";

    public static String dec(String text, String key, String iv)
            throws Exception {
        SecretKeySpec keyval;
        if (iv.length() == 16) {
            keyval = new SecretKeySpec(key.getBytes(), ALGORITHM);
        } else {
            keyval = new SecretKeySpec(key.substring(0, 16).getBytes(), ALGORITHM);
        }
        IvParameterSpec ivspec = new IvParameterSpec(iv.substring(0, 16).getBytes());
        Cipher cipher = Cipher.getInstance(TRANSFORM);
        cipher.init(Cipher.DECRYPT_MODE, keyval, ivspec);
        byte[] buffer = hexToBin(text);
        byte[] decode = cipher.doFinal(buffer);
        return new String(decode);
    }

    public static byte[] hexToBin(String text) {
        if (text.length() < 1) {
            return null;
        }
        int len = text.length() / 2;
        byte[] result = new byte[text.length() / 2];
        for (int i = 0; i < len; i++) {
            result[i] = (byte) (0xff & Integer.parseInt(text.substring(i * 2, i * 2 + 2), 16));
        }
        return result;
    }
}
