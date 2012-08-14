package com.sfmta.websvcs.odi11gws;

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

@WebServiceClient(wsdlLocation="http://sfparktrac1b:20930/oraclediagent/OdiInvoke/#%7Bxmlns.oracle.com%2Fodi%2FOdiInvoke%2F%7DOdiInvoke?wsdl",
  targetNamespace="xmlns.oracle.com/odi/OdiInvoke/", name="OdiInvoke")
public class OdiInvoke
  extends Service
{
  private static URL wsdlLocationURL;

  private static Logger logger;
  static
  {
    try
    {
      logger = Logger.getLogger("com.sfmta.websvcs.odi11gws.OdiInvoke");
      URL baseUrl = OdiInvoke.class.getResource(".");
      if (baseUrl == null)
      {
        wsdlLocationURL =
            OdiInvoke.class.getResource("http://sfparktrac1b:20930/oraclediagent/OdiInvoke/#%7Bxmlns.oracle.com%2Fodi%2FOdiInvoke%2F%7DOdiInvoke?wsdl");
        if (wsdlLocationURL == null)
        {
          baseUrl = new File(".").toURL();
          wsdlLocationURL =
              new URL(baseUrl, "http://sfparktrac1b:20930/oraclediagent/OdiInvoke/#%7Bxmlns.oracle.com%2Fodi%2FOdiInvoke%2F%7DOdiInvoke?wsdl");
        }
      }
      else
      {
                if (!baseUrl.getPath().endsWith("/")) {
         baseUrl = new URL(baseUrl, baseUrl.getPath() + "/");
}
                wsdlLocationURL =
            new URL(baseUrl, "http://sfparktrac1b:20930/oraclediagent/OdiInvoke/#%7Bxmlns.oracle.com%2Fodi%2FOdiInvoke%2F%7DOdiInvoke?wsdl");
      }
    }
    catch (MalformedURLException e)
    {
      logger.log(Level.ALL,
          "Failed to create wsdlLocationURL using http://sfparktrac1b:20930/oraclediagent/OdiInvoke/#%7Bxmlns.oracle.com%2Fodi%2FOdiInvoke%2F%7DOdiInvoke?wsdl",
          e);
    }
  }

  public OdiInvoke()
  {
    super(wsdlLocationURL,
          new QName("xmlns.oracle.com/odi/OdiInvoke/", "OdiInvoke"));
  }

  public OdiInvoke(URL wsdlLocation, QName serviceName)
  {
    super(wsdlLocation, serviceName);
  }

  @WebEndpoint(name="OdiInvokeRequestSOAP11port0")
  public RequestPortType getOdiInvokeRequestSOAP11Port0()
  {
    return (RequestPortType) super.getPort(new QName("xmlns.oracle.com/odi/OdiInvoke/",
                                                     "OdiInvokeRequestSOAP11port0"),
                                           RequestPortType.class);
  }

  @WebEndpoint(name="OdiInvokeRequestSOAP11port0")
  public RequestPortType getOdiInvokeRequestSOAP11Port0(WebServiceFeature... features)
  {
    return (RequestPortType) super.getPort(new QName("xmlns.oracle.com/odi/OdiInvoke/",
                                                     "OdiInvokeRequestSOAP11port0"),
                                           RequestPortType.class,
                                           features);
  }
}