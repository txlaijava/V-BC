 package com.shopping.base.utils;


import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

 /**
  * 主键或单号生成工具类
  * @author wanglu
  *
  */
 public class UniqueIDentifiUtil {

     /**
      * 获取当前时间，转换yyyyMMddhhmmss格式
      * @return
      */
     public static String getDate()
     {
         Date dd = null;
         SimpleDateFormat sDateFormat   =   new SimpleDateFormat("yyyyMMddHHmmssSSS");
         String dateStr   =   sDateFormat.format(new java.util.Date());
         return dateStr;
     }
     /**
      * 将字符型数据转换为Date日期
      * @param dateStr
      * @return
      */
     public static Date strToDate(String dateStr)
     {
         Format f = new SimpleDateFormat("yyyyMMddhhmmss");
         Date d = null;
         try {
             d = (Date) f.parseObject(dateStr);
         } catch (ParseException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
         return d;
     }
     /**
      * 2位的随机码生成
      * @return
      */
     public static String getRandom()
     {
         //Random ran=new Random();

         //return String.valueOf(ran.nextInt(99));]
         return String.valueOf(((int)((Math.random() * 90) + 10)));
     }


     /**
      * 主键或单号ID长度19位，日期：年月日时分秒+随机数：两位
      * @return
      */
     public static String getRandomid()
     {
         StringBuffer sb = new StringBuffer();
         sb.append(UniqueIDentifiUtil.getDate());
         sb.append(UniqueIDentifiUtil.getRandom());
         return sb.toString();
     }

     /**
      * 主键或单号ID长度16位，日期：年月日时分秒+随机数：两位
      * @return
      */
     public static String getmovieid()
     {
         SimpleDateFormat sDateFormat   =   new SimpleDateFormat("yyyyMMddHHmmss");
         String dateStr   =   sDateFormat.format(new java.util.Date());


         StringBuffer sb = new StringBuffer();
         sb.append(dateStr);
         sb.append(UniqueIDentifiUtil.getRandom());
         return sb.toString();
     }

     public static String getVerifyCode(){
         StringBuffer sb = new StringBuffer();
         sb.append(CommUtils.randomInt(4) + "-" + CommUtils.randomInt(4) + "-" + CommUtils.randomInt(4));
         return sb.toString();
     }

 }
