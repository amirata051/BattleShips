public class Ship {
    String name;
    Point[] tilePoints;

    Ship(int r, int c, int length, boolean horizontal){
        tilePoints = new Point[length];
        for(int i = 0; i < length; i++){
            if(horizontal){
                tilePoints[i] = new Point(r, c+i);
            }else{
                tilePoints[i] = new Point(r+i, c);
            }
        }
    }

    Ship(String name, Point[] points){
        this.name = name;
        this.tilePoints = points;
    }
}
