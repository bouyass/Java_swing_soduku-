import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.lyes.model.Board;
import com.lyes.views.View;

public class Main {

	public static void main(String[] args) throws IOException {
		
		View view = new View(new Board(0));
		GameTimer gameTimer = new GameTimer(view);
		
	}

}

class GameTimer{
	Timer timer;
	
	GameTimer(View view){
		this.timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				view.updateTimer();
			}}, 1000, 1000);
	}
}
