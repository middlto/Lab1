import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//更改后
/**
 * .
 * 涓荤被
 */
public final class Main {
  /**.
     *闅愯棌鏋勯�犲嚱鏁�
     */
  private Main() {
  }

  /**
     * .
     * 涓绘柟娉�
     *
     * @param args 涓绘柟娉曞弬鏁�
     * @throws IOException 澶勭悊寮傚父
     */
  public static void main(final String[] args)
            throws IOException {
    final BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
    final Polynomial pol = new Polynomial();
    String str;
    while (true) {
      str = br.readLine();
      if (str.isEmpty()==true) {
        continue;
      }
      final Pattern p = Pattern.compile("^\\s*!");
      final Matcher m = p.matcher(str);
      final  long start = System.nanoTime();    //璧峰璁℃椂
      if (isLegal(str)) {
        pol.setEquation(str);   //璁剧疆琛ㄨ揪寮�
        pol.expression();   //澶勭悊琛ㄨ揪寮�
      } else if (m.find() && isLegal(str, pol)) {
        str = str.replace(" ", "").replace("\t", "");
        if (str.startsWith("!simplify")) {
          if (!pol.simplify(str)) {     //琛ㄨ揪寮忔眰鍊�
            System.out.println("Variable doesn't exist.");
          }
        } else if (str.startsWith("!d/d")) {
          if (!pol.derivative(str)) {  //琛ㄨ揪寮忔眰瀵�
            System.out.println("Variable doesn't exist.");
          }
        }
      } else {
        System.out.println("Error, illegal input.");
      }
      long end = System.nanoTime();  //缁堟璁℃椂
      System.out.println("start : " + start);
      System.out.println("end : " + end);
      System.out.println("running time : "
                    + (end - start) / 1000000.0 + " ms");
    }
  }

  /**.
     * 鍒ゆ柇琛ㄨ揪寮忚緭鍏ユ槸鍚﹀悎娉�
     *
     * @param s 琛ㄨ揪寮忚緭鍏�
     * @return 鍚堟硶涓簍rue锛屼笉鍚堟硶涓篺alse
     */
  private static boolean isLegal(final String s) {
    Pattern p = Pattern.compile("^\\s*" 
               +
               "(([0-9]|[1-9][0-9]+)?\\s*([a-zA-Z]+\\s*)(\\^\\s*[1-9][0-9]*\\s*)?[\\+\\*-]\\s*|" 
               +
                "([0-9]|[1-9][0-9]+)\\s*[\\+\\*-]\\s*)*" 
               +
                "(([0-9]|[1-9][0-9]+)?\\s*([a-zA-Z]+\\s*)(\\^\\s*[1-9][0-9]*\\s*)?|"
               +
                "([0-9]|[1-9][0-9]+)\\s*$)");
    Matcher m = p.matcher(s);
    if (m.matches()==true) {
      return true;
    }else{
    	return false;
    }
   
  }

  /**.
     * 鍒ゆ柇鍛戒护杈撳叆鏄惁鍚堟硶
     *
     * @param s 鍛戒护杈撳叆
     * @param p 澶氶」寮忓疄渚�
     * @return 鍚堟硶涓簍rue锛屼笉鍚堟硶涓篺alse
     */
  private static boolean isLegal(final String s, final Polynomial p) {
	if (!p.set()) {
      return false;
    }
    else {
      Pattern pattern1 = Pattern.compile("^\\s*!\\s*simplify\\s*(\\s[a-zA-Z]+\\s*=\\s*-?\\s*([0-9]|[1-9][0-9]+)"
                    +
                    "(\\.[0-9]+)?\\s*)*$");
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

/**
 * .
 * 瀛樺偍鍗曞厓绫�
 */
class Cell {
    /**.
     * 鎸囨暟
     */
  public int exp;
  /**.
     * 瀛楁瘝绯绘暟
     */
  public String cofX;
  /**.
     * 鏁板瓧绯绘暟
     */
  public BigDecimal cofN;

  /**.
     * 鏋勯�犲嚱鏁�
     */
  Cell() {
    exp = -1;
    cofX = "";
    cofN = new BigDecimal(0);
  }
}

/**
 * .
 * 澶氶」寮忕被
 */
class Polynomial {
    /**.
     * 鍒ゆ柇鏄惁杈撳叆杩囪〃杈惧紡
     */
  private boolean isSet;

  /**.
     * 琛ㄨ揪寮�
     */
  private String equation;

  /**.
     * 鍙傛暟鍒楄〃
     */
  private Cell[] list;

  /**.
     * 鏋勯�犲嚱鏁�
     */
  Polynomial() {
    isSet = false;
  }

  /**.
     * 琛ㄨ揪寮忚缃嚱鏁�
     *
     * @param s 琛ㄨ揪寮忚緭鍏�
     */
  public void setEquation(String s) {
    equation = s;
    isSet = true;
  }

  /**.
     * 鍒ゆ柇鏄惁璁剧疆琛ㄨ揪寮�
     *
     * @return 宸茶缃负true锛屾湭璁剧疆涓篺alse
     */
  public boolean set() {
    if (isSet) {
      return true;
    }
    return false;
  }

  /**.
     * 琛ㄨ揪寮忓鐞嗗嚱鏁�
     */
  public void expression() {
    String[] aryOfItem = equation.replace(" ", "").replace("\t", "").replace("-", "+-").split("\\+");
    final int numberOfItem = aryOfItem.length;
    list = new Cell[numberOfItem];
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
  }

  /**.
     * 鍗曢」鍒惰〃鍑芥暟
     *
     * @param item 鍗曢」琛ㄨ揪寮�
     * @param key  鍒惰〃鍩哄彉閲�
     * @param c    鏁版嵁琛�
     */
  private static void makeTable(String item, String key, Cell[] c) {
    int exp;
    BigDecimal cofN = new BigDecimal(1);
    String cofX = "";

    //璁＄畻鎸囨暟
    exp = getExp(key, item);

    //璁＄畻鏁板瓧绯绘暟
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


    //缁熻闈炴暟瀛楃郴鏁�
    p = Pattern.compile("([a-zA-Z]+)(\\^(\\d+))?");
    m = p.matcher(item);
    while (m.find()) {
      String inItem = m.group(1);
      if (!inItem.equals(key)) {
        Pattern p1 = Pattern.compile("(?<=[^a-zA-Z]|^)" + inItem 
        		+ "(?=[^a-zA-Z]|$)");   //\\b鏀逛负?<= ?=
        Matcher m1 = p1.matcher(cofX);
        if (!m1.find()) {
          int expX;
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
      cofX = cofX.substring(1);   //鍒犳帀寮�澶翠箻鍙�
    }
    boolean find = false;
    int lenOfList;

    //灏嗚椤规暟鎹斁鍏ヨ〃涓紝鍚堝苟鍚岀被椤�
    for (lenOfList = 0; lenOfList < c.length; lenOfList++) {
      if (c[lenOfList].exp == exp && c[lenOfList].cofX.equals(cofX)) {
        c[lenOfList].cofN = c[lenOfList].cofN.add(cofN);
        find = true;
      }
      if (c[lenOfList].exp == -1) {
        break;
      }
    }
    if (!find) {
      c[lenOfList].exp = exp;
      c[lenOfList].cofX = cofX;
      c[lenOfList].cofN = c[lenOfList].cofN.add(cofN);
    }
  }

  /**.
     * 鎶婅〃閲岀殑鍐呭杞寲涓哄瓧绗︿覆
     *
     * @param key          鍩哄彉閲忥紝蹇呴』鍒濆鍖栨爣鍑嗗彉閲忓悕
     * @param list         鏁版嵁琛�
     * @param numberOfItem 椤规暟
     * @return 鏂板瓧绗︿覆
     */
  private static String getNewEquation(String key, Cell[] list, int numberOfItem) {
    String newEquation = "";
    final BigDecimal zero = new BigDecimal(0);
    final BigDecimal one = new BigDecimal(1);
    final BigDecimal minusOne = new BigDecimal(-1);
    for (int i = 0; i < numberOfItem; i++) {
      if (list[i].exp == -1) {
        break;
      }
      if (list[i].cofN.compareTo(zero) == 1) {
        if (list[i].cofN.equals(one) && (!list[i].cofX.isEmpty() || list[i].exp != 0)) {
          newEquation += "+";
        } else {
          newEquation += "+" + list[i].cofN;
        }
      } else if (list[i].cofN.compareTo(zero) == -1) {
        if (list[i].cofN.equals(minusOne) && (!list[i].cofX.isEmpty() || list[i].exp != 0)) {
          newEquation += "-";
        } else {
          newEquation += list[i].cofN;
        }
      } else {
        continue;
      }
      if (!list[i].cofN.equals(one) && !list[i].cofN.equals(minusOne)
    		  && !list[i].cofX.isEmpty()) {
        newEquation += '*';
      }
      newEquation += list[i].cofX;
      if (list[i].exp != 0
                    && (!list[i].cofX.isEmpty() || !list[i].cofN.equals(one) 
                    		&& !list[i].cofN.equals(minusOne))) {
        newEquation += "*";
      }
      if (list[i].exp == 1) {
        newEquation += key;
      } else if (list[i].exp > 1) {
        newEquation += key + "^" + list[i].exp;
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
     * 鑾峰彇鍙橀噺鍦ㄨ椤逛腑鐨勬寚鏁�
     *
     * @param key  鍙橀噺
     * @param item 璇ラ」
     * @return 鎸囨暟
     */
  private static int getExp(String key, String item) {
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
     * 鍖栫畝鍑芥暟
     *
     * @param command 鍛戒护
     * @return 鍛戒护涓寘鍚笉瀛樺湪鐨勫彉閲忓垯杩斿洖false锛屽惁鍒欒繑鍥瀟rue
     */
  public boolean simplify(String command) {
    command = command.replace("!simplify", "");
    String tempEquation = equation;
    Pattern p = Pattern.compile("(?<=(^|\\d))[a-zA-Z]+(?==)");
    Matcher m = p.matcher(command);

    //鏇挎崲鍙橀噺
    while (m.find()) {
      if (isExist(m.group(0), tempEquation)) {
        Pattern p1 = Pattern.compile("(?<=([0-9]|^)" + m.group(0) + "=)-?(\\d+)(\\.\\d+)?(?=[a-zA-Z]|$)");
        Matcher m1 = p1.matcher(command);
        if (m1.find()) {
          tempEquation = replace(m.group(0), m1.group(0), tempEquation);
              }
      } else {
       return false;
      }
    }
    String key = "x";
    String[] aryOfItem = tempEquation.replace("--", "+").replace("+-", "-")
    		.replace("-", "+-").replace("*+-", "*-").split("\\+");
    if (aryOfItem[0].equals("")) {
      String[] tmpAry = aryOfItem;
      aryOfItem = new String[tmpAry.length - 1];
      for (int i = 0; i < aryOfItem.length; i++) {
        aryOfItem[i] = tmpAry[i + 1];
      }
    }
    Cell list[] = new Cell[aryOfItem.length];
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
    System.out.println(getNewEquation(key, list, aryOfItem.length));
    return true;
  }

  /**.
     * 鍒ゆ柇鍛戒护涓殑鍙橀噺鏄惁瀛樺湪浜庡椤瑰紡涓�
     *
     * @param str      鍙橀噺鍚�
     * @param equation 澶氶」寮�
     * @return 瀛樺湪涓簍rue锛屼笉瀛樺湪涓篺alse
     */
  private static boolean isExist(String str, String equation) {
    Pattern p = Pattern.compile("(?<=([+*-]|^))" + str + "(?=([+*-^]|$))");
    Matcher m = p.matcher(equation);
    if (m.find()) {
      return true;
    }
    return false;
  }

  /**.
     *
     * @param var 鍙橀噺鍚�
     * @param number 鏁板瓧
     * @param equation 琛ㄨ揪寮�
     * @return 琛ㄨ揪寮�
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
      } else {
        break;
      }
    }
    Pattern p = Pattern.compile("(?<=[^a-zA-Z]|^)" + var + "(?=([+*-]|$))");
    Matcher m = p.matcher(equation);
    if (m.find()) {
      equation = m.replaceAll(number);
    }
    return equation;
  }

  /**.
     * 姹傚鍑芥暟
     *
     * @param command 鍛戒护
     * @return 鍙橀噺瀛樺湪杩斿洖true锛屽惁鍒欒繑鍥瀎alse
     */
  public boolean derivative(String command) {
    command = command.replace("!d/d", "");
    String var = command;
    if (!isExist(var, equation)) {
      return false;
    }
    String aryOfItem[ ] = equation.replace("-", "+-").split("\\+");
    int numberOfItem = aryOfItem.length;
    list = new Cell[numberOfItem];
    for (int i = 0; i < numberOfItem; i++) {
      list[i] = new Cell();
    }
    for (int i = 0; i < aryOfItem.length; i++) {
      String item = aryOfItem[i];
      makeTable(item, var, list);
    }
    for (int i = 0; i < numberOfItem && list[i].exp != -1; i++) {
      BigDecimal exp = new BigDecimal(list[i].exp);
      list[i].cofN = list[i].cofN.multiply(exp);
      if (list[i].exp > 0) {
        list[i].exp--;
      }
    }
    System.out.println(getNewEquation(var, list, numberOfItem));
    return true;
  }
}
