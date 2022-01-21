import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class Window extends Application {

    private static final String imageURL = "file:C:\\Users\\Natalia\\Desktop\\Checkersss\\src\\image.jpg";
    Group layout = new Group();
    Pawn[][] pawn= new Pawn[8][8];
    Board[][] board = new Board[8][8];
    ArrayList<Position> list=new ArrayList<Position>();
    int type2;
    int colour_player=1;

    public static enum STATE{
        MENU,
        GAME
    }

    public static enum PLAYER{
        ORANGE,
        PINK
    }

    final int
            EMPTY = 0,
            ORANGE = 1,
            PINK = 2,
            ORANGE_KING = 3,
            PINK_KING = 4;

    public static STATE state=STATE.MENU;

    public static PLAYER player=PLAYER.ORANGE;


    @Override

    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("checkers");
        Group MenuLayout=new Group();

        int start = 0;
        for (int y = 0; y < 8; y++){

            if (y % 2 == 0){
                start = 0;
            }

            else
                start = 1;

            for (int x = start; x < 8; x += 2){
                Board tile = new Board(true, x*100, y*100);
                board[x][y] = tile;
                layout.getChildren().add(tile);
            }
        }

        start = 1;
        for (int y = 0; y < 8; y++){
            if (y % 2 == 0){
                start = 1;
            }

            else
                start = 0;

            for (int x = start; x < 8; x += 2){
                Board tile = new Board(false, x*100, y*100);
                board[x][y] = tile;
                layout.getChildren().add(tile);
            }
        }
//////////////////////////////////////////////////



        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (y <= 2 && (x + y) % 2 != 0) {
                    Pawn new_pawn = new Pawn(1, x * 100, y * 100);
                    pawn[x][y]=new_pawn;
                    //
                    pawn[x][y].color=ORANGE;
                    //
                    layout.getChildren().add(pawn[x][y]);


                }
                else if (y >= 5 && (x + y) % 2 != 0) {
                    Pawn new_pawn = new Pawn(2, x * 100, y * 100);
                    pawn[x][y]=new_pawn;
                    //
                    pawn[x][y].color=PINK;
                    //
                    layout.getChildren().add(pawn[x][y]);


                }
                //
                else {
                    Pawn new_pawn= new Pawn();
                    pawn[x][y]=new_pawn;
                    pawn[x][y].color=EMPTY;
                }
                //
            }
        }

        //////////////////////////////////////////////////////

        layout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int col = (int) ((event.getX() - 2) / 100);
                int row = (int) ((event.getY() - 2) / 100);
                if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                    System.out.println("picked this col " + col + "row " + row);
                    Position pos= new Position(col,row);
                    number_of_clicks(pos);
                }
            }
        });

        /////////////////////////////////////////////////

        Menu vbox= new Menu(
                new MenuItem("New Game"),
                new MenuItem("Exit")
        );

        vbox.setTranslateX(100);
        vbox.setTranslateY(300);
        MenuLayout.getChildren().add(vbox);

////////////////////////////////////////////////////////
        MenuLayout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double mx=event.getX();
                double my=event.getY();
                if(mx>=100 && mx<=300){
                    if(my>=300 && my<=330){
                        Window.state= Window.STATE.GAME;
                        Scene scene = new Scene(layout, 800, 800);
                        primaryStage.setScene(scene);
                    }
                }
            }
        });


        ////////////////////////////////////////////////////////////

        Image image=new Image(imageURL);

        ImagePattern pattern = new ImagePattern(image,0,0,1,1,true);


        Scene MenuScene = new Scene(MenuLayout, 800, 800);
        primaryStage.setScene(MenuScene);
        MenuScene.setFill(pattern);

        primaryStage.show();

    }

    void number_of_clicks(Position p){
        int fromcol;
        int fromrow;
        int tocol;
        int torow;

        list.add(p);

        if(list.size()==1){
            int x,y;
            fromcol=list.get(0).x;
            fromrow=list.get(0).y;
            if(pawn[fromcol][fromrow].color==EMPTY){
                System.out.println("You Cant move anything from here because this area is empty");
                list.clear();
            }
            else if(pawn[fromcol][fromrow].color!=ORANGE && Window.player== PLAYER.ORANGE){
                System.out.println("Not this pawn");
                list.clear();
            }
            else if(pawn[fromcol][fromrow].color!=PINK && Window.player== PLAYER.PINK){
                System.out.println("Not this pawn");
                list.clear();
            }
        }
        if(list.size()==2) {
            System.out.println("wsp.0 \n" + list.get(0).x + "\n" + list.get(0).y + "\n");
            System.out.println("wsp.1 \n " + list.get(1).x + "\n" + list.get(1).y + "\n");
            fromcol=list.get(0).x;
            fromrow=list.get(0).y;
            tocol=list.get(1).x;
            torow=list.get(1).y;
            list.clear();
            check(fromcol,fromrow,tocol,torow);

        }
    }

    void change(int fromcol, int fromrow, int tocol, int torow){
        if(pawn[tocol][torow].color==EMPTY) {
            Pawn pawn_1 = pawn[fromcol][fromrow];
            type2 = pawn_1.type;
            Pawn pawn_2 = new Pawn(type2, tocol * 100, torow * 100);
            pawn[tocol][torow] = pawn_2;
            pawn[tocol][torow].color=type2;
            layout.getChildren().add(pawn[tocol][torow]);
            layout.getChildren().remove(pawn[fromcol][fromrow]);
            Pawn pawn_3=new Pawn();
            pawn[fromcol][fromrow]=pawn_3;
            pawn[fromcol][fromrow].color=EMPTY;

        }
        else{
            System.out.println("there is pawn already");
        }
    }

    void take(int fromcol, int fromrow, int tocol, int torow, int midcol, int midrow){
        Pawn color1 = pawn[fromcol][fromrow];
        type2 = color1.type;
        Pawn color2=pawn[midcol][midrow];
        int type3;
        type3=color2.type;
        if(type2!=type3) {
            //jeżeli jest pusto to dokonamy zbicia
            if (pawn[tocol][torow].color == EMPTY) {
                Pawn pawn_1 = pawn[fromcol][fromrow];
                type2 = pawn_1.type;
                Pawn pawn_2 = new Pawn(type2, tocol * 100, torow * 100);
                pawn[tocol][torow] = pawn_2;
                pawn[tocol][torow].color = type2;
                layout.getChildren().add(pawn[tocol][torow]);
                layout.getChildren().remove(pawn[fromcol][fromrow]);
                layout.getChildren().remove(pawn[midcol][midrow]);
                Pawn pawn_3 = new Pawn();
                pawn[fromcol][fromrow] = pawn_3;
                pawn[fromcol][fromrow].color = EMPTY;
                Pawn pawn_4 = new Pawn();
                pawn[midcol][midrow] = pawn_4;
                pawn[midcol][midrow].color = EMPTY;

            } else {
                System.out.println("there is a pawn already");
            }
        }
        else{
            System.out.println(" You can't get your own pawn");
        }
    }

    void check(int fromcol, int fromrow, int tocol, int torow){

        int midcol;
        int midrow;

        colour_player= colour_player+1;

        if(colour_player%2!=0){
            Window.player=PLAYER.ORANGE;
        }
        else if (colour_player%2==0) {
            Window.player=PLAYER.PINK;
        }

        if(tocol==fromcol+1 && torow==fromrow+1){
            change(fromcol,fromrow,tocol,torow);
        }
        else if(tocol==fromcol-1 && torow==fromrow+1){
            change(fromcol,fromrow,tocol,torow);
        }
        else if(tocol==fromcol-1 && torow==fromrow-1){
            change(fromcol,fromrow,tocol,torow);
        }
        else if(tocol==fromcol+1 && torow==fromrow-1){
            change(fromcol,fromrow,tocol,torow);
        }
        else if(tocol==fromcol+2 && torow==fromrow+2){
            midcol=fromcol+1;
            midrow=fromrow+1;
            take(fromcol,fromrow,tocol,torow,midcol,midrow);
        }
        else if(tocol==fromcol-2 && torow==fromrow+2){
            midcol=fromcol-1;
            midrow=fromrow+1;
            take(fromcol,fromrow,tocol,torow,midcol,midrow);
        }
        else if(tocol==fromcol+2 && torow==fromrow-2){
            midcol=fromcol+1;
            midrow=fromrow-1;
            take(fromcol,fromrow,tocol,torow,midcol,midrow);
        }
        else if(tocol==fromcol-2 && torow==fromrow-2){
            midcol=fromcol-1;
            midrow=fromrow-1;
            take(fromcol,fromrow,tocol,torow,midcol,midrow);
        }
        else {
            System.out.println("you cant't do this");
        }


    }

    public static void main(String[] args){

        launch(args);

    }
}