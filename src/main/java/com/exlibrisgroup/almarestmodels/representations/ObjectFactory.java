//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.23 at 12:56:31 PM IDT 
//


package com.exlibrisgroup.almarestmodels.representations;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.exlibrisgroup.almarestmodels.representations package. 
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

    private final static QName _Representations_QNAME = new QName("", "representations");
    private final static QName _Representation_QNAME = new QName("", "representation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.exlibrisgroup.almarestmodels.representations
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
     * Create an instance of {@link Representations }
     * 
     */
    public Representations createRepresentations() {
        return new Representations();
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
     * Create an instance of {@link Representation.EntityType }
     * 
     */
    public Representation.EntityType createRepresentationEntityType() {
        return new Representation.EntityType();
    }

    /**
     * Create an instance of {@link Representation.AccessRightsPolicyId }
     * 
     */
    public Representation.AccessRightsPolicyId createRepresentationAccessRightsPolicyId() {
        return new Representation.AccessRightsPolicyId();
    }

    /**
     * Create an instance of {@link Representation.Repository }
     * 
     */
    public Representation.Repository createRepresentationRepository() {
        return new Representation.Repository();
    }

    /**
     * Create an instance of {@link Representation.Files }
     * 
     */
    public Representation.Files createRepresentationFiles() {
        return new Representation.Files();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Representations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "representations")
    public JAXBElement<Representations> createRepresentations(Representations value) {
        return new JAXBElement<Representations>(_Representations_QNAME, Representations.class, null, value);
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