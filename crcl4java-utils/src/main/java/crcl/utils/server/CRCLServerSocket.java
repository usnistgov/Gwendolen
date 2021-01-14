/*
 * This software is public domain software, however it is preferred
 * that the following disclaimers be attached.
 * Software Copywrite/Warranty Disclaimer
 * 
 * This software was developed at the National Institute of Standards and
 * Technology by employees of the Federal Government in the course of their
 * official duties. Pursuant to title 17 Section 105 of the United States
 * Code this software is not subject to copyright protection and is in the
 * public domain.
 * 
 * This software is experimental. NIST assumes no responsibility whatsoever 
 * for its use by other parties, and makes no guarantees, expressed or 
 * implied, about its quality, reliability, or any other characteristic. 
 * We would appreciate acknowledgement if the software is used. 
 * This software can be redistributed and/or modified freely provided 
 * that any derivative works bear some notice that they are derived from it, 
 * and any modified versions bear some notice that they have been modified.
 * 
 *  See http://www.copyright.gov/title17/92chap1.html#105
 * 
 */
package crcl.utils.server;

import crcl.base.CRCLCommandInstanceType;
import crcl.base.CRCLCommandType;
import crcl.base.CRCLStatusType;
import crcl.base.CommandStateEnumType;
import crcl.base.CommandStatusType;
import crcl.base.ConfigureJointReportType;
import crcl.base.ConfigureJointReportsType;
import crcl.base.ConfigureStatusReportType;
import crcl.base.CountSensorStatusType;
import crcl.base.DisableSensorType;
import crcl.base.EnableSensorType;
import crcl.base.ForceTorqueSensorStatusType;
import crcl.base.GetStatusType;
import crcl.base.GuardLimitEnumType;
import crcl.base.GuardType;
import crcl.base.GuardsStatusesType;
import crcl.base.JointPositionsTolerancesType;
import crcl.base.JointStatusType;
import crcl.base.JointStatusesType;
import crcl.base.MoveThroughToType;
import crcl.base.MoveToType;
import crcl.base.OnOffSensorStatusType;
import crcl.base.PointType;
import crcl.base.PoseStatusType;
import crcl.base.PoseToleranceType;
import crcl.base.PoseType;
import crcl.base.RotAccelAbsoluteType;
import crcl.base.RotAccelRelativeType;
import crcl.base.RotAccelType;
import crcl.base.RotSpeedAbsoluteType;
import crcl.base.RotSpeedRelativeType;
import crcl.base.RotSpeedType;
import crcl.base.ScalarSensorStatusType;
import crcl.base.SensorStatusType;
import crcl.base.SensorStatusesType;
import crcl.base.SetAngleUnitsType;
import crcl.base.SetDefaultJointPositonsTolerancesType;
import crcl.base.SetEndPoseToleranceType;
import crcl.base.SetForceUnitsType;
import crcl.base.SetLengthUnitsType;
import crcl.base.SetRotAccelType;
import crcl.base.SetRotSpeedType;
import crcl.base.SetTorqueUnitsType;
import crcl.base.SetTransAccelType;
import crcl.base.SetTransSpeedType;
import crcl.base.SettingsStatusType;
import crcl.base.TransAccelAbsoluteType;
import crcl.base.TransAccelRelativeType;
import crcl.base.TransAccelType;
import crcl.base.TransSpeedAbsoluteType;
import crcl.base.TransSpeedRelativeType;
import crcl.base.TransSpeedType;
import crcl.utils.CRCLCopier;
import static crcl.utils.CRCLCopier.copy;
import crcl.utils.CRCLException;
import crcl.utils.CRCLPosemath;
import crcl.utils.CRCLSocket;
import crcl.utils.CRCLUtils;
import static crcl.utils.CRCLUtils.getNonNullCmd;
import static crcl.utils.CRCLUtils.getNonNullFilteredList;
import crcl.utils.XFuture;
import crcl.utils.XFutureVoid;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.validation.Schema;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * 
 * NOTE: Comments beginning with {@literal @} or {@literal >>>} are used by Checker Framework Comments
 * beginning with {@literal @} must have no spaces in the comment or Checker will ignore
 * it.
 *
 * See http://types.cs.washington.edu/checker-framework for null pointer
 * checks. This file can be compiled without the Checker Framework, but using
 * the framework allows potential NullPointerExceptions to be found.
 */

 /*>>>
import org.checkerframework.checker.nullness.qual.*;
 */
/**
 *
 * @author Will Shackleford {@literal <william.shackleford@nist.gov> }
 */
public class CRCLServerSocket<STATE_TYPE extends CRCLServerClientState> implements AutoCloseable {

    private static final Map<Integer, CRCLServerSocket> portMap
            = new ConcurrentHashMap<>();

    private final List<CRCLServerClientInfo> clients = new ArrayList<>();

    private final CRCLServerSocketStateGenerator<STATE_TYPE> stateGenerator;

    private final Class<STATE_TYPE> stateClass;

    private volatile @MonotonicNonNull
    CRCLStatusType serverSideStatus;

    private volatile @Nullable
    Runnable guardCheckUpdatePositionOnlyRunnable = null;

    public @Nullable
    Runnable getGuardCheckUpdatePositionOnlyRunnable() {
        return guardCheckUpdatePositionOnlyRunnable;
    }

    public void setGuardCheckUpdatePositionOnlyRunnable(Runnable guardCheckUpdatePositionOnlyRunnable) {
        this.guardCheckUpdatePositionOnlyRunnable = guardCheckUpdatePositionOnlyRunnable;
    }

    private final Map<String, SensorServerInterface> sensorServers = new HashMap<>();

    public void addSensorServer(String sensorId, SensorServerInterface sensorServer) {
        sensorServers.put(sensorId, sensorServer);
    }

    public void removeSensorServer(String sensorId) {
        sensorServers.remove(sensorId);
    }

    private final List<SensorServerFinderInterface> sensorFinders = new ArrayList<>();

    public void addSensorFinder(SensorServerFinderInterface sensorFinder) {
        sensorFinders.add(sensorFinder);
    }

    public void removeSensorFinder(SensorServerFinderInterface sensorFinder) {
        sensorFinders.remove(sensorFinder);
    }

    public void clearSensorFinders() {
        sensorFinders.clear();
    }

    public @Nullable
    CRCLStatusType getServerSideStatus() {
        return serverSideStatus;
    }

    private boolean automaticallyHandleSensorServers = true;

    public final void refreshSensorFinders() {

        clearSensorFinders();
//        try {
//            ClassLoader cl = Thread.currentThread().getContextClassLoader();
//            System.out.println("cl = " + cl);
//            Class clzz = cl.loadClass("com.github.wshackle.atinetft_proxy.ATIForceTorqueSensorFinder");
//            System.out.println("clzz = " + clzz);
//            ProtectionDomain protDom = clzz.getProtectionDomain();
//            System.out.println("protDom = " + protDom);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(CRCLServerSocket.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            Class clzz = Class.forName("com.github.wshackle.atinetft_proxy.ATIForceTorqueSensorFinder");
//            System.out.println("clzz = " + clzz);
//            ProtectionDomain protDom = clzz.getProtectionDomain();
//            System.out.println("protDom = " + protDom);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(CRCLServerSocket.class.getName()).log(Level.SEVERE, null, ex);
//        }

        addSensorFinder(new RemoteCrclSensorExtractorFinder());
        ServiceLoader<SensorServerFinderInterface> loader
                = ServiceLoader
                        .load(SensorServerFinderInterface.class);

        Iterator<SensorServerFinderInterface> it = loader.iterator();
//        System.out.println("it = " + it);
        while (it.hasNext()) {
            SensorServerFinderInterface finder = it.next();
//            System.out.println("finder = " + finder);
            addSensorFinder(finder);
        }
//        System.out.println("this.sensorFinders = " + this.sensorFinders);

    }

    /**
     * Get the value of automaticallyHandleSensorServers
     *
     * @return the value of automaticallyHandleSensorServers
     */
    public boolean isAutomaticallyHandleSensorServers() {
        return automaticallyHandleSensorServers;
    }

    /**
     * Set the value of automaticallyHandleSensorServers
     *
     * @param automaticallyHandleSensorServers new value of
     * automaticallyHandleSensorServers
     */
    public void setAutomaticallyHandleSensorServers(boolean automaticallyHandleSensorServers) {
        this.automaticallyHandleSensorServers = automaticallyHandleSensorServers;
    }

    public void setServerSideStatus(CRCLStatusType serverSideStatus) {
        this.serverSideStatus = serverSideStatus;
        CommandStatusType cst = serverSideStatus.getCommandStatus();
        if (null == cst) {
            cst = new CommandStatusType();
            serverSideStatus.setCommandStatus(cst);
            setWorkingState();
            cst.setCommandID(1);
            cst.setStatusID(1);
        }
    }

    private boolean automaticallyConvertUnits = true;

    /**
     * Get the value of automaticallyConvertUnits
     *
     * @return the value of automaticallyConvertUnits
     */
    public boolean isAutomaticallyConvertUnits() {
        return automaticallyConvertUnits;
    }

    /**
     * Set the value of automaticallyConvertUnits
     *
     * @param automaticallyConvertUnits new value of automaticallyConvertUnits
     */
    public void setAutomaticallyConvertUnits(boolean automaticallyConvertUnits) {
        this.automaticallyConvertUnits = automaticallyConvertUnits;
    }

    private boolean automaticallySendServerSideStatus = false;

    /**
     * Get the value of automaticallySendServerSideStatus
     *
     * @return the value of automaticallySendServerSideStatus
     */
    public boolean isAutomaticallySendServerSideStatus() {
        return automaticallySendServerSideStatus;
    }

    /**
     * Set the value of automaticallySendServerSideStatus
     *
     * @param automaticallySendServerSideStatus new value of
     * automaticallySendServerSideStatus
     */
    public void setAutomaticallySendServerSideStatus(boolean automaticallySendServerSideStatus) {
        if (automaticallySendServerSideStatus && null == serverSideStatus) {
            throw new IllegalStateException("serverSideStatus == null");
        }
        this.automaticallySendServerSideStatus = automaticallySendServerSideStatus;
    }

    private class CRCLServerClientInfo implements AutoCloseable {

        private final CRCLSocket socket;

        private final STATE_TYPE state;

        private final @Nullable
        Future<?> future;

        public CRCLSocket getSocket() {
            return socket;
        }

        public @Nullable
        Future<?> getFuture() {
            return future;
        }

        CRCLServerClientInfo(CRCLSocket socket, @Nullable Future<?> future, STATE_TYPE state) {
            this.socket = socket;
            this.future = future;
            this.state = state;
        }

        @Override
        public void close() {
            startingSocketChannel = false;
            try {
                if (null != socket) {
                    this.socket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(CRCLServerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (null != future) {
                this.future.cancel(true);
            }
        }
    }

    /**
     * Get the value of clients
     *
     * @return the value of clients
     */
    public List<CRCLServerClientInfo> getClients() {
        return Collections.unmodifiableList(clients);
    }

    private int port = CRCLSocket.DEFAULT_PORT;

    /**
     * Get the value of port
     *
     * @return the value of port
     */
    public int getPort() {
        return port;
    }

    public boolean isClosed() {
        if (null != serverSocket) {
            return serverSocket.isClosed();
        }
        if (null != serverSocketChannel) {
            return !serverSocketChannel.isOpen();
        }
        return true;
    }

    /**
     * Set the value of port
     *
     * @param port new value of port
     * @throws java.io.IOException if current socket fails to close
     */
    public void setPort(int port) throws IOException {
        if (isRunning()) {
            throw new IllegalStateException("Can not set field when server is running.");
        }
        int oldport = this.port;
        if (portMap.get(oldport) == this) {
            portMap.remove(oldport);
        }
        this.port = port;
        if (serverSocket != null) {
            serverSocket.close();
        }
        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
        checkPortMap(port);
        portMap.put(port, this);
    }

    private @MonotonicNonNull
    ExecutorService callbackService = null;

    /**
     * Get the value of callbackService
     *
     * @return the value of callbackService
     */
    public @Nullable
    ExecutorService getCallbackService() {
        return callbackService;
    }

    /**
     * Set the value of callbackService
     *
     * @param callbackService new value of callbackService
     */
    public void setCallbackService(ExecutorService callbackService) {
        if (isRunning()) {
            throw new IllegalStateException("Can not set field when server is running.");
        }
        this.callbackService = callbackService;
    }

    private @MonotonicNonNull
    ExecutorService executorService;

    /**
     * Get the value of executorService
     *
     * @return the value of executorService
     */
    public @Nullable
    ExecutorService getExecutorService() {
        return executorService;
    }

    /**
     * Set the value of executorService
     *
     * @param executorService new value of executorService
     */
    public void setExecutorService(ExecutorService executorService) {
        if (isRunning()) {
            throw new IllegalStateException("Can not set field when server is running.");
        }
        this.executorService = executorService;
    }

    private volatile @MonotonicNonNull
    ServerSocketChannel serverSocketChannel;

    private boolean closing = false;

    @Override
    public void close() {
        started = false;
        closing = true;
        if (queueEvents) {
            try {
                queue.offer(CRCLServerSocketEvent.serverClosed(stateClass), 1, TimeUnit.SECONDS);
            } catch (Exception ex) {
                Logger.getLogger(CRCLServerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (null != serverSocketChannel) {
            try {
                serverSocketChannel.close();
            } catch (IOException ignored) {
            }
        }
        if (null != serverSocket) {
            try {
                serverSocket.close();
            } catch (IOException ignored) {
            }
        }
        if (null != selector) {
            try {
                selector.close();
            } catch (IOException ignored) {
            }
        }
        for (int i = 0; i < clients.size(); i++) {
            try {
                CRCLServerClientInfo client = clients.get(i);
                client.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        clients.clear();
        if (null != executorService) {
            executorService.shutdownNow();
            try {
                executorService.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException ex) {
                Logger.getLogger(CRCLServerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (null != callbackService) {
            callbackService.shutdownNow();
            try {
                callbackService.awaitTermination(1, TimeUnit.SECONDS);
            } catch (Exception ex) {
                Logger.getLogger(CRCLServerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        listeners.clear();
        queue.clear();
        if (portMap.get(port) == this) {
            portMap.remove(port);
        }
    }

//    @Override
//    protected void finalize() throws Throwable {
//        close();
//        super.finalize();
//    }
    private boolean validate;

    /**
     * Get the value of validate
     *
     * @return the value of validate
     */
    public boolean isValidate() {
        return validate;
    }

    /**
     * Set the value of validate
     *
     * @param validate new value of validate
     */
    public void setValidate(boolean validate) {
        if (isRunning()) {
            throw new IllegalStateException("Can not set field when server is running.");
        }
        this.validate = validate;
    }

    private @MonotonicNonNull
    ServerSocket serverSocket;

    final List<CRCLServerSocketEventListener<STATE_TYPE>> listeners = new ArrayList<>();

    public synchronized void addListener(CRCLServerSocketEventListener<STATE_TYPE> l) {
        if (isRunning()) {
            throw new IllegalStateException("Can not add listener when server is already running.");
        }
        listeners.add(l);
    }

    public synchronized void removeListener(CRCLServerSocketEventListener<STATE_TYPE> l) {
        if (isRunning()) {
            throw new IllegalStateException("Can not remove listener when server is already running.");
        }
        listeners.remove(l);
    }

    private boolean queueEvents = false;

    /**
     * Get the value of queueEvents
     *
     * @return the value of queueEvents
     */
    public boolean isQueueEvents() {
        return queueEvents;
    }

    /**
     * Set the value of queueEvents
     *
     * @param queueEvents new value of queueEvents
     */
    public void setQueueEvents(boolean queueEvents) {
        if (isRunning()) {
            throw new IllegalStateException("Can not set field when server is running.");
        }
        this.queueEvents = queueEvents;
    }

    private BlockingQueue<CRCLServerSocketEvent<STATE_TYPE>> queue = new LinkedBlockingQueue<>();

    public void comleteGuardTrigger() {
        if (null != serverSideStatus) {
            long now = System.currentTimeMillis();
            long timeDiff = now - this.guardTriggerStartTime;
            long timeDiffMicros = 1000 * timeDiff;
            final GuardsStatusesType guardsStatuses = serverSideStatus.getGuardsStatuses();
            if (null != guardsStatuses) {
                guardsStatuses.setTriggerStopTimeMicros(timeDiffMicros);
            }
        }
    }

    private void handleEvent(final CRCLServerSocketEvent<STATE_TYPE> event) throws Exception {
        if (closing) {
            return;
        }
        if (handleAutomaticEvents(event)) {
            return;
        }
        completeHandleEvent(event);
    }

    @SuppressWarnings("nullness")
    private boolean handleAutomaticEvents(final CRCLServerSocketEvent<STATE_TYPE> event) throws IllegalStateException, CRCLException, InterruptedException {
        final CRCLCommandInstanceType instanceIn = event.getInstance();
        if (automaticallySendServerSideStatus
                && event.getEventType() == CRCLServerSocketEventType.CRCL_COMMAND_RECIEVED
                && serverSideStatus != null
                && instanceIn != null) {
            STATE_TYPE state = event.getState();
            if (null == state) {
                throw new NullPointerException("state");
            }
            final CRCLCommandType cmd = instanceIn.getCRCLCommand();
            if (serverSideStatus.getCommandStatus() == null) {
                serverSideStatus.setCommandStatus(new CommandStatusType());
            }
            final CommandStatusType commandStatus = serverSideStatus.getCommandStatus();
            try {
                if (cmd instanceof GetStatusType) {
                    CRCLSocket source = event.getSource();
                    if (null == source) {
                        throw new NullPointerException("source");
                    }

                    if (null != updateStatusSupplier) {
                        XFuture<CRCLStatusType> supplierFuture = updateStatusSupplier.get();
                        supplierFuture.thenAccept((CRCLStatusType suppliedStatus) -> {
                            finishWriteStatus(state, suppliedStatus, source, event, commandStatus);
                        });
                        return true;
                    }
                    finishWriteStatus(state, serverSideStatus, source, event, commandStatus);
//                    CRCLStatusType statusToSend;
//                    synchronized (this) {
//                        asyncCheckSensorServers();
//                        statusToSend = state.filterSettings.filterStatus(serverSideStatus);
//                        state.cmdId = statusToSend.getCommandStatus().getCommandID();
//                    }
////                statusToSend.getCommandStatus().setCommandID(state.cmdId);
//                    source.writeStatus(statusToSend);
                    return true;
                } else {
                    state.cmdId = cmd.getCommandID();

                    commandStatus.setCommandID(cmd.getCommandID());
                    String cmdStatusName = cmd.getClass().getSimpleName();
                    if (cmd.getName() != null && cmd.getName().length() > 0 && !cmd.getName().startsWith(cmdStatusName)) {
                        cmdStatusName += cmd.getName();
                    }
                    if (cmdStatusName.length() > 120) {
                        cmdStatusName = cmdStatusName.substring(0, 120);
                    }
                    commandStatus.setName(cmdStatusName);
                    if (null != instanceIn.getProgramFile()) {
                        commandStatus.setProgramFile(instanceIn.getProgramFile());
                    }
                    if (null != instanceIn.getProgramIndex()) {
                        commandStatus.setProgramIndex(instanceIn.getProgramIndex());
                    }
                    if (null != instanceIn.getProgramLength()) {
                        commandStatus.setProgramLength(instanceIn.getProgramLength());
                    }
                    setWorkingState();
                    final SettingsStatusType serverSideSettingsStatus;
                    final SettingsStatusType initialServerSideSettingsStatus
                            = serverSideStatus.getSettingsStatus();
                    if (null == initialServerSideSettingsStatus) {
                        serverSideSettingsStatus = new SettingsStatusType();
                        serverSideStatus.setSettingsStatus(serverSideSettingsStatus);
                    } else {
                        serverSideSettingsStatus = initialServerSideSettingsStatus;
                    }
                    if (cmd instanceof crcl.base.InitCanonType) {
                        commandStatus.setCommandState(CommandStateEnumType.CRCL_WORKING);
                    } else if (cmd instanceof ConfigureStatusReportType) {
                        state.filterSettings.setConfigureStatusReport((ConfigureStatusReportType) cmd);
                        final ConfigureStatusReportType configStatusCmd = (ConfigureStatusReportType) cmd;
                        if (configStatusCmd.isReportJointStatuses() && null == serverSideStatus.getJointStatuses()) {
                            serverSideStatus.setJointStatuses(new JointStatusesType());
                        }
                        if (configStatusCmd.isReportPoseStatus() && null == serverSideStatus.getPoseStatus()) {
                            serverSideStatus.setPoseStatus(new PoseStatusType());
                        }
                        if (configStatusCmd.isReportSensorsStatus() && null == serverSideStatus.getSensorStatuses()) {
                            serverSideStatus.setSensorStatuses(new SensorStatusesType());
                        }
                        if (configStatusCmd.isReportSettingsStatus() && null == serverSideSettingsStatus) {
                            serverSideStatus.setSettingsStatus(new SettingsStatusType());
                        }
                        if (configStatusCmd.isReportGuardsStatus() && null == serverSideStatus.getGuardsStatuses()) {
                            serverSideStatus.setGuardsStatuses(new GuardsStatusesType());
                        }
                        setDoneState();
                        return true;
                    } else if (cmd instanceof ConfigureJointReportsType) {
                        ConfigureJointReportsType cjrt = (ConfigureJointReportsType) cmd;
                        if (cjrt.isResetAll()) {
                            state.filterSettings.clearConfigJointsReportMap();
                        }
                        state.filterSettings.configureJointReports(cjrt.getConfigureJointReport());
                        setDoneState();
                        return true;
                    } else if (cmd instanceof SetDefaultJointPositonsTolerancesType) {
                        SetDefaultJointPositonsTolerancesType setDefaultJointPositonsTolerancesCommand
                                = (SetDefaultJointPositonsTolerancesType) cmd;
                        final JointPositionsTolerancesType jointTolerancesCopy
                                = CRCLCopier.copy(setDefaultJointPositonsTolerancesCommand.getJointTolerances());
                        serverSideSettingsStatus.setJointTolerances(jointTolerancesCopy);
                        setDoneState();
                        return true;
                    } else if (cmd instanceof SetEndPoseToleranceType) {
                        SetEndPoseToleranceType setEndPoseToleranceCommand
                                = (SetEndPoseToleranceType) cmd;
                        final PoseToleranceType inputEndPoseTolerance = setEndPoseToleranceCommand.getTolerance();
                        final PoseToleranceType endPoseTolerance
                                = CRCLCopier.copy(inputEndPoseTolerance);
                        if (automaticallyConvertUnits) {
                            endPoseTolerance.setXPointTolerance(state.filterSettings.convertLengthToServer(inputEndPoseTolerance.getXPointTolerance()));
                            endPoseTolerance.setYPointTolerance(state.filterSettings.convertLengthToServer(inputEndPoseTolerance.getYPointTolerance()));
                            endPoseTolerance.setZPointTolerance(state.filterSettings.convertLengthToServer(inputEndPoseTolerance.getZPointTolerance()));

                            endPoseTolerance.setXAxisTolerance(state.filterSettings.convertAngleToServer(inputEndPoseTolerance.getXAxisTolerance()));
                            endPoseTolerance.setZAxisTolerance(state.filterSettings.convertAngleToServer(inputEndPoseTolerance.getZAxisTolerance()));
                        }
                        serverSideSettingsStatus.setEndPoseTolerance(endPoseTolerance);
                        setDoneState();
                        return true;
                    }
                    if (automaticallyConvertUnits) {
                        if (cmd instanceof SetLengthUnitsType) {
                            SetLengthUnitsType setLengthUnitsCmd = (SetLengthUnitsType) cmd;
                            state.filterSettings.getClientUserSet().setLengthUnit(setLengthUnitsCmd.getUnitName());
                            setDoneState();
                            return true;
                        } else if (cmd instanceof SetAngleUnitsType) {
                            SetAngleUnitsType setAngleUnitsCmd = (SetAngleUnitsType) cmd;
                            state.filterSettings.getClientUserSet().setAngleUnit(setAngleUnitsCmd.getUnitName());
                            setDoneState();
                            return true;
                        } else if (cmd instanceof SetForceUnitsType) {
                            SetForceUnitsType setForceUnitsCmd = (SetForceUnitsType) cmd;
                            state.filterSettings.getClientUserSet().setForceUnit(setForceUnitsCmd.getUnitName());
                            return true;
                        } else if (cmd instanceof SetTorqueUnitsType) {
                            SetTorqueUnitsType setTorqueUnitsCmd = (SetTorqueUnitsType) cmd;
                            state.filterSettings.getClientUserSet().setTorqueUnit(setTorqueUnitsCmd.getUnitName());
                            setDoneState();
                            return true;
                        } else if (cmd instanceof MoveToType) {
                            MoveToType moveToCmdIn = (MoveToType) cmd;
                            MoveToType moveToCmdOut = new MoveToType();
                            moveToCmdOut.setCommandID(moveToCmdIn.getCommandID());
                            moveToCmdOut.setName(moveToCmdIn.getName());
                            moveToCmdOut.setMoveStraight(moveToCmdIn.isMoveStraight());
                            moveToCmdOut.setEndPosition(copy(moveToCmdIn.getEndPosition()));
                            PointType outPoint = moveToCmdOut.getEndPosition().getPoint();
                            PointType inPoint = moveToCmdIn.getEndPosition().getPoint();
                            outPoint.setX(state.filterSettings.convertLengthToServer(inPoint.getX()));
                            outPoint.setY(state.filterSettings.convertLengthToServer(inPoint.getY()));
                            outPoint.setZ(state.filterSettings.convertLengthToServer(inPoint.getZ()));
                            if (moveToCmdIn.getGuard() != null && !moveToCmdIn.getGuard().isEmpty()) {
                                moveToCmdOut.getGuard().addAll(moveToCmdIn.getGuard());
                            }
                            CRCLCommandInstanceType newCommandInstance = createNewCommandInstance(moveToCmdOut, instanceIn);
                            completeHandleEvent(CRCLServerSocketEvent.commandRecieved(state, newCommandInstance));
                            return true;
                        } else if (cmd instanceof MoveThroughToType) {
                            MoveThroughToType moveThroughToCmdIn = (MoveThroughToType) cmd;
                            MoveThroughToType moveThroughToCmdOut = new MoveThroughToType();
                            moveThroughToCmdOut.setCommandID(moveThroughToCmdIn.getCommandID());
                            moveThroughToCmdOut.setName(moveThroughToCmdIn.getName());
                            moveThroughToCmdOut.setMoveStraight(moveThroughToCmdIn.isMoveStraight());
                            final List<PoseType> waypointInList = moveThroughToCmdIn.getWaypoint();
                            final List<PoseType> waypointOutList = moveThroughToCmdOut.getWaypoint();
                            for (int i = 0; i < moveThroughToCmdIn.getNumPositions() && i < waypointInList.size(); i++) {
                                PoseType wayPointInI = waypointInList.get(i);
                                PoseType wayPointOutI = waypointOutList.get(i);
                                PointType outPoint = wayPointOutI.getPoint();
                                PointType inPoint = wayPointInI.getPoint();
                                outPoint.setX(state.filterSettings.convertLengthToServer(inPoint.getX()));
                                outPoint.setY(state.filterSettings.convertLengthToServer(inPoint.getY()));
                                outPoint.setZ(state.filterSettings.convertLengthToServer(inPoint.getZ()));
                            }
                            CRCLCommandInstanceType newCommandInstance = createNewCommandInstance(moveThroughToCmdOut, instanceIn);
                            completeHandleEvent(CRCLServerSocketEvent.commandRecieved(state, newCommandInstance));
                            return true;
                        } else if (cmd instanceof SetTransSpeedType) {
                            SetTransSpeedType setTransSpeedCmdIn = (SetTransSpeedType) cmd;
                            SetTransSpeedType setTransSpeedCmdOut = new SetTransSpeedType();
                            final TransSpeedType transSpeedIn = setTransSpeedCmdIn.getTransSpeed();
                            if (transSpeedIn instanceof TransSpeedAbsoluteType) {
                                TransSpeedAbsoluteType transSpeedAbsIn = (TransSpeedAbsoluteType) transSpeedIn;
                                TransSpeedAbsoluteType transSpeedAbsOut = new TransSpeedAbsoluteType();
                                setTransSpeedCmdOut.setCommandID(setTransSpeedCmdIn.getCommandID());
                                setTransSpeedCmdOut.setName(setTransSpeedCmdIn.getName());
                                transSpeedAbsOut.setSetting(state.filterSettings.convertLengthToServer(transSpeedAbsIn.getSetting()));
                                setTransSpeedCmdOut.setTransSpeed(transSpeedAbsOut);
                                CRCLCommandInstanceType newCommandInstance = createNewCommandInstance(setTransSpeedCmdOut, instanceIn);
                                serverSideSettingsStatus.setTransSpeedAbsolute(transSpeedAbsOut);
                                serverSideSettingsStatus.setTransSpeedRelative(null);
                                completeHandleEvent(CRCLServerSocketEvent.commandRecieved(state, newCommandInstance));
                                return true;
                            } else if (transSpeedIn instanceof TransSpeedRelativeType) {
                                TransSpeedRelativeType transSpeedRelativeIn = (TransSpeedRelativeType) transSpeedIn;
                                serverSideSettingsStatus.setTransSpeedRelative(transSpeedRelativeIn);
                                serverSideSettingsStatus.setTransSpeedAbsolute(null);
                                return false;
                            } else {
                                return false;
                            }
                        } else if (cmd instanceof SetRotSpeedType) {
                            SetRotSpeedType setRotSpeedCmdIn = (SetRotSpeedType) cmd;
                            SetRotSpeedType setRotSpeedCmdOut = new SetRotSpeedType();
                            final RotSpeedType rotSpeedIn = setRotSpeedCmdIn.getRotSpeed();
                            if (rotSpeedIn instanceof RotSpeedAbsoluteType) {
                                RotSpeedAbsoluteType rotSpeedAbsIn = (RotSpeedAbsoluteType) rotSpeedIn;
                                RotSpeedAbsoluteType rotSpeedAbsOut = new RotSpeedAbsoluteType();
                                setRotSpeedCmdOut.setCommandID(setRotSpeedCmdIn.getCommandID());
                                setRotSpeedCmdOut.setName(setRotSpeedCmdIn.getName());
                                rotSpeedAbsOut.setSetting(state.filterSettings.convertAngleToServer(rotSpeedAbsIn.getSetting()));
                                setRotSpeedCmdOut.setRotSpeed(rotSpeedAbsOut);
                                CRCLCommandInstanceType newCommandInstance = createNewCommandInstance(setRotSpeedCmdOut, instanceIn);
                                serverSideSettingsStatus.setRotSpeedAbsolute(rotSpeedAbsOut);
                                serverSideSettingsStatus.setRotSpeedRelative(null);
                                completeHandleEvent(CRCLServerSocketEvent.commandRecieved(state, newCommandInstance));
                                return true;
                            } else if (rotSpeedIn instanceof RotSpeedRelativeType) {
                                RotSpeedRelativeType rotSpeedRelativeIn = (RotSpeedRelativeType) rotSpeedIn;
                                serverSideSettingsStatus.setRotSpeedRelative(rotSpeedRelativeIn);
                                serverSideSettingsStatus.setRotSpeedAbsolute(null);
                                return false;
                            } else {
                                return false;
                            }

                        } else if (cmd instanceof SetTransAccelType) {
                            SetTransAccelType setTransAccelCmdIn = (SetTransAccelType) cmd;
                            SetTransAccelType setTransAccelCmdOut = new SetTransAccelType();
                            final TransAccelType transAccelIn = setTransAccelCmdIn.getTransAccel();
                            if (transAccelIn instanceof TransAccelAbsoluteType) {
                                TransAccelAbsoluteType transAccelAbsIn = (TransAccelAbsoluteType) transAccelIn;
                                TransAccelAbsoluteType transAccelAbsOut = new TransAccelAbsoluteType();
                                setTransAccelCmdOut.setCommandID(setTransAccelCmdIn.getCommandID());
                                setTransAccelCmdOut.setName(setTransAccelCmdIn.getName());
                                transAccelAbsOut.setSetting(state.filterSettings.convertLengthToServer(transAccelAbsIn.getSetting()));
                                setTransAccelCmdOut.setTransAccel(transAccelAbsOut);
                                CRCLCommandInstanceType newCommandInstance = createNewCommandInstance(setTransAccelCmdOut, instanceIn);
                                serverSideSettingsStatus.setTransAccelAbsolute(transAccelAbsOut);
                                serverSideSettingsStatus.setTransAccelRelative(null);
                                completeHandleEvent(CRCLServerSocketEvent.commandRecieved(state, newCommandInstance));
                                return true;
                            } else if (transAccelIn instanceof TransAccelRelativeType) {
                                TransAccelRelativeType transAccelRelativeIn = (TransAccelRelativeType) transAccelIn;
                                serverSideSettingsStatus.setTransAccelRelative(transAccelRelativeIn);
                                serverSideSettingsStatus.setTransAccelAbsolute(null);
                                return false;
                            } else {
                                return false;
                            }

                        } else if (cmd instanceof SetRotAccelType) {
                            SetRotAccelType setRotAccelCmdIn = (SetRotAccelType) cmd;
                            SetRotAccelType setRotAccelCmdOut = new SetRotAccelType();
                            final RotAccelType rotAccelIn = setRotAccelCmdIn.getRotAccel();
                            if (rotAccelIn instanceof RotAccelAbsoluteType) {
                                RotAccelAbsoluteType rotAccelAbsIn = (RotAccelAbsoluteType) rotAccelIn;
                                RotAccelAbsoluteType rotAccelAbsOut = new RotAccelAbsoluteType();
                                setRotAccelCmdOut.setCommandID(setRotAccelCmdIn.getCommandID());
                                setRotAccelCmdOut.setName(setRotAccelCmdIn.getName());
                                rotAccelAbsOut.setSetting(state.filterSettings.convertAngleToServer(rotAccelAbsIn.getSetting()));
                                setRotAccelCmdOut.setRotAccel(rotAccelAbsOut);
                                CRCLCommandInstanceType newCommandInstance = createNewCommandInstance(setRotAccelCmdOut, instanceIn);
                                serverSideSettingsStatus.setRotAccelAbsolute(rotAccelAbsOut);
                                serverSideSettingsStatus.setRotAccelRelative(null);
                                completeHandleEvent(CRCLServerSocketEvent.commandRecieved(state, newCommandInstance));
                                return true;
                            } else if (rotAccelIn instanceof RotAccelRelativeType) {
                                RotAccelRelativeType rotAccelRelativeIn = (RotAccelRelativeType) rotAccelIn;
                                serverSideSettingsStatus.setRotAccelRelative(rotAccelRelativeIn);
                                serverSideSettingsStatus.setRotAccelAbsolute(null);
                                return false;
                            } else {
                                return false;
                            }
                        }
                    }
                    if (automaticallyHandleSensorServers) {
                        if (cmd instanceof EnableSensorType) {
                            EnableSensorType enableSensorCmd = (EnableSensorType) cmd;
                            boolean errorOccured = false;
                            boolean sensorFound = false;
                            final String sensorID = enableSensorCmd.getSensorID();
                            if (sensorServers.containsKey(sensorID)) {
                                sensorFound = true;
                            } else {
                                for (SensorServerFinderInterface finder : sensorFinders) {
                                    SensorServerInterface sensorSvr = finder.findSensorServer(sensorID, enableSensorCmd.getSensorOption());
                                    if (null != sensorSvr) {
                                        SensorServerInterface oldSensorServr = this.sensorServers.get(sensorID);
                                        if (null != oldSensorServr) {
                                            try {
                                                oldSensorServr.close();
                                            } catch (Exception ex) {
                                                Logger.getLogger(CRCLServerSocket.class.getName()).log(Level.SEVERE, null, ex);
                                                commandStatus.setCommandState(CommandStateEnumType.CRCL_ERROR);
                                                errorOccured = true;
                                                commandStatus.setStateDescription(ex.getMessage());
                                            }
                                        }
                                        sensorFound = true;
                                        this.sensorServers.put(sensorID, sensorSvr);
                                        break;
                                    }
                                }
                            }
                            if (!errorOccured) {
                                if (sensorFound) {
                                    clearStateDescription(commandStatus);
                                    setDoneState();
                                } else {
                                    commandStatus.setStateDescription(sensorID + " not found.");
                                    commandStatus.setCommandState(CommandStateEnumType.CRCL_ERROR);
                                }
                            }
                            return true;
                        } else if (cmd instanceof DisableSensorType) {
                            boolean errorOccured = false;
                            boolean sensorFound = false;
                            DisableSensorType disableSensorCmd = (DisableSensorType) cmd;
                            final String sensorID = disableSensorCmd.getSensorID();
                            SensorServerInterface sensorSvr = sensorServers.remove(sensorID);
                            if (null != sensorSvr) {
                                try {
                                    sensorSvr.close();
                                    sensorFound = true;
                                } catch (Exception ex) {
                                    Logger.getLogger(CRCLServerSocket.class.getName()).log(Level.SEVERE, "", ex);
                                    commandStatus.setCommandState(CommandStateEnumType.CRCL_ERROR);
                                    errorOccured = true;
                                    commandStatus.setStateDescription(ex.getMessage());
                                }
                            }
                            if (!errorOccured) {
                                if (sensorFound) {
                                    clearStateDescription(commandStatus);
                                    setDoneState();
                                } else {
                                    commandStatus.setStateDescription(sensorID + " not found.");
                                    setDoneState();
                                }
                            }
                            return true;
                        }
                    }

                }
            } catch (Exception ex) {
                Logger.getLogger(CRCLServerSocket.class
                        .getName()).log(Level.SEVERE, "CRCLServerSocket: port=" + port + ",event=" + event, ex);
                commandStatus.setStateDescription(ex.getMessage());
                commandStatus.setCommandState(CommandStateEnumType.CRCL_ERROR);
                return true;
            }
        }
        return false;
    }

    private volatile double x;
    private volatile double y;
    private volatile double z;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        if (null != serverSideStatus) {
            PoseStatusType poseStatus = serverSideStatus.getPoseStatus();
            if (null != poseStatus) {
                PoseType pose = poseStatus.getPose();
                if (null != pose) {
                    PointType point = pose.getPoint();
                    if (null != point) {
                        point.setX(x);
                    }
                }
            }
        }
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        if (null != serverSideStatus) {
            PoseStatusType poseStatus = serverSideStatus.getPoseStatus();
            if (null != poseStatus) {
                PoseType pose = poseStatus.getPose();
                if (null != pose) {
                    PointType point = pose.getPoint();
                    if (null != point) {
                        point.setY(y);
                    }
                }
            }
        }
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
        if (null != serverSideStatus) {
            PoseStatusType poseStatus = serverSideStatus.getPoseStatus();
            if (null != poseStatus) {
                PoseType pose = poseStatus.getPose();
                if (null != pose) {
                    PointType point = pose.getPoint();
                    if (null != point) {
                        point.setZ(z);
                    }
                }
            }
        }
    }

    @SuppressWarnings({"nullness"})
    private void finishWriteStatus(STATE_TYPE state, CRCLStatusType suppliedStatus, CRCLSocket source, final CRCLServerSocketEvent<STATE_TYPE> event, final CommandStatusType commandStatus) {
        try {
            if (null != suppliedStatus) {
                PoseStatusType poseStatus = suppliedStatus.getPoseStatus();
                if (null != poseStatus) {
                    PoseType pose = poseStatus.getPose();
                    if (null != pose) {
                        PointType point = pose.getPoint();
                        if (null != point) {
                            x = point.getX();
                            y = point.getY();
                            z = point.getZ();
                        }
                    }
                }
            }
            final ConfigureStatusReportType configureStatusReport = state.filterSettings.getConfigureStatusReport();
            if (configureStatusReport == null
                    || !configureStatusReport.isReportSensorsStatus()
                    || sensorServers.isEmpty()) {
                finishWriteStatus2(state, suppliedStatus, source, event, commandStatus);
                return;
            }
            asyncCheckSensorServers()
                    .thenRun(() -> {
                        finishWriteStatus2(state, suppliedStatus, source, event, commandStatus);
                    });
        } catch (Exception ex) {
            Logger.getLogger(CRCLServerSocket.class
                    .getName()).log(Level.SEVERE, "CRCLServerSocket: port=" + port + ",event=" + event, ex);
            commandStatus.setStateDescription(ex.getMessage());
            commandStatus.setCommandState(CommandStateEnumType.CRCL_ERROR);
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings({"nullness"})
    private void finishWriteStatus2(STATE_TYPE state, CRCLStatusType suppliedStatus, CRCLSocket source, final CRCLServerSocketEvent<STATE_TYPE> event, final CommandStatusType commandStatus) {
        try {
            CRCLStatusType statusToSend;
            synchronized (suppliedStatus) {
                statusToSend = state.filterSettings.filterStatus(suppliedStatus);
                state.cmdId = statusToSend.getCommandStatus().getCommandID();
            }
            synchronized (source) {
                source.writeStatus(statusToSend);
            }
        } catch (Exception ex) {
            Logger.getLogger(CRCLServerSocket.class
                    .getName()).log(Level.SEVERE, "CRCLServerSocket: port=" + port + ",event=" + event, ex);
            commandStatus.setStateDescription(ex.getMessage());
            commandStatus.setCommandState(CommandStateEnumType.CRCL_ERROR);
            throw new RuntimeException(ex);
        }
    }

    private void clearStateDescription(final CommandStatusType commandStatus) {
        if (commandStatus.getCommandState() != CommandStateEnumType.CRCL_ERROR) {
            commandStatus.setStateDescription("");
        }
    }

    private void setDoneState() {
        if (null != serverSideStatus) {
            final CommandStatusType commandStatus = serverSideStatus.getCommandStatus();
            if (null != commandStatus) {
                if (commandStatus.getCommandState() != CommandStateEnumType.CRCL_ERROR) {
                    commandStatus.setCommandState(CommandStateEnumType.CRCL_DONE);
                }
            }
        }
    }

    private void setWorkingState() {
        if (null != serverSideStatus) {
            final CommandStatusType commandStatus = serverSideStatus.getCommandStatus();
            if (null != commandStatus) {
                if (commandStatus.getCommandState() != CommandStateEnumType.CRCL_ERROR) {
                    commandStatus.setCommandState(CommandStateEnumType.CRCL_WORKING);
                }
            }
        }
    }

    private volatile @Nullable
    Thread checkSensorsServiceThread = null;

    private static final AtomicInteger tcount = new AtomicInteger();

    private final ThreadFactory checkSensorsServiceThreadFactory
            = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, tcount.incrementAndGet() + "CRCLServerSocketCheckSensors" + getPort());
            thread.setDaemon(true);
            checkSensorsServiceThread = thread;
            return thread;
        }
    };

    private final ExecutorService checkSensorsService = Executors.newSingleThreadExecutor(checkSensorsServiceThreadFactory);

    private volatile @Nullable
    XFutureVoid lastAsyncCheckSensorServersFuture = null;

    private XFutureVoid asyncCheckSensorServers() {
        if (sensorServers.isEmpty()) {
            return XFutureVoid.completedFuture();
        }
        if (null == serverSideStatus) {
            return XFutureVoid.completedFuture();
        }
        if (null != lastAsyncCheckSensorServersFuture && !lastAsyncCheckSensorServersFuture.isDone()) {
            return lastAsyncCheckSensorServersFuture;
        }
        if (System.currentTimeMillis() - lastCheckSensorServersInternalTime < 10) {
            return XFutureVoid.completedFuture();
        }
        XFutureVoid ret = XFutureVoid.runAsync("CheckSensors", this::checkSensorServersInternal, checkSensorsService);
        lastAsyncCheckSensorServersFuture = ret;
        return ret;
    }

    private volatile long lastCheckSensorServersInternalTime = -1;

    private void checkSensorServersInternal() {
        if (null == serverSideStatus) {
            return;
        }
        final SensorStatusesType sensorStatuses;
        final List<CountSensorStatusType> countSensorStatusList;
        final List<OnOffSensorStatusType> onOffSensorStatusList;
        final List<ScalarSensorStatusType> scalarSensorStatusList;
        final List<ForceTorqueSensorStatusType> forceTorqueSensorStatusList;

        synchronized (serverSideStatus) {
            if (null == serverSideStatus.getSensorStatuses()) {
                serverSideStatus.setSensorStatuses(new SensorStatusesType());
            }
            sensorStatuses = serverSideStatus.getSensorStatuses();

            if (null != sensorStatuses) {
                countSensorStatusList = CRCLUtils.getNonNullFilteredList(sensorStatuses.getCountSensorStatus());
                onOffSensorStatusList = CRCLUtils.getNonNullFilteredList(sensorStatuses.getOnOffSensorStatus());
                scalarSensorStatusList = CRCLUtils.getNonNullFilteredList(sensorStatuses.getScalarSensorStatus());
                forceTorqueSensorStatusList = CRCLUtils.getNonNullFilteredList(sensorStatuses.getForceTorqueSensorStatus());
            } else {
                return;
            }
        }
        List<SensorStatusType> newSensorStats = new ArrayList<>();
        for (SensorServerInterface sensorServer : sensorServers.values()) {
            SensorStatusType sensorStat = sensorServer.getCurrentSensorStatus();
            if (null != sensorStat) {
                newSensorStats.add(sensorStat);
            }
        }
        boolean onOffStatusListModified = updateOnOffList(newSensorStats, onOffSensorStatusList);
        boolean countStatusListModified = updateCountList(newSensorStats, countSensorStatusList);
        boolean scalarStatusListModified = updateScalarList(newSensorStats, scalarSensorStatusList);
        boolean forceTorqueStatusListModified = updateForceTorqueList(newSensorStats, forceTorqueSensorStatusList);
        boolean modified = onOffStatusListModified || countStatusListModified || scalarStatusListModified || forceTorqueStatusListModified;
        if (modified) {
            synchronized (serverSideStatus) {
                completeCheckSensorServersInternal(sensorStatuses, onOffStatusListModified, onOffSensorStatusList, countStatusListModified, countSensorStatusList, scalarStatusListModified, scalarSensorStatusList, forceTorqueStatusListModified, forceTorqueSensorStatusList);
            }
        }
        lastCheckSensorServersInternalTime = System.currentTimeMillis();
    }

    @SuppressWarnings({"nullness", "initialization"})
    private void completeCheckSensorServersInternal(final SensorStatusesType sensorStatuses, boolean onOffStatusListModified, final List<OnOffSensorStatusType> onOffSensorStatusList, boolean countStatusListModified, final List<CountSensorStatusType> countSensorStatusList, boolean scalarStatusListModified, final List<ScalarSensorStatusType> scalarSensorStatusList, boolean forceTorqueStatusListModified, final List<ForceTorqueSensorStatusType> forceTorqueSensorStatusList) {
        final List<OnOffSensorStatusType> onOffSensorStatus = sensorStatuses.getOnOffSensorStatus();
        final List<CountSensorStatusType> countSensorStatus = sensorStatuses.getCountSensorStatus();
        final List<ScalarSensorStatusType> scalarSensorStatus = sensorStatuses.getScalarSensorStatus();
        final List<ForceTorqueSensorStatusType> forceTorqueSensorStatus = sensorStatuses.getForceTorqueSensorStatus();

        if (onOffStatusListModified && null != onOffSensorStatus) {
            onOffSensorStatus.clear();
            onOffSensorStatus.addAll(onOffSensorStatusList);
        }
        if (countStatusListModified && null != countSensorStatus) {
            countSensorStatus.clear();
            countSensorStatus.addAll(countSensorStatusList);
        }
        if (scalarStatusListModified && null != scalarSensorStatus) {
            scalarSensorStatus.clear();
            scalarSensorStatus.addAll(scalarSensorStatusList);
        }
        if (forceTorqueStatusListModified && null != forceTorqueSensorStatus) {
            forceTorqueSensorStatus.clear();
            forceTorqueSensorStatus.addAll(forceTorqueSensorStatusList);
        }
    }

    public boolean updateOnOffList(
            List<SensorStatusType> newSensorStats,
            List<OnOffSensorStatusType> onOffSensorStatusList) {
        boolean onOffStatusListModified = false;
        for (int newSensorStatsIndex = 0; newSensorStatsIndex < newSensorStats.size(); newSensorStatsIndex++) {
            SensorStatusType newSensorStat = newSensorStats.get(newSensorStatsIndex);
            for (int i = 0; i < onOffSensorStatusList.size(); i++) {
                OnOffSensorStatusType onOffSensorStat = onOffSensorStatusList.get(i);
                if (Objects.equals(newSensorStat.getSensorID(), onOffSensorStat.getSensorID())) {
                    onOffSensorStatusList.remove(i);
                    onOffStatusListModified = true;
                    break;
                }
            }
            if (newSensorStat instanceof OnOffSensorStatusType) {
                OnOffSensorStatusType onOffSensorStat = (OnOffSensorStatusType) newSensorStat;
                onOffSensorStatusList.add(onOffSensorStat);
                onOffStatusListModified = true;
            }

        }
        return onOffStatusListModified;
    }

    public boolean updateCountList(
            List<SensorStatusType> newSensorStats,
            List<CountSensorStatusType> countSensorStatusList) {
        boolean countStatusListModified = false;
        for (int newSensorStatsIndex = 0; newSensorStatsIndex < newSensorStats.size(); newSensorStatsIndex++) {
            SensorStatusType newSensorStat = newSensorStats.get(newSensorStatsIndex);
            for (int i = 0; i < countSensorStatusList.size(); i++) {
                CountSensorStatusType countSensorStat = countSensorStatusList.get(i);
                if (Objects.equals(newSensorStat.getSensorID(), countSensorStat.getSensorID())) {
                    countSensorStatusList.remove(i);
                    countStatusListModified = true;
                    break;
                }
            }
            if (newSensorStat instanceof CountSensorStatusType) {
                CountSensorStatusType countSensorStat = (CountSensorStatusType) newSensorStat;
                countSensorStatusList.add(countSensorStat);
                countStatusListModified = true;
            }

        }
        return countStatusListModified;
    }

    public boolean updateScalarList(
            List<SensorStatusType> newSensorStats,
            List<ScalarSensorStatusType> scalarSensorStatusList) {
        boolean scalarStatusListModified = false;
        for (int newSensorStatsIndex = 0; newSensorStatsIndex < newSensorStats.size(); newSensorStatsIndex++) {
            SensorStatusType newSensorStat = newSensorStats.get(newSensorStatsIndex);
            for (int i = 0; i < scalarSensorStatusList.size(); i++) {
                ScalarSensorStatusType scalarSensorStat = scalarSensorStatusList.get(i);
                if (Objects.equals(newSensorStat.getSensorID(), scalarSensorStat.getSensorID())) {
                    scalarSensorStatusList.remove(i);
                    scalarStatusListModified = true;
                    break;
                }
            }
            if (newSensorStat instanceof ScalarSensorStatusType) {
                ScalarSensorStatusType scalarSensorStat = (ScalarSensorStatusType) newSensorStat;
                scalarSensorStatusList.add(scalarSensorStat);
                scalarStatusListModified = true;
            }
        }
        return scalarStatusListModified;
    }

    public boolean updateForceTorqueList(
            List<SensorStatusType> newSensorStats,
            List<ForceTorqueSensorStatusType> forceTorqueSensorStatusList) {
        boolean forceTorqueStatusListModified = false;
        for (int newSensorStatsIndex = 0; newSensorStatsIndex < newSensorStats.size(); newSensorStatsIndex++) {
            SensorStatusType newSensorStat = newSensorStats.get(newSensorStatsIndex);
            for (int i = 0; i < forceTorqueSensorStatusList.size(); i++) {
                ForceTorqueSensorStatusType forceTorqueSensorStat = forceTorqueSensorStatusList.get(i);
                if (Objects.equals(newSensorStat.getSensorID(), forceTorqueSensorStat.getSensorID())) {
                    forceTorqueSensorStatusList.remove(i);
                    forceTorqueStatusListModified = true;
                    break;
                }
            }
            if (newSensorStat instanceof ForceTorqueSensorStatusType) {
                ForceTorqueSensorStatusType forceTorqueSensorStat = (ForceTorqueSensorStatusType) newSensorStat;
                forceTorqueSensorStatusList.add(forceTorqueSensorStat);
                forceTorqueStatusListModified = true;
            }

        }
        return forceTorqueStatusListModified;
    }

    private CRCLCommandInstanceType createNewCommandInstance(CRCLCommandType newCRCLCommand, final CRCLCommandInstanceType instanceIn) {
        CRCLCommandInstanceType newCommandInstance = new CRCLCommandInstanceType();
        newCommandInstance.setCRCLCommand(newCRCLCommand);
        newCommandInstance.setName(instanceIn.getName());
        newCommandInstance.setProgramFile(instanceIn.getProgramFile());
        newCommandInstance.setProgramIndex(instanceIn.getProgramIndex());
        newCommandInstance.setProgramLength(instanceIn.getProgramLength());
        return newCommandInstance;
    }

    private void completeHandleEvent(final CRCLServerSocketEvent<STATE_TYPE> event) throws InterruptedException {
        if (event.getEventType() == CRCLServerSocketEventType.CRCL_COMMAND_RECIEVED) {
            CRCLCommandInstanceType instanceIn = event.getInstance();
            if (null == instanceIn) {
                throw new NullPointerException("event.getInstance() ==null : event=" + event);
            }
            STATE_TYPE state = event.getState();
            if (null == state) {
                throw new NullPointerException("event.getState() ==null : event=" + event);
            }
            CRCLCommandType cmd = getNonNullCmd(instanceIn);
            final List<GuardType> cmdGuardList = getNonNullFilteredList(cmd.getGuard());
            if (null != cmdGuardList && !cmdGuardList.isEmpty()) {
                startCheckingGuards(cmdGuardList, state, cmd.getCommandID(), instanceIn);
            }
        }
        for (int i = 0; i < listeners.size(); i++) {
            if (closing) {
                return;
            }
            final CRCLServerSocketEventListener<STATE_TYPE> l = listeners.get(i);
            if (null == callbackService) {
                l.accept(event);
            } else {
                callbackService.submit(new Runnable() {
                    @Override
                    public void run() {
                        if (closing) {
                            return;
                        }
                        l.accept(event);
                    }
                });
            }
        }
        if (queueEvents && !closing) {
            queue.put(event);
        }
        if (event.getException() instanceof SocketException
                || event.getException() instanceof EOFException) {
            try {
                CRCLSocket source = event.getSource();
                if (null != source) {
                    source.close();

                }
            } catch (IOException ex) {
                Logger.getLogger(CRCLServerSocket.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0; i < clients.size(); i++) {
                CRCLServerClientInfo c = clients.get(i);
                if (Objects.equals(c.getSocket(), event.getSource())) {
                    clients.remove(c);
                }
            }
            throw new InterruptedException("Closing socket due to " + event.getException());
        }
        return;
    }

    public CRCLServerSocketEvent waitForEvent() throws InterruptedException {
        if (!started && !isRunning()) {
            throw new IllegalStateException("CRCLServerSocket must be running/started before call to waitForEvent.");
        }
        if (!queueEvents) {
            throw new IllegalStateException("queueEvents should be set before call to waitForEvent.");

        }
        return queue.take();
    }

    public List<CRCLServerSocketEvent<STATE_TYPE>> checkForEvents() {
        List<CRCLServerSocketEvent<STATE_TYPE>> l = new ArrayList<>();
        queue.drainTo(l);
        return l;
    }

    private @MonotonicNonNull
    Selector selector;

    /**
     * Get the value of selector
     *
     * @return the value of selector
     */
    public @Nullable
    Selector getSelector() {
        return selector;
    }

    /**
     * Set the value of selector
     *
     * @param selector new value of selector
     */
    public void setSelector(Selector selector) {
        if (isRunning()) {
            throw new IllegalStateException("Can not set field when server is running.");
        }
        this.selector = selector;
    }

    /*@Nullable*/
    private SocketAddress localAddress;

    /*@Nullable*/
    private InetAddress bindAddress;

    /**
     * Get the value of bindAddress
     *
     * @return the value of bindAddress
     */
    /*@Nullable*/
    public InetAddress getBindAddress() {
        return bindAddress;
    }

    /**
     * Set the value of bindAddress
     *
     * @param bindAddress new value of bindAddress
     */
    public void setBindAddress(InetAddress bindAddress) {
        if (isRunning()) {
            throw new IllegalStateException("Can not set field when server is running.");
        }
        this.bindAddress = bindAddress;
    }

    private static int runcount = 0;

    private boolean multithreaded;

    /**
     * Get the value of multithreaded
     *
     * @return the value of multithreaded
     */
    public boolean isMultithreaded() {
        return multithreaded;
    }

    /**
     * Set the value of multithreaded
     *
     * @param multithreaded new value of multithreaded
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public void setMultithreaded(boolean multithreaded) throws IOException, InterruptedException {
        if (isRunning()) {
            throw new IllegalStateException("Can not set field when server is running.");
        }
        this.multithreaded = multithreaded;
        if (multithreaded) {
            if (null != serverSocketChannel) {
                serverSocketChannel.close();
            }
            if (null != selector) {
                selector.close();
            }
        } else {
            if (null != serverSocket) {
                serverSocket.close();
            }
            if (null != executorService) {
                executorService.shutdownNow();
                executorService.awaitTermination(1, TimeUnit.SECONDS);
            }
        }
    }

    private volatile boolean running = false;

    public boolean isRunning() {
        return running;
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            runServer();
        }
    };

    public Runnable getRunnable() {
        return runnable;
    }

    public static class TraceInfo {

        final Thread thread;
        final String threadname;
        final StackTraceElement[] trace;
        final long time;

        public TraceInfo() {
            this.thread = Thread.currentThread();
            this.threadname = thread.getName();
            this.trace = thread.getStackTrace();
            this.time = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return "TraceInfo{" + "thread=" + thread + ", time=" + time + " (" + (System.currentTimeMillis() - time) + " ago)" + ", threadname=" + threadname + ",\n trace=" + CRCLUtils.traceToString(trace) + "}\n";
        }
    }

    private static final ConcurrentLinkedDeque<TraceInfo> runServerTraces
            = new ConcurrentLinkedDeque<>();

    public final void runServer() {

        runServerTraces.add(new TraceInfo());
        if (isRunning()) {
            throw new IllegalStateException("Can not start again when server is already running.");
        }
        running = true;
        try {
            if (this.closing) {
                return;
            }
            runcount++;
            for (CRCLServerClientInfo crclSocket : clients) {
                crclSocket.close();
            }
            clients.clear();
            if (null == localAddress) {
                localAddress = new InetSocketAddress(port);
            }
            if (multithreaded) {
                runMultiThreaded();
            } else {
                runSingleThreaded();
            }
        } catch (Throwable ex) {
            running = false;

            if (!closing) {
                Logger.getLogger(CRCLServerSocket.class
                        .getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        } finally {
            running = false;
        }
    }

    private @MonotonicNonNull
    UnitsTypeSet serverUnits = null;

    /**
     * Get the value of serverUnits
     *
     * @return the value of serverUnits
     */
    public @Nullable
    UnitsTypeSet getServerUnits() {
        return serverUnits;
    }

    /**
     * Set the value of serverUnits
     *
     * @param serverUnits new value of serverUnits
     */
    public void setServerUnits(UnitsTypeSet serverUnits) {
        this.serverUnits = serverUnits;
    }

    private @MonotonicNonNull
    UnitsScaleSet serverToClientScales = null;

    /**
     * Get the value of serverToClientScales
     *
     * @return the value of serverToClientScales
     */
    public @Nullable
    UnitsScaleSet getServerToClientScales() {
        return serverToClientScales;
    }

    /**
     * Set the value of serverToClientScales
     *
     * @param serverToClientScales new value of serverToClientScales
     */
    public void setServerToClientScales(UnitsScaleSet serverToClientScales) {
        this.serverToClientScales = serverToClientScales;
    }

    private volatile @MonotonicNonNull
    Schema cmdSchema = null;

    private volatile File @Nullable [] cmdSchemaFiles
            = null;

    private volatile @MonotonicNonNull
    Schema statSchema = null;

    public @Nullable
    Schema getCmdSchema() {
        return cmdSchema;
    }

    public void setCmdSchema(Schema cmdSchema) {
        this.cmdSchema = cmdSchema;
    }

    public File @Nullable [] getCmdSchemaFiles() {
        return cmdSchemaFiles;
    }

    public void setCmdSchemaFiles(File[] cmdSchemaFiles) {
        this.cmdSchemaFiles = cmdSchemaFiles;
    }

    public @Nullable
    Schema getStatSchema() {
        return statSchema;
    }

    public void setStatSchema(Schema statSchema) {
        this.statSchema = statSchema;
    }

    private final IdentityHashMap<CRCLSocket, STATE_TYPE> socketToStateMap
            = new IdentityHashMap<>();

    private boolean sendAllJointPositionsByDefault = true;

    /**
     * Get the value of sendAllJointPositionsByDefault
     *
     * @return the value of sendAllJointPositionsByDefault
     */
    public boolean isSendAllJointPositionsByDefault() {
        return sendAllJointPositionsByDefault;
    }

    /**
     * Set the value of sendAllJointPositionsByDefault
     *
     * @param sendAllJointPositionsByDefault new value of
     * sendAllJointPositionsByDefault
     */
    public void setSendAllJointPositionsByDefault(boolean sendAllJointPositionsByDefault) {
        this.sendAllJointPositionsByDefault = sendAllJointPositionsByDefault;
    }

    private volatile StackTraceElement bindTrace @Nullable []  = null;
    private volatile @Nullable
    Thread bindThread = null;
    private volatile boolean startingSocketChannel = false;

    public boolean isStartingSocketChannel() {
        return startingSocketChannel;
    }

    private void runSingleThreaded() throws Exception {
        StackTraceElement initBindTrace @Nullable []  = this.bindTrace;
        @Nullable
        Thread initBindThread = this.bindThread;
        ServerSocketChannel channelForRun = this.serverSocketChannel;

        if (null == channelForRun) {
            SocketAddress localAddressToBind = this.localAddress;
            if (null == localAddressToBind) {
                throw new IllegalStateException("null == localAddressToBind");
            }
            try {

                channelForRun = (ServerSocketChannel) ServerSocketChannel.open()
                        .bind(localAddressToBind)
                        .setOption(StandardSocketOptions.SO_REUSEADDR, true)
                        .configureBlocking(false);
                bindTrace = Thread.currentThread().getStackTrace();
                bindThread = Thread.currentThread();
                this.serverSocketChannel = channelForRun;
                startingSocketChannel = false;
            } catch (BindException bindException) {
                System.out.println("");
                System.out.flush();
                System.err.println("");
                System.err.flush();
                if (localAddressToBind instanceof InetSocketAddress) {
                    InetSocketAddress loclaInetSocketAddress = (InetSocketAddress) localAddressToBind;
                    System.err.println("localAdrress = " + loclaInetSocketAddress.getHostString() + ":" + loclaInetSocketAddress.getPort());
                }

                System.err.println("start runServerTraces");
                for (TraceInfo ti : runServerTraces) {
                    System.err.println("ti = " + ti);
                }
                System.err.println("end runServerTraces");
                System.err.println("");

                System.err.println("start startServerTraces");
                for (TraceInfo ti : startServerTraces) {
                    System.err.println("ti = " + ti);
                }
                System.err.println("end startServerTraces");

                CRCLServerSocket otherServer = portMap.get(port);
                System.err.println("otherServer = " + otherServer);
                if (null != otherServer) {
                    final boolean sameServer = otherServer == this;
                    System.out.println("(otherServer==this) = " + sameServer);
                    if (!sameServer) {
                        System.out.println("otherServer.startTrace = " + CRCLUtils.traceToString(otherServer.startTrace));
                        System.out.println("otherServer.startThread = " + otherServer.startThread);
                        System.out.println("otherServer.bindTrace = " + CRCLUtils.traceToString(otherServer.bindTrace));
                        System.out.println("otherServer.bindThread = " + otherServer.bindThread);
                    }
                }
                System.err.println("portMap = " + portMap);

                System.err.println("Thread.currentThread() = " + Thread.currentThread());
                System.err.println("startTrace = " + CRCLUtils.traceToString(startTrace));
                System.err.println("bindTrace = " + CRCLUtils.traceToString(bindTrace));
                System.out.println("bindThread = " + bindThread);
                System.err.println("initBindTrace = " + CRCLUtils.traceToString(initBindTrace));
                System.out.println("initBindThread = " + bindThread);

                System.out.println("");
                System.out.flush();
                System.err.println("");
                System.err.flush();
                throw new IOException(bindException);
            }
        }
        Selector selectorForRun = this.selector;
        if (null == selectorForRun) {
            selectorForRun = Selector.open();
            this.selector = selectorForRun;
        }
        if (null == channelForRun) {
            throw new IllegalStateException("serverSocketChannel==null");
        }

        if (null == selectorForRun) {
            throw new IllegalStateException("selectorForRun==null");
        }
        channelForRun.register(selectorForRun, SelectionKey.OP_ACCEPT);
        while (!closing && !Thread.currentThread().isInterrupted()) {
            selectorForRun.select();
            SelectionKey keys[] = getAndClearKeys(selectorForRun);
            for (int i = 0; i < keys.length; i++) {
                SelectionKey key = keys[i];

                if (key.channel() == channelForRun) {
                    SocketChannel clientSocketChannel
                            = channelForRun.accept();
                    if (null == clientSocketChannel) {
                        System.out.println("key = " + key);
                    } else {
                        clientSocketChannel
                                = (SocketChannel) clientSocketChannel.configureBlocking(false);
                        CRCLSocket crclSocket = CRCLSocket.newCRCLSocketForSocketChannel(clientSocketChannel);
                        if (null != statSchema) {
                            crclSocket.setStatSchema(statSchema);
                        }
                        if (null != cmdSchema) {
                            crclSocket.setCmdSchema(cmdSchema);
                            crclSocket.setCmdSchema(cmdSchema);
                        }

                        SelectionKey newKey
                                = clientSocketChannel.register(selectorForRun, SelectionKey.OP_READ, crclSocket);
                        STATE_TYPE state = generateNewClientState(crclSocket);
                        if (null == state) {
                            throw new IllegalStateException("stateGenerator.generate(crclSocket) returned null");
                        }
                        clients.add(new CRCLServerClientInfo(crclSocket, null, state));
                        socketToStateMap.put(crclSocket, state);
                        handleEvent(CRCLServerSocketEvent.newClient(state));
                    }
                } else {
                    CRCLSocket crclSocket = (CRCLSocket) key.attachment();
                    STATE_TYPE state = socketToStateMap.get(crclSocket);
                    if (null == state) {
                        throw new IllegalStateException("ssocketToStateMap.get(crclSocket) returned null");
                    }
                    List<CRCLCommandInstanceType> cmdInstances;

                    try {
                        SocketChannel s = ((SocketChannel) key.channel());
                        try {
                            ByteBuffer bb = ByteBuffer.allocate(4096);
                            int readbytes = s.read(bb);
                            if (readbytes > 0) {
                                String string = new String(bb.array(), 0, readbytes);
                                cmdInstances = crclSocket.parseMultiCommandString(string, validate);
                                for (CRCLCommandInstanceType instance : cmdInstances) {
                                    handleEvent(CRCLServerSocketEvent.commandRecieved(state, instance));
                                }
                            } else {
                                try {
                                    socketToStateMap.remove(crclSocket);
                                    s.close();
                                    key.cancel();
                                    for (int j = 0; j < clients.size(); j++) {
                                        CRCLServerClientInfo c = clients.get(j);
                                        if (Objects.equals(c.getSocket(), crclSocket)) {
                                            clients.remove(c);
                                        }
                                    }
                                } catch (Exception exception) {
                                    handleEvent(CRCLServerSocketEvent.exceptionOccured(state, exception));
                                }
                            }
                        } catch (Exception ex) {
                            if (!closing && (!(ex instanceof ClosedChannelException) && !(ex.getCause() instanceof ClosedChannelException))) {
                                final Logger logger = Logger
                                        .getLogger(CRCLServerSocket.class
                                                .getName());
                                logger.log(Level.SEVERE, "EXCEPTION: " + ex.getMessage() + ", startTrace = " + CRCLUtils.traceToString(startTrace));
                                final String exInfoString = " port =" + port + ",closing = " + closing + ",key=" + key;
                                logger.log(Level.SEVERE, "EXCEPTION: " + ex.getMessage() + exInfoString);
                                logger.log(Level.SEVERE, "", ex);
                            }
                            handleEvent(CRCLServerSocketEvent.exceptionOccured(state, ex));
                            try {
                                s.close();
                                key.cancel();

                            } catch (Exception exception) {
                                handleEvent(CRCLServerSocketEvent.exceptionOccured(state, exception));
                            }

                            try {
                                crclSocket.close();
                                socketToStateMap.remove(crclSocket);
                                for (int j = 0; j < clients.size(); j++) {
                                    CRCLServerClientInfo c = clients.get(j);
                                    if (Objects.equals(c.getSocket(), crclSocket)) {
                                        clients.remove(c);
                                    }
                                }
                            } catch (Exception exception) {
                                handleEvent(CRCLServerSocketEvent.exceptionOccured(state, exception));
                            }
                        }
                    } catch (Exception ex2) {
                        if (!closing && (!(ex2 instanceof ClosedChannelException) && !(ex2.getCause() instanceof ClosedChannelException))) {
                            final Logger logger = Logger
                                    .getLogger(CRCLServerSocket.class
                                            .getName());
                            logger.log(Level.SEVERE, "port =" + port + ",closing = " + closing, ex2);
                        }
                        handleEvent(CRCLServerSocketEvent.exceptionOccured(state, ex2));
                    }
                }
            }
        }
    }

    private void setupNewClientState(STATE_TYPE state) {
        if (null != updateStatusSupplier) {
            XFuture<CRCLStatusType> supplierFuture = updateStatusSupplier.get();
            supplierFuture.thenRun(() -> completeSetupNewClientState(state));
        } else {
            completeSetupNewClientState(state);
        }
    }

    private synchronized void completeSetupNewClientState(STATE_TYPE state) {
        if (automaticallyConvertUnits && null != serverToClientScales) {
            state.filterSettings.setServerToClientScaleSet(serverToClientScales);
        }
        if (automaticallyConvertUnits && null != serverUnits) {
            state.filterSettings.setServerUserSet(serverUnits);
        }
        if (sendAllJointPositionsByDefault && null != serverSideStatus) {
            final JointStatusesType jointStatuses = serverSideStatus.getJointStatuses();
            if (null != jointStatuses) {
                final List<JointStatusType> jointStatusList
                        = getNonNullFilteredList(jointStatuses.getJointStatus());
                List<ConfigureJointReportType> cjrList = new ArrayList<>();
                for (int j = 0; j < jointStatusList.size(); j++) {
                    JointStatusType jst = jointStatusList.get(j);
                    ConfigureJointReportType cjr = new ConfigureJointReportType();
                    cjr.setJointNumber(jst.getJointNumber());
                    cjr.setReportPosition(true);
                    cjrList.add(cjr);
                }
                state.filterSettings.configureJointReports(cjrList);
            }
        }
    }

    @SuppressWarnings("nullness")
    private SelectionKey[] getAndClearKeys(Selector selectorForRun) {
        SelectionKey keys[] = new SelectionKey[0];
        synchronized (selectorForRun) {
            Set<SelectionKey> keySet = selectorForRun.selectedKeys();
            synchronized (keySet) {
                keys = keySet.toArray(keys);
                keySet.removeAll(Arrays.asList(keys));
            }
        }
        return keys;
    }

    private int backlog = 0;

    /**
     * Get the value of backlog
     *
     * @return the value of backlog
     */
    public int getBacklog() {
        return backlog;
    }

    /**
     * Set the value of backlog
     *
     * @param backlog new value of backlog
     * @throws java.io.IOException
     */
    public void setBacklog(int backlog) throws IOException {
        if (isRunning()) {
            throw new IllegalStateException("Can not start again when server is already running.");
        }
        this.backlog = backlog;
        if (serverSocket != null) {
            serverSocket.close();
        }
        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
    }

    private Socket acceptFromServerSocket() throws SocketException, IOException {
        ServerSocket acceptServerSocket = this.serverSocket;
        if (null == acceptServerSocket) {
            throw new IllegalStateException("serverSocket == null");
        }
        // This is an anoying hack around the problem that you can't rely on
        // serverSocket.accept() being interrupted.
        // This only generally needs to happen when shuting down the server
        int orig_timeout = acceptServerSocket.getSoTimeout();
        acceptServerSocket.setSoTimeout(500);
        Socket ret = null;
        boolean interrupted = false;
        try {
            interrupted = interrupted || Thread.currentThread().isInterrupted();
            while (ret == null && !closing && !interrupted) {
                try {
                    ret = acceptServerSocket.accept();
                } catch (SocketTimeoutException ignored) {
                }
            }
        } finally {
            acceptServerSocket.setSoTimeout(orig_timeout);
        }
        if (null == ret) {
            throw new IllegalStateException("acceptFromServerSocket returned null,closing=" + closing + ",interrupted=" + interrupted);
        }
        return ret;
    }

    private void runMultiThreaded() throws Exception {

        if (null != serverSocketChannel) {
            serverSocketChannel.close();
        }
        if (null != selector) {
            selector.close();
        }

        ExecutorService runMultiExecutorService = initExecutorService();
        if (null == runMultiExecutorService) {
            throw new IllegalStateException("null == runMultiExcututorService");
        }
        if (null == serverSocket) {
            bindTrace = Thread.currentThread().getStackTrace();
            bindThread = Thread.currentThread();
            if (null != bindAddress) {
                serverSocket = new ServerSocket(port, backlog, bindAddress);
            } else {
                serverSocket = new ServerSocket(port);
            }
        }
        serverSocket.setReuseAddress(true);
        while (!closing && !Thread.currentThread().isInterrupted()) {

            Socket socket = acceptFromServerSocket();
            if (null == socket) {
                return;
            }
            final CRCLSocket crclSocket = CRCLSocket.newCRCLSocketForSocketSchemas(socket, cmdSchema, statSchema, null);
            STATE_TYPE state = generateNewClientState(crclSocket);
            Future<?> future = runMultiExecutorService.submit(createHandleClientThreadRunnable(state));
            clients.add(new CRCLServerClientInfo(crclSocket, future, state));
            handleEvent(CRCLServerSocketEvent.newClient(state));
        }
    }

    private STATE_TYPE generateNewClientState(final CRCLSocket crclSocket) {
        STATE_TYPE state = stateGenerator.generate(crclSocket);
        setupNewClientState(state);
        return state;
    }

    private Runnable createHandleClientThreadRunnable(final STATE_TYPE state) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    CRCLSocket crclSocket = state.getCs();
                    while (!closing
                            && crclSocket.isConnected()
                            && !Thread.currentThread().isInterrupted()) {
                        try {
                            CRCLCommandInstanceType instance = crclSocket.readCommand(validate);
                            handleEvent(CRCLServerSocketEvent.commandRecieved(state, instance));
                        } catch (Exception ex) {
                            handleEvent(CRCLServerSocketEvent.exceptionOccured(state, ex));

                        }
                    }
                } catch (Exception ex1) {
                    Logger.getLogger(CRCLServerSocket.class
                            .getName()).log(Level.SEVERE, null, ex1);
                }
            }
        };
    }
    private String threadNamePrefix = "CRCLServerSocket";

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    private @Nullable
    Supplier<XFuture<CRCLStatusType>> updateStatusSupplier = null;

    public @Nullable
    Supplier<XFuture<CRCLStatusType>> getUpdateStatusSupplier() {
        return updateStatusSupplier;
    }

    public void setUpdateStatusSupplier(Supplier<XFuture<CRCLStatusType>> updateStatusSupplier) {
        this.updateStatusSupplier = updateStatusSupplier;
    }

    private volatile int updateStatusRunCount = 0;

    private ExecutorService initExecutorService() {
        ExecutorService es = this.executorService;
        if (null != es) {
            return es;
        }
        ExecutorService newExecutorService = Executors.newCachedThreadPool(new ThreadFactory() {

            int num = 0;

            @Override
            public Thread newThread(Runnable r) {
                num++;
                Thread t = new Thread(r, threadNamePrefix + "_" + port + "_" + runcount + "_" + num);
//                        t.setDaemon(true);
                return t;
            }
        });
        this.executorService = newExecutorService;
        return newExecutorService;
    }

    public CRCLServerSocket(CRCLServerSocketStateGenerator<STATE_TYPE> stateGenerator) throws IOException {
        this(CRCLSocket.DEFAULT_PORT, stateGenerator);
    }

    public CRCLServerSocket(int port, CRCLServerSocketStateGenerator<STATE_TYPE> stateGenerator) throws IOException {
        this(port, 0, stateGenerator);
    }

    public CRCLServerSocket(int port, int backlog, CRCLServerSocketStateGenerator<STATE_TYPE> stateGenerator) throws IOException {
        this(port, backlog, (new InetSocketAddress(port)).getAddress(), stateGenerator);
    }

    public CRCLServerSocket(int port, int backlog, InetAddress addr, CRCLServerSocketStateGenerator<STATE_TYPE> stateGenerator) throws IOException {
        this(port, backlog, addr, Boolean.getBoolean("CRCLServerSocket.multithreaded"), stateGenerator);
    }

    private static <STATE_TYPE extends CRCLServerClientState> Class<STATE_TYPE> classFromGenerator(CRCLServerSocketStateGenerator<STATE_TYPE> stateGenerator) {
        try {
            Class<?> stateGeneratorClass = stateGenerator.getClass();
            Method genMethod = stateGeneratorClass.getMethod("generate", CRCLSocket.class
            );
            @SuppressWarnings("unchecked")
            Class<STATE_TYPE> stateClassFromReturnType = (Class<STATE_TYPE>) ((Object) genMethod.getReturnType());
            return stateClassFromReturnType;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private volatile boolean automaticallyHandleGuards = true;

    public boolean isAutomaticallyHandleGuards() {
        return automaticallyHandleGuards;
    }

    public void setAutomaticallyHandleGuards(boolean automaticallyHandleGuards) {
        this.automaticallyHandleGuards = automaticallyHandleGuards;
    }

    private @Nullable
    Executor handleGuardsExecutor = null;

    public @Nullable
    Executor getHandleGuardsExecutor() {
        return handleGuardsExecutor;
    }

    public void setHandleGuardsExecutor(Executor handleGuardsExecutor) {
        this.handleGuardsExecutor = handleGuardsExecutor;
    }

    private @Nullable
    SensorStatusType getNewSensorStatus(String id) {
        SensorServerInterface sensorServer = this.sensorServers.get(id);
        if (sensorServer == null) {
            return null;
        }
        return sensorServer.getCurrentSensorStatus();
    }

    private boolean checkGuardsOnce(List<GuardType> guards,
            STATE_TYPE guard_client_state,
            long cmdID,
            CRCLCommandInstanceType commandInstance,
            Map<String, Double> guardInitialValues,
            int count,
            long time) throws Exception {
        if (null == serverSideStatus) {
            return false;
        }
        final CommandStatusType serverSideCommandStatus = serverSideStatus.getCommandStatus();
        if (null == serverSideCommandStatus) {
            return false;
        }
        if (serverSideCommandStatus.getCommandID() != cmdID) {
            return false;
        }
        if (serverSideCommandStatus.getCommandState() != CommandStateEnumType.CRCL_WORKING) {
            return false;
        }
        Map<String, SensorStatusType> sensorStatMap = new HashMap<>();
        for (GuardType guard : guards) {
            if (isClosed()) {
                return false;
            }
            if (serverSideStatus.getCommandStatus() != serverSideCommandStatus) {
                return false;
            }
            if (serverSideCommandStatus.getCommandID() != cmdID) {
                return false;
            }
            if (serverSideCommandStatus.getCommandState() != CommandStateEnumType.CRCL_WORKING) {
                return false;
            }
            double value = getGuardValue(guard, sensorStatMap);
            final String guardMapId = guardMapId(guard);
            final GuardLimitEnumType guarLimitType = Objects.requireNonNull(guard.getLimitType());
            switch (guarLimitType) {
                case OVER_MAX:
                    if (value > guard.getLimitValue()) {
                        triggerGuard(value, guard_client_state, commandInstance, guard);
                    }
                    break;

                case UNDER_MIN:
                    if (value < guard.getLimitValue()) {
                        triggerGuard(value, guard_client_state, commandInstance, guard);
                    }
                    break;

                case DECREASE_BEYOND_LIMIT: {
                    Double initialValue = guardInitialValues.get(guardMapId);
                    if (null == initialValue) {
                        throw new NullPointerException("guardInitialValues.get(guardMapId) == null : guardMapId=" + guardMapId + ", guardInitialValues=" + guardInitialValues);
                    }
                    double diff = initialValue - value;
                    System.out.println("guard: diff = " + diff + ", count=" + count + ", time=" + time);
                    if (diff > guard.getLimitValue()) {
                        triggerGuard(value, guard_client_state, commandInstance, guard);
                    }
                }
                break;

                case INCREASE_OVER_LIMIT: {
                    Double initialValue = guardInitialValues.get(guardMapId);
                    if (null == initialValue) {
                        throw new NullPointerException("guardInitialValues.get(guardMapId) == null : guardMapId=" + guardMapId + ", guardInitialValues=" + guardInitialValues);
                    }
                    double diff = value - initialValue;
                    if (diff > guard.getLimitValue()) {
                        triggerGuard(value, guard_client_state, commandInstance, guard);
                    }
                }
                break;
            }
        }
        return true;
    }

    private volatile long guardTriggerStartTime = -1;
    private static final int EXTRA_GUARD_SAMPLES = 10;

    private void triggerGuard(double value, STATE_TYPE guard_client_state, CRCLCommandInstanceType commandInstance, GuardType guard) throws Exception {
        if (null == serverSideStatus) {
            throw new RuntimeException("null == serverSideStatus)");
        }
        final GuardsStatusesType initialGuardsStatuses = serverSideStatus.getGuardsStatuses();
        final GuardsStatusesType guardsStatuses;
        if (null == initialGuardsStatuses) {
            final GuardsStatusesType newGuardsStatusesType = new GuardsStatusesType();
            serverSideStatus.setGuardsStatuses(newGuardsStatusesType);
            guardsStatuses = newGuardsStatusesType;
        } else {
            guardsStatuses = initialGuardsStatuses;
        }
        if (guard.getLastCheckTime() > 0) {
            guardTriggerStartTime = guard.getLastCheckTime();
        } else {
            guardTriggerStartTime = System.currentTimeMillis();
        }
        guardsStatuses.setTriggerCount(guardsStatuses.getTriggerCount() + 1);
        guardsStatuses.setTriggerValue(value);
        if (null != serverSideStatus.getPoseStatus()) {
            final PoseType serverSidePose = CRCLPosemath.getNullablePose(serverSideStatus);
            if (null != serverSidePose) {
                final PoseType serverSidePoseCopy = copy(serverSidePose);
                if (null == serverSidePoseCopy) {
                    throw new NullPointerException("serverSidePoseCopy");
                }
                guardsStatuses.setTriggerPose(serverSidePoseCopy);
            }
        }
        handleEvent(CRCLServerSocketEvent.guardLimitReached(guard_client_state, commandInstance, guard));
        long delayMillis = DEFAULT_GUARDS_CHECK_DELAY_MILLIS;
        Long microsLong = guard.getRecheckTimeMicroSeconds();
        if (null != microsLong) {
            long micros = microsLong;
            delayMillis = micros / 1000;
        }
        for (int i = 0; i < EXTRA_GUARD_SAMPLES; i++) {

            double extraValue = getGuardValue(guard, null);
//            System.out.println("extraValue = " + extraValue + ",i = " + i + ", guardHistory = " + guardHistory);
            if (delayMillis > 0) {
                Thread.sleep(delayMillis);
            }
        }
    }

    private static String guardMapId(GuardType guard) {
        return guard.getSensorID() + "." + guard.getSubField();
    }

    public List<GuardHistoryElement> getGuardHistoryList() {
        return guardHistory.getList();
    }

    private final GuardHistory guardHistory = new GuardHistory();

    private double getGuardValue(GuardType guard, @Nullable Map<String, SensorStatusType> sensorStatMap) throws RuntimeException {

        double value;
        final String sensorID = guard.getSensorID();
        SensorStatusType stat = sensorStatMap != null ? sensorStatMap.get(sensorID) : null;
        if (null == stat && null != sensorID) {
            stat = getNewSensorStatus(sensorID);
            if (null != stat) {
                if (sensorStatMap != null) {
                    sensorStatMap.put(sensorID, stat);
                }
            } else {
                throw new RuntimeException("bad guard sensor id " + sensorID + ", sensorServers=" + sensorServers);
            }
        }
        if (stat != null) {
            final long time = stat.getLastReadTime();
            guard.setLastCheckTime(time);
            if (guard.getCheckCount() < 1) {
                guard.setCheckCount(1L);
            } else {
                guard.setCheckCount(guard.getCheckCount() + 1);
            }
            if (stat instanceof ForceTorqueSensorStatusType) {
                ForceTorqueSensorStatusType forceTorqueSensorStat = (ForceTorqueSensorStatusType) stat;
                final String subField = guard.getSubField();
                if (null == subField) {
                    throw new RuntimeException("stat instanceof ForceTorqueSensorStatusType but null == guard.getSubField() " + sensorID + ", guard=" + guard + ", stat=" + stat);
                }
                switch (subField) {
                    case "Fx":
                        value = forceTorqueSensorStat.getFx();
                        break;

                    case "Fy":
                        value = forceTorqueSensorStat.getFy();
                        break;

                    case "Fz":
                        value = forceTorqueSensorStat.getFz();
                        break;

                    case "Tx":
                        value = forceTorqueSensorStat.getTz();
                        break;

                    case "Ty":
                        value = forceTorqueSensorStat.getTy();
                        break;

                    case "Tz":
                        value = forceTorqueSensorStat.getTz();
                        break;

                    default:
                        value = 0;
                        break;

                }
            } else {
                value = 0;
            }
            guard.setLastCheckValue(value);
            if (null != guardCheckUpdatePositionOnlyRunnable) {
                guardCheckUpdatePositionOnlyRunnable.run();
            }
            guardHistory.addElement(time, value, x, y, z);
        } else {
            value = 0;
        }
        return value;
    }

    private void checkGuardsUntilDone(List<GuardType> guards,
            STATE_TYPE guard_client_state,
            long cmdID,
            CRCLCommandInstanceType commandInstance,
            long delayMillis,
            Map<String, Double> guardInitialValues) throws Exception {
        long startTime = System.currentTimeMillis();
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (null == serverSideStatus) {
                return;
            }
            if (isClosed()) {
                return;
            }
            final CommandStatusType serverSideCommandStatus = serverSideStatus.getCommandStatus();
            if (null == serverSideCommandStatus) {
                return;
            }
            if (serverSideCommandStatus.getCommandID() != cmdID) {
                return;
            }
            if (serverSideCommandStatus.getCommandState() != CommandStateEnumType.CRCL_WORKING) {
                return;
            }
            final long timeDiff = System.currentTimeMillis() - startTime;
            if (!checkGuardsOnce(guards, guard_client_state, cmdID, commandInstance, guardInitialValues, count, timeDiff)) {
                return;
            }
            count++;
            if (delayMillis > 0) {
                Thread.sleep(delayMillis);
            }
        }
    }

    private static final long DEFAULT_GUARDS_CHECK_DELAY_MILLIS = 10;

    private Runnable createGuardsCheckerRunnable(final List<GuardType> guards,
            final STATE_TYPE guard_client_state,
            final long cmdID,
            final CRCLCommandInstanceType commandInstance) {
        if (null == serverSideStatus) {
            throw new RuntimeException("null == serverSideStatus)");
        }
        long delayMillis = DEFAULT_GUARDS_CHECK_DELAY_MILLIS;
        for (int i = 0; i < guards.size(); i++) {
            final GuardType guardI = guards.get(i);
            Long microsLong = guardI.getRecheckTimeMicroSeconds();
            if (null != microsLong) {
                long micros = microsLong;
                delayMillis = micros / 1000;
            }
            if (guardI.getLimitType() == GuardLimitEnumType.INCREASE_OVER_LIMIT
                    || guardI.getLimitType() == GuardLimitEnumType.DECREASE_BEYOND_LIMIT) {
                if (guardI.getLimitValue() <= 0) {
                    Runnable r = () -> {
                        if (null == serverSideStatus) {
                            throw new RuntimeException("null == serverSideStatus)");
                        }
                        final CommandStatusType commandStatus = CRCLUtils.getNonNullCommandStatus(serverSideStatus);
                        commandStatus.setStateDescription("Invalid guard:" + guardI.getName() + " for sensor " + guardI.getSensorID() + " with limit type " + guardI.getLimitType() + " must have positive limit value. limit value=" + guardI.getLimitValue());
                        commandStatus.setCommandState(CommandStateEnumType.CRCL_ERROR);
                    };
                    r.run();
                    return r;
                }
            }
        }
        final long finalDelayMillis = delayMillis;

        return new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, SensorStatusType> sensorStatMap = new HashMap<>();
                    final Map<String, Double> newInitalialValuesMap = new HashMap<>();
                    for (int i = 0; i < guards.size(); i++) {
                        final GuardType guardI = guards.get(i);
                        newInitalialValuesMap.put(guardMapId(guardI), getGuardValue(guardI, sensorStatMap));
                    }
                    checkGuardsUntilDone(guards, guard_client_state, cmdID, commandInstance, finalDelayMillis, newInitalialValuesMap);
                } catch (Exception ex) {
                    final String errMsg;
                    if (null != commandInstance) {
                        final CRCLCommandType crclCommand = commandInstance.getCRCLCommand();
                        errMsg = "commandInstance=" + CRCLSocket.commandToSimpleString(crclCommand);
                    } else {
                        errMsg = "commandInstance=null";
                    }
                    Logger.getLogger(CRCLServerSocket.class.getName()).log(Level.SEVERE, errMsg, ex);
                    try {
                        handleEvent(CRCLServerSocketEvent.exceptionOccured(guard_client_state, ex));
                    } catch (Exception ex1) {
                        Logger.getLogger(CRCLServerSocket.class.getName()).log(Level.SEVERE, errMsg, ex1);
                    }
                }
            }
        };
    }

    private void startCheckingGuards(final List<GuardType> guards,
            final STATE_TYPE guard_client_state,
            final long cmdID,
            final CRCLCommandInstanceType commandInstance) {

        if (null == serverSideStatus) {
            return;
        }
        if (isClosed()) {
            return;
        }
        final CommandStatusType serverSideCommandStatus = serverSideStatus.getCommandStatus();
        if (null == serverSideCommandStatus) {
            return;
        }
        if (serverSideCommandStatus.getCommandID() != cmdID) {
            return;
        }
        if (serverSideCommandStatus.getCommandState() != CommandStateEnumType.CRCL_WORKING) {
            return;
        }
        Executor executor = handleGuardsExecutor;
        if (null == executor) {
            executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {

                int num = 0;

                @Override
                public Thread newThread(Runnable r) {
                    num++;
                    Thread t = new Thread(r, "checkingGuards_" + threadNamePrefix + "_" + port + "_" + runcount + "_" + num);
                    t.setDaemon(true);
                    return t;
                }
            });
            handleGuardsExecutor = executor;
        }

        final Runnable guardRunnable = createGuardsCheckerRunnable(guards, guard_client_state, cmdID, commandInstance);
        executor.execute(guardRunnable);
    }

    public CRCLServerSocket(int port, int backlog, InetAddress addr, boolean multithreaded, CRCLServerSocketStateGenerator<STATE_TYPE> stateGenerator) throws IOException {
        this(port, backlog, addr, multithreaded, stateGenerator, classFromGenerator(stateGenerator));
    }

    private volatile StackTraceElement createTrace @Nullable []  = null;

    @SuppressWarnings("initialization")
    public CRCLServerSocket(int port, int backlog, InetAddress addr, boolean multithreaded, CRCLServerSocketStateGenerator<STATE_TYPE> stateGenerator, Class<STATE_TYPE> stateClass) {
        this.port = port;
        this.backlog = backlog;
        this.bindAddress = addr;
        this.localAddress = new InetSocketAddress(bindAddress, port);
        this.multithreaded = multithreaded;
        this.stateGenerator = stateGenerator;
        this.stateClass = stateClass;
        refreshSensorFinders();
        checkPortMap(port);
        createTrace = Thread.currentThread().getStackTrace();
        portMap.put(port, this);
    }

    private void checkPortMap(int port1) throws IllegalStateException {
        if (portMap.containsKey(port1)) {
            CRCLServerSocket otherServer = portMap.get(port1);
            System.err.println("this = " + this);
            if (null != this && this.startTrace != null) {
                System.out.println("this.startTrace = " + CRCLUtils.traceToString(this.startTrace));
            }
            if (null != this && this.createTrace != null) {
                System.out.println("this.createTrace = " + CRCLUtils.traceToString(this.createTrace));
            }
            System.err.println("otherServer = " + otherServer);
            if (null != otherServer && otherServer.startTrace != null) {
                System.out.println("otherServer.startTrace = " + CRCLUtils.traceToString(otherServer.startTrace));
            }
            if (null != otherServer && otherServer.createTrace != null) {
                System.out.println("otherServer.createTrace = " + CRCLUtils.traceToString(otherServer.createTrace));
            }
            System.err.println("portMap = " + portMap);
            throw new IllegalStateException("two servers for same port " + port);
        }
    }

    private static final CRCLServerSocketStateGenerator<CRCLServerClientState> DEFAULT_STATE_GENERATOR
            = new CRCLServerSocketStateGenerator<CRCLServerClientState>() {
        @Override
        public CRCLServerClientState generate(CRCLSocket crclSocket) {
            return new CRCLServerClientState(crclSocket);
        }
    };

    static public CRCLServerSocket<CRCLServerClientState> newDefaultServer() throws IOException {
        return new CRCLServerSocket<>(DEFAULT_STATE_GENERATOR);
    }

    static public CRCLServerSocket<CRCLServerClientState> newDefaultServer(int port) throws IOException {
        return new CRCLServerSocket<>(port, DEFAULT_STATE_GENERATOR);
    }

    static public CRCLServerSocket<CRCLServerClientState> newDefaultServer(int port, int backlog) throws IOException {
        return new CRCLServerSocket<>(port, backlog, DEFAULT_STATE_GENERATOR);
    }

    static public CRCLServerSocket<CRCLServerClientState> newDefaultServer(int port, int backlog, InetAddress bindAddr) throws IOException {
        return new CRCLServerSocket<>(port, backlog, bindAddr, DEFAULT_STATE_GENERATOR);
    }

    static public CRCLServerSocket<CRCLServerClientState> newDefaultServer(int port, int backlog, InetAddress bindAddr, boolean multithreaded) throws IOException {
        return new CRCLServerSocket<>(port, backlog, bindAddr, multithreaded, DEFAULT_STATE_GENERATOR);
    }

    private boolean started = false;

    private volatile StackTraceElement startTrace @Nullable []  = null;
    private volatile @Nullable
    Thread startThread = null;

    private static final ConcurrentLinkedDeque<TraceInfo> startServerTraces
            = new ConcurrentLinkedDeque<>();

    public Future<?> start() {
        if (isRunning()) {
            throw new IllegalStateException("Can not start again when server is already running.");
        }
        ExecutorService serviceFoStart = initExecutorService();
        startTrace = Thread.currentThread().getStackTrace();
        startThread = Thread.currentThread();
        started = true;
        startServerTraces.add(new TraceInfo());
        if (!multithreaded) {
            startingSocketChannel = true;
        }
        return serviceFoStart.submit(this.runnable);
    }

    @Override
    public String toString() {
        return "CRCLServerSocket{" + "port=" + port + ", localAddress=" + localAddress + ", bindAddress=" + bindAddress + ", multithreaded=" + multithreaded + ", threadNamePrefix=" + threadNamePrefix + ", started=" + started + '}';
    }

}
