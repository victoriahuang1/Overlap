import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

/**
 * 
 * @author Victoria
 * Try to avoid placing a line on an already-existing one
 *
 */

public class Game implements Runnable{

    JLabel status;
    static final int THICK_MIN = 2;
    static final int THICK_MAX = 10;
    static final int THICK_INIT = 5;
    
    public void run() {
        final JFrame frame = new JFrame("Overlap");
        frame.setLocation(300, 300);
        
        // main playing area
        final GameCourt court = new GameCourt();
        frame.add(court, BorderLayout.CENTER);
        
        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
                status.setText("Score: " + court.getScore());
            }
        });
        control_panel.add(reset);
        
        // Thickness Slider
        final JSlider thickness = new JSlider(JSlider.HORIZONTAL, THICK_MIN, THICK_MAX, THICK_INIT);
        
        thickness.setMajorTickSpacing(1);
        thickness.setPaintTicks(true);
        thickness.setPaintLabels(true);
        thickness.setBorder(
                BorderFactory.createEmptyBorder(0,0,10,0));
        
        thickness.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                court.changeThick(thickness.getValue());
            }
        });
        
        control_panel.add(thickness);
        
        // Score panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        status = new JLabel("Score: " + court.getScore());
        status_panel.add(status);
        
        court.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    status.setText("Score: " + court.getScore());
                }
                if(court.hasLost()){
                    JOptionPane.showMessageDialog(null, "GAME OVER!");
                }
            }
        });
        
        // Put the frame on the screen
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        court.reset();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
