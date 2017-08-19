package tdiant.tbeko.core.math;

import java.math.BigDecimal;

/**
 * Created by tdiant on 2017/8/18.
 */
public class ArithHelper {
    private static final int DEF_DIV_SCALE = 16; // 除法运算精度

    private ArithHelper() {
    }

    public static double add(String v1, String v2) { //加法
        BigDecimal b1 = new BigDecimal(v1) ,b2 = new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }

    public static double sub(String v1, String v2) { //减法
        BigDecimal b1 = new BigDecimal(v1), b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }

    public static double mul(String v1, String v2) { //乘法
        BigDecimal b1 = new BigDecimal(v1) ,b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }

    public static double div(String v1, String v2) { //除法
        BigDecimal b1 = new BigDecimal(v1), b2 = new BigDecimal(v2);
        return b1.divide(b2, DEF_DIV_SCALE, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double quotient(String v1, String v2){ //取商
        BigDecimal b1 = new BigDecimal(v1) ,b2 = new java.math.BigDecimal(v2);
        return b1.divide(b2).setScale(0, BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static double mod(String v1, String v2){
        BigDecimal b1 = new BigDecimal(v1) , b2 = new BigDecimal(v2);
        return b1.divideAndRemainder(b2)[1].doubleValue();
    }
}
