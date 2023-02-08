package Util;

import processing.core.*;

public class SensorSystem {
  // SensorSystem - alle bilens sensorer - ogå dem der ikke bruges af "hjernen"

  // wall detectors
  float sensorMag = 30;
  float sensorAngle = (float) (Math.PI / 8);

  PVector anchorPos = new PVector();

  PVector sensorVectorFront = new PVector(0, sensorMag);
  PVector sensorVectorLeft = new PVector(0, sensorMag);
  PVector sensorVectorRight = new PVector(0, sensorMag);

  public boolean frontSensorSignal = false;
  public boolean leftSensorSignal = false;
  public boolean rightSensorSignal = false;

  // crash detection
  int whiteSensorFrameCount = 0; // udenfor banen

  // clockwise rotation detection
  PVector centerToCarVector = new PVector();
  float lastRotationAngle = -1;
  float clockWiseRotationFrameCounter = 0;
  boolean clockwise = true;


  public int totalRotations = 0;
  // lapTime calculation
  boolean lastGreenDetection;
  int lastTimeInFrames = 0;
  int lapTimeInFrames = 10000;
  int laps = 0;

  private PApplet p;

  public SensorSystem(PApplet p) {
    this.p = p;
  }

  public void reset() {
    whiteSensorFrameCount = 0;
    lastRotationAngle = -1;
    clockWiseRotationFrameCounter = 0;
    totalRotations = 0;
    lastGreenDetection = false;
    lastTimeInFrames = 0;
    lapTimeInFrames = 10000;
    laps = 0;
  }

  public void displaySensors() {
    this.p.strokeWeight((float) 0.5);
    if (frontSensorSignal) {
      this.p.fill(255, 0, 0);
      this.p.ellipse(anchorPos.x + sensorVectorFront.x, anchorPos.y + sensorVectorFront.y, 8, 8);
    }
    if (leftSensorSignal) {
      this.p.fill(255, 0, 0);
      this.p.ellipse(anchorPos.x + sensorVectorLeft.x, anchorPos.y + sensorVectorLeft.y, 8, 8);
    }
    if (rightSensorSignal) {
      this.p.fill(255, 0, 0);
      this.p.ellipse(anchorPos.x + sensorVectorRight.x, anchorPos.y + sensorVectorRight.y, 8, 8);
    }
    this.p.line(anchorPos.x, anchorPos.y, anchorPos.x + sensorVectorFront.x, anchorPos.y + sensorVectorFront.y);
    this.p.line(anchorPos.x, anchorPos.y, anchorPos.x + sensorVectorLeft.x, anchorPos.y + sensorVectorLeft.y);
    this.p.line(anchorPos.x, anchorPos.y, anchorPos.x + sensorVectorRight.x, anchorPos.y + sensorVectorRight.y);

    this.p.strokeWeight(2);
    if (whiteSensorFrameCount > 0) {
      this.p.fill(whiteSensorFrameCount * 10, 0, 0);
    } else {
      this.p.fill(0, clockWiseRotationFrameCounter, 0);
    }
    this.p.ellipse(anchorPos.x, anchorPos.y, 10, 10);
  }

  public void updateSensorsignals(PVector pos, PVector vel) {
    // Collision detectors
    frontSensorSignal = this.p.get((int) (pos.x + sensorVectorFront.x), (int) (pos.y + sensorVectorFront.y)) == -1;
    leftSensorSignal = this.p.get((int) (pos.x + sensorVectorLeft.x), (int) (pos.y + sensorVectorLeft.y)) == -1;
    rightSensorSignal = this.p.get((int) (pos.x + sensorVectorRight.x), (int) (pos.y + sensorVectorRight.y)) == -1;
    // Crash detector
    int color_car_position = this.p.get((int) (pos.x), (int) (pos.y));
    if (color_car_position == -1) {
      whiteSensorFrameCount = whiteSensorFrameCount + 1;
    }
    
    // count clockWiseRotationFrameCounter
    centerToCarVector.set((this.p.height / 2) - pos.x, (this.p.width / 2) - pos.y);
    float currentRotationAngle = centerToCarVector.heading();
    float deltaHeading = lastRotationAngle - centerToCarVector.heading();
    if (deltaHeading > 0) {
      clockWiseRotationFrameCounter -= 1;
      clockwise = false;
    } else {
      clockWiseRotationFrameCounter += 1;
      clockwise = true;
    }

    // Laptime calculation
    boolean currentGreenDetection = false;
    if (this.p.red(color_car_position) == 0 && this.p.blue(color_car_position) == 0
        && this.p.green(color_car_position) != 0 && clockwise) {// den grønne målstreg er etekteret
      currentGreenDetection = true;
    }
    if (lastGreenDetection && !currentGreenDetection) { // sidst grønt - nu ikke -vi har passeret målstregen
      if (clockwise) {
        //lapTimeInFrames = this.p.frameCount - lastTimeInFrames; // LAPTIME BEREGNES - frames nu - frames sidst
        //lastTimeInFrames = this.p.frameCount;
        laps++;
      } else {
        laps--;
      }
      
    }
    lastGreenDetection = currentGreenDetection; // Husker om der var grønt sidst

    totalRotations += deltaHeading > 0 ? 1 : -1;
    lastRotationAngle = currentRotationAngle;

    updateSensorVectors(vel);

    anchorPos.set(pos.x, pos.y);
  }

  void updateSensorVectors(PVector vel) {
    if (vel.mag() != 0) {
      sensorVectorFront.set(vel);
      sensorVectorFront.normalize();
      sensorVectorFront.mult(sensorMag);
    }
    sensorVectorLeft.set(sensorVectorFront);
    sensorVectorLeft.rotate(-sensorAngle);
    sensorVectorRight.set(sensorVectorFront);
    sensorVectorRight.rotate(sensorAngle);
  }

  public int getFitness() {
    double fitness = 2000 + (double) (this.clockWiseRotationFrameCounter) + 1500 * laps
        - this.whiteSensorFrameCount * 15;
    //if (this.lastTimeInFrames > 0) {
    //  fitness += 3000;
    //}
    if (fitness < 0 || totalRotations > 10) {
      fitness = 0;
    }
    return (int) fitness;
  }
}
