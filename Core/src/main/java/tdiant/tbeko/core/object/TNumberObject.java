package tdiant.tbeko.core.object;

import java.math.BigDecimal;

/**
 * Created by tdiant on 2017/8/18.
 */
public class TNumberObject implements TObject {
    private BigDecimal value = new BigDecimal(0);

    public TNumberObject() {
    }

    public TNumberObject(double num) {
        this.value = new BigDecimal(num);
    }

    @Override
    public TObjectType getType() {
        return TObjectType.Number;
    }

    public BigDecimal getBigDecimal() {
        return value;
    }

    public TNumberObject setBigDecimal(BigDecimal bd) {
        this.value = bd;
        return this;
    }

    public double getValue() {
        return value.doubleValue();
    }

    public TNumberObject setValue(double num) {
        this.value = new BigDecimal(num);
        return this;
    }

    public TNumberObject setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        String str = this.value.doubleValue() + "";
        if (str.contains(".")) str = ((int) this.value.doubleValue()) + "";
        return str;
    }
}
