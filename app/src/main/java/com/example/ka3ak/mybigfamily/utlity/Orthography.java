package com.example.ka3ak.mybigfamily.utlity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Orthography {

    public static boolean dataRegex(String data){
        Pattern pattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$");
        Matcher matcher = pattern.matcher(data);

        return matcher.matches();
    }
    public static boolean nameRegex(String data){
        Pattern pattern = Pattern.compile("^([A-Z][a-z\\-\\']{1,50})|([А-Я][а-я\\-\\']{1,50})$");
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }


    public static void main(String[] args) {

        System.out.println(  dataRegex("22/12/1988"));
        System.out.println(nameRegex("Члд"));
        System.out.println(nameRegex("лдж"));
        System.out.println(nameRegex("лдДЖжлд"));
        System.out.println(nameRegex("ЖДЭЖ"));
        System.out.println(nameRegex("Dmn"));
        System.out.println(nameRegex("dgfzFFF"));
        System.out.println(nameRegex("SDFFDSF"));
        System.out.println(nameRegex("Sgjhghj"));





    }

}
