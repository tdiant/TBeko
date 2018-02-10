package tdiant.tbeko.core.statement;

import tdiant.tbeko.core.TBekoCore;
import tdiant.tbeko.core.object.TNumberObject;
import tdiant.tbeko.core.object.TStringObject;

import java.math.BigDecimal;

/**
 * Created by tdiant on 2018/2/9.
 */
public class SetStatement extends Statement {
    public SetStatement(TBekoCore tbc, String arg) {
        super(tbc, arg);
    }

    @Override
    public void run() {
        if(getArg().split("=").length!=2){
            //报错
            this.getTBekoCore().getInteractBlock().outError("Wrong Set Statement",this.getTBekoCore());
            return;
        }
        String[] arg = getArg().split("=");
        String[] arg_split = arg[1].split("&");
        String result = "";

        for(String str:arg_split) {
            if (str.charAt(0) == '\"') {
                result += str.substring(1, str.length());
            } else {
                if (this.getTBekoCore().isMathStatement(str)) {
                    Double d = null;
                    try {
                         d = new Double(this.getTBekoCore().counterString(str));
                        if (d.equals(Double.NaN)) {
                            //报错
                            this.getTBekoCore().getInteractBlock().outError("Wrong Set Statement",this.getTBekoCore());
                            return;
                        }
                    }catch (Exception e){
                        this.getTBekoCore().getInteractBlock().outError("Wrong Set Statement",this.getTBekoCore());
                        //报错
                        return;
                    }
                    //this.getTBekoCore().putObject(str, new TNumberObject(d));
                    if(d!=null) result += d.toString();
                }
            }
        }

        if (isNumeric(result)) {
            this.getTBekoCore().putObject(arg[0],new TNumberObject(new BigDecimal(result)));
        }else{
            this.getTBekoCore().putObject(arg[0],new TStringObject(result));
        }
    }

    public static boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }
}
