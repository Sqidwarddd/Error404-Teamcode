package org.firstinspires.ftc.teamcode;

}
package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.DcMotor;

public class MecanumStrafeDrive extends LinearOpMode {

    // Define motors
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    @Override
    public void runOpMode() {

        // Initialize motors with REV Control Hub ports 0-3
        frontLeft = hardwareMap.dcMotor.get("motor0"); // Motor 0: Front Left
        frontRight = hardwareMap.dcMotor.get("motor1"); // Motor 1: Front Right
        backLeft = hardwareMap.dcMotor.get("motor2"); // Motor 2: Back Left
        backRight = hardwareMap.dcMotor.get("motor3"); // Motor 3: Back Right

        // Set motor directions (if needed for your setup)
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start
        waitForStart();

        while (opModeIsActive()) {

            // Get joystick inputs
            double drive = -gamepad1.left_stick_y; // Forward/Backward movement (negative for forward)
            double strafe = gamepad1.right_stick_x; // Strafe Left/Right (positive for right)
            double rotate = gamepad1.left_stick_x; // Rotate Left/Right (positive for right)

            // Calculate motor powers for mecanum drive
            double frontLeftPower = drive + strafe + rotate;
            double frontRightPower = drive - strafe - rotate;
            double backLeftPower = drive - strafe + rotate;
            double backRightPower = drive + strafe - rotate;

            // Normalize the motor powers to ensure they stay within the range of -1 to 1
            double maxPower = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                    Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));

            if (maxPower > 1.0) {
                frontLeftPower /= maxPower;
                frontRightPower /= maxPower;
                backLeftPower /= maxPower;
                backRightPower /= maxPower;
            }

            // Set motor powers
            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);

            // Telemetry feedback (optional)
            telemetry.addData("Drive", drive);
            telemetry.addData("Strafe", strafe);
            telemetry.addData("Rotate", rotate);
            telemetry.update();
        }
    }
}
