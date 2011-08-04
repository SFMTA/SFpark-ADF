package sfpark.priceChange.manager.view.backing.util.dialog;

import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;

import sfpark.adf.tools.translation.CommonBundleKey;
import sfpark.adf.tools.translation.TranslationUtil;

import sfpark.adf.tools.view.backing.util.dialog.DialogBeanCallback;
import sfpark.adf.tools.view.backing.util.dialog.helpers.DialogType;

import sfpark.priceChange.manager.view.backing.BaseBean;

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
                        TranslationUtil.getCommonBundleString(CommonBundleKey.dialog_title_confirmation);

                break;

            case ALERT:
                dialogTitle =
                        TranslationUtil.getCommonBundleString(CommonBundleKey.dialog_title_alert);

                break;

            case INFO:
                dialogTitle =
                        TranslationUtil.getCommonBundleString(CommonBundleKey.dialog_title_info);

                break;

            case WARNING:
                dialogTitle =
                        TranslationUtil.getCommonBundleString(CommonBundleKey.dialog_title_warning);

                break;

            case ERROR:
                dialogTitle =
                        TranslationUtil.getCommonBundleString(CommonBundleKey.dialog_title_error);

                break;

            default:
                dialogTitle =
                        TranslationUtil.getCommonBundleString(CommonBundleKey.dialog_title_default);

                break;

            }
        }

        setDialogTitle(dialogTitle);

        setDialogText((dialogText != null) ? dialogText :
                      TranslationUtil.getCommonBundleString(CommonBundleKey.dialog_text_default));

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
