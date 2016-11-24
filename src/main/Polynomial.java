package main;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 多项式类
 */
public class Polynomial {
    private boolean isSet;
    private String equation;
    private cell list[];

    /**
     * 构造函数
     */
    public Polynomial() {
        isSet = false;
    }

    /**
     * 表达式设置函数
     *
     * @param s 表达式输入
     */
    public void setEquation(String s) {
        equation = s;
        isSet = true;
    }

    /**
     * 判断是否设置表达式
     *
     * @return 已设置为true，未设置为false
     */
    public boolean set() {
        if (isSet)
            return true;
        return false;
    }

    /**
     * 表达式处理函数
     */
    public void expression() {
        String aryOfItem[] = equation.replace(" ", "").replace("\t", "").replace("-", "+-").split("\\+");
        int numberOfItem = aryOfItem.length;
        list = new cell[numberOfItem];
        String key = "x";
        for (int i = 0; i < numberOfItem; i++)
            list[i] = new cell();
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(equation);
        if (m.find())
            key = m.group(0);
        for (int i = 0; i < aryOfItem.length; i++) {
            String item = aryOfItem[i];
            makeTable(item, key, list);
        }
        equation = getNewEquation(key, list, numberOfItem);
        System.out.println(equation);
    }

    /**
     * 单项制表函数
     *
     * @param item 单项表达式
     * @param key  制表基变量
     * @param c    数据表
     */
    private static void makeTable(String item, String key, cell c[]) {
        int exp = 0;
        BigDecimal cofN = new BigDecimal(1);
        String cofX = "";

        //计算指数
        exp = getExp(key, item);

        //计算数字系数
        Pattern p = Pattern.compile("(?<=([^^\\d]|^))(-?\\d+(\\.\\d+)?)(?=\\*|$|[a-zA-Z])");
        Matcher m = p.matcher(item);
        while (m.find())
            cofN = cofN.multiply(new BigDecimal(m.group(0)));
        p = Pattern.compile("-(?=[a-zA-Z])");
        m = p.matcher(item);
        while (m.find())
            cofN = cofN.multiply(new BigDecimal(-1));


        //统计非数字系数
        p = Pattern.compile("([a-zA-Z]+)(\\^(\\d+))?");
        m = p.matcher(item);
        while (m.find()) {
            String inItem = m.group(1);
            if (!inItem.equals(key)) {
                Pattern p1 = Pattern.compile("(?<=[^a-zA-Z]|^)" + inItem + "(?=[^a-zA-Z]|$)");   //\\b改为?<= ?=
                Matcher m1 = p1.matcher(cofX);
                if (!m1.find()) {
                    int expX = 0;
                    expX = getExp(inItem, item);
                    if (expX == 1)
                        cofX += "*" + inItem;
                    else
                        cofX += "*" + inItem + "^" + expX;
                }
            }
        }
        if (!cofX.equals(""))
            cofX = cofX.substring(1);   //删掉开头乘号
        boolean find = false;
        int lenOfList;

        //将该项数据放入表中，合并同类项
        for (lenOfList = 0; lenOfList < c.length; lenOfList++) {
            if (c[lenOfList].exp == exp && c[lenOfList].cofX.equals(cofX)) {
                c[lenOfList].cofN = c[lenOfList].cofN.add(cofN);
                find = true;
            }
            if (c[lenOfList].exp == -1)
                break;
        }
        if (find == false) {
            c[lenOfList].exp = exp;
            c[lenOfList].cofX = cofX;
            c[lenOfList].cofN = c[lenOfList].cofN.add(cofN);
        }
    }

    /**
     * 把表里的内容转化为字符串
     *
     * @param key          基变量，必须初始化标准变量名
     * @param list         数据表
     * @param numberOfItem 项数
     * @return 新字符串
     */
    private static String getNewEquation(String key, cell list[], int numberOfItem) {
        String newEquation = "";
        BigDecimal zero = new BigDecimal(0);
        BigDecimal one = new BigDecimal(1);
        BigDecimal minusOne = new BigDecimal(-1);
        for (int i = 0; i < numberOfItem; i++) {
            if (list[i].exp == -1)
                break;
            if (list[i].cofN.compareTo(zero) == 1) {
                if (list[i].cofN.equals(one) && (!list[i].cofX.isEmpty() || list[i].exp != 0))
                    newEquation += "+";
                else
                    newEquation += "+" + list[i].cofN;
            } else if (list[i].cofN.compareTo(zero) == -1) {
                if (list[i].cofN.equals(minusOne) && (!list[i].cofX.isEmpty() || list[i].exp != 0))
                    newEquation += "-";
                else
                    newEquation += list[i].cofN;
            } else
                continue;
            if (!list[i].cofN.equals(one) && !list[i].cofN.equals(minusOne) && !list[i].cofX.isEmpty())
                newEquation += '*';
            newEquation += list[i].cofX;
            if (list[i].exp != 0 &&
                    (!list[i].cofX.isEmpty() || !list[i].cofN.equals(one) && !list[i].cofN.equals(minusOne)))
                newEquation += "*";
            if (list[i].exp == 1)
                newEquation += key;
            else if (list[i].exp > 1)
                newEquation += key + "^" + list[i].exp;
        }
        if (newEquation.length() == 0)
            newEquation = "0";
        else if (newEquation.charAt(0) == '+')
            newEquation = newEquation.substring(1);
        return newEquation;
    }

    /**
     * 获取变量在该项中的指数
     *
     * @param key  变量
     * @param item 该项
     * @return 指数
     */
    private static int getExp(String key, String item) {
        int exp = 0;
        Pattern p = Pattern.compile("(?<=[^a-zA-Z]|^)" + key + "(\\^(\\d+))?(?=([+*-]|$))");     //加了第一个?<=
        Matcher m = p.matcher(item);
        while (m.find()) {
            if (m.group(0).equals(key))
                exp++;
            else
                exp += Integer.parseInt(m.group(2));    //2改成3改成2
        }
        return exp;
    }

    /**
     * 化简函数
     *
     * @param command 命令
     * @return 命令中包含不存在的变量则返回false，否则返回true
     */
    public String simplify(String command) {
        command = command.replace("!simplify", "");
        String tempEquation = equation;
        Pattern p = Pattern.compile("(?<=(^|\\d))[a-zA-Z]+(?==)");
        Matcher m = p.matcher(command);

        //替换变量
        while (m.find()) {
            if (isExist(m.group(0), tempEquation)) {
                Pattern p1 = Pattern.compile("(?<=([0-9]|^)" + m.group(0) + "=)-?(\\d+)(\\.\\d+)?(?=[a-zA-Z]|$)");
                Matcher m1 = p1.matcher(command);
                if (m1.find()) {
                    tempEquation = replace(m.group(0), m1.group(0), tempEquation);
                }
            } else
                return null;
        }
        String key = "x";
        String aryOfItem[] = tempEquation.replace("--", "+").replace("+-", "-").replace("-", "+-").replace("*+-", "*-")
                .split("\\+");
        if (aryOfItem[0].equals("")) {
            String tmpAry[] = aryOfItem;
            aryOfItem = new String[tmpAry.length - 1];
            for (int i = 0; i < aryOfItem.length; i++)
                aryOfItem[i] = tmpAry[i + 1];
        }
        cell list[] = new cell[aryOfItem.length];
        for (int i = 0; i < aryOfItem.length; i++)
            list[i] = new cell();
        p = Pattern.compile("[a-zA-Z]+");
        m = p.matcher(tempEquation);
        if (m.find())
            key = m.group(0);
        for (int i = 0; i < aryOfItem.length; i++)
            makeTable(aryOfItem[i], key, list);
        System.out.println(getNewEquation(key, list, aryOfItem.length));
        return getNewEquation(key, list, aryOfItem.length);
    }

    /**
     * 判断命令中的变量是否存在于多项式中
     *
     * @param str      变量名
     * @param equation 多项式
     * @return 存在为true，不存在为false
     */
    private static boolean isExist(String str, String equation) {
        Pattern p = Pattern.compile("(?<=([+*-]|^))" + str + "(?=([+*-^]|$))");
        Matcher m = p.matcher(equation);
        if (m.find())
            return true;
        return false;
    }

    /**
     * 将变量替换为数字 并计算幂
     *
     * @param var      变量名
     * @param number   数字
     * @param equation 表达式
     */
    private static String replace(String var, String number, String equation) {
        while (true) {
            Pattern p = Pattern.compile("(?<=[^a-zA-Z]|^)(" + var + ")(\\^(\\d+))");
            Matcher m = p.matcher(equation);
            if (m.find()) {
                int n = Integer.parseInt(m.group(3));
                BigDecimal result = new BigDecimal(number);
                result = result.pow(n);
                equation = m.replaceFirst(String.valueOf(result));
            } else
                break;
        }
        Pattern p = Pattern.compile("(?<=[^a-zA-Z]|^)" + var + "(?=([+*-]|$))");
        Matcher m = p.matcher(equation);
        if (m.find())
            equation = m.replaceAll(number);
        return equation;
    }

    /**
     * 求导函数
     *
     * @param command 命令
     * @return 变量存在返回true，否则返回false
     */
    public String derivative(String command) {
        command = command.replace("!d/d", "");
        String var = command;
        if (!isExist(var, equation))
            return null;
        String aryOfItem[] = equation.replace("-", "+-").split("\\+");
        int numberOfItem = aryOfItem.length;
        list = new cell[numberOfItem];
        for (int i = 0; i < numberOfItem; i++)
            list[i] = new cell();
        for (int i = 0; i < numberOfItem; i++) {
            String item = aryOfItem[i];
            makeTable(item, var, list);
        }
        for (int i = 0; i < numberOfItem; i++) {
            BigDecimal exp = new BigDecimal(list[i].exp);
            list[i].cofN = list[i].cofN.multiply(exp);
            if (list[i].exp > 0)
                list[i].exp--;
        }
        String result = getNewEquation(var, list, numberOfItem);
        System.out.println(result);
        return result;
    }
}
