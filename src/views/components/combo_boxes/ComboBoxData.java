package views.components.combo_boxes;

public class ComboBoxData {

    private String displayText;
    private Object supportingData;

    public ComboBoxData (String _displayText, Object _supportingData) {
        this.displayText = _displayText;
        this.supportingData = _supportingData;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String _displayText) {
        this.displayText = _displayText;
    }

    public Object getSupportingData() {
        return supportingData;
    }

    public void setSupportingData(Object _supportingData) {
        this.supportingData = _supportingData;
    }

    @Override
    public String toString() {
        return this.displayText;
    }

}
