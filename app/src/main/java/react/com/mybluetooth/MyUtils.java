package react.com.mybluetooth;

/**
 * Created by ShiBo on 2016/11/21.
 */
public class MyUtils {
    private static String hexString="0123456789ABCDEF";
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /*
   * 把16进制字符串转换成字节数组
   * @param hex
   * @return
           */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }
    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
    //分钟的转化
    public  static byte minuteJz(String minuteStr) {
        byte s;
        if(minuteStr == null){
            return s = 0x1e;
        }
        else if (minuteStr.equals("00")) {
            return s = 0x00;
        } else if (minuteStr.equals("01")) {
            return s = 0x01;
        } else if (minuteStr.equals("02")) {
            return s = 0x02;
        } else if (minuteStr.equals("03")) {
            return s = 0x03;
        } else if (minuteStr.equals("04")) {
            return s = 0x04;
        } else if (minuteStr.equals("05")) {
            return s = 0x05;
        } else if (minuteStr.equals("06")) {
            return s = 0x06;
        } else if (minuteStr.equals("07")) {
            return s = 0x07;
        } else if (minuteStr.equals("08")) {
            return s = 0x08;
        } else if (minuteStr.equals("09")) {
            return s = 0x09;
        } else if (minuteStr.equals("10")) {
            return s = 0x0a;
        } else if (minuteStr.equals("11")) {
            return s = 0x0b;
        } else if (minuteStr.equals("12")) {
            return s = 0x0c;
        } else if (minuteStr.equals("13")) {
            return s = 0x0d;
        } else if (minuteStr.equals("14")) {
            return s = 0x0e;
        } else if (minuteStr.equals("15")) {
            return s = 0x0f;
        } else if (minuteStr.equals("16")) {
            s = 0x10;
            return s;
        } else if (minuteStr.equals("17")) {
            return s = 0x11;
        } else if (minuteStr.equals("18")) {
            return s = 0x12;
        } else if (minuteStr.equals("19")) {
            return s = 0x13;
        } else if (minuteStr.equals("20")) {
            return s = 0x14;
        } else if (minuteStr.equals("21")) {
            return s = 0x15;
        } else if (minuteStr.equals("22")) {
            return s = 0x16;
        } else if (minuteStr.equals("23")) {
            return s = 0x17;
        } else if (minuteStr.equals("24")) {
            return s = 0x18;
        } else if (minuteStr.equals("25")) {
            return s = 0x19;
        } else if (minuteStr.equals("26")) {
            return s = 0x1a;
        } else if (minuteStr.equals("27")) {
            return s = 0x1b;
        } else if (minuteStr.equals("28")) {
            return s = 0x1c;
        } else if (minuteStr.equals("29")) {
            return s = 0x1d;
        } else if (minuteStr.equals("30")) {
            return s = 0x1e;
        } else if (minuteStr.equals("31")) {
            return s = 0x1f;
        } else if (minuteStr.equals("32")) {
            return s = 0x20;
        } else if (minuteStr.equals("33")) {
            return s = 0x21;
        } else if (minuteStr.equals("34")) {
            return s = 0x22;
        } else if (minuteStr.equals("35")) {
            return s = 0x23;
        } else if (minuteStr.equals("36")) {
            return s = 0x24;
        } else if (minuteStr.equals("37")) {
            return s = 0x25;
        } else if (minuteStr.equals("38")) {
            return s = 0x26;
        } else if (minuteStr.equals("39")) {
            return s = 0x27;
        } else if (minuteStr.equals("40")) {
            return s = 0x28;
        } else if (minuteStr.equals("41")) {
            return s = 0x29;
        } else if (minuteStr.equals("42")) {
            return s = 0x2a;
        } else if (minuteStr.equals("43")) {
            return s = 0x2b;
        } else if (minuteStr.equals("44")) {
            return s = 0x2c;
        } else if (minuteStr.equals("45")) {
            return s = 0x2d;
        } else if (minuteStr.equals("46")) {
            return s = 0x2e;
        } else if (minuteStr.equals("47")) {
            return s = 0x2f;
        } else if (minuteStr.equals("48")) {
            return s = 0x30;
        } else if (minuteStr.equals("49")) {
            return s = 0x31;
        } else if (minuteStr.equals("50")) {
            return s = 0x32;
        } else if (minuteStr.equals("51")) {
            return s = 0x33;
        } else if (minuteStr.equals("52")) {
            return s = 0x34;
        } else if (minuteStr.equals("53")) {
            return s = 0x35;
        } else if (minuteStr.equals("54")) {
            return s = 0x36;
        } else if (minuteStr.equals("55")) {
            return s = 0x37;
        } else if (minuteStr.equals("56")) {
            return s = 0x38;
        } else if (minuteStr.equals("57")) {
            return s = 0x39;
        } else if (minuteStr.equals("58")) {
            return s = 0x3a;
        } else if (minuteStr.equals("59")) {
            return s = 0x3b;
        } else {
            return s=0x30;
        }

    }
    //时间的转化
    public static byte timeJz(String hourStr){
        byte s =0x12;
        if(hourStr!=null) {
            if(hourStr.equals("00")){
                return s = 0x00;
            }
            else if (hourStr.equals("01")) {
                return s = 0x01;
            } else if (hourStr.equals("02")) {
                return s = 0x02;
            } else if (hourStr.equals("03")) {
                return s = 0x03;
            } else if (hourStr.equals("04")) {
                return s = 0x04;
            } else if (hourStr.equals("05")) {
                return s = 0x05;
            } else if (hourStr.equals("06")) {
                return s = 0x06;
            } else if (hourStr.equals("07")) {
                return s = 0x07;
            } else if (hourStr.equals("08")) {
                return s = 0x08;
            } else if (hourStr.equals("09")) {
                return s = 0x09;
            } else if (hourStr.equals("10")) {
                return s = 0x0a;
            } else if (hourStr.equals("11")) {
                return s = 0x0b;
            } else if (hourStr.equals("12")) {
                return s = 0x0c;
            } else if (hourStr.equals("13")) {
                return s = 0x0d;
            } else if (hourStr.equals("14")) {
                return s = 0x0e;
            } else if (hourStr.equals("15")) {
                return s = 0x0f;
            } else if (hourStr.equals("16")) {
                return s = 0x10;
            } else if (hourStr.equals("17")) {
                return s = 0x11;
            } else if (hourStr.equals("18")) {
                return s = 0x12;
            } else if (hourStr.equals("19")) {
                return s = 0x13;
            } else if (hourStr.equals("20")) {
                return s = 0x14;
            } else if (hourStr.equals("21")) {
                return s = 0x15;
            } else if (hourStr.equals("22")) {
                return s = 0x16;
            } else if (hourStr.equals("23")) {
                return s = 0x17;
            } else if (hourStr.equals("24")) {
                return s = 0x18;
            }
        }
        return s;

    }
    //byte[]合并
    public static byte[] bytes2Bytes(byte[] b1,byte[] b2){
        byte[] ret = new byte[b1.length+b2.length];
        System.arraycopy(b1, 0, ret, 0, b1.length);
        System.arraycopy(b2, 0, ret, b1.length, b2.length);
        return ret;
    }
    //GBK中文
    public static String hexToStringGBK(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        try {
            s = new String(baKeyword, "GBK");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
            return "";
        }
        return s;
    }
    //vyte进制转16string
    public static String byteToHexstring(byte[] bytes){
        String hs = "";
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = (Integer.toHexString(bytes[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs;
    }
    /*
* 将字符串编码成16进制数字,适用于所有字符（包括中文）
*/
    public static String encode(String str)
    {
//根据默认编码获取字节数组
        byte[] bytes=str.getBytes();
        StringBuilder sb=new StringBuilder(bytes.length*2);
//将字节数组中每个字节拆解成2位16进制整数
        for(int i=0;i<bytes.length;i++)
        {
            sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
            sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
        }
        return sb.toString();
    }
}
