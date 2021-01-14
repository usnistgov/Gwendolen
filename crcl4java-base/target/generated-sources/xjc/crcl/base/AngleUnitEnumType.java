//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.21 at 09:41:19 AM EDT 
//


package crcl.base;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AngleUnitEnumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AngleUnitEnumType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="degree"/&gt;
 *     &lt;enumeration value="radian"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "AngleUnitEnumType")
@XmlEnum
public enum AngleUnitEnumType {

    @XmlEnumValue("degree")
    DEGREE("degree"),
    @XmlEnumValue("radian")
    RADIAN("radian");
    private final String value;

    AngleUnitEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AngleUnitEnumType fromValue(String v) {
        for (AngleUnitEnumType c: AngleUnitEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
