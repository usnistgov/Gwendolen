#!/bin/bash

set -x;

#/home/shackle/jdk1.8.0_181/bin/javac -d /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/classes -classpath /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/classes:/home/shackle/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/home/shackle/.m2/repository/com/sun/xml/bind/jaxb-impl/2.3.1/jaxb-impl-2.3.1.jar:/home/shackle/.m2/repository/com/sun/xml/bind/jaxb-core/2.3.0.1/jaxb-core-2.3.0.1.jar:/home/shackle/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/home/shackle/.m2/repository/org/checkerframework/checker-qual/3.1.1/checker-qual-3.1.1.jar:/home/shackle/.m2/repository/org/checkerframework/checker/3.1.1/checker-3.1.1.jar:/home/shackle/.m2/repository/org/checkerframework/jdk8/3.1.1/jdk8-3.1.1.jar:/home/shackle/.m2/repository/com/google/errorprone/javac/9+181-r4173-1/javac-9+181-r4173-1.jar: -sourcepath /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/src/main/java:/home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc:/home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/annotations: /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ConfigureJointReportType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetRobotParametersType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ThreeFingerGripperStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/AngleUnitEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointLimitType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/StopMotionType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/MiddleCommandType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetEndPoseToleranceType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CommandStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransAccelRelativeType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetDefaultJointPositonsTolerancesType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/PoseToleranceType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/InitCanonType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CRCLCommandType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/OpenToolChangerType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointDetailsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/StopConditionEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetRotAccelType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SettingsStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/VectorType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/PointType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CRCLProgramType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CommandStateEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetTransAccelType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ObjectFactory.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetMotionCoordinationType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/MoveThroughToType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/WrenchType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/MoveToType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ActuateJointType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotSpeedAbsoluteType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotAccelAbsoluteType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CRCLStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotSpeedRelativeType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetForceUnitsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/DisableGripperType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/PoseStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetEndEffectorParametersType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/OnOffSensorStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointStatusesType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointPositionToleranceSettingType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetTorqueUnitsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/EnableSensorType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetRotSpeedType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/GripperStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransSpeedRelativeType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/PoseAndSetType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/src/main/java/crcl/base/package-info.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/EnableGripperType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetIntermediatePoseToleranceType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CountSensorStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TorqueUnitEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetTransSpeedType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/EnableRobotParameterStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/MoveScrewType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/GuardsStatusesType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointForceTorqueType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransSpeedType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RunProgramType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/GuardType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotAccelRelativeType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CloseToolChangerType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotSpeedType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ScalarSensorStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/EndCanonType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ForceUnitEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ForceTorqueSensorStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ConfigureStatusReportType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/DwellType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/GuardLimitEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/PoseType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointPositionsTolerancesType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointSpeedAccelType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransAccelAbsoluteType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ActuateJointsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransAccelType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SensorStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CRCLCommandInstanceType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ParallelGripperStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/DataThingType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ParameterSettingType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/DisableSensorType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/LengthUnitEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetLengthUnitsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/GetStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TwistType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetAngleUnitsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotAccelType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/DisableRobotParameterStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ConfigureJointReportsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SensorStatusesType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/MessageType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransSpeedAbsoluteType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/VacuumGripperStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetEndEffectorType.java -s /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/annotations -processor org.checkerframework.checker.nullness.NullnessChecker -processorpath /home/shackle/.m2/repository/org/checkerframework/checker/3.1.1/checker-3.1.1.jar: -g -nowarn -target 1.8 -source 1.8 -encoding UTF-8 -Xmaxerrs 10000 -Xmaxwarns 10000 -AsuppressWarnings=purity,uninitialized,nullness,keyfor

# -d /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/target/classes -classpath /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/target/classes:/home/shackle/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/home/shackle/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/home/shackle/.m2/repository/com/sun/xml/bind/jaxb-impl/2.3.1/jaxb-impl-2.3.1.jar:/home/shackle/.m2/repository/com/sun/xml/bind/jaxb-core/2.3.0.1/jaxb-core-2.3.0.1.jar:/home/shackle/.m2/repository/com/github/wshackle/crcl4java-base/1.9.1-SNAPSHOT/crcl4java-base-1.9.1-SNAPSHOT.jar:/home/shackle/.m2/repository/com/github/wshackle/rcslib/2017.07.19/rcslib-2017.07.19.jar:/home/shackle/.m2/repository/org/checkerframework/checker-qual/3.1.1/checker-qual-3.1.1.jar:/home/shackle/.m2/repository/org/checkerframework/checker/3.1.1/checker-3.1.1.jar:/home/shackle/.m2/repository/org/checkerframework/jdk8/3.1.1/jdk8-3.1.1.jar:/home/shackle/.m2/repository/com/google/errorprone/javac/9+181-r4173-1/javac-9+181-r4173-1.jar: -sourcepath /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java:/home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/target/generated-sources/annotations: /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/SensorServerFinderInterface.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/SensorServerInterface.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/CRCLCopier.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/CRCLCommandWrapper.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/RemoteCrclSensorExtractorFinder.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/PerfTest.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/stubs/PendantClientOuterStub.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/UnitsTypeSet.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/outer/interfaces/PendantClientOuter.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/XFutureVoid.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/CRCLSocketConsumer.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/XFuture.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/stubs/SimServerOuterStub.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/ServerJInternalFrameProviderFinderInterface.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/CRCLException.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/AnnotatedPose.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/CRCLServerSocketStateGenerator.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/outer/interfaces/ProgramRunData.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/kinematics/SimRobotEnum.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/CRCLServerSocketEventListener.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/CRCLSchemaOutputResolver.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/CRCLUtils.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/CRCLCommandWrapperConsumer.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/CRCLSocket.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/CRCLServerSocketEventType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/GuardHistoryElement.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/outer/interfaces/CommandStatusLogElement.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/outer/interfaces/TimeStampedStatus.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/kinematics/SimulatedKinematicsSimple.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/CRCLStatusFilterSettings.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/GuardHistory.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/CRCLSchemaUtils.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/CRCLServerClientState.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/UnitsScaleSet.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/outer/interfaces/PendantClientMenuOuter.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/kinematics/SimulatedKinematicsPlausible.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/RemoteCrclSensorExtractor.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/CRCLServerSocketEvent.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/XpathUtils.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/PoseToleranceChecker.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/PropertiesUtils.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/outer/interfaces/SimServerMenuOuter.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/CRCLServerSocket.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/CRCLPosemath.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/outer/interfaces/SimServerOuter.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/src/main/java/crcl/utils/server/ServerJInternalFrameProviderInterface.java -s /home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/target/generated-sources/annotations -processor org.checkerframework.checker.nullness.NullnessChecker -processorpath /home/shackle/.m2/repository/org/checkerframework/checker/3.1.1/checker-3.1.1.jar: -g -nowarn -target 1.8 -source 1.8 -encoding UTF-8 -Xmaxerrs 10000 -Xmaxwarns 10000 -Xbootclasspath/p:/home/shackle/.m2/repository/org/checkerframework/jdk8/3.1.1/jdk8-3.1.1.jar

#export CLASSPATH="/home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/classes:/home/shackle/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/home/shackle/.m2/repository/com/sun/xml/bind/jaxb-impl/2.3.1/jaxb-impl-2.3.1.jar:/home/shackle/.m2/repository/com/sun/xml/bind/jaxb-core/2.3.0.1/jaxb-core-2.3.0.1.jar:/home/shackle/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/home/shackle/.m2/repository/org/checkerframework/checker-qual/3.1.1/checker-qual-3.1.1.jar:/home/shackle/.m2/repository/org/checkerframework/checker/3.1.1/checker-3.1.1.jar:/home/shackle/.m2/repository/org/checkerframework/jdk8/3.1.1/jdk8-3.1.1.jar:/home/shackle/.m2/repository/com/google/errorprone/javac/9+181-r4173-1/javac-9+181-r4173-1.jar:";
export CLASSPATH="/home/shackle/crcl/tools/java/crcl4java/crcl4java-utils/target/classes:/home/shackle/.m2/repository/javax/xml/bind/jaxb-api/2.3.1/jaxb-api-2.3.1.jar:/home/shackle/.m2/repository/javax/activation/javax.activation-api/1.2.0/javax.activation-api-1.2.0.jar:/home/shackle/.m2/repository/com/sun/xml/bind/jaxb-impl/2.3.1/jaxb-impl-2.3.1.jar:/home/shackle/.m2/repository/com/sun/xml/bind/jaxb-core/2.3.0.1/jaxb-core-2.3.0.1.jar:/home/shackle/.m2/repository/com/github/wshackle/crcl4java-base/1.9.1-SNAPSHOT/crcl4java-base-1.9.1-SNAPSHOT.jar:/home/shackle/.m2/repository/com/github/wshackle/rcslib/2017.07.19/rcslib-2017.07.19.jar:/home/shackle/.m2/repository/org/checkerframework/checker-qual/3.1.1/checker-qual-3.1.1.jar:/home/shackle/.m2/repository/org/checkerframework/checker/3.1.1/checker-3.1.1.jar:/home/shackle/.m2/repository/org/checkerframework/jdk8/3.1.1/jdk8-3.1.1.jar:/home/shackle/.m2/repository/com/google/errorprone/javac/9+181-r4173-1/javac-9+181-r4173-1.jar:"

echo CLASSPATH="${CLASSPATH}"

for javasource in `find target/generated-sources/ -name \*.java` ; do
    echo ${javasource}
    /home/shackle/jdk1.8.0_181/bin/javac -d /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/classes -classpath "${CLASSPATH}" -sourcepath /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/src/main/java:/home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc:/home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/annotations: ${javasource} -s /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/annotations -processor org.checkerframework.checker.nullness.NullnessChecker -processorpath /home/shackle/.m2/repository/org/checkerframework/checker/3.1.1/checker-3.1.1.jar: -g -nowarn -target 1.8 -source 1.8 -encoding UTF-8 -Xmaxerrs 10000 -Xmaxwarns 10000 -AsuppressWarnings=purity,uninitialized,nullness,keyfor || exit 1


#/home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ConfigureJointReportType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetRobotParametersType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ThreeFingerGripperStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/AngleUnitEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointLimitType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/StopMotionType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/MiddleCommandType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetEndPoseToleranceType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CommandStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransAccelRelativeType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetDefaultJointPositonsTolerancesType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/PoseToleranceType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/InitCanonType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CRCLCommandType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/OpenToolChangerType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointDetailsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/StopConditionEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetRotAccelType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SettingsStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/VectorType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/PointType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CRCLProgramType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CommandStateEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetTransAccelType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ObjectFactory.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetMotionCoordinationType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/MoveThroughToType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/WrenchType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/MoveToType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ActuateJointType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotSpeedAbsoluteType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotAccelAbsoluteType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CRCLStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotSpeedRelativeType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetForceUnitsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/DisableGripperType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/PoseStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetEndEffectorParametersType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/OnOffSensorStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointStatusesType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointPositionToleranceSettingType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetTorqueUnitsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/EnableSensorType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetRotSpeedType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/GripperStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransSpeedRelativeType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/PoseAndSetType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/src/main/java/crcl/base/package-info.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/EnableGripperType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetIntermediatePoseToleranceType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CountSensorStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TorqueUnitEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetTransSpeedType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/EnableRobotParameterStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/MoveScrewType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/GuardsStatusesType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointForceTorqueType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransSpeedType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RunProgramType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/GuardType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotAccelRelativeType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CloseToolChangerType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotSpeedType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ScalarSensorStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/EndCanonType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ForceUnitEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ForceTorqueSensorStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ConfigureStatusReportType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/DwellType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/GuardLimitEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/PoseType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointPositionsTolerancesType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/JointSpeedAccelType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransAccelAbsoluteType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ActuateJointsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransAccelType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SensorStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/CRCLCommandInstanceType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ParallelGripperStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/DataThingType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ParameterSettingType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/DisableSensorType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/LengthUnitEnumType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetLengthUnitsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/GetStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TwistType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetAngleUnitsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/RotAccelType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/DisableRobotParameterStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/ConfigureJointReportsType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SensorStatusesType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/MessageType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/TransSpeedAbsoluteType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/VacuumGripperStatusType.java /home/shackle/crcl/tools/java/crcl4java/crcl4java-base/target/generated-sources/xjc/crcl/base/SetEndEffectorType.java 

done 