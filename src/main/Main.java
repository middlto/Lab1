package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * .
 * 主类
 */
public final class Main {
    /**.
     *隐藏构造函数
     */
    private Main() {
    }

    /**
     * .
     * 主方法
     *
     * @param args 主方法参数
     * @throws IOException 处理异常
     */
    public static void main(final String[] args)
            throws IOException {
        final BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
        final Polynomial pol = new Polynomial();
        String str;
        while (true) {
            str = br.readLine();
            if (str.isEmpty()) {
                continue;
            }
            final Pattern p = Pattern.compile("^\\s*!");
            final Matcher m = p.matcher(str);
            final long start = System.nanoTime();    //起始计时
            if (isLegal(str)) {
                pol.setEquation(str);   //设置表达式
                pol.expression();   //处理表达式
            } else if (m.find() && isLegal(str, pol)) {
                str = str.replace(" ", "").replace("\t", "");
                if (str.startsWith("!simplify")) {
                    if (pol.simplify(str) != null) {     //表达式求值
                        System.out.println("Variable doesn't exist.");
                    }
                } else if (str.startsWith("!d/d")) {
                    if (pol.derivative(str) != null) {  //表达式求导
                        System.out.println("Variable doesn't exist.");
                    }
                }
            } else {
                System.out.println("Error, illegal input.");
            }
            long end = System.nanoTime();  //终止计时
            System.out.println("start : " + start);
            System.out.println("end : " + end);
            System.out.println("running time : "
                    + (end - start) / 1000000.0 + " ms");
        }
    }

    /**.
     * 判断表达式输入是否合法
     *
     * @param s 表达式输入
     * @return 合法为true，不合法为false
     */
    private static boolean isLegal(final String s) {
        Pattern p = Pattern.compile("^\\s*"
                + "(([0-9]|[1-9][0-9]+)?\\s*([a-zA-Z]+\\s*)(\\^\\s*[1-9][0-9]*\\s*)?[\\+\\*-]\\s*|"
                + "([0-9]|[1-9][0-9]+)\\s*[\\+\\*-]\\s*)*"
                + "(([0-9]|[1-9][0-9]+)?\\s*([a-zA-Z]+\\s*)(\\^\\s*[1-9][0-9]*\\s*)?|"
                + "([0-9]|[1-9][0-9]+)\\s*$)");
        Matcher m = p.matcher(s);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**.
     * 判断命令输入是否合法
     *
     * @param s 命令输入
     * @param p 多项式实例
     * @return 合法为true，不合法为false
     */
    private static boolean isLegal(final String s, final Polynomial p) {
        if (!p.set()) {
            return false;
        } else {
            Pattern pattern1 = Pattern.compile("^\\s*!\\s*simplify\\s*(\\s[a-zA-Z]+\\s*=\\s*-?\\s*([0-9]|[1-9][0-9]+)"
                    + "(\\.[0-9]+)?\\s*)*$");
            Pattern pattern2 = Pattern.compile("^\\s*!\\s*d\\s*/\\s*d\\s*[a-zA-Z]+\\s*$");
            Matcher matcher1 = pattern1.matcher(s);
            Matcher matcher2 = pattern2.matcher(s);
            if (matcher1.matches() || matcher2.matches()) {
                return true;
            }
            return false;
        }
    }
}

