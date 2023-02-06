import Car.CarSystem;
import processing.core.*;

public class Main extends PApplet {
	int populationSize = 100;
	CarSystem carSystem = new CarSystem(populationSize, this);
	PImage track;

	public void settings() {
		size(1000, 500);
		track = loadImage("TrackNew.png");
	}

	public void draw() {
		clear();
		fill(255);
		rect(0,50,1000,1000);
		image(track,0,0);  
	  
		carSystem.updateAndDisplay();

		if (frameCount % 600 == 0) {
			carSystem.nextGen();
		}
	}

	public static void main(String[] args) {
		PApplet.runSketch(new String[] { "Main" }, new Main());
	}
}
