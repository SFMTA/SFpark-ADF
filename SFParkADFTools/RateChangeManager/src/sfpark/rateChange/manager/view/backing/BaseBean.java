package sfpark.rateChange.manager.view.backing;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import sfpark.rateChange.manager.view.flow.NavigationMode;

public abstract class BaseBean {

    protected BaseBean() {
    }

    protected void printLog(String message) {
        // During development phase, uncomment to make logging easy
        // System.out.println(message);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // CURRENT PAGE MODE MECHANISM

    private static final String CURRENT_PAGE_MODE =
        "sessionScopeKey.currentPageMode";

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
}
