package frc.robot;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.GenericHID;

/**
 * Handle input from the T.16000M Flight Stick controller connected to the Driver Station.
 *
 * <p>This class handles Flight Stick input that comes from the Driver Station. Each time a value is
 * requested the most recent value is returned. There is a single class instance for each controller
 * and the mapping of ports to hardware buttons depends on the code in the Driver Station.
 * 
 * @author Gage Rocheleau
 */
public class FlightStick extends GenericHID {
    /** Represents a digital button of a T.16000M Flight Stick. */
    public enum Button {
        kTrigger(1),
        kMiddleThumb(2),
        kLeftThumb(3),
        kRightThumb(4),
        kLeftTopLeft(5),
        kLeftTopMiddle(6),
        kLeftTopRight(7),
        kLeftBottomRight(8),
        kLeftBottomMiddle(9),
        kLeftBottomLeft(10),
        kRightTopRight(11),
        kRightTopMiddle(12),
        kRightTopLeft(13),
        kRightBottomLeft(14),
        kRightBottomMiddle(15),
        kRightBottomRight(16);

        @SuppressWarnings("MemberName")
        public final int value;

        Button(int value) {
            this.value = value;
        }

        /**
        * Get the human-friendly name of the button, matching the relevant methods. This is done by
        * stripping the leading `k`, and append `Button`.
        *
        * <p>Primarily used for automated unit tests.
        *
        * @return the human-friendly name of the button.
        */
        @Override
        public String toString() {
            var name = this.name().substring(1); // Remove leading `k`
            return name + "Button";
        }
    }

    /** Represents an axis on an a T.16000M Flight Stick. */
    public enum Axis {
        kX(0),
        kY(1),
        kZRotate(2),
        kSlider(3);

        @SuppressWarnings("MemberName")
        public final int value;

        Axis(int value) {
            this.value = value;
        }

        /**
        * Get the human-friendly name of the axis, matching the relevant methods. This is done by
        * stripping the leading `k`, and if a trigger axis append `Axis`.
        *
        * <p>Primarily used for automated unit tests.
        *
        * @return the human-friendly name of the axis.
        */
        @Override
        public String toString() {
            var name = this.name().substring(1); // Remove leading `k`
            return name;
        }
    }

    /**
    * Construct an instance of a controller.
    *
    * @param port The port index on the Driver Station that the controller is plugged into.
    */
    public FlightStick(final int port) {
        super(port);

        HAL.report(tResourceType.kResourceType_Joystick, port + 1);
    }

    /**
     * Get the X axis value of the Flight Stick.
     * 
     * @return The axis value.
     */
    public double getX() {
        return getRawAxis(Axis.kX.value);
    }

    /**
     * Get the Y axis value of the Flight Stick.
     * 
     * @return The axis value.
     */
    public double getY() {
        return getRawAxis(Axis.kY.value);
    }

    /**
     * Get the Z axis value of the Flight Stick.
     * 
     * @return The axis value.
     */
    public double getZ() {
        return getRawAxis(Axis.kZRotate.value);
    }

    /**
     * Get the slider value of the Flight Stick.
     * 
     * @return The slider value.
     */
    public double getSlider() {
        return getRawAxis(Axis.kSlider.value);
    }

    
}
