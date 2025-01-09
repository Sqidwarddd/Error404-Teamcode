 /* Copyright (c) 2021 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

 package org.firstinspires.ftc.teamcode;

 import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
 import com.qualcomm.robotcore.eventloop.opmode.OpMode;
 import com.qualcomm.robotcore.hardware.DcMotor;
 import com.qualcomm.robotcore.hardware.Servo;

 @TeleOp(name = "Byron's Velocity Raptors Code", group = "TeleOp")
 public class Velocity_Raptors_Java_Op_Mode extends OpMode {

     // Motor and Servo Declarations
     private DcMotor viperSlideMotor;
     private DcMotor frontLeftMotor;
     private DcMotor frontRightMotor;
     private DcMotor backLeftMotor;
     private DcMotor backRightMotor;

     private Servo intakeServo1;
     private Servo intakeServo2;
     private Servo clawServo; // Changed from grabberServo to clawServo
     private Servo intakeFlipperServo1; // First intake flipper servo
     private Servo intakeFlipperServo2; // Second intake flipper servo
     private Servo nonIntakeFlipperServo; // Only one non-intake flipper servo

     // Variables for servo positions
     private boolean clawToggle = false;
     private boolean intakeFlipperToggle = false;
     private boolean nonIntakeFlipperToggle = false;

     @Override
     public void init() {
         try {
             // Initialize hardware
             // Motors connected to Control Hub
             frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor"); // Motor 0 (Control Hub)
             frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor"); // Motor 1 (Control Hub)
             backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor"); // Motor 2 (Control Hub)
             backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor"); // Motor 3 (Control Hub)

             // Motors connected to Expansion Hub
             viperSlideMotor = hardwareMap.get(DcMotor.class, "viperSlideMotor"); // Motor 0 (Expansion Hub)

             // Initialize servos connected to Control Hub
             intakeServo1 = hardwareMap.get(Servo.class, "intakeServo1"); // Servo 0 (Control Hub)
             intakeServo2 = hardwareMap.get(Servo.class, "intakeServo2"); // Servo 1 (Control Hub)
             clawServo = hardwareMap.get(Servo.class, "clawServo"); // Servo 2 (Control Hub)

             // Initialize servos connected to Expansion Hub
             intakeFlipperServo1 = hardwareMap.get(Servo.class, "intakeFlipperServo1"); // Servo 3 (Expansion Hub)
             intakeFlipperServo2 = hardwareMap.get(Servo.class, "intakeFlipperServo2"); // Servo 4 (Expansion Hub)
             nonIntakeFlipperServo = hardwareMap.get(Servo.class, "nonIntakeFlipperServo"); // Servo 5 (Expansion Hub)

             // Optionally reverse motors if needed
             frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
             backLeftMotor.setDirection(DcMotor.Direction.REVERSE);

             telemetry.addData("Status", "Hardware Initialized");
         } catch (Exception e) {
             telemetry.addData("Error", "Failed to initialize hardware: " + e.getMessage());
         }
     }

     @Override
     public void loop() {
         // Motor control logic (same as before)
         double drive = -gamepad1.left_stick_y;
         double turn = gamepad1.left_stick_x;
         double strafe = gamepad1.right_stick_x;

         double frontLeftPower = drive + turn + strafe;
         double frontRightPower = drive - turn - strafe;
         double backLeftPower = drive + turn - strafe;
         double backRightPower = drive - turn + strafe;

         frontLeftMotor.setPower(frontLeftPower);
         frontRightMotor.setPower(-frontRightPower);
         backLeftMotor.setPower(-backLeftPower);
         backRightMotor.setPower(-backRightPower);

         //Slide movement
         if (gamepad1.dpad_up) {
             viperSlideMotor.setPower(1);
         } else if (gamepad1.dpad_down) {
             viperSlideMotor.setPower(1);
         } else {
             viperSlideMotor.setPower(0);
         }

         // Continuous intake servo control with triggers
         if (gamepad1.right_trigger > 0.1) {
             intakeServo1.setPosition(1.0); // Rotate intake servo 1 continuously in one direction
             intakeServo2.setPosition(0.0); // Rotate intake servo 2 continuously in the opposite direction
         } else if (gamepad1.left_trigger > 0.1) {
             intakeServo1.setPosition(0.0); // Rotate intake servo 1 continuously in the opposite direction
             intakeServo2.setPosition(1.0); // Rotate intake servo 2 continuously in one direction
         } else {
             intakeServo1.setPosition(0.5); // Neutral position
             intakeServo2.setPosition(0.5); // Neutral position
         }

         // Claw control (X button) logic remains the same
         if (gamepad1.x) {
             if (!clawToggle) {
                 clawServo.setPosition(clawServo.getPosition() == 0.0 ? 0.25 : 0.0); // Open/Close claw
                 clawToggle = true;
             }
         } else {
             clawToggle = false;
         }

         // Intake Flipper logic (B button) remains the same
         if (gamepad1.b) {
             if (!intakeFlipperToggle) {
                 double currentPosition1 = intakeFlipperServo1.getPosition();
                 intakeFlipperServo1.setPosition(currentPosition1 == 0.0 ? 0.5 : 0.0); // Toggle first flipper
                 double currentPosition2 = intakeFlipperServo2.getPosition();
                 intakeFlipperServo2.setPosition(currentPosition2 == 0.0 ? 0.5 : 0.0); // Toggle second flipper
                 intakeFlipperToggle = true;
             }
         } else {
             intakeFlipperToggle = false;
         }

         // Non-Intake Flipper (A button) - set to 0.66
         if (gamepad1.a) {
             nonIntakeFlipperServo.setPosition(0.66); // Rotate to 0.66 when A is pressed
         } else {
             nonIntakeFlipperServo.setPosition(0.0); // Default position when A is not pressed
         }

         // Telemetry for debugging
         telemetry.addData("Viper Slide Power", viperSlideMotor.getPower());
         telemetry.addData("Front Left Power", frontLeftPower);
         telemetry.addData("Front Right Power", frontRightPower);
         telemetry.addData("Back Left Power", backLeftPower);
         telemetry.addData("Back Right Power", backRightMotor.getPower());
         telemetry.addData("Intake Servo 1 Position", intakeServo1.getPosition());
         telemetry.addData("Intake Servo 2 Position", intakeServo2.getPosition());
         telemetry.addData("Claw Servo Position", clawServo.getPosition());
         telemetry.addData("Intake Flipper 1 Position", intakeFlipperServo1.getPosition());
         telemetry.addData("Intake Flipper 2 Position", intakeFlipperServo2.getPosition());
         telemetry.addData("Non-Intake Flipper Position", nonIntakeFlipperServo.getPosition());
         telemetry.update();
     }
 }