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
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 MoveToType is derived from MiddleCommandType.
 *                 An instance of MoveToType has the following elements:
 *                 Name (inherited, optional)
 *                 CommandID (inherited)
 *                 MoveStraight
 *                 EndPosition.
 * 
 *                 EndPosition is a Pose to which the robot will move. If the value of
 *                 MoveStraight is true, the controlled point must be moved in a
 *                 straight line. If the value of MoveStraight is false, the
 *                 controlled point may be moved along any convenient trajectory.
 * 
 *                 The robot must reach the EndPosition within the tolerance
 *                 established (1) by the tolerance given for the pose in the
 *                 EndPosition, if there is a tolerance in the EndPosition, or if not
 *                 (2) by the most recently executed instance of
 *                 SetEndPoseToleranceType. The speed and acceleration to use are set
 *                 either in the EndPosition or by previously executed CRCL commands.
 * 
 *                 The type of EndPosition may be either PoseType or
 *                 PoseAndSetType, which is derived from PoseType.
 *             
 * 
 * <p>Java class for MoveToType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MoveToType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}MiddleCommandType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MoveStraight" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="EndPosition" type="{}PoseType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MoveToType", propOrder = {
    "moveStraight",
    "endPosition"
})
public class MoveToType
    extends MiddleCommandType
{

    @XmlElement(name = "MoveStraight")
    protected boolean moveStraight;
    @XmlElement(name = "EndPosition", required = true)
    protected PoseType endPosition;

    /**
     * Gets the value of the moveStraight property.
     * 
     */
    public boolean isMoveStraight() {
        return moveStraight;
    }

    /**
     * Sets the value of the moveStraight property.
     * 
     */
    public void setMoveStraight(boolean value) {
        this.moveStraight = value;
    }

    /**
     * Gets the value of the endPosition property.
     * 
     * @return
     *     possible object is
     *     {@link PoseType }
     *     
     */
    public PoseType getEndPosition() {
        return endPosition;
    }

    /**
     * Sets the value of the endPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link PoseType }
     *     
     */
    public void setEndPosition(PoseType value) {
        this.endPosition = value;
    }

}
