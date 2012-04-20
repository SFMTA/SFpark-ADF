
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
 *         &lt;element name="Request" type="{xmlns.oracle.com/odi/OdiInvoke/}ScenarioRequestType"/>
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
@XmlRootElement(name = "OdiStartScenWithCallbackRequest")
public class OdiStartScenWithCallbackRequest {

    @XmlElement(name = "Credentials", required = true)
    protected OdiCredentialType credentials;
    @XmlElement(name = "Request", required = true)
    protected ScenarioRequestType request;

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
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link ScenarioRequestType }
     *     
     */
    public ScenarioRequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScenarioRequestType }
     *     
     */
    public void setRequest(ScenarioRequestType value) {
        this.request = value;
    }

}
