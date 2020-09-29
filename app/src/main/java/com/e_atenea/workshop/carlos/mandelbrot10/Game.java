package com.e_atenea.workshop.carlos.mandelbrot10;

import android.graphics.Canvas;

public abstract class Game implements GameViewDrawer, Runnable {

	private final GameView gameView;
	private Thread thread;
	private volatile boolean running;
	private final SyncQueue<GameMsg> queue;


	public Game(GameView gameView){
		this.gameView = gameView;
		thread = null;
		queue = new SyncQueue<>();
	}

	//Metodo llamado por el MainThread
	public void onResume(){
		queue.clear();
		thread = new Thread(this, "game thread");
		thread.start();
	}

	@Override
	public void onUpdate() {}

	@Override
	public void onDraw(Canvas c) {}

	//MainThread
	public void onPause(){
		queue.clear();
		running = false;
		try {
			thread.join();		//El MainThread se duerme hasta que el hilo secundario acabe
		} catch (InterruptedException e){}
		thread = null;
	}

	//Esto lo ejecuta el hilo secundario, no hay una fs, irá tan rápido como el procesador pueda
	@Override
	public void run() {
		running = true;
		while (running){
			despacharMsg();
			onUpdate();
			gameView.transfer(this);
		}
	}

	public void sendMsg(GameMsg msg){
		queue.put(msg);
	}

	private void despacharMsg(){
		//while (!queue.isEmpty()){}
		GameMsg msg;
		while ((msg = queue.get()) != null){
			msg.run();
		}
	}

}
