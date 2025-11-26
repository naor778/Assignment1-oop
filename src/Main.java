import biuoop.GUI;
import biuoop.DrawSurface;
import java.awt.Color;

public class Main {

                public static void main(String[] args) {
                    Game game = new Game();
                    game.initialize();
                    game.run();
                }


    private static Block[] addScreenBorders(GameEnvironment env, int width, int height) {

        int thickness = 10;  // עובי הקיר

        Block top = new Block(
                new Rectangle(new Point(0, 0), width, thickness),
                Color.GRAY
        );

        Block bottom = new Block(
                new Rectangle(new Point(0, height - thickness), width, thickness),
                Color.GRAY
        );

        Block left = new Block(
                new Rectangle(new Point(0, 0), thickness, height),
                Color.GRAY
        );

        Block right = new Block(
                new Rectangle(new Point(width - thickness, 0), thickness, height),
                Color.GRAY
        );


        env.addCollidable(top);
        env.addCollidable(bottom);
        env.addCollidable(left);
        env.addCollidable(right);

        return new Block[]{top, bottom, left, right};
    }

}
