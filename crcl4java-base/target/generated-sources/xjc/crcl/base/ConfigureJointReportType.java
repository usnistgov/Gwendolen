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
 *                 ConfigureJointReportType is derived from DataThingType. 
 *                 An instance of ConfigureJointReportType has the following elements:
 *                 Name (inherited, optional)
 *                 JointNumber
 *                 ReportPosition
 *                 ReportTorqueOrForce
 *                 ReportVelocity
 * 
 *                 ConfigureJointReportType is used to specify whether and how status
 *                 reporting should be done for the joint identified by its joint
 *                 number. For each ReportXXX element, true means XXX data should be
 *                 reported and false means XXX data should not be reported.
 *             
 * 
 * <p>Java class for ConfigureJointReportType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfigureJointReportType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}DataThingType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="JointNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ReportPosition" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ReportTorqueOrForce" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ReportVelocity" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfigureJointReportType", propOrder = {
    "jointNumber",
    "reportPosition",
    "reportTorqueOrForce",
    "reportVelocity"
})
public class ConfigureJointReportType
    extends DataThingType
{

    @XmlElement(name = "JointNumber")
    protected int jointNumber;
    @XmlElement(name = "ReportPosition")
    protected boolean reportPosition;
    @XmlElement(name = "ReportTorqueOrForce")
    protected boolean reportTorqueOrForce;
    @XmlElement(name = "ReportVelocity")
    protected boolean reportVelocity;

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
     * Gets the value of the reportPosition property.
     * 
     */
    public boolean isReportPosition() {
        return reportPosition;
    }

    /**
     * Sets the value of the reportPosition property.
     * 
     */
    public void setReportPosition(boolean value) {
        this.reportPosition = value;
    }

    /**
     * Gets the value of the reportTorqueOrForce property.
     * 
     */
    public boolean isReportTorqueOrForce() {
        return reportTorqueOrForce;
    }

    /**
     * Sets the value of the reportTorqueOrForce property.
     * 
     */
    public void setReportTorqueOrForce(boolean value) {
        this.reportTorqueOrForce = value;
    }

    /**
     * Gets the value of the reportVelocity property.
     * 
     */
    public boolean isReportVelocity() {
        return reportVelocity;
    }

    /**
     * Sets the value of the reportVelocity property.
     * 
     */
    public void setReportVelocity(boolean value) {
        this.reportVelocity = value;
    }

}
