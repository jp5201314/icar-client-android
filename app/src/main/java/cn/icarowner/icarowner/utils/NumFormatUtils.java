package cn.icarowner.icarowner.utils;

/**
 * NumFormatUtils
 * create by 崔婧
 * create at 2017/5/18 下午1:40
 */
public class NumFormatUtils {

    public static String formatNumber(int number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return String.valueOf(number);
        }
    }
}
