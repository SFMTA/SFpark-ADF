
package com.sfmta.websvcs.applyratexml.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RepositoryConnectionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RepositoryConnectionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="JdbcDriver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="JdbcUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="JdbcUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="JdbcPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OdiUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OdiPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WorkRepository" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RepositoryConnectionType", propOrder = {

})
public class RepositoryConnectionType {

    @XmlElement(name = "JdbcDriver")
    protected String jdbcDriver;
    @XmlElement(name = "JdbcUrl")
    protected String jdbcUrl;
    @XmlElement(name = "JdbcUser")
    protected String jdbcUser;
    @XmlElement(name = "JdbcPassword")
    protected String jdbcPassword;
    @XmlElement(name = "OdiUser", required = true)
    protected String odiUser;
    @XmlElement(name = "OdiPassword", required = true)
    protected String odiPassword;
    @XmlElement(name = "WorkRepository")
    protected String workRepository;

    /**
     * Gets the value of the jdbcDriver property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJdbcDriver() {
        return jdbcDriver;
    }

    /**
     * Sets the value of the jdbcDriver property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJdbcDriver(String value) {
        this.jdbcDriver = value;
    }

    /**
     * Gets the value of the jdbcUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * Sets the value of the jdbcUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJdbcUrl(String value) {
        this.jdbcUrl = value;
    }

    /**
     * Gets the value of the jdbcUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJdbcUser() {
        return jdbcUser;
    }

    /**
     * Sets the value of the jdbcUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJdbcUser(String value) {
        this.jdbcUser = value;
    }

    /**
     * Gets the value of the jdbcPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJdbcPassword() {
        return jdbcPassword;
    }

    /**
     * Sets the value of the jdbcPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJdbcPassword(String value) {
        this.jdbcPassword = value;
    }

    /**
     * Gets the value of the odiUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOdiUser() {
        return odiUser;
    }

    /**
     * Sets the value of the odiUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOdiUser(String value) {
        this.odiUser = value;
    }

    /**
     * Gets the value of the odiPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOdiPassword() {
        return odiPassword;
    }

    /**
     * Sets the value of the odiPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOdiPassword(String value) {
        this.odiPassword = value;
    }

    /**
     * Gets the value of the workRepository property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkRepository() {
        return workRepository;
    }

    /**
     * Sets the value of the workRepository property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkRepository(String value) {
        this.workRepository = value;
    }

}
