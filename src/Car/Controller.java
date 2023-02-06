package Car;

import processing.core.*;
import Util.NeuralNetwork;
import Util.SensorSystem;

public class Controller {
  // Forbinder - Sensorer & Hjerne & Bil
  float varians = 2; // hvor stor er variansen på de tilfældige vægte og bias
  int carFitness = 0;
  Car bil = new Car();
  NeuralNetwork hjerne = new NeuralNetwork(varians);
  SensorSystem sensorSystem;
  PApplet p;

  public Controller(PApplet p) {
    sensorSystem = new SensorSystem(p);
    this.p = p;
  }

  void update() {
    // 1.)opdtarer bil
    bil.update();
    // 2.)opdaterer sensorer
    sensorSystem.updateSensorsignals(bil.pos, bil.vel);
    carFitness = sensorSystem.getFitness();
    // 3.)hjernen beregner hvor meget der skal drejes
    float turnAngle = 0;
    float x1 = sensorSystem.leftSensorSignal ? 1 : 0;
    float x2 = sensorSystem.frontSensorSignal ? 1 : 0;
    float x3 = sensorSystem.rightSensorSignal ? 1 : 0;
    turnAngle = hjerne.getOutput(x1, x2, x3);
    // 4.)bilen drejes
    bil.turnCar(turnAngle);
  }

  

  void display() {
    bil.displayCar(this.p);
    sensorSystem.displaySensors();
  }
  
}
