package isp.lab10.raceapp;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CarRace {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CarPanel carPanel = new CarPanel();

        frame.getContentPane().add(carPanel);
        frame.pack();
        frame.setSize(500,300);
        frame.setVisible(true);

        Car car1 = new Car("Red car", carPanel);
        Car car2 = new Car("Blue car", carPanel);
        Car car3 = new Car("Green car", carPanel);
        Car car4 = new Car("Yellow car", carPanel);

        JFrame semaphore = new JFrame("Semaphore");
        SemaphorePanel semaphorePanel = new SemaphorePanel();
        semaphore.getContentPane().add(semaphorePanel);
        semaphore.setSize(200,300);
        semaphore.setVisible(true);
        SemaphoreThread semaphoreThread = new SemaphoreThread(semaphorePanel);
        PlaySound sound = new PlaySound();
        sound.playSound();
        try {
            Thread.sleep(5000);
            sound.stopSound();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        semaphoreThread.start();
        try {
            semaphoreThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        car1.start();
        car2.start();
        car3.start();
        car4.start();

    }
    private final Car[] cars;

    public CarRace(Car[] cars) {
        this.cars = cars;
    }

    public void go() {

        for (Car car : cars) {
            car.start();
        }
    }
}
class Car extends Thread {
    private final String name;
    private int distance = 0;
    private final CarPanel carPanel;

    public Car(String name, CarPanel carPanel) {
        //set thread name;
        setName(name);
        this.name = name;
        this.carPanel = carPanel;
    }

    public void run() {
        while (distance < 700) {
            // simulate the car moving at a random speed
            int speed = (int) (Math.random() * 10) + 1;
            distance += speed;

            carPanel.updateCarPosition(name, distance);

            try {
                // pause for a moment to simulate the passage of time
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        carPanel.carFinished(name);
    }
}

class CarPanel extends JPanel {
    private int[] carPositions;
    private String[] carNames;
    private Color[] carColors;
    private String[] carImages;

    public CarPanel() {
        carPositions = new int[4];
        carNames = new String[]{"Leclerc", "Hamilton", "Verstappen", "Sainz"};
        carColors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        carImages = new String[]{"leclerc.jpg", "hamilton.jpg", "verstappen.jpg", "sainzjr.jpg"};
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        for (int i = 0; i < 4; i++) {
            int yPos = 50 + i * 50; // Vertical position of the car
            int xPos = carPositions[i]; // Horizontal position of the car
            int carSize = 60; // Size of the car (slightly larger)

            Ellipse2D ellipse = new Ellipse2D.Double(xPos, yPos, 50, 50);
            g2.setClip(ellipse);
            g2.draw(ellipse);

            BufferedImage img = null;
            try {
                img = ImageIO.read(new File(".\\" + carImages[i]));
                // Draw the image inside the ellipse with adjusted dimensions
                g2.drawImage(img, xPos - 5, yPos - 5, carSize, carSize, null); // Adjusted dimensions
            } catch (IOException e) {
                e.printStackTrace();
            }
            g2.drawString(carNames[i], xPos, yPos - 5);
        }
    }



    public void updateCarPosition(String carName, int distance) {
        int carIndex = getCarIndex(carName);
        if (carIndex != -1) {
            carPositions[carIndex] = distance;
            repaint();
        }
    }

    public void carFinished(String carName) {
        System.out.println("Car finished race.");
    }

    private int getCarIndex(String carName) {
        for (int i = 0; i < 4; i++) {
            if (carNames[i].equals(carName)) {
                return i;
            }
        }
        return -1;
    }

    public String[] getCarNames() {
        return new String[]{"Leclerc", "Hamilton", "Verstappen", "Sainz"};
    }

    public int[] getCarDistances() {
        return carPositions;
    }

    public String[] getCarImages() {
        return new String[]{"leclerc_win.jpg", "hamilton_win.jpg", "ver_win.jpg", "sainz_win.jpg"};
    }

}
