package com.authh.springjwt.Authentication.controller;

import java.util.Base64;

public class test1 {

        public static void main(String[] args) {
            String hexKey = "913cfccc96cc2f2a8d96001cdf5082a9831c352d3592e20bec29a4e8fadda593";
            byte[] bytes = hexStringToByteArray(hexKey);
            String base64Key = Base64.getEncoder().encodeToString(bytes);
            System.out.println(base64Key);
        }

        private static byte[] hexStringToByteArray(String s) {
            int len = s.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                      + Character.digit(s.charAt(i+1), 16));
            }
            return data;
        }


}
