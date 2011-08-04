package sfpark.adf.tools.view.backing.util;

import javax.el.ELContext;

import javax.faces.context.FacesContext;

public final class ADFUtil {

    /**
     * To avoid instantiation
     */
    private ADFUtil() {
        super();
    }

    /**
     * Returns the current instance of Bean
     * 
     * @param beanClassName reference name of the bean for which the instance is being asked for
     * @return current instance of the class
     */
    public static Object getCurrentInstanceFor(String beanClassName) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        Object beanInstance =
            elContext.getELResolver().getValue(elContext, null, beanClassName);

        return beanInstance;
    }

}
