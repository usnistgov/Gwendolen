//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.21 at 09:41:19 AM EDT 
//


package crcl.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 JointLimitType is derived from DataThingType.
 *                 JointLimitType reports the limits of one joint.
 *                 An instance of JointStatusType has the following elements:
 *                 Name (inherited, optional)
 *                 JointNumber
 *                 JointMinPosition (optional)
 *                 JointMaxPosition (optional)
 *                 JointMaxTorqueOrForce (optional)
 *                 JointMaxVelocity (optional).
 * 
 *             
 * 
 * <p>Java class for JointLimitType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JointLimitType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}DataThingType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="JointNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="JointMinPosition" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="JointMaxPosition" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="JointMaxTorqueOrForce" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="JointMaxVelocity" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JointLimitType", propOrder = {
    "jointNumber",
    "jointMinPosition",
    "jointMaxPosition",
    "jointMaxTorqueOrForce",
    "jointMaxVelocity"
})
public class JointLimitType
    extends DataThingType
{

    @XmlElement(name = "JointNumber")
    protected int jointNumber;
    @XmlElement(name = "JointMinPosition")
    protected Double jointMinPosition;
    @XmlElement(name = "JointMaxPosition")
    protected Double jointMaxPosition;
    @XmlElement(name = "JointMaxTorqueOrForce")
    protected Double jointMaxTorqueOrForce;
    @XmlElement(name = "JointMaxVelocity")
    protected Double jointMaxVelocity;

    /**
     * Gets the value of the jointNumber property.
     * 
     */
    public int getJointNumber() {
        return jointNumber;
    }

    /**
     * Sets the value of the jointNumber property.
     * 
     */
    public void setJointNumber(int value) {
        this.jointNumber = value;
    }

    /**
     * Gets the value of the jointMinPosition property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getJointMinPosition() {
        return jointMinPosition;
    }

    /**
     * Sets the value of the jointMinPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setJointMinPosition(Double value) {
        this.jointMinPosition = value;
    }

    /**
     * Gets the value of the jointMaxPosition property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getJointMaxPosition() {
        return jointMaxPosition;
    }

    /**
     * Sets the value of the jointMaxPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setJointMaxPosition(Double value) {
        this.jointMaxPosition = value;
    }

    /**
     * Gets the value of the jointMaxTorqueOrForce property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getJointMaxTorqueOrForce() {
        return jointMaxTorqueOrForce;
    }

    /**
     * Sets the value of the jointMaxTorqueOrForce property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setJointMaxTorqueOrForce(Double value) {
        this.jointMaxTorqueOrForce = value;
    }

    /**
     * Gets the value of the jointMaxVelocity property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getJointMaxVelocity() {
        return jointMaxVelocity;
    }

    /**
     * Sets the value of the jointMaxVelocity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setJointMaxVelocity(Double value) {
        this.jointMaxVelocity = value;
    }

}