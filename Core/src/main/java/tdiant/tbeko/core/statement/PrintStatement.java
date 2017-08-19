package tdiant.tbeko.core.statement;

import tdiant.tbeko.core.TBekoCore;

/**
 * Created by tdiant on 2017/8/18.
 */
public class PrintStatement extends Statement {

    public PrintStatement(TBekoCore tbc, String arg) {
        super(tbc, arg);
    }

    @Override
    public void run(){
        if(this.getArg().length()<=0){
            //报错
            return;
        }

        this.setArg( this.getArg().replace(" ",""));
        String[] args = this.getArg().split(",");
        for(String argStr : args){
            /*if(false){ //判断是不是算式
                //
            }else{
                for(String keyStr : this.getTBekoCore().getObjectMap().keySet()){
                    if(keyStr.equalsIgnoreCase(argStr)){
                        System.out.println(""+ this.getTBekoCore().getObjectMap().get(keyStr).toString()); //输出语句
                    }
                }
            }*/
            String output = this.getTBekoCore().counterString(argStr);

            //去除首尾引号
            if(output.charAt(0)=='\"') output=output.substring(1,output.length());
            if(output.charAt(output.length()-1)=='\"') output=output.substring(0,output.length()-1);

            this.getTBekoCore().getInteractBlock().outMessage(output);
        }
    }
}
