package tdiant.tbeko.core.statement;

import tdiant.tbeko.core.TBekoCore;

/**
 * Created by tdiant on 2018/2/10.
 */
public class DoStatement extends Statement {
    public DoStatement(TBekoCore tbc, String arg) {
        super(tbc, arg);
    }

    @Override
    public void run() {
        //寻找LOOP
        int LOOP_line_num=-1;
        for(int i=this.getTBekoCore().getLineNum()+1;i<=this.getTBekoCore().getMaxLineNum();i++){
            if(this.getTBekoCore().getLineCode(i).trim().replace(" ","").contains("LOOPUNTIL")){
                LOOP_line_num = i;
            }
        }

        if(LOOP_line_num==-1){
            //报错
            this.getTBekoCore().getInteractBlock().outError("DO Statement Cannot Find a correct LOOP Statement to correct.",this.getTBekoCore());
            return;
        }

        String[] loop_args = this.getTBekoCore().getLineCode(LOOP_line_num).trim().split(" ");
        if(loop_args.length<3 || !loop_args[1].contains("UNTIL")){
            //LOOP不正确
            this.getTBekoCore().getInteractBlock().outError("DO Statement Cannot Find a correct LOOP Statement With UNTIL to correct.",this.getTBekoCore());
            return;
        }

        String pdStr = this.getTBekoCore().getLineCode(LOOP_line_num).replaceAll("LOOP UNTIL ","").replace(" ","");

        RelationOperator ro = IfFunction.getRelation(pdStr);
        String[] strArray = IfFunction.getRelationArray(pdStr);
        if (strArray.length != 2) {
            //报错：判断式不正确
            this.getTBekoCore().getInteractBlock().outError("Wrong Relation Equation",this.getTBekoCore());
            return;
        }

        //先执行一次
        for(int i=this.getTBekoCore().getLineNum()+1;i<LOOP_line_num;i++){
            this.getTBekoCore().readLine(this.getTBekoCore().getLineCode(i));
        }

        while(isRun(ro,strArray)){
            for(int i=this.getTBekoCore().getLineNum()+1;i<LOOP_line_num;i++){
                this.getTBekoCore().readLine(this.getTBekoCore().getLineCode(i));
            }
        }

        this.getTBekoCore().moveLineNum(LOOP_line_num+1);
    }

    private boolean isRun(RelationOperator ro,String[] strArray){
        boolean isRun = false;
        //if (this.getTBekoCore().counterString(strArray[0]).equalsIgnoreCase(this.getTBekoCore().counterString(strArray[1])))
        if (IfFunction.isCorrectPrerequisite(this.getTBekoCore().counterString(strArray[0]),this.getTBekoCore().counterString(strArray[1]),ro))
            isRun = true;
        return !isRun;
    }
}
