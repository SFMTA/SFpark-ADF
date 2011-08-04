package sfpark.adf.tools.view.backing.helper;

/**
 * To be implemented by those Bean Classes which are under Request Scope
 */
public interface RequestScopeBeanInterface {

    /**
     * To be called with cache cleaning is required
     */
    public void clearPageFlowScopeCache();

}
