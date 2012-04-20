
package com.sfmta.websvcs.odi11gws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Credentials" type="{xmlns.oracle.com/odi/OdiInvoke/}OdiCredentialType"/>
 *         &lt;element name="OdiStopLoadPlanRequest" type="{xmlns.oracle.com/odi/OdiInvoke/}StopLoadPlanRequestType"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "OdiStopLoadPlanRequest")
public class OdiStopLoadPlanRequest {

    @XmlElement(name = "Credentials", required = true)
    protected OdiCredentialType credentials;
    @XmlElement(name = "OdiStopLoadPlanRequest", required = true)
    protected StopLoadPlanRequestType odiStopLoadPlanRequest;

    /**
     * Gets the value of the credentials property.
     * 
     * @return
     *     possible object is
     *     {@link OdiCredentialType }
     *     
     */
    public OdiCredentialType getCredentials() {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link OdiCredentialType }
     *     
     */
    public void setCredentials(OdiCredentialType value) {
        this.credentials = value;
    }

    /**
     * Gets the value of the odiStopLoadPlanRequest property.
     * 
     * @return
     *     possible object is
     *     {@link StopLoadPlanRequestType }
     *     
     */
    public StopLoadPlanRequestType getOdiStopLoadPlanRequest() {
        return odiStopLoadPlanRequest;
    }

    /**
     * Sets the value of the odiStopLoadPlanRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link StopLoadPlanRequestType }
     *     
     */
    public void setOdiStopLoadPlanRequest(StopLoadPlanRequestType value) {
        this.odiStopLoadPlanRequest = value;
    }

}
