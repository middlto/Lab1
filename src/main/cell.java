package main;

import java.math.BigDecimal;

/**
 * 存储单元类
 */
public class cell {
    public int exp;
    public String cofX;
    public BigDecimal cofN;

    public cell() {
        exp = -1;
        cofX = "";
        cofN = new BigDecimal(0);
    }
}
