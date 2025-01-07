package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Javaopmode2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("FrontLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("BackLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("FrontRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("BackRight");
        DcMotor Slide = hardwareMap.dcMotor.get("Slide");
        Servo IntakeLeft = hardwareMap.servo.get("IntakeLeft");
        Servo IntakeRight = hardwareMap.servo.get("IntakeRight");
        Servo clawSlideServo = hardwareMap.servo.get("clawSlideServo");
        Servo ClawServo = hardwareMap.servo.get("ClawServo");
        Servo IntakeSlideServoLeft = hardwareMap.servo.get("IntakeSlideServoLeft");
        Servo IntakeSlideServoRight = hardwareMap.servo.get("IntakeSlideServoRight");
        Servo IntakeFlipServo = hardwareMap.servo.get("IntakeFlipServo");
        Servo IntakeFlipServo2 = hardwareMap.servo.get("IntakeFlipServo2");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(-backLeftPower);
            frontRightMotor.setPower(-frontRightPower);
            backRightMotor.setPower(-backRightPower);

            if (gamepad1.dpad_right) {
                IntakeSlideServoLeft.setPosition(1);
                IntakeSlideServoRight.setPosition(1);
            } else if (gamepad1.dpad_left) {
                IntakeSlideServoLeft.setPosition(0.5);
                IntakeSlideServoRight.setPosition(0.5);
            } else {
                IntakeSlideServoRight.setPosition(0);
                IntakeSlideServoLeft.setPosition(0);
            }

            // Servo Code for TeleOp

            double SLIDE_SPEED;
            SLIDE_SPEED = gamepad2.left_trigger;
            Slide.setPower(-SLIDE_SPEED);
            if (gamepad2.dpad_down) {
                Slide.setPower(SLIDE_SPEED);
            } else {
                Slide.setPower(0);
            }

            if (gamepad2.b) {
                IntakeLeft.setPosition(1);
                IntakeRight.setPosition(1);
            } else {
                IntakeLeft.setPosition(0);
                IntakeRight.setPosition(0);
            }

            if (gamepad2.left_bumper) {
                clawSlideServo.setPosition(-1);
            } else {
                clawSlideServo.setPosition(0);
            }

            if (gamepad2.right_bumper) {
                ClawServo.setPosition(1);
            } else {
                ClawServo.setPosition(0);
            }

            if (gamepad2.dpad_left) {
                IntakeFlipServo.setPosition(1);
                IntakeFlipServo2.setPosition(1);
            } else if (gamepad2.dpad_right) {
                IntakeFlipServo.setPosition(0);
                IntakeFlipServo2.setPosition(0);
            } else {
                IntakeFlipServo.setPosition(0);
                IntakeFlipServo2.setPosition(0);
        }
    }
}
}