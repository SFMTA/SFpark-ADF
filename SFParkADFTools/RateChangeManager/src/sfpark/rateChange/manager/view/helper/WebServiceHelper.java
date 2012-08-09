package sfpark.rateChange.manager.view.helper;

import com.sfmta.websvcs.pricingxml.MeterPricingWebService;
import com.sfmta.websvcs.pricingxml.MeterPricingWebService_Service;

import com.sfmta.xsd.mprequest.Mpresponse;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.BindingProvider;

import oracle.adf.share.logging.ADFLogger;

import javax.xml.namespace.QName;

import javax.xml.ws.Service;

import sfpark.adf.tools.helper.DeveloperMode;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.provider.PropertyFileProvider;
import sfpark.adf.tools.utilities.generic.StringUtil;

import sfpark.rateChange.manager.view.backing.rateChange.RateChangePropertiesBean;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- --------------------------------------------------------
 * 20120130-01 Mark Piller - Oracle Consulting  Added debug statement to identify URL invoked for WS
 * 20120320-01 Mark Piller - Oracle Consulting  Added debug statement to identify processID for WS
 * 20120320-02 Mark Piller - Oracle Consulting  Change debug to info for logger
 * 20120531-01 Mark Piller - Oracle Consulting  Added debug statement to identify response from WS
 * 20120531-02 Mark Piller - Oracle Consulting  Change logger to ADF Logger for consistency
 * 20120531-03 Mark Piller - Oracle Consulting  Added parameters to creation of Web Service
 */
public final class WebServiceHelper {

    private static final String WEB_SERVICE_URL =
        "http://lnxappdev1:8001/SFpark/ProxyServices/MeterPricingRequest";
    private static final String WEB_SERVICE_URL_PROPERTY_KEY =
        "SFPARK.ADF.TOOLS.WEBSERVICE.URL";

    private static final String CLASSNAME = WebServiceHelper.class.getName();
    // 20120531-02  private static final Logger LOGGER = Logger.getLogger(CLASSNAME);
    private static ADFLogger adfLogger = ADFLogger.createADFLogger(CLASSNAME); // 20120531-02
    

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
        // 20120531-02  LOGGER.info("DEBUG >> process ID passed to callWebService(): " + processID); // 20120320-01 Added debug statement // 20120320-02
        adfLogger.info("DEBUG >> process ID passed to callWebService(): " + processID);

        String webServiceURL = getWebServiceURL();
        // 20120531-02  LOGGER.info("DEBUG >> Web Service URL used is: " + webServiceURL); // 20120130-01 Added debug statement // 20120320-02
        adfLogger.info("DEBUG >> Web Service URL used is: " + webServiceURL);

        if (StringUtil.isBlank(webServiceURL)) {
            return OperationStatus.failure(new NoSuchFieldException("Web Service URL was not found. "));
        }

        // 20120531-03
        URL webServiceUrl = null;
        try {
            webServiceUrl = new URL(webServiceURL);
        } catch (MalformedURLException e) {
            // end
        }
        // 20120531-03 replaced by below logic --> MeterPricingWebService_Service service = new MeterPricingWebService_Service();
        // 20120531-03  
        MeterPricingWebService_Service service = new MeterPricingWebService_Service(webServiceUrl, new QName("http://www.sfmta.com/websvcs/pricingxml", "MeterPricingWebService"));
        MeterPricingWebService webServiceSOAP = service.getMeterPricingWebServiceSOAP();

        ((BindingProvider)webServiceSOAP).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                                                                  webServiceURL);

        Mpresponse mpResponse = null;

        try {
            mpResponse = webServiceSOAP.processMeterPricingXML(processID);
        } catch (Exception e) {
            // 20120531-02  LOGGER.error("DEBUG >> Web Service URL is invalid and/or not working. ", e);
            adfLogger.info( "Web Service URL is invalid and/or not working. " + e.toString());
            return OperationStatus.failure(new Exception("Web Service Error. Contact System Administrator. "));
        }

        String response = mpResponse.getResponse();
        // 20120531-02  LOGGER.info("DEBUG >> Response from call to Web Service: " + response); // 20120531-01 Added debug statement // 20120320-02
        adfLogger.info("DEBUG >> Response from call to Web Service: " + response);

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
