package com.sfmta.websvcs.pricingxml;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
// !DO NOT EDIT THIS FILE!
// This source file is generated by Oracle tools
// Contents may be subject to change
// For reporting problems, use the following
// Version = Oracle WebServices (11.1.1.0.0, build 101221.1153.15811)

@WebService(wsdlLocation="http://lnxappdev1:8001/SFpark/ProxyServices/MeterPricingRequest?WSDL",
  targetNamespace="http://www.sfmta.com/websvcs/pricingxml", name="MeterPricingWebService")
@XmlSeeAlso(
  { com.sfmta.xsd.mprequest.ObjectFactory.class })
@SOAPBinding(style=Style.DOCUMENT, parameterStyle=ParameterStyle.BARE)
public interface MeterPricingWebService
{
  @WebMethod(operationName="ProcessMeterPricingXML")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="http://www.sfmta.com/websvcs/pricingxml/MeterPricingWebService/ProcessMeterPricingXMLRequest",
    output="http://www.sfmta.com/websvcs/pricingxml/MeterPricingWebService/ProcessMeterPricingXMLResponse")
  @WebResult(targetNamespace="http://www.sfmta.com/xsd/mprequest",
    partName="mpresponse", name="mpresponse")
  public com.sfmta.xsd.mprequest.Mpresponse processMeterPricingXML(@WebParam(targetNamespace="http://www.sfmta.com/xsd/mprequest",
      partName="mprequest", name="mprequest")
    String mprequest);
}
