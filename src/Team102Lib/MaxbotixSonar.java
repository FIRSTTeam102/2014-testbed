/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Team102Lib;

/*
 * This Class came from Team 2587 DiscoBots.
 * MaxbotixSonar class for reading values from the Maxbotix Sonars.
 * Will have functions for running them in daisy chain mode which will require
 * a digital output.
 *
 * Remember that Maxbotix sensors get connected to Analog inputs not digital
 * inputs like other ultrasonic sensors.
 */
// import Utils.DiscoUtils;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.parsing.ISensor;

/**
 *
 * @author JAG
 */
public class MaxbotixSonar extends SensorBase implements PIDSource, ISensor {

    /**
     * The units to return when PIDGet is called
     */
    public static class Unit {

        /**
         * The integer value representing this enumeration
         */
        public final int value;
        static final int kInches_val = 0;
        static final int kMillimeters_val = 1;
        /**
         * Use inches for PIDGet
         */
        public static final Unit kInches = new Unit(kInches_val);
        /**
         * Use millimeters for PIDGet
         */
        public static final Unit kMillimeter = new Unit(kMillimeters_val);

        private Unit(int value) {
            this.value = value;
        }
    }
    private static final double PING_TIME = 20 * 1e-6;  ///< Time (sec) for the ping trigger pulse.
    private static final double MAX_SONAR_TIME = 0.1;   ///< Max time (ms) between readings.
    private static final double CM_TO_IN = 0.3937;
    private static final double MIN_VOLTAGE = 0.01;       //Minimum voltage the ultrasonic sensor can return
    private AnalogChannel m_inputChannel = null;
    private DigitalOutput m_pingChannel = null;
    private double m_previousReading = -1.0;
    private int m_allocatedChannels = 0;
    private boolean m_daisyChainEnabled = false;
    private boolean m_enabled = false;
    private Unit m_units;
    private static Thread m_task = null;

    /**
     * Background task that pings the first sensor in a chain of Maxbotix-XL ultrasonic sensors. The first sensor
     * pings the next and so on. The first sensor has to be repinged after all have finished which is 100ms per sensor
     *
     */
    private class DaisyChainPulser extends Thread {

        public synchronized void run() {
            while (m_daisyChainEnabled) {
                DigitalOutput u = m_pingChannel;
                if (u == null) {
                    return;
                } else {
                    u.pulse(PING_TIME); // do the ping
                }
                Timer.delay(m_allocatedChannels * .15);                                                 // wait for ping to return
            }
        }
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor using the default modules.
     * This is designed to support the Maxbotix-XL  ultrasonic sonar sensors. This
     * constructor is for the first module that is the start of a daisy chain. This
     * constructor assumes the analog I/O channel is in the default analog module
     * and that the ping channel is in the default digital module.
     * @param pingChannel The digital output channel that sends the pulse to initiate the
     * daisy chain of Maxbotix sonars
     * @param inputChannel The analog input channel has the analog voltage of the distance.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(final int inputChannel, final int pingChannel, Unit units) {
        m_pingChannel = new DigitalOutput(pingChannel);
        m_inputChannel = new AnalogChannel(inputChannel);
        m_allocatedChannels++;
        m_units = units;
        initializeDaisyChain();
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor using the default modules.
     * This is designed to support the Maxbotix-XL  ultrasonic sonar sensors. This
     * constructor is for the first module that is the start of a daisy chain. This
     * constructor assumes the analog I/O channel is in the default analog module
     * and that the ping channel is in the default digital module.
     * Defualts to Inches
     * @param pingChannel The digital output channel that sends the pulse to initiate the
     * daisy chain of Maxbotix sonars
     * @param inputChannel The analog input channel has the analog voltage of the distance.
     */
    public MaxbotixSonar(final int inputChannel, final int pingChannel) {
        this(pingChannel, inputChannel, Unit.kInches);
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor from a AnalogChannel for the input channel and a DigitalOutput
     * for the ping channel.
     * @param pingChannel The digital output object that starts the sensor daisy chain doing a ping. Requires a 20uS pulse to start.
     * @param inputChannel The AnalogChannel object that times the return pulse to determine the range.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(AnalogChannel inputChannel, DigitalOutput pingChannel, Unit units) {
        if (pingChannel == null || inputChannel == null) {
            throw new NullPointerException("Null Channel Provided");
        }
        m_allocatedChannels++;
        m_pingChannel = pingChannel;
        m_inputChannel = inputChannel;
        m_units = units;
        initializeDaisyChain();
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor from a AnalogChannel for the input channel and a DigitalOutput
     * for the ping channel.
     * Defaults to Inches
     * @param pingChannel The digital output object that starts the sensor daisy chain doing a ping. Requires a 20uS pulse to start.
     * @param inputChannel The AnalogChannel object that times the return pulse to determine the range.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(AnalogChannel inputChannel, DigitalOutput pingChannel) {
        this(inputChannel, pingChannel, Unit.kInches);
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor using the default modules.
     * This is designed to support the Maxbotix-XL  ultrasonic sonar sensors. This
     * constructor is for all sensors that are not the first sensor in a daisy chain. This
     * constructor assumes the analog I/O channel is in the default analog module
     * @param inputChannel The analog input channel has the analog voltage of the distance.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(final int inputChannel, Unit units) {
        m_inputChannel = new AnalogChannel(inputChannel);
        m_allocatedChannels++;
        m_units = units;
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor using the default modules.
     * This is designed to support the Maxbotix-XL  ultrasonic sonar sensors. This
     * constructor is for all sensors that are not the first sensor in a daisy chain. This
     * constructor assumes the analog I/O channel is in the default analog module
     * Defaults to using inches
     * @param inputChannel The analog input channel has the analog voltage of the distance.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(final int inputChannel) {
        this(inputChannel, Unit.kInches);
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor from a AnalogChannel for the input channel
     * Used when not the first in a daisy chain.
     * @param inputChannel The AnalogChannel object that times the return pulse to determine the range.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(AnalogChannel inputChannel, Unit units) {
        if (inputChannel == null) {
            throw new NullPointerException("Null Channel Provided");
        }
        m_allocatedChannels++;
        m_inputChannel = inputChannel;
        m_units = units;
    }

    /**
     * Create an instance of the MaxbotixSonar Sensor from a AnalogChannel for the input channel.
     * Used when not the first in a daisy chain.
     * Defaults to inches
     * @param inputChannel The AnalogChannel object that times the return pulse to determine the range.
     * @param units The units returned in either kInches or kMilliMeters
     */
    public MaxbotixSonar(AnalogChannel inputChannel) {
        this(inputChannel, Unit.kInches);
    }

    /**
     * This method starts the DaisyChainTask that pings the first sonar.
     */
    private synchronized void initializeDaisyChain() {
        if (m_task == null) {
            m_task = new DaisyChainPulser();
        }
//        DiscoUtils.debugPrintln("Intialized DaisyChain");
        m_daisyChainEnabled = true;
        m_task.start();
    }

    /**
     * Return the voltage reading after applying a small mode filter
     * Check the reading against the last reading to see if they are with in a
     * given a range before outputing it, if not output the previous reading.
     * Return the Voltage reading directly form the analog module
     */
    public double getVoltage() {
        double newReading = m_inputChannel.getVoltage();
        double goodReading = m_previousReading;
        if (m_previousReading == -1 || (newReading - m_previousReading) < .5){
            m_previousReading = newReading;
            goodReading = newReading;
        } else {
            m_previousReading = newReading;
        }
        return goodReading;
    }

    /**
     * Get the range in centimeters from the MaxbotixSonar sensor, only if the range is valid.
     * @return double Range in millimeters of the target returned from the MaxbotixSonarsensor.
     */
    public double getRangeCM() {
        return getVoltage() / 5 * 1024;
    }

    /**
     * Get the range in millimeters from the MaxbotixSonar sensor.
     * @return double Range in millimeters of the target returned from the MaxbotixSonarsensor. If there is
     * no valid value yet then return 0.
     */
    public double getRangeMM() {
        return getRangeCM() * 10;
    }

    /**
     * Get the range in inches from the MaxbotixSonar sensor.
     * @return double Range in inches of the target returned from the MaxbotixSonarsensor. If there is
     * no valid value yet then return 0.
     */
    public double getRangeInches() {
//        return getRangeCM() * CM_TO_IN;
        return getVoltage() / (.0098);
    }

    /**
     * Get the range in the current DistanceUnit for the PIDSource base object.
     *
     * @return The range in DistanceUnit
     */
    public double pidGet() {
        switch (m_units.value) {
            case Unit.kInches_val:
                return getRangeInches();
            case Unit.kMillimeters_val:
                return getRangeMM();
            default:
                return 0.0;
        }
    }

    /**
     * Set the current DistanceUnit that should be used for the PIDSource base object.
     *
     * @param units The DistanceUnit that should be used.
     */
    public void setDistanceUnits(Unit units) {
        m_units = units;
    }

    /**
     * Get the current DistanceUnit that is used for the PIDSource base object.
     *
     * @return The type of DistanceUnit that is being used.
     */
    public Unit getDistanceUnits() {
        return m_units;
    }

    /**
     * Is the MaxbotixSonar enabled
     * @return true if the ultrasonic is enabled
     */
    public boolean isEnabled() {
        return m_enabled;
    }

    /**
     * Set if the MaxbotixSonar is enabled
     * @param enable set to true to enable the ultrasonic
     */
    public void setEnabled(boolean enable) {
        m_enabled = enable;
    }
}
