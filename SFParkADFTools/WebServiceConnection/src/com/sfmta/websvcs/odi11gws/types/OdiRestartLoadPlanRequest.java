
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
 *       &lt;sequence>
 *         &lt;element name="Credentials" type="{xmlns.oracle.com/odi/OdiInvoke/}OdiCredentialType"/>
 *         &lt;element name="RestartLoadPlanRequest" type="{xmlns.oracle.com/odi/OdiInvoke/}RestartLoadPlanRequestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "credentials",
    "restartLoadPlanRequest"
})
@XmlRootElement(name = "OdiRestartLoadPlanRequest")
public class OdiRestartLoadPlanRequest {

    @XmlElement(name = "Credentials", required = true)
    protected OdiCredentialType credentials;
    @XmlElement(name = "RestartLoadPlanRequest", required = true)
    protected RestartLoadPlanRequestType restartLoadPlanRequest;

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
     * Gets the value of the restartLoadPlanRequest property.
     * 
     * @return
     *     possible object is
     *     {@link RestartLoadPlanRequestType }
     *     
     */
    public RestartLoadPlanRequestType getRestartLoadPlanRequest() {
        return restartLoadPlanRequest;
    }

    /**
     * Sets the value of the restartLoadPlanRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestartLoadPlanRequestType }
     *     
     */
    public void setRestartLoadPlanRequest(RestartLoadPlanRequestType value) {
        this.restartLoadPlanRequest = value;
    }

}
