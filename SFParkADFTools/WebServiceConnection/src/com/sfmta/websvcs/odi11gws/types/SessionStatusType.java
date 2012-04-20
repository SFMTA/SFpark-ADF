
package com.sfmta.websvcs.odi11gws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SessionStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SessionStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SessionId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="SessionStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SessionReturnCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SessionMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SessionStatusType", propOrder = {
    "sessionId",
    "sessionStatus",
    "sessionReturnCode",
    "sessionMessage"
})
public class SessionStatusType {

    @XmlElement(name = "SessionId")
    protected long sessionId;
    @XmlElement(name = "SessionStatus", required = true)
    protected String sessionStatus;
    @XmlElement(name = "SessionReturnCode")
    protected String sessionReturnCode;
    @XmlElement(name = "SessionMessage")
    protected String sessionMessage;

    /**
     * Gets the value of the sessionId property.
     * 
     */
    public long getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     */
    public void setSessionId(long value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the sessionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionStatus() {
        return sessionStatus;
    }

    /**
     * Sets the value of the sessionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionStatus(String value) {
        this.sessionStatus = value;
    }

    /**
     * Gets the value of the sessionReturnCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionReturnCode() {
        return sessionReturnCode;
    }

    /**
     * Sets the value of the sessionReturnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionReturnCode(String value) {
        this.sessionReturnCode = value;
    }

    /**
     * Gets the value of the sessionMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionMessage() {
        return sessionMessage;
    }

    /**
     * Sets the value of the sessionMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionMessage(String value) {
        this.sessionMessage = value;
    }

}
