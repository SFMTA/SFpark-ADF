package sfpark.priceChange.manager.view.backing.deploymentProcess;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import sfpark.adf.tools.model.data.dto.rateChange.RateChangeProcessControlDTO;
import sfpark.adf.tools.view.backing.helper.PropertiesBeanInterface;
import sfpark.adf.tools.view.backing.helper.RequestScopeBeanInterface;

import sfpark.adf.tools.view.backing.util.ADFUtil;

public class DeploymentProcessPropertiesBean implements PropertiesBeanInterface,
                                                                         RequestScopeBeanInterface {

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTORS

    public DeploymentProcessPropertiesBean() {
        super();
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public static DeploymentProcessPropertiesBean getInstance() {
        return (DeploymentProcessPropertiesBean)ADFUtil.getCurrentInstanceFor("deploymentProcessPropertiesBean");
    }

    public void clearPageFlowScopeCache() {
        // TODO
    }

    public void setInlineMessageText(String inlineMessageText) {
        // this.setPageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_TEXT.getKey(), inlineMessageText);
    }

    public String getInlineMessageText() {
        return ""; // (String)removePageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_TEXT.getKey());
    }

    public void setInlineMessageClass(String inlineMessageClass) {
        // this.setPageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_CLASS.getKey(), inlineMessageClass);
    }

    public String getInlineMessageClass() {
        return ""; // (String)removePageFlowScopeValue(PageFlowScopeKey.INLINE_MESSAGE_CLASS.getKey());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DTO INFORMATION

    public RateChangeProcessControlDTO getRateChangeProcessControlDTO() {
        RateChangeProcessControlDTO DTO = new RateChangeProcessControlDTO();
            // (RateChangeProcessControlDTO)getPageFlowScopeValue(PageFlowScopeKey.SELECTED_ACTIVE_DEPLOY_PROCESS_FOR_PROPS.getKey());

        if (DTO == null) {
            // Invalid situation
            // TODO decide on proper workflow
        }

        return DTO;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALIDATORS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL READ-ONLY INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL RENDER INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL VISIBLE INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ALL DISABLE INFORMATION

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // DISPLAY LIST VALUES

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // VALUE CHANGE HANDLERS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void saveButtonHandler(ActionEvent event) {
    }

    public void cancelButtonHandler(ActionEvent event) {
    }

    public void anyValueChangeHandler(ValueChangeEvent event) {
        // setCurrentPageSaved(Boolean.FALSE);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS EXTRA

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // UI BINDINGS

}

/*
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // EVENT HANDLERS

    public void saveButtonHandler(ActionEvent event) {

        Boolean currentPageSaved = Boolean.FALSE;

        if (isPasswordPolicyValid()) {
            OperationStatus operationStatus =
                RealmsProvider.instance.saveDBRealmProperties(getDto());

            if (operationStatus.getType().isSuccess()) {
                String saveInlineMessage =
                    StringUtil.concatenate(TranslationUtil.getTranslatedString(BundlePropertyFiles.CommonBundle.getBaseName(),
                                                                               "string_save_successful"),
                                           getDto().getName());

                setSessionScopeValue(SessionScopeKey.REALM_SAVED_MESSAGE,
                                     saveInlineMessage);

                currentPageSaved = Boolean.TRUE;

                removePageFlowScopeValue(PageFlowScopeKey.REALM_LIST);

                removeSessionScopeValue(SessionScopeKey.DB_REALM_ID_FOR_PROPS);
                setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO,
                                     NavigationFlow.Manage_Realms.name());

            } else {
                inlineMessageText =
                        TranslationUtil.getTranslatedString(BundlePropertyFiles.ApplicationBundle.getBaseName(),
                                                            "error_save_failure");
                inlineMessageClass = OperationStatus.STYLECLASS_FAILURE;
                currentPageSaved = Boolean.FALSE;
            }
        }

        setCurrentPageSaved(currentPageSaved);

    }

    public void cancelButtonHandler(ActionEvent event) {

        if (!isCurrentPageSaved()) {
            DialogBean.getInstance().UnsavedPageDialog(this.getCallback());
        } else {
            ignoreCurrentAndMoveOn();
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private void ignoreCurrentAndMoveOn() {
        removeSessionScopeValue(SessionScopeKey.DB_REALM_ID_FOR_PROPS);
        setSessionScopeValue(SessionScopeKey.NAVIGATION_INFO,
                             NavigationFlow.Manage_Realms.name());
    }

    private DialogBeanCallback getCallback() {

        DialogBeanCallback callback = new DialogBeanCallback() {
            public void okButtonHandler() {
                ignoreCurrentAndMoveOn();
                NavigationBean.getInstance().refreshRegionContent();
            }

            public void cancelButtonHandler() {
                // Do nothing
            }
        };

        return callback;
    }

}

 */
