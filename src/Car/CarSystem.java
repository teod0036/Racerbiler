package Car;

import java.util.ArrayList;
import java.util.Arrays;

import Util.NeuralNetwork;
import Util.SensorSystem;
import processing.core.*;

public class CarSystem {
    // CarSystem -
    // Her kan man lave en generisk alogoritme, der skaber en optimal "hjerne" til
    // de forhåndenværende betingelser

    PApplet p;

    ArrayList<Controller> ControllerList = new ArrayList<>();

    public CarSystem(int populationSize, PApplet p) {
        this.p = p;
        for (int i = 0; i < populationSize; i++) {
            Controller controller = new Controller(p);
            ControllerList.add(controller);
        }
    }

    int generation = 1;

    public void nextGen() {
        generation++;
        // sorterer Controllerne efter fitness.
        for (int i = 0; i < this.ControllerList.size(); i++) {
            int j = i;
            while (j > 0 && this.ControllerList.get(j - 1).carFitness < this.ControllerList.get(j).carFitness) {
                Controller temp = this.ControllerList.get(j - 1);
                this.ControllerList.set(j - 1, this.ControllerList.get(j));
                this.ControllerList.set(j, temp);
                j = j - 1;
            }
        }
        Controller[] parents = new Controller[this.ControllerList.size()];
        for (int i = 0; i < this.ControllerList.size(); i++) {
            parents[i] = this.ControllerList.get(i);
            parents[i].sensorSystem.reset();
        }
        for (int i = 0; i < this.ControllerList.size(); i++) {
            if (i <= 4) {
                this.ControllerList.set(i, parents[i]);
            } else {
                parents[i].hjerne = NeuralNetwork.mix(this.randomCar(parents).hjerne, this.randomCar(parents).hjerne);
                this.ControllerList.set(i, parents[i]);
            }
        }
        // Set all positions to 0
        for (Controller controller : ControllerList) {
            controller.bil.pos.x = 737;
            controller.bil.pos.y = 442;
            controller.bil.vel.x = 0;
            controller.bil.vel.y = 5;
        }
    }

    private Controller randomCar(Controller[] pop) {
        int weighting = 0;

        for (int i = 0; i < pop.length; i++) {
            weighting += pop[i].sensorSystem.getFitness();
        }

        int index = 0;
        for (double r = Math.random() * weighting; index < pop.length - 1; ++index) {
            r -= pop[index].sensorSystem.getFitness();
            if (r <= 0.0)
                break;
        }
        return pop[index];
    }

    public void updateAndDisplay() {
        // 1.) Opdaterer sensorer og bilpositioner
        for (Controller controller : ControllerList) {
            controller.update();
        }

        // 2.) Tegner tilsidst - så sensorer kun ser banen og ikke andre biler!
        for (Controller controller : ControllerList) {
            controller.display();
        }

        //  Draw generation counter top left
        p.fill(0, 0, 255);
        p.textSize(20);
        p.text("Generation: " + generation, 10, 20);
    }
}
