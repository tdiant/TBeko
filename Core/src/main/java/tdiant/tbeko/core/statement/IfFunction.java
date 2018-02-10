package tdiant.tbeko.core.statement;

import tdiant.tbeko.core.TBekoCore;

enum RelationOperator {
    LT, //   <  小于
    LE, //   <= 小于等于
    GT, //   >  大于
    GE, //   >= 大于等于
    EQ, //   == 等于
    NE  //   != 不等于
}

/**
 * Created by tdiant on 2017/8/19.
 */
public class IfFunction extends Statement {

    public IfFunction(TBekoCore tbc, String arg) {
        super(tbc, arg);
    }

    @Override
    public void run() {
        if (this.getArg().toUpperCase().split("THEN").length == 1) { //不是一行式的if
            String[] argArray = this.getArg().split("THEN");
            String pdStr = argArray[0].trim().replace(" ", "");
            //while(pdStr.charAt(0)==' ') pdStr = pdStr.substring(1,pdStr.length());

            RelationOperator ro = this.getRelation(pdStr);
            String[] strArray = this.getRelationArray(pdStr);
            if (strArray.length != 2) {
                //报错：判断式不正确
                this.getTBekoCore().getInteractBlock().outError("Wrong Relation Equation",this.getTBekoCore());
                return;
            }

            boolean isRun = false;
            //if (this.getTBekoCore().counterString(strArray[0]).equalsIgnoreCase(this.getTBekoCore().counterString(strArray[1])))
            if (this.isCorrectPrerequisite(this.getTBekoCore().counterString(strArray[0]),this.getTBekoCore().counterString(strArray[1]),ro))
                isRun = true;

            if (isRun) {
                int nowLine = this.getTBekoCore().getLineNum();
                boolean isRead = true;
                for (int i = nowLine + 1; i <= this.getTBekoCore().getMaxLineNum(); i++) {
                    String str = this.getTBekoCore().getLineCode(i);
                    //预处理
                    while (str.length() != 0 && str.charAt(0) == ' ') str = str.replaceFirst(" ", ""); //取出头部空格
                    String[] cmd = str.split(" ");
                    if (cmd.length <= 0) continue; //空语句

                    //关键语句分析
                    if (cmd[0].equalsIgnoreCase("END")) { //判断END IF
                        if (cmd.length == 1) { //判断是不是END结束语句
                            this.getTBekoCore().stop();
                        } else {
                            if (cmd[1].equalsIgnoreCase("IF")) { //END IF
                                this.getTBekoCore().moveLineNum(i + 1);
                                return;
                            }
                        }
                    } else if (cmd[0].equalsIgnoreCase("ELSE")) { //判断是不是ELSE语句
                        isRead = false;
                    }

                    if (isRead) {
                        this.getTBekoCore().readLine(str);
                        this.getTBekoCore().moveLineNum(i+1);
                    }
                }
            }else{
                int nowLine = this.getTBekoCore().getLineNum();

                this.getTBekoCore().moveLineNum( this.getTBekoCore().getLineNum() + 1);

                //定位LineNum至ELSE
                for (int i = nowLine + 1; i <= this.getTBekoCore().getMaxLineNum(); i++) {
                    String str = this.getTBekoCore().getLineCode(i);

                    //预处理
                    while (str.length() != 0 && str.charAt(0) == ' ') str = str.replaceFirst(" ", ""); //取出头部空格
                    String[] cmd = str.split(" ");
                    if (cmd.length <= 0) continue; //空语句

                    if(cmd[0].equalsIgnoreCase("ELSE")){
                        this.getTBekoCore().moveLineNum(this.getTBekoCore().getLineNum()+1);
                        break;
                    }else{
                        this.getTBekoCore().moveLineNum(this.getTBekoCore().getLineNum()+1);
                    }
                }

                nowLine = this.getTBekoCore().getLineNum()-1;
                for (int i = nowLine + 1; i <= this.getTBekoCore().getMaxLineNum(); i++) {
                    String str = this.getTBekoCore().getLineCode(i);

                    //预处理
                    while (str.length() != 0 && str.charAt(0) == ' ') str = str.replaceFirst(" ", ""); //取出头部空格
                    String[] cmd = str.split(" ");
                    if (cmd.length <= 0) continue; //空语句

                    //boolean isRead = true;

                    //关键语句分析
                    if (cmd[0].equalsIgnoreCase("END")) { //判断END IF
                        if (cmd.length == 1) { //判断是不是END结束语句
                            this.getTBekoCore().stop();
                        } else if(cmd.length != 1) {
                            if (cmd[1].equalsIgnoreCase("IF")) { //END IF
                                this.getTBekoCore().moveLineNum(i + 1);
                                return;
                            }
                        }
                    }

//                    if (isRead) {
                        this.getTBekoCore().readLine(str);
                        this.getTBekoCore().moveLineNum(i+1);
  //                  }
                }
            }

        } else if (this.getArg().toUpperCase().split("THEN").length > 0) { //一行类的if
            String[] argArray = this.getArg().toUpperCase().split("THEN");
            this.getTBekoCore().readLine(argArray[1]);
        }
    }

    public static RelationOperator getRelation(String str) {
        if (str.contains("<=")) return RelationOperator.LE;
        if (str.contains(">=")) return RelationOperator.GE;
        //if(str.contains("==")) return RelationOperator.EQ;
        if (str.contains("!=")) return RelationOperator.NE;
        if (str.contains("<")) return RelationOperator.LT;
        if (str.contains(">")) return RelationOperator.GT;
        if (str.contains("=")) return RelationOperator.EQ;
        return null;
    }

    public static String[] getRelationArray(String str) {
        if (getRelation(str) == RelationOperator.LE) return str.replace("<=", "\n").split("\n");
        if (getRelation(str) == RelationOperator.GE) return str.replace(">=", "\n").split("\n");
        if (getRelation(str) == RelationOperator.NE) return str.replace("!=", "\n").split("\n");
        if (getRelation(str) == RelationOperator.LT) return str.replace("<", "\n").split("\n");
        if (getRelation(str) == RelationOperator.GT) return str.replace(">", "\n").split("\n");
        if (getRelation(str) == RelationOperator.EQ) return str.replace("=", "\n").split("\n");
        return null;
    }

    public static boolean isCorrectPrerequisite(String obj1, String obj2, RelationOperator ro){
        if(ro == RelationOperator.EQ)
            if (obj1.equalsIgnoreCase(obj2)) return true; else return false;
        if(ro == RelationOperator.LT)
            if (Double.parseDouble(obj1) < Double.parseDouble(obj2)) return true; else return false;
        if(ro == RelationOperator.GT)
            if (Double.parseDouble(obj1) > Double.parseDouble(obj2)) return true; else return false;
        if(ro == RelationOperator.LE)
            if (Double.parseDouble(obj1) <= Double.parseDouble(obj2)) return true; else return false;
        if(ro == RelationOperator.GE)
            if (Double.parseDouble(obj1) >= Double.parseDouble(obj2)) return true; else return false;
        if(ro == RelationOperator.NE)
            if (!obj1.equalsIgnoreCase(obj2)) return true; else return false;
        return false;
    }
}
