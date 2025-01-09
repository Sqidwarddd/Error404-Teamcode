package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Custom Servo and Motor Code", group = "TeleOp")
public class testing_zone extends OpMode {
    private Servo intakeServo1, intakeServo2, flipServo1, flipServo2, clawServo, armServo;

    // Track the state of toggle buttons
    private boolean flipToggle = false;
    private boolean clawToggle = false;
    private boolean clawButtonPressed = false;

    @Override
    public void init() {
        // Initialize motors
        // Declare motors and servos
        DcMotor motor1 = hardwareMap.get(DcMotor.class, "motor1");
        DcMotor motor2 = hardwareMap.get(DcMotor.class, "motor2");
        DcMotor motor3 = hardwareMap.get(DcMotor.class, "motor3");
        DcMotor motor4 = hardwareMap.get(DcMotor.class, "motor4");
        DcMotor motor5 = hardwareMap.get(DcMotor.class, "motor5");

        // Initialize servos
        intakeServo1 = hardwareMap.get(Servo.class, "intakeServo1");
        intakeServo2 = hardwareMap.get(Servo.class, "intakeServo2");
        flipServo1 = hardwareMap.get(Servo.class, "flipServo1");
        flipServo2 = hardwareMap.get(Servo.class, "flipServo2");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        armServo = hardwareMap.get(Servo.class, "armServo");

        // Set initial servo positions
        intakeServo1.setPosition(0.5);
        intakeServo2.setPosition(0.5);
        flipServo1.setPosition(0);
        flipServo2.setPosition(1); // Assuming opposite direction
        clawServo.setPosition(0.5);
        armServo.setPosition(0.5);
    }

    @Override
    public void loop() {
        // Control intake servos with triggers
        if (gamepad1.right_trigger > 0) {
            intakeServo1.setPosition(1); // Spin into the robot
            intakeServo2.setPosition(1);
        } else if (gamepad1.left_trigger > 0) {
            intakeServo1.setPosition(0); // Spin out (deposit)
            intakeServo2.setPosition(0);
        } else {
            intakeServo1.setPosition(0.5); // Stop
            intakeServo2.setPosition(0.5);
        }

        // Control flip servos with a button (toggle behavior)
        if (gamepad1.a && !flipToggle) {
            flipToggle = true;
            if (flipServo1.getPosition() == 0) {
                flipServo1.setPosition(1); // Rotate forward
                flipServo2.setPosition(0); // Opposite rotation
            } else {
                flipServo1.setPosition(0); // Rotate back
                flipServo2.setPosition(1);
            }
        } else if (!gamepad1.a) {
            flipToggle = false;
        }

        // Control claw servo (toggle behavior)
        if (gamepad1.b && !clawButtonPressed) {
            clawButtonPressed = true;
            if (clawToggle) {
                clawServo.setPosition(0.5); // Return to original position
            } else {
                clawServo.setPosition(0.75); // Rotate 45 degrees
            }
            clawToggle = !clawToggle;
        } else if (!gamepad1.b) {
            clawButtonPressed = false;
        }

        // Control arm servo with another button
        if (gamepad1.x) {
            armServo.setPosition(0.66); // Rotate to position
        } else {
            armServo.setPosition(0.5); // Return to original position
        }
    }
}
