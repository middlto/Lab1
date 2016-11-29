package control;

import entity.Polynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by meng on 2016/11/23.
 */
public class InputControl {
    /**.
     * 判断表达式输入是否合法
     *
     * @param s 表达式输入
     * @return 合法为true，不合法为false
     */
    public static boolean isLegal(final String s) {
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
    public static boolean isLegal(final String s, final Polynomial p) {
        if (!p.isSet()) {
            return false;
        } else {
            Pattern pattern1 = Pattern.compile("^\\s*!\\s*simplify\\s*(\\s[a-zA-Z]+\\s*=\\s*-?\\s*([0-9]|[1-9][0-9]+)"
                    + "(\\.[0-9]+)?\\s*)*$");
            Pattern pattern2 = Pattern.compile("^\\s*!\\s*d\\s*/\\s*d\\s*[a-zA-Z]+\\s*$");
            Matcher matcher1 = pattern1.matcher(s);
            Matcher matcher2 = pattern2.matcher(s);
            if (matcher1.matches()) {   //化简命令
                //检测重复赋值变量
                String tempS = s.replace(" ", "").replace("\t", "").replace("!simplify", "");
                Pattern pattern = Pattern.compile("(?<=(^|\\d))[a-zA-Z]+(?==)");
                Matcher m = pattern.matcher(tempS);
                List<String> varList = new ArrayList<String>();
                while (m.find()) {
                    if (varList.contains(m.group(0))) {
                        return false;
                    }
                    varList.add(m.group(0));
                }
                return true;
            } else if (matcher2.matches()) {    //求导命令
                return true;
            }
            return false;
        }
    }
}
