package tdiant.tbeko;

import tdiant.tbeko.core.TBekoCore;

/**
 * Created by tdiant on 2017/8/18.
 */
public class JavaTest {
    public static void main(String[] args) {
        /*String str = "" +
                "INPUT \"ABC\";S,B\r\n" +
                "PRINT S+16\r\n" +
                "PRINT B";*/
        /*String str = "" +
                "INPUT \"ABC\", A\n" +
                "PRINT \"test\";A" +
                "";*/
        String str = "INPUT \"INPUT X:\"; x\n" +
                "IF x<0 THEN\n" +
                "y=2*x+3\n" +
                "ELSE\n" +
                "IF x>0 THEN\n" +
                "y=-2*x+5\n" +
                "ELSE\n" +
                "y=0\n" +
                "END IF\n" +
                "END IF\n" +
                "PRINT Y\n" +
                "END";
        DefaultInteractBlock dib = new DefaultInteractBlock();
        TBekoCore tbc = new TBekoCore(str, dib);
        tbc.read();
    }
}
