package com.ted.eBayDIT.utility;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public static double[] toPrimitive(ArrayList<Double> arrayL) {
        if (arrayL == null) {
            return null;
        }
        final double[] result = new double[arrayL.size()];
        for (int i = 0; i < arrayL.size(); i++) {
            result[i] = arrayL.get(i);
        }

        return result;
    }



    public static ArrayList<Double> sum2ArrayLists(double[] v1 , double[] v2  ){

        if(v1.length != v2.length) {throw new RuntimeException("Can't sum vectors of different size!");}

        ArrayList<Double> sumVector = new ArrayList<>();
//        double[] sumVector = new double[v1.length];


        for (int i = 0; i < v1.length; i++) {

            sumVector.add( v1[i]+ v2[i] );
        }


        return sumVector;
    }

    public static double euclideanDistance(double[] array1, double[] array2)
    {
        double Sum = 0.0;
        for(int i=0;i<array1.length;i++) {
            Sum = Sum + Math.pow((array1[i]-array2[i]),2.0);
        }

        return Math.sqrt(Sum);
    }

    public static double cosineDistance(double[] vectorA, double[] vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }

        double cosineSimilarity;

        if (normA == 0.0 && normB == 0.0)
            cosineSimilarity = 0.0;
        else
            cosineSimilarity = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));


        return  1.0 - cosineSimilarity; //return dist
    }


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
