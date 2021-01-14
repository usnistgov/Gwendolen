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
 *                 TransAccelAbsoluteType is derived from TransAccelType.
 *                 An instance of TransAccelAbsoluteType has the following
 *                 elements:
 *                 Name (inherited, optional)
 *                 Setting.
 * 
 *                 Setting is a real number that represents the target
 *                 translational acceleration for the controlled point, in
 *                 current length units per second per second.
 *             
 * 
 * <p>Java class for TransAccelAbsoluteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransAccelAbsoluteType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}TransAccelType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Setting" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransAccelAbsoluteType", propOrder = {
    "setting"
})
public class TransAccelAbsoluteType
    extends TransAccelType
{

    @XmlElement(name = "Setting")
    protected double setting;

    /**
     * Gets the value of the setting property.
     * 
     */
    public double getSetting() {
        return setting;
    }

    /**
     * Sets the value of the setting property.
     * 
     */
    public void setSetting(double value) {
        this.setting = value;
    }

}
