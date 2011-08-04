package sfpark.adf.tools.view.backing.util.wizard;

/**
 * This interface should be implemented by those beans which form a part of the
 * Wizard Model
 */
public interface WizardBeanInterface {

    /**
     * Triggered when 'Previous' is clicked
     */
    public boolean onPreviousActionHandler();

    /**
     * Triggered when 'Next' is clicked
     */
    public boolean onNextActionHandler();

    /**
     * To be performed irrespective of wizard state
     */
    public void compulsoryOperation();

}
