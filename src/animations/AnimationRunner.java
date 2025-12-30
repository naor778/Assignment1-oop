package animations;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

public class AnimationRunner {
   private GUI gui;
   private Sleeper sleeper;
   private int framesPerSecond;
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.sleeper = new Sleeper();
        this.framesPerSecond = 60;
    }
    public void run(Animation animation){
      int millisecondsPerFrame = 1000 / framesPerSecond;

      while (!animation.shouldStop()){
          long startTime = System.currentTimeMillis();



          DrawSurface d = gui.getDrawSurface();
          animation.doOneFrame(d);
          gui.show(d);







          long usedTime = System.currentTimeMillis() - startTime;
          long milliSecondsLeftToSleep = millisecondsPerFrame - usedTime;

          if (milliSecondsLeftToSleep > 0) {
              sleeper.sleepFor(milliSecondsLeftToSleep);
      }


    }
}
    }