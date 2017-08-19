package tdiant.tbeko.core;

import tdiant.tbeko.core.interact.IInteractBlock;
import tdiant.tbeko.core.math.Calculator;
import tdiant.tbeko.core.object.TObject;
import tdiant.tbeko.core.object.TObjectType;
import tdiant.tbeko.core.object.TStringObject;
import tdiant.tbeko.core.statement.IfFunction;
import tdiant.tbeko.core.statement.InputStatement;
import tdiant.tbeko.core.statement.PrintStatement;

import java.util.*;

/**
 * Created by tdiant on 2017/8/18.
 */
public class TBekoCore {
    public static final String HH_TAG = "@[TBEKO_HH]";

    private boolean isStart = false;

    private String src = "";
    private IInteractBlock iib;

    private Map<String, TObject> objectMap = new HashMap<>();
    private int readLine = -1;
    private int maxReadLine = -1;

    public TBekoCore(String src, IInteractBlock iib) {
        if (src != null)
            this.src = src;
        this.iib = iib;
    }

    public void read() {
        this.src = this.src.replace("\r", HH_TAG).replace("\n", HH_TAG).replace(HH_TAG + HH_TAG, HH_TAG);
        this.src = this.src.replace(HH_TAG, "\n");
        this.src = this.src.trim();

        isStart = true;

        String[] srcArray = this.src.split("\n");
        for (readLine = 0; readLine < srcArray.length; readLine++) {
            //System.out.println("\r\n"+"READ"+readLine+":: "+srcArray[readLine]+"\r\n");
            readLine(srcArray[readLine]);
        }
    }

    public void readLine(String str) {
        //处理语句结构
        while (str.length() != 0 && str.charAt(0) == ' ') str = str.replaceFirst(" ", ""); //取出头部空格
        String[] cmd = str.split(" ");
        if (cmd.length <= 0) return; //空语句

        //预处理字符串
        boolean isStringCatching = false;
        char[] strCharArray = str.toCharArray();
        String tempStr = "";
        int lastStringCharLoc = -1, k = 0;

        for (int i = 0; i < strCharArray.length; i++) {
            char c = strCharArray[i];
            if (c == '\"') {
                if (isStringCatching) {
                    isStringCatching = false;
                    String oName = "TAUTO_A_" + k;
                    this.putObject(oName, new TStringObject(tempStr.substring(1, tempStr.length())));
                    str = new StringBuilder(str).insert(lastStringCharLoc, oName).toString();
                    lastStringCharLoc = -1;

                    i += oName.length();

                    StringBuffer sb = new StringBuffer(str);
                    sb.setCharAt(i, ' ');
                    str = sb.toString();
                } else {
                    isStringCatching = true;
                    lastStringCharLoc = i;
                    k++;
                    StringBuffer sb = new StringBuffer(str);
                    sb.setCharAt(i, ' ');
                    str = sb.toString();
                }
            }
            if (isStringCatching) {
                tempStr += c;
                StringBuffer sb = new StringBuffer(str);
                sb.setCharAt(i, ' ');
                str = sb.toString();
            }
        }

        if (isStringCatching) {
            //报错：引号不匹配
            return;
        }

        //处理出来最终的arg
        String arg = str.replaceFirst(cmd[0], "");
        while (arg.length() != 0 && arg.charAt(0) == ' ') arg = arg.replaceFirst(" ", ""); //取出头部空格

        //匹配语句类型
        switch (cmd[0].toUpperCase()) {
            default:
                //报错
                return;
            case "PRINT":
                new PrintStatement(this, arg.trim()).run();
                return;
            case "INPUT":
                new InputStatement(this, arg.trim()).run();
                return;
            case "SET":
                return;
            case "IF":
                new IfFunction(this, arg.trim()).run();
                return;
            case "DO":
                return;
            case "WHILE":
                return;
        }
    }

    public String getSourceCode() {
        return src;
    }

    public Map<String, TObject> getObjectMap() {
        return objectMap;
    }

    public String counterString(String str) {
        if (this.isMathStatement(str)) { //算式
            if ((this.mathCount(str) + "").equalsIgnoreCase(Double.NaN + "")) {
                String backStr_1 = this.replaceObjectName(str);
                if (backStr_1.contains("\"")) { //字符串拼接
                    if (this.isMathStatement(backStr_1.replace("+", ""))) {
                        //警告：字符串的拼接不能出现其它运算符
                        return str;
                    } else {
                        String[] args = backStr_1.replace("+", "\n").split("\n");
                        List<String> stringList = new ArrayList<>();
                        for (String stringStr : args) {
                            boolean isStart = false;
                            int startLoc = -1;
                            char[] strArray = new char[stringStr.length() + 50];
                            for (int i = 0; i < stringStr.length(); i++) {
                                if (stringStr.charAt(i) == '\"') {
                                    if (!isStart) {
                                        isStart = true;
                                        continue;
                                    } else {
                                        isStart = false;
                                        break;
                                    }
                                } else {
                                    strArray[i - startLoc] = stringStr.charAt(i);
                                }
                            }
                            String string = new String(strArray);
                            stringList.add(string);
                        }
                        String endStringStr = "";
                        for (String s : stringList) endStringStr += s;
                        return endStringStr;
                    }
                } else { //数字计算
                    if ((this.mathCount(backStr_1) + "").equalsIgnoreCase(Double.NaN + "")) {
                        //警告：式子存在问题
                        return str;
                    } else {
                        return this.mathCount(backStr_1) + "";
                    }
                }
            } else {
                return this.mathCount(str) + "";
            }
        } else {
            return replaceObjectName(str);
        }
        //return str;//什么也没匹配上
    }

    public String replaceObjectName(String str) {
        return this.replaceObjectName(str, false);
    }

    public String replaceObjectName(String str, boolean b) {
        //替换所有的变量为值
        List<String> objectNameList = new ArrayList<>();
        for (Object o : Arrays.asList(this.objectMap.keySet().toArray())) objectNameList.add(o.toString());

        Collections.sort(objectNameList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });

        if (b)
            str = str.replace("+", " + ").replace("-", " - ").replace("*", " * ").replace("/", " / ").replace("\\", " \\ ").replace("%", " % ");

        for (String objectName : objectNameList) {
            if (str.contains(objectName.toString().trim())) {
                if (b && !str.contains(" " + objectName.toString().trim() + " ")) {
                    continue;
                }
                TObject to = this.objectMap.get(objectName);
                if (to.getType() == TObjectType.Number) {
                    str = str.replace(objectName, this.objectMap.get(objectName).toString() + "");
                } else if (to.getType() == TObjectType.String) {
                    str = str.replace(objectName, "\"" + this.objectMap.get(objectName).toString() + "\"");
                } else {
                    //报错：未知的类型
                    continue;
                }
            }
        }
        return str;
    }

    public double mathCount(String str) {
        return Calculator.conversion(str.replace(" ", ""));
    }

    public boolean isMathStatement(String str) {
        return Calculator.isHaveOperator(str);
    }

    public IInteractBlock getInteractBlock() {
        return iib;
    }

    public void getInteractBlock(IInteractBlock iib) {
        this.iib = iib;
    }

    public void putObject(String name, TObject value) {
        name = name.toUpperCase();
        if (value != null)
            this.objectMap.put(name, value);
        else
            this.objectMap.remove(name);
    }

    public String getLineCode(int i) {
        if (!this.isStart) return null; //没有开始解析禁止访问行
        String[] srcArray = this.src.split("\n");
        if (srcArray.length >= i || i < 0) return null;
        return srcArray[i];
    }

    public int getLineNum() {
        return this.readLine;
    }

    public void moveLineNum(int readLine) {
        this.readLine = readLine;
    }

    public int getMaxLineNum() {
        if (!isStart) return -233;

        if (this.maxReadLine < 0)
            this.maxReadLine = src.split("\n").length - 1;

        return maxReadLine;
    }

    //退出
    @Deprecated
    public void stop() {
        System.exit(0);
    }
}
