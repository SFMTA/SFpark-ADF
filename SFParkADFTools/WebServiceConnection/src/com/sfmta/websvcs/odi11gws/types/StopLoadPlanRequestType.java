
package com.sfmta.websvcs.odi11gws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StopLoadPlanRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StopLoadPlanRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LoadPlanInstanceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="LoadPlanInstanceRunCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="StopLevel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StopLoadPlanRequestType", propOrder = {
    "loadPlanInstanceId",
    "loadPlanInstanceRunCount",
    "stopLevel"
})
public class StopLoadPlanRequestType {

    @XmlElement(name = "LoadPlanInstanceId")
    protected long loadPlanInstanceId;
    @XmlElement(name = "LoadPlanInstanceRunCount")
    protected long loadPlanInstanceRunCount;
    @XmlElement(name = "StopLevel", required = true)
    protected String stopLevel;

    /**
     * Gets the value of the loadPlanInstanceId property.
     * 
     */
    public long getLoadPlanInstanceId() {
        return loadPlanInstanceId;
    }

    /**
     * Sets the value of the loadPlanInstanceId property.
     * 
     */
    public void setLoadPlanInstanceId(long value) {
        this.loadPlanInstanceId = value;
    }

    /**
     * Gets the value of the loadPlanInstanceRunCount property.
     * 
     */
    public long getLoadPlanInstanceRunCount() {
        return loadPlanInstanceRunCount;
    }

    /**
     * Sets the value of the loadPlanInstanceRunCount property.
     * 
     */
    public void setLoadPlanInstanceRunCount(long value) {
        this.loadPlanInstanceRunCount = value;
    }

    /**
     * Gets the value of the stopLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStopLevel() {
        return stopLevel;
    }

    /**
     * Sets the value of the stopLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStopLevel(String value) {
        this.stopLevel = value;
    }

}
