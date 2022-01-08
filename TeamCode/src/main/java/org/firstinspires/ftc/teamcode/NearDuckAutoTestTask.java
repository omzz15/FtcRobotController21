package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import org.firstinspires.ftc.teamcode.base.Robot;
        import org.firstinspires.ftc.teamcode.deprecated.arm.Arm;
        import org.firstinspires.ftc.teamcode.other.Position;
        import org.firstinspires.ftc.teamcode.other.task.Task;
        import org.firstinspires.ftc.teamcode.parts.drive.Drive;
        import org.firstinspires.ftc.teamcode.parts.duckspinner.DuckSpinner;
        import org.firstinspires.ftc.teamcode.parts.intake.Intake;
        import org.firstinspires.ftc.teamcode.parts.movement.Movement;
        import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
        import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
        import org.firstinspires.ftc.teamcode.parts.vision.Vision;

@TeleOp(name = "Auto Near Ducks", group = "Test")
public class NearDuckAutoTestTask extends LinearOpMode {
    Movement move;
    Robot robot;
    Arm arm;
    Intake intake;
    PositionTracker tracker;
    Boolean enableDelay = false;

    @Override
    public void runOpMode() {
        robot = new Robot(this);
        new Drive(robot);
        new PositionTracker(robot);
        new DuckSpinner(robot);
        intake = new Intake(robot);
        move = new Movement(robot);
        arm = new Arm(robot);
        new Vision(robot);

        enableDelay = true; // set to false to disable the testing delays

        addTask("Go to spinner", new Position(-58.9, 51, -14.1));
        //addTask("Go to Spinner", () -> );
        /*****************************************
         * Start main opmode running
         *****************************************/
        robot.init();
        waitForStart();
        robot.start();

        while (opModeIsActive()) {
            robot.run();
            robot.sendTelemetry();
        }
        robot.stop();
    }

    /*****************************************
     * Helper methods for creating tasks
     *****************************************/
    private void addDelay(int delay) {
        if (enableDelay) {
            Task task = new Task();
            task.addDelay(delay);
            robot.taskManager.getMain().addSequentialTask(task);
        }
    }

    private void addTask(String name, Position p, Task.EndPoint end) {
        addTask(name, p, end, true);
    }

    private void addTask(String name, Position p, Task.EndPoint end, Boolean background) {
        if (background) {
            Task task = new Task();
            ///task = this.move.addMoveToPositionToTask(new Task(), p, ((MovementSettings) move.settings).losePosSettings);
            task.addStep(end);
            ///robot.taskManager.getMain().addTask(name, task, true);
        } else {
            ///robot.taskManager.getMain().addSequentialTask(this.move.addMoveToPositionToTask(new Task(), p, ((MovementSettings) move.settings).losePosSettings));
        }
    }

    private void addTask(String name, Position p) {
        addTask(name, p, null, false);

        ///robot.taskManager.getMain().addSequentialTask(this.move.addMoveToPositionToTask(new Task(), p, ((MovementSettings) move.settings).losePosSettings));
    }

    private void addTask(String name, Task.Step step) {
        Task.EndPoint end = () -> (arm.settings.runMode == 1);
        addTask(name, step, end);
    }

    private void addTask(String name, Task.Step step, Task.EndPoint end) {
        Task task = new Task();
        task.addStep(step, end);
        //task.addStep(() -> { robot.addTelemetry("Task status", name);});
        robot.taskManager.getMain().addSequentialTask(task);
    }


}