package com.bilgeadam.utility;

import java.util.UUID;

public class CodeGenerator {

    public static  String generateCode(){
        String code = UUID.randomUUID().toString();
        String [] data = code.split("-");
        String newCode = "";
        for(String string:data){
            newCode+=string.charAt(0);
            newCode+=string.charAt(1);
        }
        return newCode;
    }
}
