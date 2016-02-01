//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.01.21 at 05:00:46 PM IST 
//


package com.exlibrisgroup.almarestmodels.representation;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.exlibrisgroup.almarestmodels.representation package. 
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

    private final static QName _Representation_QNAME = new QName("", "representation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.exlibrisgroup.almarestmodels.representation
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Representation }
     * 
     */
    public Representation createRepresentation() {
        return new Representation();
    }

    /**
     * Create an instance of {@link Representation.Library }
     * 
     */
    public Representation.Library createRepresentationLibrary() {
        return new Representation.Library();
    }

    /**
     * Create an instance of {@link Representation.UsageType }
     * 
     */
    public Representation.UsageType createRepresentationUsageType() {
        return new Representation.UsageType();
    }

    /**
     * Create an instance of {@link Representation.Repository }
     * 
     */
    public Representation.Repository createRepresentationRepository() {
        return new Representation.Repository();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Representation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "representation")
    public JAXBElement<Representation> createRepresentation(Representation value) {
        return new JAXBElement<Representation>(_Representation_QNAME, Representation.class, null, value);
    }

}