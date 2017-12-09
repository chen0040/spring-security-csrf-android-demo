package com.github.chen0040.bootslingshot.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by xschen on 13/6/2017.
 */
public class StringUtils {
   public static boolean isEmpty(String text){
      return text == null || text.equals("");
   }


   public static String encodeUriComponent(String s) {
      try {
         return URLEncoder.encode(s, "UTF-8")
                 .replaceAll("\\+", "%20")
                 .replaceAll("\\%21", "!")
                 .replaceAll("\\%27", "'")
                 .replaceAll("\\%28", "(")
                 .replaceAll("\\%29", ")")
                 .replaceAll("\\%7E", "~");
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }
      return s;
   }

   public static String toCurrencyString(double val){
      return String.format("%.2f", val);
   }

   public static String toDateString(Date date){
      if(date == null) return "(null)";
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return format.format(date);
   }

   public static String toString(int val){
      return String.format("%d", val);
   }

   public static String toString(float val){
      return String.format("%.2f", val);
   }

   public static String toString(double val){
      return String.format("%.2lf", val);
   }

   public static Date parseDate(String dateString){
      Date date = null;
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try {
         date = format.parse(dateString);
      }catch (ParseException ex){
         ex.printStackTrace();
      }
      return date;
   }

}
