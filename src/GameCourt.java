import java.awt.*;
import java.awt.event.*;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameCourt extends JPanel{

    public static final int COURT_LENGTH = 300;
    public static final int INTERVAL = 1;
    
    private Set<Line> lines = new TreeSet<>();
    private Line movingLine;
    private boolean hasLost = false;
    
    public GameCourt(){
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // make background white
        setBackground(Color.WHITE);
        
        // making axis
        
        lines.add(new Line(COURT_LENGTH, COURT_LENGTH/2));
        lines.add(new Line(0, COURT_LENGTH/2));
        lines.add(new Line(COURT_LENGTH/2, COURT_LENGTH));
        lines.add(new Line(COURT_LENGTH/2, 0));
        
        movingLine = new Line(COURT_LENGTH, COURT_LENGTH/2);

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(! hasLost){
                    tick();
                    repaint();
                }
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!
        
        setFocusable(true);
        
        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
                int size = lines.size();
                if (e.getKeyCode() == KeyEvent.VK_SPACE){
                    lines.add(new Line(movingLine.getX(), movingLine.getY()));
                    repaint();
                    if(size == lines.size()){
                        hasLost = true;
                    }
                }
            }
        });
    }
    
    void tick(){
        int x = movingLine.getX();
        int y = movingLine.getY();
        if(y <= COURT_LENGTH/2 && x > COURT_LENGTH/2){
            if(y != 0 ){
                movingLine.setEnd(COURT_LENGTH, y - 1);
            } else {
                movingLine.setEnd(x - 1, 0);
            }
        } else if (y < COURT_LENGTH/2 && x <= COURT_LENGTH/2){
            if(x == 0 ){
                movingLine.setEnd(0, y + 1);
            } else {
                movingLine.setEnd(x - 1, 0);
            }
        } else if (y >= COURT_LENGTH/2 && x < COURT_LENGTH/2){
            if(y != COURT_LENGTH ){
                movingLine.setEnd(0, y + 1);
            } else {
                movingLine.setEnd(x + 1, COURT_LENGTH);
            }
        } else {
            if(x == COURT_LENGTH ){
                movingLine.setEnd(COURT_LENGTH, y - 1);
            } else {
                movingLine.setEnd(x + 1, COURT_LENGTH);
            }
        }
        
    }
    
    public void reset(){
        lines.removeAll(lines);
        
        lines.add(new Line(COURT_LENGTH, COURT_LENGTH/2));
        lines.add(new Line(0, COURT_LENGTH/2));
        lines.add(new Line(COURT_LENGTH/2, COURT_LENGTH));
        lines.add(new Line(COURT_LENGTH/2, 0));
        hasLost = false;
        
        repaint();

        requestFocusInWindow();
    }
    
    public int getScore(){
        return lines.size()-4;
    }
    
    public boolean hasLost(){
        return hasLost;
    }
    
    public void changeThick(int t){
        Line.setThick(t);
        repaint();
        requestFocusInWindow();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Line l: lines){
            l.draw(g);
        }
        movingLine.draw(g);
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(COURT_LENGTH, COURT_LENGTH);
    }
    
}
