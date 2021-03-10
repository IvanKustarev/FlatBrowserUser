package CommonClasses;

import java.io.Serializable;

public abstract class AbstractDataBlock implements Serializable {
    public String phrase = null;
    public String parameter = null;
    public Flat flat = null;
//    FlatCollection flatCollection = null;
    public  Flat[] flats;
    public boolean allRight;

//    @Override
//    public abstract String toString();

    public abstract boolean startProcessingCommand(AbstractDataBlock answer);
}
