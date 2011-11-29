
package com.sfmta.websvcs.applyratexml.types;

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
 *         &lt;element name="RepositoryConnection" type="{xmlns.oracle.com/odi/OdiInvoke/}RepositoryConnectionType"/>
 *         &lt;element name="Command" type="{xmlns.oracle.com/odi/OdiInvoke/}ScenarioCommandType"/>
 *         &lt;element name="Agent" type="{xmlns.oracle.com/odi/OdiInvoke/}AgentType"/>
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
@XmlRootElement(name = "invokeScenarioRequest")
public class InvokeScenarioRequest {

    @XmlElement(name = "RepositoryConnection", required = true)
    protected RepositoryConnectionType repositoryConnection;
    @XmlElement(name = "Command", required = true)
    protected ScenarioCommandType command;
    @XmlElement(name = "Agent", required = true)
    protected AgentType agent;

    /**
     * Gets the value of the repositoryConnection property.
     * 
     * @return
     *     possible object is
     *     {@link RepositoryConnectionType }
     *     
     */
    public RepositoryConnectionType getRepositoryConnection() {
        return repositoryConnection;
    }

    /**
     * Sets the value of the repositoryConnection property.
     * 
     * @param value
     *     allowed object is
     *     {@link RepositoryConnectionType }
     *     
     */
    public void setRepositoryConnection(RepositoryConnectionType value) {
        this.repositoryConnection = value;
    }

    /**
     * Gets the value of the command property.
     * 
     * @return
     *     possible object is
     *     {@link ScenarioCommandType }
     *     
     */
    public ScenarioCommandType getCommand() {
        return command;
    }

    /**
     * Sets the value of the command property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScenarioCommandType }
     *     
     */
    public void setCommand(ScenarioCommandType value) {
        this.command = value;
    }

    /**
     * Gets the value of the agent property.
     * 
     * @return
     *     possible object is
     *     {@link AgentType }
     *     
     */
    public AgentType getAgent() {
        return agent;
    }

    /**
     * Sets the value of the agent property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentType }
     *     
     */
    public void setAgent(AgentType value) {
        this.agent = value;
    }

}
