package tdiant.tbeko.core.statement;

import tdiant.tbeko.core.TBekoCore;

/**
 * Created by tdiant on 2017/8/18.
 */
public abstract class Statement {
    private TBekoCore tbc;
    private String arg = "";

    public Statement(TBekoCore tbc, String arg){
        if(arg!=null)
            this.arg = arg;
        this.tbc = tbc;
    }

    public abstract void run();

    public String getArg(){
        return arg;
    }

    public TBekoCore getTBekoCore(){
        return tbc;
    }

    public void setArg(String arg){
        this.arg = arg;
    }
}
