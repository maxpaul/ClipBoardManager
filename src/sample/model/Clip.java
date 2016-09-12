package sample.model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Created by itsab on 2016-09-12.
 */
public class Clip {

    private final StringProperty timeStamp;
    private final StringProperty textValue;
    
    /**
     * Default constructor.
     */
    public Clip() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param timeStamp
     * @param textValue
     */
    public Clip(String timeStamp, String textValue) {
        this.timeStamp = new SimpleStringProperty(timeStamp);
        this.textValue = new SimpleStringProperty(textValue);
    }

    public String getTimeStamp() {
        return timeStamp.get();
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp.set(timeStamp);
    }

    public StringProperty timeStampProperty() {
        return timeStamp;
    }

    public String getTextValue() {
        return textValue.get();
    }

    public void setTextValue(String textValue) {
        this.textValue.set(textValue);
    }

    public StringProperty textValueProperty() {
        return textValue;
    }
}
