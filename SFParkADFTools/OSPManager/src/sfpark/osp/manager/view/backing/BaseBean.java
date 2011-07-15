package sfpark.osp.manager.view.backing;

import javax.el.ELContext;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import sfpark.osp.manager.view.flow.NavigationMode;

public abstract class BaseBean {
    private static final String CURRENT_PAGE_SAVED =
        "sessionScopeKey.currentPageSaved";
    private static final String CURRENT_PAGE_MODE =
        "sessionScopeKey.currentPageMode";

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CURRENT ADF CONTROL MECHANISM

    private String currentADFControl = "";

    protected void setCurrentADFControl(String currentADFControl) {
        this.currentADFControl = currentADFControl;
    }

    protected String getCurrentADFControl() {
        return currentADFControl;
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    protected BaseBean() {
        // TODO: Create a Session Scope User Context
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CURRENT PAGE SAVED MECHANISM

    protected boolean isCurrentPageSaved() {
        Boolean isCurrentPageSaved =
            (Boolean)getSessionScopeValue(CURRENT_PAGE_SAVED);

        // If the value is null, then the page was never edited, and so the page
        // retains all the original values
        return (isCurrentPageSaved == null) ? Boolean.TRUE :
               isCurrentPageSaved.booleanValue();
    }

    protected void setCurrentPageSaved(Boolean currentPageSaved) {
        setSessionScopeValue(CURRENT_PAGE_SAVED, currentPageSaved);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CURRENT PAGE MODE MECHANISM

    protected NavigationMode getCurrentPageMode() {
        String pageModeStr = (String)getSessionScopeValue(CURRENT_PAGE_MODE);

        // If the value is null, then the mechanism is broken. Just set it to
        // READ_ONLY
        return (pageModeStr == null) ? NavigationMode.READ_ONLY :
               NavigationMode.valueOf(pageModeStr);
    }

    protected void setCurrentPageMode(NavigationMode pageMode) {
        setSessionScopeValue(CURRENT_PAGE_MODE, pageMode.name());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PAGE FLOW SCOPE

    /**
     * Set a key/value pair in Page Flow Scope
     *
     * @param key keyword
     * @param value corresponding value
     */
    protected void setPageFlowScopeValue(String key, Object value) {
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put(key,
                                                                    value);
    }

    /**
     * Get the corresponding value of the keyword in Page Flow Scope
     *
     * @param key keyword
     * @return corresponding value
     */
    protected Object getPageFlowScopeValue(String key) {
        return AdfFacesContext.getCurrentInstance().getPageFlowScope().get(key);
    }

    /**
     * Remove a key (and return corresponding value) from Page Flow Scope
     *
     * @param key keyword to be removed
     */
    protected Object removePageFlowScopeValue(String key) {
        return AdfFacesContext.getCurrentInstance().getPageFlowScope().remove(key);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // SESSION SCOPE

    /**
     * Set a key/value pair in Session Scope
     *
     * @param key keyword
     * @param value corresponding value
     */
    protected void setSessionScopeValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key,
                                                                                   value);
    }

    /**
     * Get the corresponding value of the keyword in Session Scope
     *
     * @param key keyword
     * @return corresponding value
     */
    protected Object getSessionScopeValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    /**
     * Remove a key (and return corresponding value) from Session Scope
     *
     * @param key keyword
     * @return corresponding value
     */
    protected Object removeSessionScopeValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // REQUEST SCOPE

    /**
     * Set a key/value pair in Request Scope
     *
     * @param key keyword
     * @param value corresponding value
     */
    protected void setRequestScopeValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(key,
                                                                                   value);
    }

    /**
     * Get the corresponding value of the keyword in Request Scope
     *
     * @param key keyword
     * @return corresponding value
     */
    protected Object getRequestScopeValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(key);
    }

    /**
     * Remove a key (and return corresponding value) from Request Scope
     *
     * @param key keyword
     * @return corresponding value
     */
    protected Object removeRequestScopeValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove(key);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    /**
     * Get the current HttpSession Object
     *
     * @param create If true, session will be created if it doesn't exist
     * @return session (current or created based on above)
     */
    protected HttpSession getHttpSession(boolean create) {
        return (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(create);
    }

    /**
     * Get the value of the parameter identified by name in the URL
     *
     * @param name identifier of the parameter in URL
     * @return value of the URL parameter
     */
    protected String getRequestParameterValue(String name) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    /**
     * Get the HttpServletResponse from FacesContext
     *
     * @return
     */
    protected HttpServletResponse getHttpResponse() {
        return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
    }

    protected HttpServletRequest getHttpRequest() {
        return (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    /**
     * Get the value of the application initialization parameter (if any)
     *
     * @param key keyword
     * @return corresponding value
     */
    protected String getInitParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getInitParameter(key);
    }

    /**
     * Add an UI component as a partial target.
     * @param cmp the UIComponent
     */
    protected void addPartialTarget(UIComponent cmp) {
        if (cmp != null) {
            AdfFacesContext.getCurrentInstance().addPartialTarget(cmp);
        }
    }

    /**
     * Get the current FacesContext instance.
     * @return FacesContext
     */
    protected FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Sets the focus on the UIComponent after its rendered
     *
     * @param uiComponent to be focused on
     */
    protected void setFocus(UIComponent uiComponent) {
        pushJavaScript(getComponentForJS(uiComponent) + ".focus();");
    }

    /**
     * Send down a JavaScript to the client.
     * @param script the JavaScript
     */
    protected void pushJavaScript(String script) {
        Service.getRenderKitService(getFacesContext(),
                                    ExtendedRenderKitService.class).addScript(getFacesContext(),
                                                                              script);
    }

    /**
     * Displays the JavaScript <code>alert()</code>
     * @param message To be displayed
     */
    protected void browserWindowAlert(String message) {
        StringBuffer script = new StringBuffer("alert('");

        script.append(message);
        script.append("')");

        pushJavaScript(script.toString());
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // PRIVATE HELPER METHODS

    private String getComponentForJS(UIComponent uiComponent) {
        return "AdfPage.PAGE.findComponent('" +
            uiComponent.getClientId(getFacesContext()) + "')";
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // ACCESSOR METHODS

    /**
     * Returns the instance of the subclass of the BaseBean being called for
     * @param beanClassName subclass reference name of BaseBean for which the instance is being asked for
     * @return current instance of the subclass of BaseBean
     */
    protected static <T extends BaseBean> T getCurrentInstanceFor(String beanClassName) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Object beanInstance =
            elContext.getELResolver().getValue(elContext, null, beanClassName);

        return (T)beanInstance;
    }
}
