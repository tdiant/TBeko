package tdiant.tbeko.core.interact;

import tdiant.tbeko.core.object.TNumberObject;
import tdiant.tbeko.core.object.TObject;
import tdiant.tbeko.core.object.TStringObject;

/**
 * Created by tdiant on 2017/8/19.
 */
public interface IInteractBlock {
    public void outWaring(String message,int line,String code);
    public void outError(String message,int line,String code);
    public void outMessage(String message);
    public TObject inObject();
    public TStringObject getStringObject();
    public TNumberObject getNumberObject();
}
