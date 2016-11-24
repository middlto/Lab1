package main;

import java.math.BigDecimal;

/**
 * .
 * 存储单元类
 */
public class Cell {
    /**.
     * 指数
     */
    public int exp;
    /**.
     * 字母系数
     */
    public String cofX;
    /**.
     * 数字系数
     */
    public BigDecimal cofN;

    /**.
     * 构造函数
     */
    Cell() {
        exp = -1;
        cofX = "";
        cofN = new BigDecimal(0);
    }
}
