package com.br.gamifit.helper;

import android.util.Base64;

public class CodeConversorHelper {

    public static String formatCodeToBase64(String code){
        StringBuilder builder = new StringBuilder();

        builder.append(code.charAt(0));

        for(int i=1;i<code.length();i++){
            if(code.charAt(i)==' '){
                builder.append(code.charAt(i-1));
                if(code.length()-1!=i){
                    builder.append(code.charAt(i+1));
                }
            }
        }

        builder.append(code.charAt(code.length()-1));

        return builder.toString();
    }
}
