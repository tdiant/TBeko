package tdiant.tbeko.TBeko.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tdiant on 2017/8/18.
 */
public class TBekoCore {
    public static final String HH_TAG = "@[TBEKO_HH]";

    private static Map<String,TObject> objectMap = new HashMap<>();
    private static int readLine = -1;

    public static void main(String[] args) {
        String str = "PRINT \"Hello World!\" \r\nPRINT \"HELLO!\"";
        read(str);
    }

    public static void read(String programSrc){
        programSrc = programSrc.replace("\r",HH_TAG).replace("\n",HH_TAG).replace(HH_TAG+HH_TAG,HH_TAG);
        programSrc = programSrc.replace(HH_TAG,"\n");
        programSrc =programSrc.trim();

        System.out.println(programSrc);
        System.out.println("============");

        String[] srcArray = programSrc.split("\n");
        for(readLine=0;readLine<srcArray.length;readLine++) {
            //System.out.println("\r\n"+"READ"+readLine+":: "+srcArray[readLine]+"\r\n");
            readLine(srcArray[readLine]);
        }
    }

    public static void readLine(String str){
        //处理语句结构
        while(str.length()!=0 && str.charAt(0)==' ') str = str.replaceFirst(" ",""); //取出头部空格
        String[] cmd = str.split(" ");
        if(cmd.length<=0) return; //空语句

        //预处理字符串、转义
        char[] strCharArray = str.toCharArray();
        int lastStringCharLoc = -1, k = 0;
        for(int i=0;i<strCharArray.length;i++){
            char c = strCharArray[i];
            if(c == '\\'){ //转义判断
            } else if(c == '\"'){ //字符串判断
                if(lastStringCharLoc<0){ //首个引号
                    lastStringCharLoc = i;
                    k++;
                }else{ //末个引号
                    char[] stringCharArray = new char[str.length() + 50];
                    strCharArray[lastStringCharLoc] = ' ';
                    for(int a = lastStringCharLoc+1; a<i; a++){
                        char strChar = strCharArray[a];
                        stringCharArray[a-lastStringCharLoc] = strChar;
                        strCharArray[a] = ' ';
                    }
                    strCharArray[i] = ' ';
                    str = new String(strCharArray);

                    String string = new String(stringCharArray);
                    TStringObject tso = new TStringObject(string);
                    objectMap.put("TBEKO_AUTO_A_"+k,tso);
                    StringBuilder stringBuilder = new StringBuilder(str);
                    stringBuilder.insert(lastStringCharLoc,"TBEKO_AUTO_A_"+k);
                    str = stringBuilder.toString();
                    lastStringCharLoc = -1;
                }
            }
        }
        //处理出来最终的arg
        String arg = str.replaceFirst(cmd[0],"");
        while(arg.length()!=0 && arg.charAt(0)==' ') arg = arg.replaceFirst(" ",""); //取出头部空格

        //匹配语句类型
        switch (cmd[0].toUpperCase()){
            default:
                //报错
                return;
            case "PRINT":
                PrintStatement(arg.trim());
                return;
            case "INPUT":
                return;
            case "SET":
                return;
            case "IF":
                return;
            case "DO":
                return;
            case "WHILE":
                return;
        }
    }

    private static void PrintStatement(String arg){
        if(arg.length()<=0){
            //报错
            return;
        }
        arg = arg.replace(" ","");
        String[] args = arg.split(",");
        for(String argStr : args){
            if(false){ //判断是不是算式
                //
            }else{
                for(String keyStr : objectMap.keySet()){
                    if(keyStr.equalsIgnoreCase(argStr)){
                        System.out.println(""+objectMap.get(keyStr).toString()); //输出语句
                    }
                }
            }
        }
    }
}

interface TObject{
    public TObjectType getType();
}

enum TObjectType{
    String
}

class TStringObject implements TObject{
    private String value;

    public TStringObject(){}

    public TStringObject(String value){
        this.value = value;
    }

    @Override
    public TObjectType getType() {
        return TObjectType.String;
    }

    public String getValue() {
        return value;
    }

    public TStringObject setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
