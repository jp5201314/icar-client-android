package cn.icarowner.icarowner.utils;

import java.text.DecimalFormat;

/**
 * OperationUtils
 * create by 崔婧
 * create at 2017/5/18 下午1:40
 */
public class OperationUtils {

    public static int multiplication(double money) {
        return (int) ArithmeticUtil.mul(money, 100);
    }

    public static double division(double money) {

        return ArithmeticUtil.div(money, 100);
    }


    public static String formatNum(double num) {
        String s;
        if (num > 999.99) {
            DecimalFormat df = new DecimalFormat("0,000.00");
            s = df.format(num);
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            s = df.format(num);
        }
        return s;
    }

    public static String formatByDecimalPoint(double num) {
        String s;
        if (num == (int) num) {
            DecimalFormat df = new DecimalFormat("0");
            s = df.format(num);
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            s = df.format(num);
        }
        return s;

    }


}
