package tdiant.tbeko.core.statement;

import tdiant.tbeko.core.TBekoCore;

/**
 * Created by tdiant on 2018/2/10.
 */
public class WhileStatement extends Statement {
    public WhileStatement(TBekoCore tbc, String arg) {
        super(tbc, arg);
    }

    @Override
    public void run() {
        String pdStr = getArg().trim().replace(" ", "");
        //while(pdStr.charAt(0)==' ') pdStr = pdStr.substring(1,pdStr.length());

        RelationOperator ro = IfFunction.getRelation(pdStr);
        String[] strArray = IfFunction.getRelationArray(pdStr);
        if (strArray.length != 2) {
            //报错：判断式不正确
            this.getTBekoCore().getInteractBlock().outError("Wrong Relation Equation",this.getTBekoCore());
            return;
        }

        //寻找WEND
        int WEND_line_num=-1;
        for(int i=this.getTBekoCore().getLineNum()+1;i<=this.getTBekoCore().getMaxLineNum();i++){
            if(this.getTBekoCore().getLineCode(i).trim().replace(" ","").equals("WEND")){
                WEND_line_num = i;
            }
        }

        if(WEND_line_num==-1){
            //报错：没有WEND
            this.getTBekoCore().getInteractBlock().outError("WHILE Statement Cannot Find a correct WEND Statement to correct.",this.getTBekoCore());
            return;
        }

        while(isRun(ro,strArray)){
            for(int i=this.getTBekoCore().getLineNum()+1;i<WEND_line_num;i++){
                this.getTBekoCore().readLine(this.getTBekoCore().getLineCode(i));
            }
        }

        this.getTBekoCore().moveLineNum(WEND_line_num+1);
    }

    private boolean isRun(RelationOperator ro,String[] strArray){
        boolean isRun = false;
        //if (this.getTBekoCore().counterString(strArray[0]).equalsIgnoreCase(this.getTBekoCore().counterString(strArray[1])))
        if (IfFunction.isCorrectPrerequisite(this.getTBekoCore().counterString(strArray[0]),this.getTBekoCore().counterString(strArray[1]),ro))
            isRun = true;
        return isRun;
    }
}
