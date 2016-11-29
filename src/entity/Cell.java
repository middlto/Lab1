package entity;

/**
 * Created by meng on 2016/11/23.
 */

import java.math.BigDecimal;

/**
 * .
 * 存储单元类
 */
public class Cell {
    /**.
     * 指数
     */
    private int exp;

    /**.
     * 字母系数
     */
    private String cofX;

    /**.
     * 数字系数
     */
    private BigDecimal cofN;

    /**.
     * 构造函数
     */
    public Cell() {
        exp = -1;
        cofX = "";
        cofN = new BigDecimal(0);
    }

    public int getExp() {
        return exp;
    }

    public void setExp(final int exp) {
        this.exp = exp;
    }

    public String getCofX() {
        return cofX;
    }

    public void setCofX(final String cofX) {
        this.cofX = cofX;
    }

    public BigDecimal getCofN() {
        return cofN;
    }

    public void setCofN(final BigDecimal cofN) {
        this.cofN = cofN;
    }
}
