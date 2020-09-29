package com.e_atenea.workshop.carlos.mandelbrot10;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	private MandelbrotGame mandelbrotGame;
	private GameView gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		gameView = new GameView(this);
		gameView.setOnTouchListener(new TouchListener());
		Button button = new Button(this);
		button.setText("DEPTH");
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER | Gravity.BOTTOM;
		button.setLayoutParams(params);
		button.setOnClickListener(new DepthListener());
		((ViewGroup)findViewById(R.id.frameLayout)).addView(gameView);
		((ViewGroup)findViewById(R.id.frameLayout)).addView(button);

		mandelbrotGame = new MandelbrotGame(gameView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mandelbrotGame.onResume();
	}

	@Override
	protected void onPause() {
		mandelbrotGame.onPause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);	//Destruye el proceso porque no es seguro que el activity destruya los objetos o los statics
	}

	private class DepthListener implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			MandelbrotMsg msg = new ZoomMsg();
			msg.setListener(mandelbrotGame);
			mandelbrotGame.sendMsg(msg);
		}
	}

	private class TouchListener implements View.OnTouchListener {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == motionEvent.ACTION_DOWN){
				MandelbrotMsg msg = new MotionEventMsg(motionEvent);
				msg.setListener(mandelbrotGame);
				mandelbrotGame.sendMsg(msg);
				return true;
			}
			return false;
		}
	}
}
