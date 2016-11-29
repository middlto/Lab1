package boundary;

import control.InputControl;
import control.PolynomialControl;
import entity.Polynomial;

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
    public static void main(final String[] args) throws IOException {
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
            String equation;
            final long start = System.nanoTime();    //起始计时
            if (InputControl.isLegal(str)) {
                pol.setEquation(str);   //设置表达式
                equation = PolynomialControl.expression(pol.getEquation()); //处理表达式
                pol.setEquation(equation);
            } else if (m.find() && InputControl.isLegal(str, pol)) {
                str = str.replace(" ", "").replace("\t", "");
                if (str.startsWith("!simplify")) {
                    if (PolynomialControl.simplify(str, pol.getEquation()) == null) {     //表达式求值
                        System.out.println("Variable doesn't exist.");
                    }
                } else if (str.startsWith("!d/d")) {
                    if (PolynomialControl.derivative(str, pol.getEquation()) == null) {  //表达式求导
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
}

