class CarSystem {
  //CarSystem - 
  //Her kan man lave en generisk alogoritme, der skaber en optimal "hjerne" til de forhåndenværende betingelser
  
  ArrayList<CarController> CarControllerList  = new ArrayList<CarController>();

  CarSystem(int populationSize) {
    for (int i=0; i<populationSize; i++) { 
      CarController controller = new CarController();
      CarControllerList.add(controller);
    }
  }
  
  void nextGen(CarSystem x) {
    //sorterer carcontrollerne efter fitness.
    for (int i = 0; i < carSystem.CarControllerList.size(); i++) {
            int j = i;
            while (j > 0 && carSystem.CarControllerList.get(j - 1).carFitness < carSystem.CarControllerList.get(j).carFitness) {
                CarController temp = carSystem.CarControllerList.get(j - 1).clone();
                carSystem.CarControllerList.set(j-1, carSystem.CarControllerList.get(j).clone());
                carSystem.CarControllerList.set(j, temp.clone());
                j = j - 1;
            }
        }
  }

  void updateAndDisplay() {
    //1.) Opdaterer sensorer og bilpositioner
    for (CarController controller : CarControllerList) {
      controller.update();
    }

    //2.) Tegner tilsidst - så sensorer kun ser banen og ikke andre biler!
    for (CarController controller : CarControllerList) {
      controller.display();
    }
  }
}
