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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 *                 EnableGripperType is derived from MiddleCommandType.
 *                 An instance of EnableGripperType has the following elements:
 *                 Name (inherited, optional)
 *                 CommandID (inherited)
 *                 GripperId
 *                 GripperOptions (optional).
 * 
 *                 This enables the reporting of a sensor.
 *             
 * 
 * <p>Java class for EnableGripperType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EnableGripperType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}MiddleCommandType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GripperName" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN"/&gt;
 *         &lt;element name="GripperOption" type="{}ParameterSettingType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnableGripperType", propOrder = {
    "gripperName",
    "gripperOption"
})
public class EnableGripperType
    extends MiddleCommandType
{

    @XmlElement(name = "GripperName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String gripperName;
    @XmlElement(name = "GripperOption")
    protected List<ParameterSettingType> gripperOption;

    /**
     * Gets the value of the gripperName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGripperName() {
        return gripperName;
    }

    /**
     * Sets the value of the gripperName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGripperName(String value) {
        this.gripperName = value;
    }

    /**
     * Gets the value of the gripperOption property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the gripperOption property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGripperOption().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParameterSettingType }
     * 
     * 
     */
    public List<ParameterSettingType> getGripperOption() {
        if (gripperOption == null) {
            gripperOption = new ArrayList<ParameterSettingType>();
        }
        return this.gripperOption;
    }

}
