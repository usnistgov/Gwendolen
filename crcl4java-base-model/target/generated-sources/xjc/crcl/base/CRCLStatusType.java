//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.23 at 10:21:09 AM EDT 
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
 *                 CRCLStatusType is derived from DataThingType.
 *                 An instance of CRCLStatusType has the following elements:
 *                 Name (inherited, optional)
 *                 CommandStatus
 *                 JointStatuses (optional)
 *                 PoseStatus (optional)
 *                 GripperStatus (optional)
 *                 SettingsStatus (optional).
 * 
 *                 Status is returned periodically by the controller.
 * 
 *                 See notes at the beginning of this file regarding configuring
 *                 CRCL status messages.
 * 
 *                 GripperStatus should not be reported when there is no gripper
 *                 and should be reported when there is a gripper.
 * 
 *                 The coordinate system in which the Pose is reported is always
 *                 robot coordinates.
 * 
 *                 If CRCL status is being reported on separate channels for both
 *                 a robot and a gripper, the status reported on the robot
 *                 channel should include a Pose, while the status reported on the
 *                 gripper channel should not include a Pose.
 *             
 * 
 * <p>Java class for CRCLStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CRCLStatusType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}DataThingType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CommandStatus" type="{}CommandStatusType"/&gt;
 *         &lt;element name="JointStatuses" type="{}JointStatusesType" minOccurs="0"/&gt;
 *         &lt;element name="PoseStatus" type="{}PoseStatusType" minOccurs="0"/&gt;
 *         &lt;element name="GripperStatus" type="{}GripperStatusType" minOccurs="0"/&gt;
 *         &lt;element name="SettingsStatus" type="{}SettingsStatusType" minOccurs="0"/&gt;
 *         &lt;element name="SensorStatuses" type="{}SensorStatusesType" minOccurs="0"/&gt;
 *         &lt;element name="GuardsStatuses" type="{}GuardsStatusesType" minOccurs="0"/&gt;
 *         &lt;sequence minOccurs="0"&gt;
 *           &lt;element name="ModelStatus" type="{}ModelsStatusType" maxOccurs="unbounded"/&gt;
 *         &lt;/sequence&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CRCLStatusType", propOrder = {
    "commandStatus",
    "jointStatuses",
    "poseStatus",
    "gripperStatus",
    "settingsStatus",
    "sensorStatuses",
    "guardsStatuses",
    "modelStatus"
})
public class CRCLStatusType
    extends DataThingType
{

    @XmlElement(name = "CommandStatus", required = true)
    protected CommandStatusType commandStatus;
    @XmlElement(name = "JointStatuses")
    protected JointStatusesType jointStatuses;
    @XmlElement(name = "PoseStatus")
    protected PoseStatusType poseStatus;
    @XmlElement(name = "GripperStatus")
    protected GripperStatusType gripperStatus;
    @XmlElement(name = "SettingsStatus")
    protected SettingsStatusType settingsStatus;
    @XmlElement(name = "SensorStatuses")
    protected SensorStatusesType sensorStatuses;
    @XmlElement(name = "GuardsStatuses")
    protected GuardsStatusesType guardsStatuses;
    @XmlElement(name = "ModelStatus")
    protected List<ModelsStatusType> modelStatus;

    /**
     * Gets the value of the commandStatus property.
     * 
     * @return
     *     possible object is
     *     {@link CommandStatusType }
     *     
     */
    public CommandStatusType getCommandStatus() {
        return commandStatus;
    }

    /**
     * Sets the value of the commandStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommandStatusType }
     *     
     */
    public void setCommandStatus(CommandStatusType value) {
        this.commandStatus = value;
    }

    /**
     * Gets the value of the jointStatuses property.
     * 
     * @return
     *     possible object is
     *     {@link JointStatusesType }
     *     
     */
    public JointStatusesType getJointStatuses() {
        return jointStatuses;
    }

    /**
     * Sets the value of the jointStatuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link JointStatusesType }
     *     
     */
    public void setJointStatuses(JointStatusesType value) {
        this.jointStatuses = value;
    }

    /**
     * Gets the value of the poseStatus property.
     * 
     * @return
     *     possible object is
     *     {@link PoseStatusType }
     *     
     */
    public PoseStatusType getPoseStatus() {
        return poseStatus;
    }

    /**
     * Sets the value of the poseStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link PoseStatusType }
     *     
     */
    public void setPoseStatus(PoseStatusType value) {
        this.poseStatus = value;
    }

    /**
     * Gets the value of the gripperStatus property.
     * 
     * @return
     *     possible object is
     *     {@link GripperStatusType }
     *     
     */
    public GripperStatusType getGripperStatus() {
        return gripperStatus;
    }

    /**
     * Sets the value of the gripperStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link GripperStatusType }
     *     
     */
    public void setGripperStatus(GripperStatusType value) {
        this.gripperStatus = value;
    }

    /**
     * Gets the value of the settingsStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SettingsStatusType }
     *     
     */
    public SettingsStatusType getSettingsStatus() {
        return settingsStatus;
    }

    /**
     * Sets the value of the settingsStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettingsStatusType }
     *     
     */
    public void setSettingsStatus(SettingsStatusType value) {
        this.settingsStatus = value;
    }

    /**
     * Gets the value of the sensorStatuses property.
     * 
     * @return
     *     possible object is
     *     {@link SensorStatusesType }
     *     
     */
    public SensorStatusesType getSensorStatuses() {
        return sensorStatuses;
    }

    /**
     * Sets the value of the sensorStatuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link SensorStatusesType }
     *     
     */
    public void setSensorStatuses(SensorStatusesType value) {
        this.sensorStatuses = value;
    }

    /**
     * Gets the value of the guardsStatuses property.
     * 
     * @return
     *     possible object is
     *     {@link GuardsStatusesType }
     *     
     */
    public GuardsStatusesType getGuardsStatuses() {
        return guardsStatuses;
    }

    /**
     * Sets the value of the guardsStatuses property.
     * 
     * @param value
     *     allowed object is
     *     {@link GuardsStatusesType }
     *     
     */
    public void setGuardsStatuses(GuardsStatusesType value) {
        this.guardsStatuses = value;
    }

    /**
     * Gets the value of the modelStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modelStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModelStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModelsStatusType }
     * 
     * 
     */
    public List<ModelsStatusType> getModelStatus() {
        if (modelStatus == null) {
            modelStatus = new ArrayList<ModelsStatusType>();
        }
        return this.modelStatus;
    }

}
