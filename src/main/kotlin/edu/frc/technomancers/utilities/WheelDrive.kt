package edu.frc.technomancers.utilities

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.frc.technomancers.robot.RobotMap
import edu.wpi.first.wpilibj.AnalogInput
import org.apache.commons.math3.util.FastMath
import kotlin.math.sign

class WheelDrive(speedMotorPort: Int, angleMotorPort: Int, analogPos: Int)
{
    private val speedMotor = TalonSRX(speedMotorPort)
    private val angleMotor = TalonSRX(angleMotorPort)
    private val encoder = AnalogInput(analogPos)

    fun drive(speed: Double, angle: Double) {
        @Suppress("MagicNumber")
        val current = encoder.voltage * RobotMap.ENCODER_TICKS_PER_REVOLUTION / 5.0
        var speedOfWheel = speed
        val midRelative = (FastMath.abs(current) % RobotMap.ENCODER_TICKS_PER_REVOLUTION) * current.sign
        val lowRelative = midRelative - RobotMap.ENCODER_TICKS_PER_REVOLUTION
        val highRelative = midRelative + RobotMap.ENCODER_TICKS_PER_REVOLUTION
        var target = (RobotMap.ENCODER_TICKS_PER_REVOLUTION/2) * angle
        val lowDelta = target - lowRelative
        val midDelta = target - midRelative
        val highDelta = target - highRelative
        var delta = displacementMin(doubleArrayOf(lowDelta, midDelta, highDelta))
        if(delta < RobotMap.ENCODER_TICKS_PER_REVOLUTION/-4){
            delta += RobotMap.ENCODER_TICKS_PER_REVOLUTION/2
            speedOfWheel *= -1
        }
        if(delta > RobotMap.ENCODER_TICKS_PER_REVOLUTION/4){
            delta -= RobotMap.ENCODER_TICKS_PER_REVOLUTION/2
            speedOfWheel *= -1
        }
        val next = delta + current
        angleMotor.set(ControlMode.Position, next)
        speedMotor.set(ControlMode.PercentOutput, speedOfWheel)
    }

    private fun displacementMin(deltas : DoubleArray):Double{
        var min : Double? = null
        for (delta in deltas){
            if (min == null){
                min = delta
            }else {
                if (FastMath.abs(delta) < FastMath.abs(min)){
                    min = delta
                }
            }
        }

        return if (min == null) 0.0 else min
    }

    fun deepsAlgorithm(angle: Double, speed: Double){
        val current = encoder.voltage * RobotMap.ENCODER_TICKS_PER_REVOLUTION / 5.0
        val convertedAngle = (angle + 1) * RobotMap.ENCODER_TICKS_PER_REVOLUTION/2.0
        var finalSpeed = speed
        var delta = 0.0
        if(current > convertedAngle){
            val distToTargetCCW = FastMath.abs(current - convertedAngle)
            val distToTargetCW = FastMath.abs(RobotMap.ENCODER_TICKS_PER_REVOLUTION - distToTargetCCW)
            val minDistance = FastMath.min(distToTargetCCW,distToTargetCW)
            when(minDistance){
                distToTargetCCW -> {
                    delta -= distToTargetCCW
                }
                distToTargetCW -> {
                    delta += distToTargetCW
                }
            }
        }
        if(current < convertedAngle){
            val distToTargetCW = FastMath.abs(convertedAngle - current)
            val distToTargetCCW = RobotMap.ENCODER_TICKS_PER_REVOLUTION - distToTargetCW
            val minDistance = FastMath.min(distToTargetCCW,distToTargetCW)
            when(minDistance){
                distToTargetCW -> {
                    delta += distToTargetCW
                }
                distToTargetCCW -> {
                    delta -= distToTargetCCW
                }
            }
        }
        if(delta < -RobotMap.ENCODER_TICKS_PER_REVOLUTION/4.0){
            delta += RobotMap.ENCODER_TICKS_PER_REVOLUTION/2.0
            finalSpeed *= -1.0
        } else if (delta > RobotMap.ENCODER_TICKS_PER_REVOLUTION/4.0){
            delta -= RobotMap.ENCODER_TICKS_PER_REVOLUTION/2.0
            finalSpeed *= -1.0
        }
        if(delta > RobotMap.SWERVE_TOLERANCE) {
            angleMotor.set(ControlMode.PercentOutput,-1.0)
        } else if(delta < RobotMap.SWERVE_TOLERANCE){
            angleMotor.set(ControlMode.PercentOutput,1.0)
        }
        speedMotor.set(ControlMode.PercentOutput, finalSpeed)
    }
}
