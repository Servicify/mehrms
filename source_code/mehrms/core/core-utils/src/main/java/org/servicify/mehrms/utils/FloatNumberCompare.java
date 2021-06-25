package org.servicify.mehrms.utils;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
//比较单精度浮点数
public class FloatNumberCompare {
    public static boolean isEqual(float a, float b) {
        if (Float.isNaN(a) || Float.isNaN(b) || Float.isInfinite(a) || Float.isInfinite(b)) {
            return false;
        }
        return (a - b) < 0.001f;
    }
}
