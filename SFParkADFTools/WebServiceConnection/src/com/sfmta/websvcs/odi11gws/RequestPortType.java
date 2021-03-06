package com.sfmta.websvcs.odi11gws;

import com.sfmta.websvcs.odi11gws.types.GetWebServiceVersionRequest;
import com.sfmta.websvcs.odi11gws.types.GetWebServiceVersionResponse;
import com.sfmta.websvcs.odi11gws.types.ObjectFactory;
import com.sfmta.websvcs.odi11gws.types.OdiGetLoadPlanStatusRequest;
import com.sfmta.websvcs.odi11gws.types.OdiGetLoadPlanStatusResponse;
import com.sfmta.websvcs.odi11gws.types.OdiGetSessionsStatusRequest;
import com.sfmta.websvcs.odi11gws.types.OdiGetSessionsStatusResponse;
import com.sfmta.websvcs.odi11gws.types.OdiRestartLoadPlanRequest;
import com.sfmta.websvcs.odi11gws.types.OdiRestartLoadPlanResponse;
import com.sfmta.websvcs.odi11gws.types.OdiRestartSessRequest;
import com.sfmta.websvcs.odi11gws.types.OdiRestartSessWithCallbackRequest;
import com.sfmta.websvcs.odi11gws.types.OdiStartLoadPlanRequest;
import com.sfmta.websvcs.odi11gws.types.OdiStartLoadPlanResponse;
import com.sfmta.websvcs.odi11gws.types.OdiStartScenRequest;
import com.sfmta.websvcs.odi11gws.types.OdiStartScenWithCallbackRequest;
import com.sfmta.websvcs.odi11gws.types.OdiStartType;
import com.sfmta.websvcs.odi11gws.types.OdiStopLoadPlanRequest;
import com.sfmta.websvcs.odi11gws.types.OdiStopLoadPlanResponse;

import javax.jws.Oneway;
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

@WebService(wsdlLocation="http://sfparktrac1b:20930/oraclediagent/OdiInvoke/#%7Bxmlns.oracle.com%2Fodi%2FOdiInvoke%2F%7DOdiInvoke?wsdl",
  targetNamespace="xmlns.oracle.com/odi/OdiInvoke/", name="requestPortType")
@XmlSeeAlso(
  { ObjectFactory.class })
@SOAPBinding(style=Style.DOCUMENT, parameterStyle=ParameterStyle.BARE)
public interface RequestPortType
{
  @WebMethod(action="xmlns.oracle.com/odi/OdiInvoke/invokeStartScen")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="xmlns.oracle.com/odi/OdiInvoke/invokeStartScen", output="xmlns.oracle.com/odi/OdiInvoke/requestPortType/invokeStartScenResponse")
  @WebResult(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/", partName="part1",
    name="OdiStartScenResponse")
  public OdiStartType invokeStartScen(@WebParam(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/",
      partName="part1", name="OdiStartScenRequest")
    OdiStartScenRequest part1);

  @WebMethod(action="xmlns.oracle.com/odi/OdiInvoke/invokeStartScenWithCallback")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="xmlns.oracle.com/odi/OdiInvoke/invokeStartScenWithCallback")
  @Oneway
  public void invokeStartScenWithCallback(@WebParam(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/",
      partName="part1", name="OdiStartScenWithCallbackRequest")
    OdiStartScenWithCallbackRequest part1);

  @WebMethod(action="xmlns.oracle.com/odi/OdiInvoke/getVersion")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="xmlns.oracle.com/odi/OdiInvoke/getVersion", output="xmlns.oracle.com/odi/OdiInvoke/requestPortType/getVersionResponse")
  @WebResult(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/", partName="part1",
    name="getWebServiceVersionResponse")
  public GetWebServiceVersionResponse getVersion(@WebParam(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/",
      partName="part1", name="getWebServiceVersionRequest")
    GetWebServiceVersionRequest part1);

  @WebMethod(action="xmlns.oracle.com/odi/OdiInvoke/invokeRestartSess")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="xmlns.oracle.com/odi/OdiInvoke/invokeRestartSess",
    output="xmlns.oracle.com/odi/OdiInvoke/requestPortType/invokeRestartSessResponse")
  @WebResult(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/", partName="part1",
    name="OdiRestartSessResponse")
  public OdiStartType invokeRestartSess(@WebParam(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/",
      partName="part1", name="OdiRestartSessRequest")
    OdiRestartSessRequest part1);

  @WebMethod(action="xmlns.oracle.com/odi/OdiInvoke/invokeRestartSessWithCallback")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="xmlns.oracle.com/odi/OdiInvoke/invokeRestartSessWithCallback")
  @Oneway
  public void invokeRestartSessWithCallback(@WebParam(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/",
      partName="part1", name="OdiRestartSessWithCallbackRequest")
    OdiRestartSessWithCallbackRequest part1);

  @WebMethod(action="xmlns.oracle.com/odi/OdiInvoke/getSessionStatus")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="xmlns.oracle.com/odi/OdiInvoke/getSessionStatus", output="xmlns.oracle.com/odi/OdiInvoke/requestPortType/getSessionStatusResponse")
  @WebResult(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/", partName="part1",
    name="OdiGetSessionsStatusResponse")
  public OdiGetSessionsStatusResponse getSessionStatus(@WebParam(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/",
      partName="part1", name="OdiGetSessionsStatusRequest")
    OdiGetSessionsStatusRequest part1);

  @WebMethod(action="xmlns.oracle.com/odi/OdiInvoke/invokeStartLoadPlan")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="xmlns.oracle.com/odi/OdiInvoke/invokeStartLoadPlan",
    output="xmlns.oracle.com/odi/OdiInvoke/requestPortType/invokeStartLoadPlanResponse")
  @WebResult(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/", partName="part1",
    name="OdiStartLoadPlanResponse")
  public OdiStartLoadPlanResponse invokeStartLoadPlan(@WebParam(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/",
      partName="part1", name="OdiStartLoadPlanRequest")
    OdiStartLoadPlanRequest part1);

  @WebMethod(action="xmlns.oracle.com/odi/OdiInvoke/invokeStopLoadPlan")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="xmlns.oracle.com/odi/OdiInvoke/invokeStopLoadPlan",
    output="xmlns.oracle.com/odi/OdiInvoke/requestPortType/invokeStopLoadPlanResponse")
  @WebResult(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/", partName="part1",
    name="OdiStopLoadPlanResponse")
  public OdiStopLoadPlanResponse invokeStopLoadPlan(@WebParam(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/",
      partName="part1", name="OdiStopLoadPlanRequest")
    OdiStopLoadPlanRequest part1);

  @WebMethod(action="xmlns.oracle.com/odi/OdiInvoke/invokeRestartLoadPlan")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="xmlns.oracle.com/odi/OdiInvoke/invokeRestartLoadPlan",
    output="xmlns.oracle.com/odi/OdiInvoke/requestPortType/invokeRestartLoadPlanResponse")
  @WebResult(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/", partName="part1",
    name="OdiRestartLoadPlanResponse")
  public OdiRestartLoadPlanResponse invokeRestartLoadPlan(@WebParam(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/",
      partName="part1", name="OdiRestartLoadPlanRequest")
    OdiRestartLoadPlanRequest part1);

  @WebMethod(action="xmlns.oracle.com/odi/OdiInvoke/getLoadPlanStatus")
  @SOAPBinding(parameterStyle=ParameterStyle.BARE)
  @Action(input="xmlns.oracle.com/odi/OdiInvoke/getLoadPlanStatus",
    output="xmlns.oracle.com/odi/OdiInvoke/requestPortType/getLoadPlanStatusResponse")
  @WebResult(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/", partName="part1",
    name="OdiGetLoadPlanStatusResponse")
  public OdiGetLoadPlanStatusResponse getLoadPlanStatus(@WebParam(targetNamespace="xmlns.oracle.com/odi/OdiInvoke/",
      partName="part1", name="OdiGetLoadPlanStatusRequest")
    OdiGetLoadPlanStatusRequest part1);
}
