package Car;

import processing.core.*;

public class Car {
	// Bil - indeholder position & hastighed & "tegning"
    PVector pos = new PVector(737, 442);
    PVector vel = new PVector(0, 5);

    void turnCar(float turnAngle) {
        vel.rotate(turnAngle);
    }

    void displayCar(PApplet p) {
        p.stroke(100);
        p.fill(100);
        p.ellipse(pos.x, pos.y, 10, 10);
    }

    void update() {
        pos.add(vel);
    }
}
