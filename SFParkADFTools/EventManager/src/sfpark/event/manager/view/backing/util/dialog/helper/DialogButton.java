package sfpark.event.manager.view.backing.util.dialog.helper;

public enum DialogButton {
    /**
     * Just an OK button
     */
    OK(1),

    /**
     * Just a Cancel button
     */
    CANCEL(1),

    /**
     * Both OK and Cancel buttons
     */
    OK_CANCEL(2);

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final int numOfButtons;

    private DialogButton(int numOfButtons) {
        this.numOfButtons = numOfButtons;
    }

    public int getNumOfButtons() {
        return numOfButtons;
    }
}
