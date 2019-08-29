package com.ted.eBayDIT.utility;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public String generateUserId(int length) {
        return generateRandomString(length);
    }

//    public long generateItemId(Long length) {
//        return generateRandomString(length);
//    }


    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }



    /*Source: https://www.journaldev.com/17899/java-simpledateformat-java-date-format */
    public static String getCurrentDateToStringDataType(){ //In Ebay date format

        String pattern = "MMM-dd-yy HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateStr = simpleDateFormat.format(new Date());
        System.out.println(dateStr);
        return dateStr;
    }

    public static Date convertStringDateToDateDataType(String timeOra) throws ParseException {

        String pattern = "MMM-dd-yy HH:mm:ss";

        Date date1=new SimpleDateFormat(pattern).parse(timeOra);
        System.out.println(timeOra+"\t"+date1);

        return date1;
    }


    public static Date getCurrentDate() throws ParseException { //In Ebay date format

        return convertStringDateToDateDataType(getCurrentDateToStringDataType());
    }



    //Source: https://stackoverflow.com/questions/49752149/how-do-i-convert-2018-04-10t040000-000z-string-to-datetime
    public static String convertFrontDateTypeToBack(String srcEnds) throws ParseException {
        String pattern = "MMM-dd-yy HH:mm:ss";

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat(pattern);

        Date date = inputFormat.parse(srcEnds);
        //        System.out.println(formattedDate); // prints 10-04-2018


        return outputFormat.format(date);
    }



}
