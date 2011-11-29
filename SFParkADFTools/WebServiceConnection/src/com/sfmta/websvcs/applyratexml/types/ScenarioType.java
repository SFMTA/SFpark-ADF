
package com.sfmta.websvcs.applyratexml.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScenarioType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScenarioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="ScenName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ScenVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScenarioType", propOrder = {

})
public class ScenarioType {

    @XmlElement(name = "ScenName", required = true)
    protected String scenName;
    @XmlElement(name = "ScenVersion", required = true)
    protected String scenVersion;

    /**
     * Gets the value of the scenName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScenName() {
        return scenName;
    }

    /**
     * Sets the value of the scenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScenName(String value) {
        this.scenName = value;
    }

    /**
     * Gets the value of the scenVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScenVersion() {
        return scenVersion;
    }

    /**
     * Sets the value of the scenVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScenVersion(String value) {
        this.scenVersion = value;
    }

}
