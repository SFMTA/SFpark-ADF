package com.sfmta.websvcs.pricingxml;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 101221.1153.15811)

@WebServiceClient(wsdlLocation="http://lnxappdev1:8001/SFpark/ProxyServices/MeterPricingRequest?WSDL",
  targetNamespace="http://www.sfmta.com/websvcs/pricingxml", name="MeterPricingWebService")
public class MeterPricingWebService_Service
  extends Service
{
  private static URL wsdlLocationURL;

  private static Logger logger;
  static
  {
    try
    {
      logger = Logger.getLogger("com.sfmta.websvcs.pricingxml.MeterPricingWebService_Service");
      URL baseUrl = MeterPricingWebService_Service.class.getResource(".");
      if (baseUrl == null)
      {
        wsdlLocationURL =
            MeterPricingWebService_Service.class.getResource("http://lnxappdev1:8001/SFpark/ProxyServices/MeterPricingRequest?WSDL");
        if (wsdlLocationURL == null)
        {
          baseUrl = new File(".").toURL();
          wsdlLocationURL =
              new URL(baseUrl, "http://lnxappdev1:8001/SFpark/ProxyServices/MeterPricingRequest?WSDL");
        }
      }
      else
      {
                if (!baseUrl.getPath().endsWith("/")) {
         baseUrl = new URL(baseUrl, baseUrl.getPath() + "/");
}
                wsdlLocationURL =
            new URL(baseUrl, "http://lnxappdev1:8001/SFpark/ProxyServices/MeterPricingRequest?WSDL");
      }
    }
    catch (MalformedURLException e)
    {
      logger.log(Level.ALL,
          "Failed to create wsdlLocationURL using http://lnxappdev1:8001/SFpark/ProxyServices/MeterPricingRequest?WSDL",
          e);
    }
  }

  public MeterPricingWebService_Service()
  {
    super(wsdlLocationURL,
          new QName("http://www.sfmta.com/websvcs/pricingxml",
                    "MeterPricingWebService"));
  }

  public MeterPricingWebService_Service(URL wsdlLocation,
                                        QName serviceName)
  {
    super(wsdlLocation, serviceName);
  }

  @WebEndpoint(name="MeterPricingWebServiceSOAP")
  public com.sfmta.websvcs.pricingxml.MeterPricingWebService getMeterPricingWebServiceSOAP()
  {
    return (com.sfmta.websvcs.pricingxml.MeterPricingWebService) super.getPort(new QName("http://www.sfmta.com/websvcs/pricingxml",
                                                                                         "MeterPricingWebServiceSOAP"),
                                                                               com.sfmta.websvcs.pricingxml.MeterPricingWebService.class);
  }

  @WebEndpoint(name="MeterPricingWebServiceSOAP")
  public com.sfmta.websvcs.pricingxml.MeterPricingWebService getMeterPricingWebServiceSOAP(WebServiceFeature... features)
  {
    return (com.sfmta.websvcs.pricingxml.MeterPricingWebService) super.getPort(new QName("http://www.sfmta.com/websvcs/pricingxml",
                                                                                         "MeterPricingWebServiceSOAP"),
                                                                               com.sfmta.websvcs.pricingxml.MeterPricingWebService.class,
                                                                               features);
  }
}
