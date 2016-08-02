//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.23 at 12:53:34 PM IDT 
//


package com.exlibrisgroup.almarestmodels.userdeposits;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Deposit activity object.
 * 
 * <p>Java class for user_deposit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="user_deposit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="user_primary_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deposit_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deposit_profile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="notes" type="{}notes" minOccurs="0"/>
 *         &lt;element name="mms_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rep_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="delivery_url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="submission_date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="modification_date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
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
@XmlType(name = "user_deposit", propOrder = {

})
@XmlRootElement(name = "user_deposit")
public class UserDeposit {

    @XmlElement(name = "user_primary_id", required = true)
    protected String userPrimaryId;
    @XmlElement(name = "deposit_id", required = true)
    protected String depositId;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(name = "deposit_profile", required = true)
    protected String depositProfile;
    @XmlElement(required = true)
    protected String status;
    protected Notes notes;
    @XmlElement(name = "mms_id", required = true)
    protected String mmsId;
    @XmlElement(name = "rep_id", required = true)
    protected String repId;
    @XmlElement(name = "delivery_url", required = true)
    protected String deliveryUrl;
    @XmlElement(name = "submission_date", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar submissionDate;
    @XmlElement(name = "modification_date")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar modificationDate;
    @XmlAttribute(name = "link")
    protected String link;

    /**
     * Gets the value of the userPrimaryId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPrimaryId() {
        return userPrimaryId;
    }

    /**
     * Sets the value of the userPrimaryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPrimaryId(String value) {
        this.userPrimaryId = value;
    }

    /**
     * Gets the value of the depositId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositId() {
        return depositId;
    }

    /**
     * Sets the value of the depositId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositId(String value) {
        this.depositId = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the depositProfile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositProfile() {
        return depositProfile;
    }

    /**
     * Sets the value of the depositProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositProfile(String value) {
        this.depositProfile = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link Notes }
     *     
     */
    public Notes getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Notes }
     *     
     */
    public void setNotes(Notes value) {
        this.notes = value;
    }

    /**
     * Gets the value of the mmsId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMmsId() {
        return mmsId;
    }

    /**
     * Sets the value of the mmsId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMmsId(String value) {
        this.mmsId = value;
    }

    /**
     * Gets the value of the repId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepId() {
        return repId;
    }

    /**
     * Sets the value of the repId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepId(String value) {
        this.repId = value;
    }

    /**
     * Gets the value of the deliveryUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryUrl() {
        return deliveryUrl;
    }

    /**
     * Sets the value of the deliveryUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryUrl(String value) {
        this.deliveryUrl = value;
    }

    /**
     * Gets the value of the submissionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSubmissionDate() {
        return submissionDate;
    }

    /**
     * Sets the value of the submissionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSubmissionDate(XMLGregorianCalendar value) {
        this.submissionDate = value;
    }

    /**
     * Gets the value of the modificationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the value of the modificationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModificationDate(XMLGregorianCalendar value) {
        this.modificationDate = value;
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

}
