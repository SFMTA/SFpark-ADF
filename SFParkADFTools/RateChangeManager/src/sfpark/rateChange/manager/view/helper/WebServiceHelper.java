package sfpark.rateChange.manager.view.helper;

import sfpark.adf.tools.helper.DeveloperMode;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.provider.PropertyFileProvider;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class WebServiceHelper {

    private static final String WEB_SERVICE_URL =
        "http://lnxappdev1:8001/SFpark/ProxyServices/MeterPricingRequest";
    private static final String WEB_SERVICE_URL_PROPERTY_KEY =
        "SFPARK.ADF.TOOLS.WEBSERVICE.URL";

    /**
     * To avoid instantiation
     */
    private WebServiceHelper() {
        super();
    }

    public static OperationStatus callWebService(String processID) {

        if (!StringUtil.isDigitsONLY(processID)) {
            return OperationStatus.failure(new IllegalArgumentException("Process ID should be valid. "));
        }

        String webServiceURL = getWebServiceURL();

        if (StringUtil.isBlank(webServiceURL)) {
            return OperationStatus.failure(new NoSuchFieldException("Web Service URL was not found. "));
        }

        // MeterPricingWebService_Service service = new MeterPricingWebService_Service();
        // MeterPricingWebService webServiceSOAP = service.getMeterPricingWebServiceSOAP();

        // ((BindingProvider)webService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, webServiceURL);

        // Mpresponse mpResponse = webService.processMeterPricingXML(processID);

        String response = ""; // mpResponse.getResponse();

        if (StringUtil.areEqual(response, "success")) {
            return OperationStatus.success();
        }

        return OperationStatus.failure(new Exception()); // mpResponse.getMessage()
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS

    private static String getWebServiceURL() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(WEB_SERVICE_URL_PROPERTY_KEY) :
               WEB_SERVICE_URL;
    }

    /*
    @WebServiceRef
    private static MeterPricingWebService_Service meterPricingWebService_Service;

    public static void main(String[] args) {
        meterPricingWebService_Service = new MeterPricingWebService_Service();
        MeterPricingWebService meterPricingWebService =
            meterPricingWebService_Service.getMeterPricingWebServiceSOAP();
        // Add your code to call the desired methods.

        try {
            MeterPricingWebService_Service service = new MeterPricingWebService_Service();
            MeterPricingWebService webServiceSOAP = service.getMeterPricingWebServiceSOAP();

            ((BindingProvider)webService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, webServiceURL);

            Mpresponse mpResponse = webService.processMeterPricingXML(processID);

            String response = mpResponse.getResponse();

            if (response.equalsIgnoreCase("success")) {
                System.out.println("Operation was successful");
            } else {
                System.out.println("Error Message  = " +
                                   mpResponse.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

     */
}
