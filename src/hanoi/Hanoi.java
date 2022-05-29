package hanoi;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

public class Hanoi {

    JFrame frame = new JFrame("Artur Pas - Wieże hanoi");
    JSlider speed = new JSlider(1, 1000, 500);
    JButton run = new JButton("Uruchom");
    JButton step = new JButton("Krok");
    JButton stop = new JButton("Zatrzymaj");
    JSlider discs = new JSlider(1, 16, 3);
    Canvas canv = new Canvas(3);

    public Hanoi() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(990, 600);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.white);
        frame.setLayout(null);

        speed.setBounds(100, 10, 200, 50);
        speed.addChangeListener((ChangeEvent e) -> {
            JSlider source = (JSlider) e.getSource();
            canv.speed = (int) source.getValue();
        });
        speed.setBackground(Color.white);
        speed.setMajorTickSpacing(999);
        speed.setMinorTickSpacing(100);
        speed.setPaintTicks(true);
        speed.setPaintLabels(true);
        frame.add(speed);

        run.setBounds(320, 10, 100, 25);
        run.addActionListener((ActionEvent e) -> {
            if (!canv.is_running) canv.runHanoi();
        });
        run.setFocusable(false);
        frame.add(run);

        step.setBounds(440, 10, 100, 25);
        step.addActionListener((ActionEvent e) -> {
            if (!canv.is_running) canv.stepHanoi();
        });
        step.setFocusable(false);
        frame.add(step);

        stop.setBounds(560, 10, 100, 25);
        stop.addActionListener((ActionEvent e) -> canv.stopHanoi());
        stop.setFocusable(false);
        frame.add(stop);

        discs.setBounds(680, 10, 200, 50);
        discs.addChangeListener((ChangeEvent e) -> {
            JSlider source = (JSlider) e.getSource();
            canv.stopHanoi();
            canv = new Canvas((int) source.getValue());
            frame.add(canv);
            canv.repaint();
            speed.setValue(500);
        });
        discs.setBackground(Color.white);
        discs.setMajorTickSpacing(15);
        discs.setMinorTickSpacing(1);
        discs.setPaintTicks(true);
        discs.setPaintLabels(true);
        frame.add(discs);

        frame.add(canv);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Hanoi window = new Hanoi();
    }
}