package control;

import entity.Cell;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by meng on 2016/11/23.
 */
public class PolynomialControl {
    /**.
     * 获取变量在该项中的指数
     *
     * @param key  变量
     * @param item 该项
     * @return 指数
     */
    private static int getExp(final String key, final String item) {
        int exp = 0;
        Pattern p = Pattern.compile("(?<=[^a-zA-Z]|^)" + key + "(\\^(\\d+))?(?=([+*-]|$))");
        Matcher m = p.matcher(item);
        while (m.find()) {
            if (m.group(0).equals(key)) {
                exp++;
            } else {
                exp += Integer.parseInt(m.group(2));
            }
        }
        return exp;
    }

    /**.
     * 单项制表函数
     *
     * @param item 单项表达式
     * @param key  制表基变量
     * @param c    数据表
     */
    private static void makeTable(final String item, final String key, final Cell[] c) {
        int exp;
        BigDecimal cofN = new BigDecimal(1);
        String cofX = "";

        //计算指数
        exp = getExp(key, item);

        //计算数字系数
        Pattern p = Pattern.compile("(?<=([^^\\d]|^))(-?\\d+(\\.\\d+)?)(?=\\*|$|[a-zA-Z])");
        Matcher m = p.matcher(item);
        while (m.find()) {
            cofN = cofN.multiply(new BigDecimal(m.group(0)));
        }
        p = Pattern.compile("-(?=[a-zA-Z])");
        m = p.matcher(item);
        while (m.find()) {
            cofN = cofN.multiply(new BigDecimal(-1));
        }


        //统计非数字系数
        p = Pattern.compile("([a-zA-Z]+)(\\^(\\d+))?");
        m = p.matcher(item);
        while (m.find()) {
            String inItem = m.group(1);
            if (!inItem.equals(key)) {
                Pattern p1 = Pattern.compile("(?<=[^a-zA-Z]|^)" + inItem
                        + "(?=[^a-zA-Z]|$)");
                Matcher m1 = p1.matcher(cofX);
                if (!m1.find()) {
                    int expX = 0;
                    expX = getExp(inItem, item);
                    if (expX == 1) {
                        cofX += "*" + inItem;
                    } else {
                        cofX += "*" + inItem + "^" + expX;
                    }
                }
            }
        }
        if (!cofX.equals("")) {
            cofX = cofX.substring(1);   //删掉开头乘号
        }
        boolean find = false;
        int lenOfList;

        //将该项数据放入表中，合并同类项
        for (lenOfList = 0; lenOfList < c.length; lenOfList++) {
            if (c[lenOfList].getExp() == exp && c[lenOfList].getCofX().equals(cofX)) {
                c[lenOfList].setCofN(c[lenOfList].getCofN().add(cofN));
                find = true;
            }
            if (c[lenOfList].getExp() == -1) {
                break;
            }
        }
        if (!find) {
            c[lenOfList].setExp(exp);
            c[lenOfList].setCofX(cofX);
            c[lenOfList].setCofN(c[lenOfList].getCofN().add(cofN));
        }
    }

    /**.
     * 把表里的内容转化为字符串
     *
     * @param key          基变量，必须初始化标准变量名
     * @param list         数据表
     * @param numberOfItem 项数
     * @return 新字符串
     */
    private static String getNewEquation(final String key, final Cell[] list, final int numberOfItem) {
        String newEquation = "";
        final BigDecimal zero = new BigDecimal(0);
        final BigDecimal one = new BigDecimal(1);
        final BigDecimal minusOne = new BigDecimal(-1);
        for (int i = 0; i < numberOfItem; i++) {
            if (list[i].getExp() == -1) {
                break;
            }
            if (list[i].getCofN().compareTo(zero) == 1) {
                if (list[i].getCofN().equals(one) && (!list[i].getCofX().isEmpty() || list[i].getExp() != 0)) {
                    newEquation += "+";
                } else {
                    newEquation += "+" + list[i].getCofN();
                }
            } else if (list[i].getCofN().compareTo(zero) == -1) {
                if (list[i].getCofN().equals(minusOne) && (!list[i].getCofX().isEmpty() || list[i].getExp() != 0)) {
                    newEquation += "-";
                } else {
                    newEquation += list[i].getCofN();
                }
            } else {
                continue;
            }
            if (!list[i].getCofN().equals(one) && !list[i].getCofN().equals(minusOne)
                    && !list[i].getCofX().isEmpty()) {
                newEquation += '*';
            }
            newEquation += list[i].getCofX();
            if (list[i].getExp() != 0
                    && (!list[i].getCofX().isEmpty() || !list[i].getCofN().equals(one)
                    && !list[i].getCofN().equals(minusOne))) {
                newEquation += "*";
            }
            if (list[i].getExp() == 1) {
                newEquation += key;
            } else if (list[i].getExp() > 1) {
                newEquation += key + "^" + list[i].getExp();
            }
        }
        if (newEquation.length() == 0) {
            newEquation = "0";
        } else if (newEquation.charAt(0) == '+') {
            newEquation = newEquation.substring(1);
        }
        return newEquation;
    }

    /**.
     * 判断命令中的变量是否存在于多项式中
     *
     * @param str      变量名
     * @param equation 多项式
     * @return 存在为true，不存在为false
     */
    private static boolean isExistent(final String str, final String equation) {
        Pattern p = Pattern.compile("(?<=([+*-]|^))" + str + "(?=([+*-^]|$))");
        Matcher m = p.matcher(equation);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**.
     * 将变量替换为所赋的值
     *
     * @param var 变量名
     * @param number 数字
     * @param equation 表达式
     * @return 表达式
     */
    private static String replace(final String var, final String number, final String equation) {
        String newEquation = equation;
        while (true) {
            Pattern p = Pattern.compile("(?<=[^a-zA-Z]|^)(" + var + ")(\\^(\\d+))");
            Matcher m = p.matcher(newEquation);
            if (m.find()) {
                int n = Integer.parseInt(m.group(3));
                BigDecimal result = new BigDecimal(number);
                result = result.pow(n);
                newEquation = m.replaceFirst(String.valueOf(result));
            } else {
                break;
            }
        }
        Pattern p = Pattern.compile("(?<=[^a-zA-Z]|^)" + var + "(?=([+*-]|$))");
        Matcher m = p.matcher(newEquation);
        if (m.find()) {
            newEquation = m.replaceAll(number);
        }
        return newEquation;
    }

    /**.
     * 表达式处理函数
     */
    public static String expression(final String oriEquation) {
        String equation = oriEquation;
        String[] aryOfItem = equation.replace(" ", "").replace("\t", "").replace("-", "+-").split("\\+");
        final int numberOfItem = aryOfItem.length;
        Cell[] list = new Cell[numberOfItem];
        String key = "x";
        for (int i = 0; i < numberOfItem; i++) {
            list[i] = new Cell();
        }
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(equation);
        if (m.find()) {
            key = m.group(0);
        }
        for (int i = 0; i < aryOfItem.length; i++) {
            String item = aryOfItem[i];
            makeTable(item, key, list);
        }
        equation = getNewEquation(key, list, numberOfItem);
        System.out.println(equation);
        return equation;
    }

    /**.
     * 化简函数
     *
     * @param command 命令
     * @return 命令中包含不存在的变量则返回false，否则返回true
     */
    public static String simplify(final String command, final String oriEquation) {
        String newCommand = command.replace("!simplify", "");
        String tempEquation = oriEquation;
        Pattern p = Pattern.compile("(?<=(^|\\d))[a-zA-Z]+(?==)");
        Matcher m = p.matcher(newCommand);

        //替换变量
        while (m.find()) {
            if (isExistent(m.group(0), tempEquation)) {
                Pattern p1 = Pattern.compile("(?<=([0-9]|^)" + m.group(0) + "=)-?(\\d+)(\\.\\d+)?(?=[a-zA-Z]|$)");
                Matcher m1 = p1.matcher(newCommand);
                if (m1.find()) {
                    tempEquation = replace(m.group(0), m1.group(0), tempEquation);
                }
            } else {
                return null;
            }
        }
        String key = "x";
        String[] aryOfItem = tempEquation.replace("--", "+").replace("+-", "-").replace("-", "+-").replace("*+-", "*-")
                .split("\\+");
        if (aryOfItem[0].equals("")) {
            String[] tmpAry = aryOfItem;
            aryOfItem = new String[tmpAry.length - 1];
            for (int i = 0; i < aryOfItem.length; i++) {
                aryOfItem[i] = tmpAry[i + 1];
            }
        }
        Cell[] list = new Cell[aryOfItem.length];
        for (int i = 0; i < aryOfItem.length; i++) {
            list[i] = new Cell();
        }
        p = Pattern.compile("[a-zA-Z]+");
        m = p.matcher(tempEquation);
        if (m.find()) {
            key = m.group(0);
        }
        for (int i = 0; i < aryOfItem.length; i++) {
            makeTable(aryOfItem[i], key, list);
        }
        String result = getNewEquation(key, list, aryOfItem.length);
        System.out.println(result);
        return result;
    }

    /**.
     * 求导函数
     *
     * @param command 命令
     * @return 变量存在返回true，否则返回false
     */
    public static String derivative(final String command, final String oriEquation) {
        String var = command.replace("!d/d", "");
        if (!isExistent(var, oriEquation)) {
            return null;
        }
        String[] aryOfItem = oriEquation.replace("-", "+-").split("\\+");
        int numberOfItem = aryOfItem.length;
        Cell[] list = new Cell[numberOfItem];
        for (int i = 0; i < numberOfItem; i++) {
            list[i] = new Cell();
        }
        for (int i = 0; i < aryOfItem.length; i++) {
            String item = aryOfItem[i];
            makeTable(item, var, list);
        }
        for (int i = 0; i < numberOfItem && list[i].getExp() != -1; i++) {
            BigDecimal exp = new BigDecimal(list[i].getExp());
            list[i].setCofN(list[i].getCofN().multiply(exp));
            if (list[i].getExp() > 0) {
                list[i].setExp(list[i].getExp() - 1);
            }
        }
        String result = getNewEquation(var, list, numberOfItem);
        System.out.println(result);
        return result;
    }
}
