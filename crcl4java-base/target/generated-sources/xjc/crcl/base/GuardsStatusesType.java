//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.21 at 09:41:19 AM EDT 
//


package crcl.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 GuardsStatusesType is derived from DataThingType.
 *                 An instance of GuardsStatusesType has the following elements:
 *                 Name (inherited, optional)
 *                 GuardsStatus (multiple).
 *  
 *                 Each GuardsStatus element gives the status of one sensor. A robot
 *                 may be associated with any number of internal or external sensors.
 *                 Any custom named internal variable could also be reported with 
 *                 the interface.
 *             
 * 
 * <p>Java class for GuardsStatusesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GuardsStatusesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}DataThingType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Guard" type="{}GuardType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="TriggerCount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="TriggerStopTimeMicros" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="TriggerValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="TriggerPose" type="{}PoseType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuardsStatusesType", propOrder = {
    "guard",
    "triggerCount",
    "triggerStopTimeMicros",
    "triggerValue",
    "triggerPose"
})
public class GuardsStatusesType
    extends DataThingType
{

    @XmlElement(name = "Guard")
    protected List<GuardType> guard;
    @XmlElement(name = "TriggerCount")
    protected int triggerCount;
    @XmlElement(name = "TriggerStopTimeMicros")
    protected long triggerStopTimeMicros;
    @XmlElement(name = "TriggerValue")
    protected Double triggerValue;
    @XmlElement(name = "TriggerPose")
    protected PoseType triggerPose;

    /**
     * Gets the value of the guard property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guard property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGuard().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GuardType }
     * 
     * 
     */
    public List<GuardType> getGuard() {
        if (guard == null) {
            guard = new ArrayList<GuardType>();
        }
        return this.guard;
    }

    /**
     * Gets the value of the triggerCount property.
     * 
     */
    public int getTriggerCount() {
        return triggerCount;
    }

    /**
     * Sets the value of the triggerCount property.
     * 
     */
    public void setTriggerCount(int value) {
        this.triggerCount = value;
    }

    /**
     * Gets the value of the triggerStopTimeMicros property.
     * 
     */
    public long getTriggerStopTimeMicros() {
        return triggerStopTimeMicros;
    }

    /**
     * Sets the value of the triggerStopTimeMicros property.
     * 
     */
    public void setTriggerStopTimeMicros(long value) {
        this.triggerStopTimeMicros = value;
    }

    /**
     * Gets the value of the triggerValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTriggerValue() {
        return triggerValue;
    }

    /**
     * Sets the value of the triggerValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTriggerValue(Double value) {
        this.triggerValue = value;
    }

    /**
     * Gets the value of the triggerPose property.
     * 
     * @return
     *     possible object is
     *     {@link PoseType }
     *     
     */
    public PoseType getTriggerPose() {
        return triggerPose;
    }

    /**
     * Sets the value of the triggerPose property.
     * 
     * @param value
     *     allowed object is
     *     {@link PoseType }
     *     
     */
    public void setTriggerPose(PoseType value) {
        this.triggerPose = value;
    }

}
