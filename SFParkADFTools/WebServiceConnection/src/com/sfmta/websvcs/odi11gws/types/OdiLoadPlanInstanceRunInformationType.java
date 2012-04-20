
package com.sfmta.websvcs.odi11gws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OdiLoadPlanInstanceRunInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OdiLoadPlanInstanceRunInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OdiLoadPlanInstanceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="RunCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="MasterRepositoryId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MasterRepositoryTimestamp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OdiLoadPlanInstanceRunInformationType", propOrder = {
    "odiLoadPlanInstanceId",
    "runCount",
    "masterRepositoryId",
    "masterRepositoryTimestamp"
})
public class OdiLoadPlanInstanceRunInformationType {

    @XmlElement(name = "OdiLoadPlanInstanceId")
    protected long odiLoadPlanInstanceId;
    @XmlElement(name = "RunCount")
    protected long runCount;
    @XmlElement(name = "MasterRepositoryId")
    protected int masterRepositoryId;
    @XmlElement(name = "MasterRepositoryTimestamp", required = true)
    protected String masterRepositoryTimestamp;

    /**
     * Gets the value of the odiLoadPlanInstanceId property.
     * 
     */
    public long getOdiLoadPlanInstanceId() {
        return odiLoadPlanInstanceId;
    }

    /**
     * Sets the value of the odiLoadPlanInstanceId property.
     * 
     */
    public void setOdiLoadPlanInstanceId(long value) {
        this.odiLoadPlanInstanceId = value;
    }

    /**
     * Gets the value of the runCount property.
     * 
     */
    public long getRunCount() {
        return runCount;
    }

    /**
     * Sets the value of the runCount property.
     * 
     */
    public void setRunCount(long value) {
        this.runCount = value;
    }

    /**
     * Gets the value of the masterRepositoryId property.
     * 
     */
    public int getMasterRepositoryId() {
        return masterRepositoryId;
    }

    /**
     * Sets the value of the masterRepositoryId property.
     * 
     */
    public void setMasterRepositoryId(int value) {
        this.masterRepositoryId = value;
    }

    /**
     * Gets the value of the masterRepositoryTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMasterRepositoryTimestamp() {
        return masterRepositoryTimestamp;
    }

    /**
     * Sets the value of the masterRepositoryTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMasterRepositoryTimestamp(String value) {
        this.masterRepositoryTimestamp = value;
    }

}
