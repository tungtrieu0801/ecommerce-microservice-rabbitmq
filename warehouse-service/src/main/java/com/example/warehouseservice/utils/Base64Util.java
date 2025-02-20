package com.example.warehouseservice.utils;


import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

public class Base64Util {

    private Base64Util() {}

    public static String encodeString(String strData) {
        return encodeBinary(strData.getBytes(StandardCharsets.UTF_8));
    }

    public static String encodeBinary(byte[] byteData) {
        return Base64.encodeBase64URLSafeString(byteData);
    }

    public static String decodeString(String base64Data) {
        return decodeBinary(base64Data.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodeBinary(byte[] base64Data) {
        return new String(Base64.decodeBase64(base64Data.toString()), StandardCharsets.UTF_8);
    }
}
