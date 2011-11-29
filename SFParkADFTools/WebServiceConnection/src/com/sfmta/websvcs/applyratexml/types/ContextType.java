
package com.sfmta.websvcs.applyratexml.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContextType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContextType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="ContextName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ContextCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContextType", propOrder = {

})
public class ContextType {

    @XmlElement(name = "ContextName", required = true)
    protected String contextName;
    @XmlElement(name = "ContextCode", required = true)
    protected String contextCode;

    /**
     * Gets the value of the contextName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContextName() {
        return contextName;
    }

    /**
     * Sets the value of the contextName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContextName(String value) {
        this.contextName = value;
    }

    /**
     * Gets the value of the contextCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContextCode() {
        return contextCode;
    }

    /**
     * Sets the value of the contextCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContextCode(String value) {
        this.contextCode = value;
    }

}
