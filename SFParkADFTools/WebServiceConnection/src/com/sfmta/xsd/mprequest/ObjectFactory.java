
package com.sfmta.xsd.mprequest;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sfmta.xsd.mprequest package. 
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

    private final static QName _Mprequest_QNAME = new QName("http://www.sfmta.com/xsd/mprequest", "mprequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sfmta.xsd.mprequest
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Mpresponse }
     * 
     */
    public Mpresponse createMpresponse() {
        return new Mpresponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.sfmta.com/xsd/mprequest", name = "mprequest")
    public JAXBElement<String> createMprequest(String value) {
        return new JAXBElement<String>(_Mprequest_QNAME, String.class, null, value);
    }

}
