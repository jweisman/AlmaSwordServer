//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.23 at 12:53:34 PM IDT 
//


package com.exlibrisgroup.almarestmodels.userdeposits;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.exlibrisgroup.almarestmodels.userdeposits package. 
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

    private final static QName _UserDepositss_QNAME = new QName("", "user_depositss");
    private final static QName _UserDeposit_QNAME = new QName("", "user_deposit");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.exlibrisgroup.almarestmodels.userdeposits
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserDeposits }
     * 
     */
    public UserDeposits createUserDeposits() {
        return new UserDeposits();
    }

    /**
     * Create an instance of {@link UserDeposit }
     * 
     */
    public UserDeposit createUserDeposit() {
        return new UserDeposit();
    }

    /**
     * Create an instance of {@link Note }
     * 
     */
    public Note createNote() {
        return new Note();
    }

    /**
     * Create an instance of {@link Notes }
     * 
     */
    public Notes createNotes() {
        return new Notes();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserDeposits }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "user_depositss")
    public JAXBElement<UserDeposits> createUserDepositss(UserDeposits value) {
        return new JAXBElement<UserDeposits>(_UserDepositss_QNAME, UserDeposits.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserDeposit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "user_deposit")
    public JAXBElement<UserDeposit> createUserDeposit(UserDeposit value) {
        return new JAXBElement<UserDeposit>(_UserDeposit_QNAME, UserDeposit.class, null, value);
    }

}
