package util;


public class StringFormat {
//	public static void main(String[] args) {
//		//System.out.println(toSubString(""));
//		StringBuffer buffer=new StringBuffer();
//		System.out.println("kk"+buffer.toString()+"PP");
//	}
		public static String toSubString(String nickName){
			StringBuffer buffer=new StringBuffer();
			//此处增加对微信昵称过滤掉图片
			//'😭Si😤Qi💓'
		//	String regEx = "[\u4e00-\u9fa5]";
			//Pattern pat = Pattern.compile(regEx);
			for(int i=0;i<nickName.length();i++){
				if(nickName.charAt(i)>=33&&nickName.charAt(i)<=125){
					buffer.append(nickName.charAt(i));
				}
				/*if((nickName.charAt(i)>=65&&nickName.charAt(i)<=90)||(nickName.charAt(i)>=97&&nickName.charAt(i)<=122)){
					nickName=toSrcString(nickName);
					i=0;
					 buffer.append(nickName.charAt(i));
				}*//*if(pat.matcher(String.valueOf(nickName.charAt(i))).find()){
					 buffer.append(nickName.charAt(i));
				}*/
				if(isChinese(nickName.charAt(i))){
					buffer.append(nickName.charAt(i));
				}
			}
			return buffer.toString();
		}
		// 根据Unicode编码完美的判断中文汉字和符号
	    private static boolean isChinese(char c) {
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
		}
	}



