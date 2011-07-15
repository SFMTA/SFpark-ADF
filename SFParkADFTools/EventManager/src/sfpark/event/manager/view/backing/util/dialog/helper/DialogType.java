package sfpark.event.manager.view.backing.util.dialog.helper;

public enum DialogType {

    /**
     * Uses the Information icon (circle with 'i')
     */
    CONFIRMATION(DialogIcon.info, DialogButton.OK_CANCEL),
    ALERT(DialogIcon.info, DialogButton.OK),
    INFO(DialogIcon.info, DialogButton.OK),

    /**
     * Uses the Warning icon (triangle with '!')
     */
    WARNING(DialogIcon.warning, DialogButton.OK_CANCEL),

    /**
     * Uses the Error icon (circle with 'x')
     */
    ERROR(DialogIcon.error, DialogButton.CANCEL);

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final DialogIcon iconType;
    private final DialogButton buttonType;

    private DialogType(DialogIcon iconType, DialogButton buttonType) {
        this.iconType = iconType;
        this.buttonType = buttonType;
    }

    public DialogIcon getIconType() {
        return iconType;
    }

    public DialogButton getButtonType() {
        return buttonType;
    }

}
