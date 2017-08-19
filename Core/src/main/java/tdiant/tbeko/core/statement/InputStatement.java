package tdiant.tbeko.core.statement;

import tdiant.tbeko.core.TBekoCore;
import tdiant.tbeko.core.object.TObject;
import tdiant.tbeko.core.object.TObjectType;

/**
 * Created by tdiant on 2017/8/19.
 */
public class InputStatement extends Statement {

    public InputStatement(TBekoCore tbc, String arg) {
        super(tbc, arg);
    }

    @Override
    public void run() {
        if(this.getArg().length()<=0){
            //报错
            return;
        }

        this.setArg( this.getArg().replace(" ",",").replace(";",""));
        while(this.getArg().contains(",,")) this.setArg(this.getArg().replace(",,",","));

        System.out.println("LL::"+this.getArg());

        String[] args = this.getArg().split(",");
        boolean b = false;
        for(String argStr : args){
            if(!b){
                TObject to = this.getTBekoCore().getObjectMap().get(argStr.trim());
                if(to!=null && to.getType()== TObjectType.String){
                    this.getTBekoCore().getInteractBlock().outMessage(to.toString(),false);
                    continue;
                }
            }
            b = true;
            TObject data = this.getTBekoCore().getInteractBlock().inObject();
            this.getTBekoCore().getObjectMap().put(argStr,data);
        }
    }
}
