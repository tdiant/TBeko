package tdiant.tbeko.core.object;

/**
 * Created by tdiant on 2017/8/18.
 */
public class TStringObject implements TObject{
    private String value;

    public TStringObject(){}

    public TStringObject(String value){
        this.value = value;
    }

    @Override
    public TObjectType getType() {
        return TObjectType.String;
    }

    public String getValue() {
        return value;
    }

    public TStringObject setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
