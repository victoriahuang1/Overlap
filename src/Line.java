import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Line implements Comparable<Line>{
    
    private int end_x;
    private int end_y; // they all start from the origin
    private static int thickness = 5; // default thickness;
    
    public Line(int x, int y){
        end_x = x;
        end_y = y;
    }
    
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(Color.BLACK);
        g2.drawLine(GameCourt.COURT_LENGTH/2, GameCourt.COURT_LENGTH/2, end_x, end_y);
    }
    
    public int getX(){
        return end_x;
    }
    
    public int getY(){
        return end_y;
    }
    
    public void setEnd(int x, int y){
        end_x = x;
        end_y = y;
    }
    
    public static void setThick(int t){
        thickness = t;
    }

    public static int getThick(){
        return thickness;
    }
    
    public int[] findNextCoords(){
        if(end_y <= GameCourt.COURT_LENGTH/2 && end_x > GameCourt.COURT_LENGTH/2){
            if(end_y != 0 ){
                return new int[] {GameCourt.COURT_LENGTH, end_y - 1};
            } else {
                return new int[] {end_x - 1, 0};
            }
        } else if (end_y < GameCourt.COURT_LENGTH/2 && end_x <= GameCourt.COURT_LENGTH/2){
            if(end_x == 0 ){
                return new int[] {0, end_y + 1};
            } else {
                return new int[] {end_x - 1, 0};
            }
        } else if (end_y >= GameCourt.COURT_LENGTH/2 && end_x < GameCourt.COURT_LENGTH/2){
            if(end_y != GameCourt.COURT_LENGTH ){
                return new int[] {0, end_y + 1};
            } else {
                return new int[] {end_x + 1, GameCourt.COURT_LENGTH};
            }
        } else {
            if(end_x == GameCourt.COURT_LENGTH ){
                return new int[] {GameCourt.COURT_LENGTH, end_y - 1};
            } else {
                return new int[] {end_x + 1, GameCourt.COURT_LENGTH};
            }
        }
    }
    
    @Override
    public String toString(){
        return "("+end_x+","+end_y+")";
    }
    
    @Override
    public int compareTo(Line l) {
        if(end_x <= l.end_x + thickness/2 && end_x >= l.end_x - thickness/2 && 
                end_y <= l.end_y + thickness/2 && end_y  >= l.end_y - thickness/2){
            return 0;
        }
        if((end_x + thickness/2 <= l.end_x + thickness/2 || end_x - thickness/2 <= l.end_x + thickness/2)
            && (end_x + thickness/2 >= l.end_x - thickness/2 || end_x - thickness/2 >= l.end_x - thickness/2) && 
            (end_y + thickness/2 <= l.end_y + thickness/2 || end_y - thickness/2 <= l.end_y + thickness/2) && 
            (end_y + thickness/2 >= l.end_y - thickness/2 || end_y - thickness/2 >= l.end_y - thickness/2)){
            return 0;
        }
        return -1;
    }
}
