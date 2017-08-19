package tdiant.tbeko;

import tdiant.tbeko.core.TBekoCore;
import tdiant.tbeko.core.interact.DefaultInteractBlock;

/**
 * Created by tdiant on 2017/8/18.
 */
public class JavaTest {
    public static void main(String[] args) {
        String str = "PRINT 1+\"ACV\"";
        DefaultInteractBlock dib = new DefaultInteractBlock();
        TBekoCore tbc = new TBekoCore(str,dib);
        tbc.read();
    }
}
