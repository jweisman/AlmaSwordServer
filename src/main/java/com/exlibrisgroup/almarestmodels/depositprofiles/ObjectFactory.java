//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.24 at 03:37:41 PM IDT 
//


package com.exlibrisgroup.almarestmodels.depositprofiles;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.exlibrisgroup.almarestmodels.depositprofiles package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.exlibrisgroup.almarestmodels.depositprofiles
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DepositProfile }
     * 
     */
    public DepositProfile createDepositProfile() {
        return new DepositProfile();
    }

    /**
     * Create an instance of {@link DepositProfile.TargetFormat }
     * 
     */
    public DepositProfile.TargetFormat createDepositProfileTargetFormat() {
        return new DepositProfile.TargetFormat();
    }

    /**
     * Create an instance of {@link DepositProfile.CollectionAssignment }
     * 
     */
    public DepositProfile.CollectionAssignment createDepositProfileCollectionAssignment() {
        return new DepositProfile.CollectionAssignment();
    }

    /**
     * Create an instance of {@link DepositProfile.AccessRightsPolicyAssignment }
     * 
     */
    public DepositProfile.AccessRightsPolicyAssignment createDepositProfileAccessRightsPolicyAssignment() {
        return new DepositProfile.AccessRightsPolicyAssignment();
    }

    /**
     * Create an instance of {@link DepositProfile.Library }
     * 
     */
    public DepositProfile.Library createDepositProfileLibrary() {
        return new DepositProfile.Library();
    }

    /**
     * Create an instance of {@link UserGroups }
     * 
     */
    public UserGroups createUserGroups() {
        return new UserGroups();
    }

    /**
     * Create an instance of {@link DepositProfiles }
     * 
     */
    public DepositProfiles createDepositProfiles() {
        return new DepositProfiles();
    }

    /**
     * Create an instance of {@link UserGroup }
     * 
     */
    public UserGroup createUserGroup() {
        return new UserGroup();
    }

}