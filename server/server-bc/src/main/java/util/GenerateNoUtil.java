package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GenerateNoUtil {
public static void main(String[] args) {
System.out.println(toDateString());
}
//生成流水号
public static String toDateString() {
	SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
	Random rom=new Random();
	int num=rom.nextInt(10000)+10000;
	return format.format(new Date())+num;
}
}
