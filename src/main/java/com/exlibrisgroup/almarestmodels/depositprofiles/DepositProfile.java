//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.24 at 03:37:41 PM IDT 
//


package com.exlibrisgroup.almarestmodels.depositprofiles;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * Deposit profile object.
 * 
 * <p>Java class for deposit_profile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deposit_profile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="instructions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="target_format">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="desc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="collection_assignment">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="desc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="access_rights_policy_assignment" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="desc" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="library">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="desc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="approval_method" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="unsuppress_on_approval" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="user_groups" type="{}user_groups" minOccurs="0"/>
 *       &lt;/all>
 *       &lt;attribute name="link" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deposit_profile", propOrder = {

})
@XmlRootElement(name = "deposit_profile")
public class DepositProfile {

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String name;
    protected Boolean active;
    protected String description;
    protected String instructions;
    @XmlElement(name = "target_format", required = true)
    protected DepositProfile.TargetFormat targetFormat;
    @XmlElement(name = "collection_assignment", required = true)
    protected DepositProfile.CollectionAssignment collectionAssignment;
    @XmlElement(name = "access_rights_policy_assignment")
    protected DepositProfile.AccessRightsPolicyAssignment accessRightsPolicyAssignment;
    @XmlElement(required = true)
    protected DepositProfile.Library library;
    @XmlElement(name = "approval_method", required = true)
    protected String approvalMethod;
    @XmlElement(name = "unsuppress_on_approval")
    protected boolean unsuppressOnApproval;
    @XmlElement(name = "user_groups")
    protected UserGroups userGroups;
    @XmlAttribute(name = "link")
    protected String link;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the active property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActive(Boolean value) {
        this.active = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the instructions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Sets the value of the instructions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstructions(String value) {
        this.instructions = value;
    }

    /**
     * Gets the value of the targetFormat property.
     * 
     * @return
     *     possible object is
     *     {@link DepositProfile.TargetFormat }
     *     
     */
    public DepositProfile.TargetFormat getTargetFormat() {
        return targetFormat;
    }

    /**
     * Sets the value of the targetFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositProfile.TargetFormat }
     *     
     */
    public void setTargetFormat(DepositProfile.TargetFormat value) {
        this.targetFormat = value;
    }

    /**
     * Gets the value of the collectionAssignment property.
     * 
     * @return
     *     possible object is
     *     {@link DepositProfile.CollectionAssignment }
     *     
     */
    public DepositProfile.CollectionAssignment getCollectionAssignment() {
        return collectionAssignment;
    }

    /**
     * Sets the value of the collectionAssignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositProfile.CollectionAssignment }
     *     
     */
    public void setCollectionAssignment(DepositProfile.CollectionAssignment value) {
        this.collectionAssignment = value;
    }

    /**
     * Gets the value of the accessRightsPolicyAssignment property.
     * 
     * @return
     *     possible object is
     *     {@link DepositProfile.AccessRightsPolicyAssignment }
     *     
     */
    public DepositProfile.AccessRightsPolicyAssignment getAccessRightsPolicyAssignment() {
        return accessRightsPolicyAssignment;
    }

    /**
     * Sets the value of the accessRightsPolicyAssignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositProfile.AccessRightsPolicyAssignment }
     *     
     */
    public void setAccessRightsPolicyAssignment(DepositProfile.AccessRightsPolicyAssignment value) {
        this.accessRightsPolicyAssignment = value;
    }

    /**
     * Gets the value of the library property.
     * 
     * @return
     *     possible object is
     *     {@link DepositProfile.Library }
     *     
     */
    public DepositProfile.Library getLibrary() {
        return library;
    }

    /**
     * Sets the value of the library property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositProfile.Library }
     *     
     */
    public void setLibrary(DepositProfile.Library value) {
        this.library = value;
    }

    /**
     * Gets the value of the approvalMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApprovalMethod() {
        return approvalMethod;
    }

    /**
     * Sets the value of the approvalMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApprovalMethod(String value) {
        this.approvalMethod = value;
    }

    /**
     * Gets the value of the unsuppressOnApproval property.
     * 
     */
    public boolean isUnsuppressOnApproval() {
        return unsuppressOnApproval;
    }

    /**
     * Sets the value of the unsuppressOnApproval property.
     * 
     */
    public void setUnsuppressOnApproval(boolean value) {
        this.unsuppressOnApproval = value;
    }

    /**
     * Gets the value of the userGroups property.
     * 
     * @return
     *     possible object is
     *     {@link UserGroups }
     *     
     */
    public UserGroups getUserGroups() {
        return userGroups;
    }

    /**
     * Sets the value of the userGroups property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserGroups }
     *     
     */
    public void setUserGroups(UserGroups value) {
        this.userGroups = value;
    }

    /**
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLink(String value) {
        this.link = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="desc" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class AccessRightsPolicyAssignment {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "desc", required = true)
        protected String desc;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the desc property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDesc() {
            return desc;
        }

        /**
         * Sets the value of the desc property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDesc(String value) {
            this.desc = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="desc" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class CollectionAssignment {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "desc")
        protected String desc;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the desc property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDesc() {
            return desc;
        }

        /**
         * Sets the value of the desc property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDesc(String value) {
            this.desc = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="desc" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Library {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "desc")
        protected String desc;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the desc property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDesc() {
            return desc;
        }

        /**
         * Sets the value of the desc property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDesc(String value) {
            this.desc = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="desc" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class TargetFormat {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "desc")
        protected String desc;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the desc property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDesc() {
            return desc;
        }

        /**
         * Sets the value of the desc property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDesc(String value) {
            this.desc = value;
        }

    }

}
