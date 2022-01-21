
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pawn extends Circle {

    int type;
    //
    int color;

    public Pawn(){

    }
    //

    public Pawn(int type, int x, int y){
        relocate(x,y);
        setRadius(50);
        setCenterX(50);
        setCenterY(50);
        this.type=type;
        if(type==1)
            setFill(Color.ORANGE);
        if(type==2)
            setFill(Color.PINK);

    }

}
