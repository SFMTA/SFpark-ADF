
package com.sfmta.websvcs.applyratexml.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScenarioCommandType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScenarioCommandType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ScenName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ScenVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Context" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LogLevel" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SyncMode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="SessionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Keywords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Variables" type="{xmlns.oracle.com/odi/OdiInvoke/}VariableType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScenarioCommandType", propOrder = {
    "scenName",
    "scenVersion",
    "context",
    "logLevel",
    "syncMode",
    "sessionName",
    "keywords",
    "variables"
})
public class ScenarioCommandType {

    @XmlElement(name = "ScenName", required = true)
    protected String scenName;
    @XmlElement(name = "ScenVersion", required = true)
    protected String scenVersion;
    @XmlElement(name = "Context", required = true)
    protected String context;
    @XmlElement(name = "LogLevel", defaultValue = "5")
    protected Integer logLevel;
    @XmlElement(name = "SyncMode", defaultValue = "1")
    protected Integer syncMode;
    @XmlElement(name = "SessionName")
    protected String sessionName;
    @XmlElement(name = "Keywords")
    protected String keywords;
    @XmlElement(name = "Variables")
    protected List<VariableType> variables;

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

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContext(String value) {
        this.context = value;
    }

    /**
     * Gets the value of the logLevel property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLogLevel() {
        return logLevel;
    }

    /**
     * Sets the value of the logLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLogLevel(Integer value) {
        this.logLevel = value;
    }

    /**
     * Gets the value of the syncMode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSyncMode() {
        return syncMode;
    }

    /**
     * Sets the value of the syncMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSyncMode(Integer value) {
        this.syncMode = value;
    }

    /**
     * Gets the value of the sessionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionName() {
        return sessionName;
    }

    /**
     * Sets the value of the sessionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionName(String value) {
        this.sessionName = value;
    }

    /**
     * Gets the value of the keywords property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Sets the value of the keywords property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeywords(String value) {
        this.keywords = value;
    }

    /**
     * Gets the value of the variables property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the variables property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVariables().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VariableType }
     * 
     * 
     */
    public List<VariableType> getVariables() {
        if (variables == null) {
            variables = new ArrayList<VariableType>();
        }
        return this.variables;
    }

}
