
package com.sfmta.websvcs.applyratexml.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sfmta.websvcs.applyratexml.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _InvokeScenarioResponse_QNAME = new QName("xmlns.oracle.com/odi/OdiInvoke/", "invokeScenarioResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sfmta.websvcs.applyratexml.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CommandResultType }
     * 
     */
    public CommandResultType createCommandResultType() {
        return new CommandResultType();
    }

    /**
     * Create an instance of {@link InvokeScenarioRequest }
     * 
     */
    public InvokeScenarioRequest createInvokeScenarioRequest() {
        return new InvokeScenarioRequest();
    }

    /**
     * Create an instance of {@link RepositoryConnectionType }
     * 
     */
    public RepositoryConnectionType createRepositoryConnectionType() {
        return new RepositoryConnectionType();
    }

    /**
     * Create an instance of {@link ScenarioCommandType }
     * 
     */
    public ScenarioCommandType createScenarioCommandType() {
        return new ScenarioCommandType();
    }

    /**
     * Create an instance of {@link AgentType }
     * 
     */
    public AgentType createAgentType() {
        return new AgentType();
    }

    /**
     * Create an instance of {@link VariableType }
     * 
     */
    public VariableType createVariableType() {
        return new VariableType();
    }

    /**
     * Create an instance of {@link ContextType }
     * 
     */
    public ContextType createContextType() {
        return new ContextType();
    }

    /**
     * Create an instance of {@link ScenarioType }
     * 
     */
    public ScenarioType createScenarioType() {
        return new ScenarioType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommandResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "xmlns.oracle.com/odi/OdiInvoke/", name = "invokeScenarioResponse")
    public JAXBElement<CommandResultType> createInvokeScenarioResponse(CommandResultType value) {
        return new JAXBElement<CommandResultType>(_InvokeScenarioResponse_QNAME, CommandResultType.class, null, value);
    }

}
