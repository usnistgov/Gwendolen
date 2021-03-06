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
 *                 PoseAndSetType is derived from PoseType. 
 *                 An instance of PoseAndSetType has the following elements:
 *                 Name (inherited, optional)
 *                 Point (inherited)
 *                 XAxis (inherited)
 *                 ZAxis  (inherited)
 *                 Coordinated
 *                 TransSpeed (optional)
 *                 RotSpeed (optional)
 *                 TransAccel (optional)
 *                 RotAccel (optional)
 *                 Tolerance (optional).
 * 
 *                 PoseAndSetType is used for waypoints of move commands. The
 *                 TransSpeed and TransAccel elements are the target translational
 *                 speed and acceleration for the controlled point as it moves to the
 *                 given pose. The RotSpeed and RotAccel elements are the target
 *                 rotational speed and acceleration for the single axis rotation
 *                 required to move from the initial pose to the target pose. The
 *                 Tolerance is the tolerance for the given pose. The TransSpeed,
 *                 TransAccel, RotSpeed, RotAccel, and Tolerance temporarily override
 *                 any set values. The set values apply again once the given pose is
 *                 reached. If the Coordinated element is set to true, translation and
 *                 rotation should finish simultaneously. If Coordinated is false,
 *                 either translation or rotation may finish first.
 *         
 *             
 * 
 * <p>Java class for PoseAndSetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PoseAndSetType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}PoseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Coordinated" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="TransSpeed" type="{}TransSpeedType" minOccurs="0"/&gt;
 *         &lt;element name="RotSpeed" type="{}RotSpeedType" minOccurs="0"/&gt;
 *         &lt;element name="TransAccel" type="{}TransAccelType" minOccurs="0"/&gt;
 *         &lt;element name="RotAccel" type="{}RotAccelType" minOccurs="0"/&gt;
 *         &lt;element name="Tolerance" type="{}PoseToleranceType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PoseAndSetType", propOrder = {
    "coordinated",
    "transSpeed",
    "rotSpeed",
    "transAccel",
    "rotAccel",
    "tolerance"
})
public class PoseAndSetType
    extends PoseType
{

    @XmlElement(name = "Coordinated")
    protected boolean coordinated;
    @XmlElement(name = "TransSpeed")
    protected TransSpeedType transSpeed;
    @XmlElement(name = "RotSpeed")
    protected RotSpeedType rotSpeed;
    @XmlElement(name = "TransAccel")
    protected TransAccelType transAccel;
    @XmlElement(name = "RotAccel")
    protected RotAccelType rotAccel;
    @XmlElement(name = "Tolerance")
    protected PoseToleranceType tolerance;

    /**
     * Gets the value of the coordinated property.
     * 
     */
    public boolean isCoordinated() {
        return coordinated;
    }

    /**
     * Sets the value of the coordinated property.
     * 
     */
    public void setCoordinated(boolean value) {
        this.coordinated = value;
    }

    /**
     * Gets the value of the transSpeed property.
     * 
     * @return
     *     possible object is
     *     {@link TransSpeedType }
     *     
     */
    public TransSpeedType getTransSpeed() {
        return transSpeed;
    }

    /**
     * Sets the value of the transSpeed property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransSpeedType }
     *     
     */
    public void setTransSpeed(TransSpeedType value) {
        this.transSpeed = value;
    }

    /**
     * Gets the value of the rotSpeed property.
     * 
     * @return
     *     possible object is
     *     {@link RotSpeedType }
     *     
     */
    public RotSpeedType getRotSpeed() {
        return rotSpeed;
    }

    /**
     * Sets the value of the rotSpeed property.
     * 
     * @param value
     *     allowed object is
     *     {@link RotSpeedType }
     *     
     */
    public void setRotSpeed(RotSpeedType value) {
        this.rotSpeed = value;
    }

    /**
     * Gets the value of the transAccel property.
     * 
     * @return
     *     possible object is
     *     {@link TransAccelType }
     *     
     */
    public TransAccelType getTransAccel() {
        return transAccel;
    }

    /**
     * Sets the value of the transAccel property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransAccelType }
     *     
     */
    public void setTransAccel(TransAccelType value) {
        this.transAccel = value;
    }

    /**
     * Gets the value of the rotAccel property.
     * 
     * @return
     *     possible object is
     *     {@link RotAccelType }
     *     
     */
    public RotAccelType getRotAccel() {
        return rotAccel;
    }

    /**
     * Sets the value of the rotAccel property.
     * 
     * @param value
     *     allowed object is
     *     {@link RotAccelType }
     *     
     */
    public void setRotAccel(RotAccelType value) {
        this.rotAccel = value;
    }

    /**
     * Gets the value of the tolerance property.
     * 
     * @return
     *     possible object is
     *     {@link PoseToleranceType }
     *     
     */
    public PoseToleranceType getTolerance() {
        return tolerance;
    }

    /**
     * Sets the value of the tolerance property.
     * 
     * @param value
     *     allowed object is
     *     {@link PoseToleranceType }
     *     
     */
    public void setTolerance(PoseToleranceType value) {
        this.tolerance = value;
    }

}
