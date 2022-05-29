package hanoi;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    Graphics2D g2d;
    Timer t = new Timer();
    
    final int DISC_WIDTH = 270;
    final int DISC_HEIGHT = 20;
    final int DISC_COLORS[][] = {
        {0, 255, 0},        // zielony
        {255, 0, 127},      // rozowy
        {255, 255, 0},      // zolty
        {127, 0, 255},      // filoetowy
        {255, 128, 0},      // pomaranczowy
        {255, 0, 0},        // czerwony
        {0, 0, 255},        // niebieski
        {128, 128, 128}     // szary
    };
    
    int discs[][] = new int[16][3];
    int discs_amount = 3;
    int speed = 500;
    int move = 1;
    boolean is_running = false;
    
    final int PILLARS_POSITIONS[] = {180, 490, 800};
    Stack<Integer> pillars[] = new Stack[16];
    
    public Canvas(int amount) {
        this.setBounds(0, 140, 990, 460);
        this.setBackground(Color.white);
        discs_amount = amount;
        
        for (int i = 0; i < 16; i++) pillars[i] = new Stack<>();
        
        for (int i = 0; i < discs_amount; i++) {
            discs[i][0] = DISC_WIDTH - 16 * (16 - discs_amount) - 16 * i;
        }
        
        for (int i = 0; i < discs_amount; i++) pillars[0].push(i);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        drawPillars();
        drawDiscs();
    }
    
    public void drawPillars() {
        g2d.setPaint(new Color(139, 69, 19));
        g2d.fillRect(PILLARS_POSITIONS[0], 0, 10, 360);
        g2d.fillRect(PILLARS_POSITIONS[1], 0, 10, 360);
        g2d.fillRect(PILLARS_POSITIONS[2], 0, 10, 360);
    }
    
    public void drawDiscs() {
        drawPillarDiscs(0);
        drawPillarDiscs(1);
        drawPillarDiscs(2);
    }
    
    public void drawPillarDiscs(int n) {
        for (int i = 0; i < pillars[n].size(); i++) {
            int elem = pillars[n].get(i);
            discs[elem][1] = PILLARS_POSITIONS[n] - 130 + 8 * (16 - discs_amount) + 8 * elem;
            discs[elem][2] = 340 - 20 * i;
            
            g2d.setPaint(new Color(DISC_COLORS[elem % 8][0], DISC_COLORS[elem % 8][1], DISC_COLORS[elem % 8][2]));
            g2d.fillRect(discs[elem][1], discs[elem][2], discs[elem][0], 20);
            g2d.setPaint(new Color(0, 0, 0));
            g2d.drawRect(discs[elem][1], discs[elem][2], discs[elem][0], 20);
        }
    }
    
    public void runHanoi() {
        t = new Timer();
        is_running = true;
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                stepHanoi();
                runHanoi();
            }
        }, speed);
    }
    
    public boolean stepHanoi() {
        if (move > Math.pow(2, discs_amount) - 1) {
            stopHanoi();
            return true;
        }
        
        int p1 = -1, p2 = -1, p3 = -1;
        if (!pillars[0].isEmpty()) p1 = pillars[0].peek();
        if (!pillars[1].isEmpty()) p2 = pillars[1].peek();
        if (!pillars[2].isEmpty()) p3 = pillars[2].peek();
        
        if (discs_amount % 2 == 0) {
            if (move % 3 == 1) {
                if (p1 > p2) pillars[1].push(pillars[0].pop());
                else pillars[0].push(pillars[1].pop());
            }
            else if (move % 3 == 2) {
                if (p1 > p3) pillars[2].push(pillars[0].pop());
                else pillars[0].push(pillars[2].pop());
            }
            else if (move % 3 == 0) {
                if (p3 > p2) pillars[1].push(pillars[2].pop());
                else pillars[2].push(pillars[1].pop());
            }
        }
        else {
            if (move % 3 == 1) {
                if (p1 > p3) pillars[2].push(pillars[0].pop());
                else pillars[0].push(pillars[2].pop());
            }
            else if (move % 3 == 2) {
                if (p1 > p2) pillars[1].push(pillars[0].pop());
                else pillars[0].push(pillars[1].pop());
            }
            else if (move % 3 == 0) {
                if (p2 > p3) pillars[2].push(pillars[1].pop());
                else pillars[1].push(pillars[2].pop());
            }
        }
        
        move++;
        repaint();
        
        return false;
    }
    
    public void stopHanoi() {
        if (is_running) {
            is_running = false;
            t.cancel();
            t.purge();
        }
    }
}