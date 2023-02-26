package com.app.producerConsumer;

import java.util.LinkedList;
import java.util.List;

public class App {
	public static void main(String[] args) throws InterruptedException {
		final Supplyer supplyer = new Supplyer();
		Thread thread1 = new Thread() {
			public void run() {
				try {
					supplyer.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					supplyer.consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

	}
}

class Supplyer {

	List<Integer> list = new LinkedList<>();
	public static int capacity = 3;

	public void producer() throws InterruptedException {
		int i = 1;
		while (true) {
			synchronized (this) {
				while (list.size() == capacity) {
					wait();
				}
				list.add(i++);
				System.out.println("producer added 1");
				Thread.sleep(1000);
				notify();
			}
		}
	}

	public void consumer() throws InterruptedException {
		int j = 1;
		while (true) {
			synchronized (this) {
				while (list.isEmpty()) {
					wait();
				}
				list.remove(j);
				System.out.println("consumer remove 1 ");
				Thread.sleep(1000);
				notify();
			}
		}

	}
}