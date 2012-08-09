
package com.sfmta.websvcs.odi11gws.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StartLoadPlanRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StartLoadPlanRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LoadPlanName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Context" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Keywords" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LoadPlanStartupParameters" type="{xmlns.oracle.com/odi/OdiInvoke/}VariableType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StartLoadPlanRequestType", propOrder = {
    "loadPlanName",
    "context",
    "keywords",
    "loadPlanStartupParameters"
})
public class StartLoadPlanRequestType {

    @XmlElement(name = "LoadPlanName", required = true)
    protected String loadPlanName;
    @XmlElement(name = "Context", required = true)
    protected String context;
    @XmlElement(name = "Keywords")
    protected String keywords;
    @XmlElement(name = "LoadPlanStartupParameters")
    protected List<VariableType> loadPlanStartupParameters;

    /**
     * Gets the value of the loadPlanName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoadPlanName() {
        return loadPlanName;
    }

    /**
     * Sets the value of the loadPlanName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoadPlanName(String value) {
        this.loadPlanName = value;
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
     * Gets the value of the loadPlanStartupParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the loadPlanStartupParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLoadPlanStartupParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VariableType }
     * 
     * 
     */
    public List<VariableType> getLoadPlanStartupParameters() {
        if (loadPlanStartupParameters == null) {
            loadPlanStartupParameters = new ArrayList<VariableType>();
        }
        return this.loadPlanStartupParameters;
    }

}
