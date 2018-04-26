
package com.yl.baiduren.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 检验三证合一代码
 *
 * @date 2017年11月28日下午2:36:04
 */
public class CompanyCodeUtil {



    public static boolean isCompanyCode(String str) {

        // 代码字符集-代码字符
        final String[] codeNo = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G",
                "H", "J", "K", "L", "M", "N", "P", "Q", "R", "T", "U", "W", "X", "Y" };

        // 代码字符集-代码字符数值
        final String[] staVal = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" };

        // 各位置序号上的加权因子
        int[] wi = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 };

        // 统一代码由十八位的数字或大写英文字母（不适用I、O、Z、S、V）组成，第18位为校验位。

        // 第1位为数字或大写英文字母，登记管理部门代码

        // 第2位为数字或大写英文字母，机构类别代码

        // 第3到8位共6位全为数字登记管理机关行政区划码

        // 第9-17位共9位为数字或大写英文字母组织机构代码

        // 第18为为数字或者大写的Y
        String regex = "^([0-9ABCDEFGHJKLMNPQRTUWXY]{2})([0-9]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-9Y])$";

        Pattern pat = Pattern.compile(regex);

        Matcher matcher = pat.matcher(str);

        if (!matcher.matches()) {

            System.out.println("表达式非法！");

            return false;

        }

        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < codeNo.length; i++) {

            map.put(codeNo[i], staVal[i]);

        }

        String[] all = new String[str.length()];

        all[0] = str.substring(0, str.length() - 1);

        all[1] = str.substring(str.length() - 1, str.length());

        final char[] values = all[0].toCharArray();

        int parity = 0;

        for (int i = 0; i < values.length; i++) {

            final String val = Character.toString(values[i]);

            parity += wi[i] * Integer.parseInt(map.get(val).toString());

        }

        String cheak = (31 - parity % 31) == 30 ? "Y" : Integer.toString(31 - parity % 31);

        return cheak.equals(all[1]);
    }

//    public static void main(String[] args) {
//
//        // TODO Auto-generated method stub
//
//        String str = "9A350100M000100Y47";
//
//        boolean res = isCompanyCode(str);
//
//        System.out.println(res);
//
//    }

}
