package com.e_atenea.workshop.carlos.mandelbrot10;

import java.util.ArrayList;
import java.util.List;

public class SyncQueue<E> {

	private List<E> list;

	public SyncQueue(){
		list = new ArrayList<E>();
	}

	synchronized public E get(){
		if (list.isEmpty()){ return null;}
		return list.remove(0);
	}

	synchronized public void put(E e){
		list.add(e);
	}

	synchronized public void clear(){
		list.clear();
	}

	synchronized public boolean isEmpty(){
		return list.isEmpty();
	}

}
