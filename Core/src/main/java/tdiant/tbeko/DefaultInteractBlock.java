package tdiant.tbeko;

import tdiant.tbeko.core.TBekoCore;
import tdiant.tbeko.core.interact.IInteractBlock;
import tdiant.tbeko.core.object.TNumberObject;
import tdiant.tbeko.core.object.TObject;
import tdiant.tbeko.core.object.TObjectType;
import tdiant.tbeko.core.object.TStringObject;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by tdiant on 2017/8/19.
 */
public class DefaultInteractBlock implements IInteractBlock {
    @Override
    public void outWaring(String message, int line, String code) {
        System.err.println("[WARING] " + message);
        System.err.println("  at line " + line + " :: " + code);
    }

    @Override
    public void outError(String message, int line, String code) {
        System.err.println("[ERROR] " + message);
        System.err.println("  at line " + line + " :: " + code);
    }

    @Override
    public void outWaring(String message, TBekoCore tbc) {
        this.outWaring(message,tbc.getLineNum(),tbc.getLineCode(tbc.getLineNum()));
    }

    @Override
    public void outError(String message, TBekoCore tbc) {
        this.outError(message,tbc.getLineNum(),tbc.getLineCode(tbc.getLineNum()));
    }

    @Override
    public void outMessage(String message) {
        this.outMessage(message, true);
    }

    @Override
    public void outMessage(String message, boolean b) {
        if (b)
            System.out.println(message);
        else
            System.out.print(message);
    }

    @Override
    public TObject inObject() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();

        //if (Pattern.compile("[0-9]*").matcher(str).matches() && str.charAt(0)!='0') {
        if(isNumber(str)){
                return new TNumberObject(new BigDecimal(str));//Double.valueOf(str));
        } else {
            return new TStringObject(str);
        }
    }

    @Override
    public TStringObject inStringObject() {
        TObject to = this.inObject();
        return new TStringObject(to.toString());
    }

    @Override
    public TNumberObject inNumberObject() {
        TObject to = this.inObject();
        if (to.getType() == TObjectType.Number)
            return (TNumberObject) to;
        else
            return this.inNumberObject();
    }

    private boolean isNumber(String str){
        try{
            new BigDecimal(str);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
