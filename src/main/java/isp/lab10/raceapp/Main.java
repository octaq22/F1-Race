package isp.lab10.raceapp;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Car Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CarPanel carPanel = new CarPanel();
        frame.getContentPane().add(carPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(800, 300);
        frame.setVisible(true);

        // Redare sunet
        PlaySound sound = new PlaySound();
        sound.playSound();

        Car car1 = new Car("Leclerc", carPanel);
        Car car2 = new Car("Hamilton", carPanel);
        Car car3 = new Car("Verstappen", carPanel);
        Car car4 = new Car("Sainz", carPanel);

        JFrame semaphoreFrame = new JFrame("Semaphore");
        SemaphorePanel semaphorePanel = new SemaphorePanel();
        semaphoreFrame.getContentPane().add(semaphorePanel, BorderLayout.WEST);
        semaphoreFrame.pack();
        semaphoreFrame.setSize(200, 300);
        semaphoreFrame.setVisible(true);

        SemaphoreThread semaphoreThread = new SemaphoreThread(semaphorePanel);
        semaphoreThread.start();

        try {
            semaphoreThread.join(); // Așteaptă terminarea firului de execuție al semaforului
            semaphoreFrame.dispose(); // Închide fereastra de semafor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Odată ce semaforul este verde, pornim mașinile
        car1.start();
        car2.start();
        car3.start();
        car4.start();

        try {
            car1.join(); // Așteaptă ca mașinile să termine cursa
            car2.join();
            car3.join();
            car4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // După ce cursele s-au terminat, afișăm un mesaj cu mașina câștigătoare
        int winningCarIndex = getWinningCarIndex(carPanel.getCarNames(), carPanel.getCarDistances());
        if (winningCarIndex != -1) {
            String winningCarName = carPanel.getCarNames()[winningCarIndex];
            String winningCarImage = carPanel.getCarImages()[winningCarIndex];
            ImageIcon icon = new ImageIcon(winningCarImage);
            JOptionPane.showMessageDialog(null, "Câștigător: " + winningCarName, "Câștigător", JOptionPane.INFORMATION_MESSAGE, icon);
        } else {
            JOptionPane.showMessageDialog(null, "Nici o mașină a câștigat!", "Câștigător", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Funcție pentru a obține indexul mașinii câștigătoare
    private static int getWinningCarIndex(String[] carNames, int[] distances) {
        int maxDistance = 0;
        int winningCarIndex = -1;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] > maxDistance) {
                maxDistance = distances[i];
                winningCarIndex = i;
            }
        }
        return winningCarIndex;
    }


}


