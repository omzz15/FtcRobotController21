package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.arm2.Arm2;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "test", group = "Test")
public class AutoTest extends LinearOpMode {
	Position[] positions = {
		new Position(),
		new Position(),
		new Position(),
		new Position()
	};
	int delay = 100;

	@Override
	public void runOpMode(){
		Robot robot = new Robot(this);

		new Drive(robot);
		Movement move = new Movement(robot);
		new PositionTracker(robot);
		new Vision(robot);

		Task t = new Task();
		Task.Step s;
		Task.EndPoint e = () -> (move.done);

		for(Position p : positions){
			s = () -> {move.setMoveToPosition(p, ((MovementSettings) move.settings).finalPosSettings);};
			t.addStep(s);
			t.addStep(e);
			t.addDelay(delay);
			robot.taskManager.addSequentialTask(t);
		}

		robot.init();

		waitForStart();

		robot.start();

		while(opModeIsActive()){
			robot.run();
			robot.sendTelemetry();
		}

		robot.stop();
	}
}