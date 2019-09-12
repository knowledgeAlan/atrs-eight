package com.zzm;

/**
 * @author zhongzuoming <zhongzuoming, 1299076979@qq.com>
 * @version v1.0
 * @Description baipao
 * @encoding UTF-8
 * @date 2019-09-11
 * @time 19:46
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class Algorithm {
    public static void main(String[] args) {

        System.out.println(multiply("9", "99"));
    }


    public static String multiply(String num1, String num2) {
        StringBuffer res = new StringBuffer();

        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int[] mul = new int[num1.length() + num2.length()];

        for (int i = num1.length() - 1; i >= 0; i--) {
            int n1 = charToInt(num1.charAt(i));

            for (int j = num2.length() - 1; j >= 0; j--) {
                int sum = n1 * charToInt(num2.charAt(j)) + mul[i + j + 1];
                mul[i + j + 1] = sum % 10;
                mul[i + j] += sum / 10;
            }
        }

        //处理结果集开始数是0 数字
        if (mul[0] != 0)
            res.append(mul[0]);
        for (int i = 1; i < mul.length; i++) {
            res.append(mul[i]);
        }

        return res.toString();
    }

    public static int charToInt(char ch) {
        return ch - '0';
    }
}
