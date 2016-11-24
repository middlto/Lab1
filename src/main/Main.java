package main;

import java.io.*;
import java.util.regex.*;

/**
 * 主类
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Polynomial pol = new Polynomial();
        String str;
        while (true) {
            str = br.readLine();
            if (str.isEmpty())
                continue;
            Pattern p = Pattern.compile("^\\s*!");
            Matcher m = p.matcher(str);
            long start = System.nanoTime();    //起始计时
            if (isLegal(str)) {
                pol.setEquation(str);
                pol.expression();
            } else if (m.find() && isLegal(str, pol)) {
                str = str.replace(" ", "").replace("\t", "");
                if (str.startsWith("!simplify")) {
                    if (pol.simplify(str) != null)
                        System.out.println("Variable error.");
                } else if (str.startsWith("!d/d"))
                    if (pol.derivative(str) != null)
                        System.out.println("Variable error.");
            } else
                System.out.println("Error, illegal input.");
            long end = System.nanoTime();  //终止计时
            System.out.println("start : " + start + " \nend : " + end + " \nrunning time : " + (end - start) / 1000000.0 + " ms");
        }
    }

    /**
     * 判断表达式输入是否合法
     *
     * @param s 表达式输入
     * @return 合法为true，不合法为false
     */
    private static boolean isLegal(String s) {
        Pattern p = Pattern.compile("^\\s*" +
                "(([0-9]|[1-9][0-9]+)?\\s*([a-zA-Z]+\\s*)(\\^\\s*[1-9][0-9]*\\s*)?[\\+\\*-]\\s*|" +
                "([0-9]|[1-9][0-9]+)\\s*[\\+\\*-]\\s*)*" +
                "(([0-9]|[1-9][0-9]+)?\\s*([a-zA-Z]+\\s*)(\\^\\s*[1-9][0-9]*\\s*)?|" +
                "([0-9]|[1-9][0-9]+)\\s*$)");
        Matcher m = p.matcher(s);
        if (m.matches())
            return true;
        return false;
    }

    /**
     * 判断命令输入是否合法
     *
     * @param s 命令输入
     * @param p 多项式实例
     * @return 合法为true，不合法为false
     */
    private static boolean isLegal(String s, Polynomial p) {
        if (!p.set())
            return false;
        else {
            Pattern pattern1 = Pattern.compile("^\\s*!\\s*simplify\\s*(\\s[a-zA-Z]+\\s*=\\s*-?\\s*([0-9]|[1-9][0-9]+)" +
                    "(\\.[0-9]+)?\\s*)*$");
            Pattern pattern2 = Pattern.compile("^\\s*!\\s*d\\s*/\\s*d\\s*[a-zA-Z]+\\s*$");
            Matcher matcher1 = pattern1.matcher(s);
            Matcher matcher2 = pattern2.matcher(s);
            if (matcher1.matches() || matcher2.matches())
                return true;
            return false;
        }
    }
}

