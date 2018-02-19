package edu.frc.technomancers.robot

import edu.wpi.first.wpilibj.Preferences

object RobotMap {
    private val pref = Preferences.getInstance()
    val FRONT_LEFT_MOTOR_DIRECTIONAL: Int
    val FRONT_LEFT_MOTOR_ROTATIONAL: Int

    val FRONT_RIGHT_MOTOR_DIRECTIONAL: Int
    val FRONT_RIGHT_MOTOR_ROTATIONAL: Int

    val BACK_LEFT_MOTOR_DIRECTIONAL: Int
    val BACK_LEFT_MOTOR_ROTATIONAL: Int

    val BACK_RIGHT_MOTOR_DIRECTIONAL: Int
    val BACK_RIGHT_MOTOR_ROTATIONAL: Int

    val ROBOT_WIDTH: Double
    val ROBOT_LENGTH: Double

    val ENCODER_TICKS_PER_REVOLUTION: Int

    val DIST_SWITCH_SIDE_TO_WALL: Double
    val DIST_SWITCH_FRONT_TO_WALL: Double
    val DIST_SCALE_TO_ROBOT: Double

    val AUTO_SPEED: Double

    init {
        FRONT_LEFT_MOTOR_DIRECTIONAL = pref.getInt("frontLeftDir", 0)
        FRONT_LEFT_MOTOR_ROTATIONAL = pref.getInt("frontLeftRot", 0)

        FRONT_RIGHT_MOTOR_DIRECTIONAL = pref.getInt("frontRightDir", 0)
        FRONT_RIGHT_MOTOR_ROTATIONAL = pref.getInt("frontRightRot", 0)

        BACK_LEFT_MOTOR_DIRECTIONAL = pref.getInt("backLeftDir", 0)
        BACK_LEFT_MOTOR_ROTATIONAL = pref.getInt("backLeftRot", 0)

        BACK_RIGHT_MOTOR_DIRECTIONAL = pref.getInt("backRightDir", 0)
        BACK_RIGHT_MOTOR_ROTATIONAL = pref.getInt("backRightRot", 0)

        ROBOT_LENGTH = pref.getDouble("robotLength", 0.0)
        ROBOT_WIDTH = pref.getDouble("RobotWidth", 0.0)

        ENCODER_TICKS_PER_REVOLUTION = pref.getInt("encoderTicksPerRevolution", 0)

        //Field Dimensions
        DIST_SWITCH_SIDE_TO_WALL = pref.getDouble("distSwitchSideToWall", 0.0)
        DIST_SWITCH_FRONT_TO_WALL = pref.getDouble("distSwitchFrontToWall", 0.0)
        DIST_SCALE_TO_ROBOT = pref.getDouble("distScaleToRobot", 0.0)

        AUTO_SPEED = pref.getDouble("autoSpeed" , 0.0)


    }
}