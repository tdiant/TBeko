package tdiant.tbeko.core.interact;

import tdiant.tbeko.core.object.TNumberObject;
import tdiant.tbeko.core.object.TObject;
import tdiant.tbeko.core.object.TObjectType;
import tdiant.tbeko.core.object.TStringObject;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by tdiant on 2017/8/19.
 */
public class DefaultInteractBlock implements IInteractBlock {
    @Override
    public void outWaring(String message, int line, String code) {
        System.err.println("[WARING] "+message);
        System.err.println("  at line "+line+" :: "+code);
    }

    @Override
    public void outError(String message, int line, String code) {
        System.err.println("[ERROR] "+message);
        System.err.println("  at line "+line+" :: "+code);
    }

    @Override
    public void outMessage(String message) {
        System.out.println(message);
    }

    @Override
    public TObject inObject() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();

        if(Pattern.compile("[0-9]*").matcher(str).matches()){
            return new TNumberObject(Double.valueOf(str));
        }else{
            return new TStringObject(str);
        }
    }

    @Override
    public TStringObject getStringObject() {
        TObject to = this.inObject();
        return new TStringObject(to.toString());
    }

    @Override
    public TNumberObject getNumberObject() {
        TObject to = this.inObject();
        if(to.getType()== TObjectType.Number)
            return (TNumberObject) to;
        else
            return getNumberObject();
    }
}
