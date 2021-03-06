package com.devinlee.javautildemo.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * BigDecimal常用工具类
 * 常见操作（加减乘除）与格式转换
 * @author devinlee
 *
 */
public class BigDecimalUtil {


    /**
     * 乘法
     * @param multiplicand  被乘数
     * @param multiplier 乘数
     * @return
     */
    public static BigDecimal multiply(BigDecimal multiplicand , BigDecimal multiplier){

        return multiplicand.multiply(multiplier);
    }

    /**
     * 除法
     * @param dividend  被除数
     * @param divisor   除数
     * @return
     */
    public static BigDecimal divide(BigDecimal dividend , BigDecimal divisor){
        return dividend.divide(divisor);
    }

    /**
     * 减法
     * @param subtracted    被减数
     * @param subtraction   减数
     * @return
     */
    public static BigDecimal subtract(BigDecimal subtracted, BigDecimal subtraction){
        return subtracted.subtract(subtraction);
    }

    /**
     * 加法
     * @param first   第一个参数
     * @param second   减数
     * @return
     */
    public static BigDecimal add(BigDecimal first, BigDecimal second){
        return first.add(second);
    }

    /**
     * 第一个是否比第二个小
     * @param first
     * @param second
     * @return
     */
    public static boolean smallerThan (BigDecimal first , BigDecimal second){
        if (first.compareTo(second) < 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 第一个是否比第二个大
     * @param first
     * @param second
     * @return
     */
    public static boolean biggerThan (BigDecimal first , BigDecimal second){
        if (first.compareTo(second) > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 是否相等
     * @param first
     * @param second
     * @return
     */
    public static boolean isEqual(BigDecimal first , BigDecimal second){
        if (first.compareTo(second) == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 四舍五入格式化,保留小数点后几位
     * @param value
     * @return
     */
    public static BigDecimal formatHalfUp(BigDecimal value , int pointLatterBits){
        return value.setScale(pointLatterBits, RoundingMode.HALF_UP);
    }

    /**
     * 保留小数点后几位,后面的全部进1
     *
     * 例如:5.342 保留两位小数结果为:5.35
     * @param value
     * @param pointLatterBits
     * @return
     */
    public static BigDecimal formatAllUp(BigDecimal value , int pointLatterBits){
        return value.setScale(pointLatterBits, RoundingMode.UP);
    }

    /**
     * 保留小数点后几位,后面的全部舍去
     *
     * 例如:5.3456 保留两位小数结果:5.34
     * @param value
     * @param pointLatterBits
     * @return
     */
    public static BigDecimal formatAllDown(BigDecimal value , int pointLatterBits){
        return value.setScale(pointLatterBits, RoundingMode.DOWN);
    }


    /**
     * 金额格式化
     * @param value 金额
     * @param pattern   格式
     * @return
     */
    public static BigDecimal format(BigDecimal value , String pattern){
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String format = decimalFormat.format(value);
        return BigDecimal.valueOf(Double.valueOf(format));
    }

    /**
     * 金额格式化
     * @param value 金额
     * @param pattern   格式
     * @return
     */
    public static String formatForString(BigDecimal value , String pattern){
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }

}
