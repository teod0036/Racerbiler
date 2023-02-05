class CarController {
  //Forbinder - Sensorer & Hjerne & Bil
  float varians             = 2; //hvor stor er variansen på de tilfældige vægte og bias
  int carFitness = 0;
  Car bil                    = new Car();
  NeuralNetwork hjerne       = new NeuralNetwork(varians); 
  SensorSystem  sensorSystem = new SensorSystem();
      
  void update() {
    //1.)opdtarer bil 
    bil.update();
    //2.)opdaterer sensorer    
    sensorSystem.updateSensorsignals(bil.pos, bil.vel);
    carFitness = sensorSystem.getFitness(sensorSystem);
    //3.)hjernen beregner hvor meget der skal drejes
    float turnAngle = 0;
    float x1 = int(sensorSystem.leftSensorSignal);
    float x2 = int(sensorSystem.frontSensorSignal);
    float x3 = int(sensorSystem.rightSensorSignal);    
    turnAngle = hjerne.getOutput(x1, x2, x3);    
    //4.)bilen drejes
    bil.turnCar(turnAngle);
  }
  
  CarController clone() {
    CarController Copy = new CarController();
    Copy.varians = varians;
    Copy.carFitness = carFitness;
    Copy.bil = bil;
    Copy.hjerne = hjerne;
    Copy.sensorSystem = sensorSystem;
    return Copy;    
  }
  
  void display(){
    bil.displayCar();
    sensorSystem.displaySensors();
  }
}
