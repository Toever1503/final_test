package com.utils;

import java.util.Base64;

public class Base64Utils {
    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    public static String encode(String str) {
        return encoder.encodeToString(str.getBytes());
    }

    public static String decode(String str) {
        return new String(decoder.decode(str));
    }
}
