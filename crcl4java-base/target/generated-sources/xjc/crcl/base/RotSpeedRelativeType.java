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
 *                 RotSpeedRelativeType is derived from RotSpeedType.
 *                 An instance of RotSpeedRelativeType has the following elements:
 *                 Name (inherited, optional)
 *                 Fraction.
 * 
 *                 Fraction is a real number that represents the fraction of the
 *                 robot's maximum rotational speed that it should use.
 *             
 * 
 * <p>Java class for RotSpeedRelativeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RotSpeedRelativeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}RotSpeedType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Fraction" type="{}FractionType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RotSpeedRelativeType", propOrder = {
    "fraction"
})
public class RotSpeedRelativeType
    extends RotSpeedType
{

    @XmlElement(name = "Fraction")
    protected double fraction;

    /**
     * Gets the value of the fraction property.
     * 
     */
    public double getFraction() {
        return fraction;
    }

    /**
     * Sets the value of the fraction property.
     * 
     */
    public void setFraction(double value) {
        this.fraction = value;
    }

}
