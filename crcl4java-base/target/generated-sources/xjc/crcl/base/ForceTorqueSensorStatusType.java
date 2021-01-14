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
 *                 SensorStatusType is derived from DataThingType.
 *                 SensorStatusType reports the status of one force/torque sensor.
 *                 Forces ar in Newtons, Torque in Newton-meters.
 *                 An instance of SensorStatusType has the following elements:
 *                 Name (inherited, optional)
 *                 SensorID (inherited)
 *                 SensorParameterSetting (inherited, optional)
 *                 Fx 
 *                 Fy
 *                 Fz
 *                 Tx
 *                 Ty
 *                 Tz.
 * 
 *             
 * 
 * <p>Java class for ForceTorqueSensorStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ForceTorqueSensorStatusType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}SensorStatusType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Fx" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Fy" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Fz" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Tx" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Ty" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Tz" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForceTorqueSensorStatusType", propOrder = {
    "fx",
    "fy",
    "fz",
    "tx",
    "ty",
    "tz"
})
public class ForceTorqueSensorStatusType
    extends SensorStatusType
{

    @XmlElement(name = "Fx")
    protected double fx;
    @XmlElement(name = "Fy")
    protected double fy;
    @XmlElement(name = "Fz")
    protected double fz;
    @XmlElement(name = "Tx")
    protected double tx;
    @XmlElement(name = "Ty")
    protected double ty;
    @XmlElement(name = "Tz")
    protected double tz;

    /**
     * Gets the value of the fx property.
     * 
     */
    public double getFx() {
        return fx;
    }

    /**
     * Sets the value of the fx property.
     * 
     */
    public void setFx(double value) {
        this.fx = value;
    }

    /**
     * Gets the value of the fy property.
     * 
     */
    public double getFy() {
        return fy;
    }

    /**
     * Sets the value of the fy property.
     * 
     */
    public void setFy(double value) {
        this.fy = value;
    }

    /**
     * Gets the value of the fz property.
     * 
     */
    public double getFz() {
        return fz;
    }

    /**
     * Sets the value of the fz property.
     * 
     */
    public void setFz(double value) {
        this.fz = value;
    }

    /**
     * Gets the value of the tx property.
     * 
     */
    public double getTx() {
        return tx;
    }

    /**
     * Sets the value of the tx property.
     * 
     */
    public void setTx(double value) {
        this.tx = value;
    }

    /**
     * Gets the value of the ty property.
     * 
     */
    public double getTy() {
        return ty;
    }

    /**
     * Sets the value of the ty property.
     * 
     */
    public void setTy(double value) {
        this.ty = value;
    }

    /**
     * Gets the value of the tz property.
     * 
     */
    public double getTz() {
        return tz;
    }

    /**
     * Sets the value of the tz property.
     * 
     */
    public void setTz(double value) {
        this.tz = value;
    }

}
