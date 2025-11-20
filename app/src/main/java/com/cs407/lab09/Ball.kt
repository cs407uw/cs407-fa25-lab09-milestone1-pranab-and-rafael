package com.cs407.lab09

import kotlin.math.pow

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        // TODO: Call reset()
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }
        // Calculate displacement using OLD velocity (Equation 2)
        val deltaX = velocityX * dT + (1f/6f) * dT.pow(2) * (3 * accX + xAcc)
        val deltaY = velocityY * dT + (1f/6f) * dT.pow(2) * (3 * accY + yAcc)

        // Update velocity (Equation 1)
        velocityX += 0.5f * (xAcc + accX) * dT
        velocityY += 0.5f * (yAcc + accY) * dT

        // Update position
        posX += deltaX
        posY += deltaY

        // Store current acceleration for next iteration
        accX = xAcc
        accY = yAcc
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // Right Wall
        if (posX > backgroundWidth - ballSize) {
            posX = backgroundWidth - ballSize
            velocityX = 0f
            accX = 0f
        }
        // Left Wall
        else if (posX < 0f) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }
        // Bottom Wall
        if (posY > backgroundHeight - ballSize) {
            posY = backgroundHeight - ballSize
            velocityY = 0f
            accY = 0f
        }
        // Top Wall
        else if (posY < 0f) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f
        isFirstUpdate = true
    }
}