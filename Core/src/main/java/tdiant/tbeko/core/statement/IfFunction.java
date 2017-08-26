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
            String[] argArray = this.getArg().toUpperCase().split("THEN");
            String pdStr = argArray[0].trim().replace(" ", "");
            //while(pdStr.charAt(0)==' ') pdStr = pdStr.substring(1,pdStr.length());

            RelationOperator ro = this.getRelation(pdStr);
            String[] strArray = this.getRelationArray(pdStr);
            if (strArray.length != 2) {
                //报错：判断式不正确
                return;
            }

            boolean isRun = false;
            if (this.getTBekoCore().counterString(strArray[0]).equalsIgnoreCase(this.getTBekoCore().counterString(strArray[1])))
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
                for (int i = nowLine + 1; i <= this.getTBekoCore().getMaxLineNum(); i++) {
                    String str = this.getTBekoCore().getLineCode(i);

                    //预处理
                    while (str.length() != 0 && str.charAt(0) == ' ') str = str.replaceFirst(" ", ""); //取出头部空格
                    String[] cmd = str.split(" ");
                    if (cmd.length <= 0) continue; //空语句

                    boolean isRead = false;

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
                    } else if (cmd[0].equalsIgnoreCase("ELSE")) { //判断是不是ELSE语句
                        isRead = true;
                    }

                    if (isRead) {
                        this.getTBekoCore().readLine(str);
                        this.getTBekoCore().moveLineNum(i+1);
                    }
                }
            }

        } else if (this.getArg().toUpperCase().split("THEN").length > 0) { //一行类的if
            String[] argArray = this.getArg().toUpperCase().split("THEN");
            this.getTBekoCore().readLine(argArray[1]);
        }
    }

    public RelationOperator getRelation(String str) {
        if (str.contains("<=")) return RelationOperator.LE;
        if (str.contains(">=")) return RelationOperator.GE;
        //if(str.contains("==")) return RelationOperator.EQ;
        if (str.contains("!=")) return RelationOperator.NE;
        if (str.contains("<")) return RelationOperator.LT;
        if (str.contains(">")) return RelationOperator.GT;
        if (str.contains("=")) return RelationOperator.EQ;
        return null;
    }

    public String[] getRelationArray(String str) {
        if (this.getRelation(str) == RelationOperator.LE) return str.replace("<=", "\n").split("\n");
        if (this.getRelation(str) == RelationOperator.GE) return str.replace(">=", "\n").split("\n");
        if (this.getRelation(str) == RelationOperator.NE) return str.replace("!=", "\n").split("\n");
        if (this.getRelation(str) == RelationOperator.LT) return str.replace("<", "\n").split("\n");
        if (this.getRelation(str) == RelationOperator.GT) return str.replace(">", "\n").split("\n");
        if (this.getRelation(str) == RelationOperator.EQ) return str.replace("=", "\n").split("\n");
        return null;
    }
}
