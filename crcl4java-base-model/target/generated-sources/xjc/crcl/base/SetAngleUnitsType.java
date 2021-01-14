//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.23 at 10:21:09 AM EDT 
//


package crcl.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 SetAngleUnits is derived from MiddleCommandType.
 *                 An instance of SetAngleUnits has the following elements:
 *                 Name (inherited, optional)
 *                 CommandID (inherited)
 *                 UnitName.
 * 
 *                 UnitName is a string that can be only the literals 'radian' or
 *                 'degree'. This tells the robot that all further commands
 *                 giving angle values will implicitly use the named unit.
 *             
 * 
 * <p>Java class for SetAngleUnitsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SetAngleUnitsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}MiddleCommandType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UnitName" type="{}AngleUnitEnumType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetAngleUnitsType", propOrder = {
    "unitName"
})
public class SetAngleUnitsType
    extends MiddleCommandType
{

    @XmlElement(name = "UnitName", required = true)
    @XmlSchemaType(name = "NMTOKEN")
    protected AngleUnitEnumType unitName;

    /**
     * Gets the value of the unitName property.
     * 
     * @return
     *     possible object is
     *     {@link AngleUnitEnumType }
     *     
     */
    public AngleUnitEnumType getUnitName() {
        return unitName;
    }

    /**
     * Sets the value of the unitName property.
     * 
     * @param value
     *     allowed object is
     *     {@link AngleUnitEnumType }
     *     
     */
    public void setUnitName(AngleUnitEnumType value) {
        this.unitName = value;
    }

}
