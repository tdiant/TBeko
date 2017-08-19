package tdiant.tbeko;

import tdiant.tbeko.core.TBekoCore;
import tdiant.tbeko.core.interact.DefaultInteractBlock;
import tdiant.tbeko.core.object.TNumberObject;

/**
 * Created by tdiant on 2017/8/18.
 */
public class JavaTest {
    public static void main(String[] args) {
        /*String str = "" +
                "INPUT \"ABC\";S,B\r\n" +
                "PRINT S+16\r\n" +
                "PRINT B";*/
        String str = "" +
                "IF S >2 THEN";
        DefaultInteractBlock dib = new DefaultInteractBlock();
        TBekoCore tbc = new TBekoCore(str, dib);
        tbc.putObject("S", new TNumberObject(2));
        tbc.read();
    }
}
