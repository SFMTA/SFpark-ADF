package sfpark.event.manager.view.backing.util.dialog;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import sfpark.event.manager.i18n.ResourceBundleUtil;
import sfpark.event.manager.view.backing.BaseBean;
import sfpark.event.manager.view.backing.util.dialog.helper.DialogType;

public class DialogBean extends BaseBean {

    private RichPopup DialogPopup;

    private String DialogTitle;
    private String DialogIconName;
    private String DialogText;

    private RichCommandButton DialogOKButton;
    private RichCommandButton DialogCancelButton;

    public static DialogBean getInstance() {
        return (DialogBean)getCurrentInstanceFor("dialogBean");
    }

    public DialogBean() {
        super();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // DIALOG API

    /*

    **
     * Wrapper method for the much used Unsaved Page Confirmation Dialog
     *
     * @param cb
     *
    public void UnsavedPageDialog(DialogBeanCallback cb) {
        this.ConfirmationDialog(TranslationUtil.getTranslatedString(BundlePropertyFiles.ApplicationBundle.getBaseName(),
                                                                    "string_unsaved_data_title"),
                                TranslationUtil.getTranslatedString(BundlePropertyFiles.ApplicationBundle.getBaseName(),
                                                                    "string_unsaved_data_text"),
                                cb);
    }
     */

    public void ConfirmationDialog(String dialogTitle, String dialogText,
                                   DialogBeanCallback cb) {
        setupDialog(DialogType.CONFIRMATION, dialogTitle, dialogText, cb);
    }

    public void AlertDialog(String dialogTitle, String dialogText) {
        setupDialog(DialogType.ALERT, dialogTitle, dialogText, null);
    }

    public void InformationDialog(String dialogTitle, String dialogText) {
        setupDialog(DialogType.INFO, dialogTitle, dialogText, null);
    }

    public void WarningDialog(String dialogTitle, String dialogText,
                              DialogBeanCallback cb) {
        setupDialog(DialogType.WARNING, dialogTitle, dialogText, cb);
    }

    public void ErrorDialog(String dialogTitle, String dialogText) {
        setupDialog(DialogType.ERROR, dialogTitle, dialogText, null);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void setupDialog(DialogType dialogType, String dialogTitle,
                             String dialogText, DialogBeanCallback cb) {

        setDialogIconName(dialogType.getIconType().name());

        if (dialogTitle == null) {
            switch (dialogType) {

            case CONFIRMATION:
                dialogTitle =
                        ResourceBundleUtil.getApplicationBundleString("string_confirmation");
                break;

            case ALERT:
                dialogTitle =
                        ResourceBundleUtil.getApplicationBundleString("string_alert");
                break;

            case INFO:
                dialogTitle =
                        ResourceBundleUtil.getApplicationBundleString("string_info");
                break;

            case WARNING:
                dialogTitle =
                        ResourceBundleUtil.getApplicationBundleString("string_warning");
                break;

            case ERROR:
                dialogTitle =
                        ResourceBundleUtil.getApplicationBundleString("string_error");
                break;

            default:
                dialogTitle =
                        ResourceBundleUtil.getApplicationBundleString("string_dialog_default");
                break;

            }
        }

        setDialogTitle(dialogTitle);

        setDialogText((dialogText != null) ? dialogText :
                      ResourceBundleUtil.getApplicationBundleString("string_default_dialog_text"));

        final DialogBeanCallback callback =
            (cb != null) ? cb : new DialogBeanCallback() {
            public void okButtonHandler() {
                // Do nothing
            }

            public void cancelButtonHandler() {
                // Do nothing
            }
        };

        clearAllActionListeners();

        switch (dialogType.getButtonType()) {

        case OK:

            getDialogOKButton().addActionListener(new ActionListener() {
                    public void processAction(ActionEvent actionEvent) {
                        callback.okButtonHandler();
                        getDialogPopup().hide();
                    }
                });

            getDialogCancelButton().setVisible(false);

            break;

        case CANCEL:

            getDialogOKButton().setVisible(false);

            getDialogCancelButton().addActionListener(new ActionListener() {
                    public void processAction(ActionEvent actionEvent) {
                        callback.cancelButtonHandler();
                        getDialogPopup().cancel();
                    }
                });

            break;

        case OK_CANCEL:

            getDialogOKButton().addActionListener(new ActionListener() {
                    public void processAction(ActionEvent actionEvent) {
                        callback.okButtonHandler();
                        getDialogPopup().hide();
                    }
                });
            getDialogOKButton().setVisible(true);

            getDialogCancelButton().addActionListener(new ActionListener() {
                    public void processAction(ActionEvent actionEvent) {
                        callback.cancelButtonHandler();
                        getDialogPopup().cancel();
                    }
                });
            getDialogCancelButton().setVisible(true);

            break;
        }

        getDialogPopup().show(new RichPopup.PopupHints());
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void clearAllActionListeners() {
        clearAllActionListeners(getDialogOKButton());
        clearAllActionListeners(getDialogCancelButton());
    }

    private void clearAllActionListeners(RichCommandButton commandButton) {
        if (commandButton != null) {
            ActionListener actionListeners[] =
                commandButton.getActionListeners();

            if (actionListeners != null && actionListeners.length > 0) {
                for (ActionListener actionListener : actionListeners) {
                    commandButton.removeActionListener(actionListener);
                }
            }
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI CONNECTIONS

    public void setDialogPopup(RichPopup DialogPopup) {
        this.DialogPopup = DialogPopup;
    }

    public RichPopup getDialogPopup() {
        return DialogPopup;
    }

    public void setDialogTitle(String DialogTitle) {
        this.DialogTitle = DialogTitle;
    }

    public String getDialogTitle() {
        return DialogTitle;
    }

    public void setDialogIconName(String DialogIconName) {
        this.DialogIconName = DialogIconName;
    }

    public String getDialogIconName() {
        return DialogIconName;
    }

    public void setDialogText(String DialogText) {
        this.DialogText = DialogText;
    }

    public String getDialogText() {
        return DialogText;
    }

    public void setDialogOKButton(RichCommandButton DialogOKButton) {
        this.DialogOKButton = DialogOKButton;
    }

    public RichCommandButton getDialogOKButton() {
        return DialogOKButton;
    }

    public void setDialogCancelButton(RichCommandButton DialogCancelButton) {
        this.DialogCancelButton = DialogCancelButton;
    }

    public RichCommandButton getDialogCancelButton() {
        return DialogCancelButton;
    }
}
