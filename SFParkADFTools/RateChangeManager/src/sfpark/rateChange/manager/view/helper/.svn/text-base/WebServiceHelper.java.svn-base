package sfpark.rateChange.manager.view.helper;

import com.sfmta.websvcs.pricingxml.MeterPricingWebService;
import com.sfmta.websvcs.pricingxml.MeterPricingWebService_Service;

import com.sfmta.xsd.mprequest.Mpresponse;

import javax.xml.ws.BindingProvider;

import sfpark.adf.tools.helper.DeveloperMode;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.provider.PropertyFileProvider;
import sfpark.adf.tools.utilities.generic.StringUtil;

public final class WebServiceHelper {

    private static final String WEB_SERVICE_URL =
        "http://lnxappdev1:8001/SFpark/ProxyServices/MeterPricingRequest";
    private static final String WEB_SERVICE_URL_PROPERTY_KEY =
        "SFPARK.ADF.TOOLS.WEBSERVICE.URL";

    private static final String CLASSNAME = WebServiceHelper.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

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

        MeterPricingWebService_Service service =
            new MeterPricingWebService_Service();
        MeterPricingWebService webServiceSOAP =
            service.getMeterPricingWebServiceSOAP();

        ((BindingProvider)webServiceSOAP).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                                                                  webServiceURL);

        Mpresponse mpResponse = null;

        try {
            mpResponse = webServiceSOAP.processMeterPricingXML(processID);
        } catch (Exception e) {
            LOGGER.error("Web Service URL is invalid and/or not working. ", e);

            return OperationStatus.failure(new Exception("Web Service Error. Contact System Administrator. "));
        }

        String response = mpResponse.getResponse();

        if (StringUtil.areEqual(response, "success")) {
            return OperationStatus.success();
        }

        return OperationStatus.failure(new Exception(mpResponse.getMessage()));
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
}
