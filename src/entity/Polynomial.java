package entity;

/**
 * Created by meng on 2016/11/23.
 */
public class Polynomial {
    /**.
     * 判断是否输入过表达式的标记
     */
    private boolean set;

    /**.
     * 表达式
     */
    private String equation;

    /**.
     * 构造函数
     */
    public Polynomial() {
        set = false;
    }

    /**.
     * 表达式设置函数
     *
     * @param s 表达式输入
     */
    public void setEquation(final String s) {
        equation = s;
        set = true;
    }

    public String getEquation() {
        return equation;
    }

    public boolean isSet() {
        return set;
    }
}
