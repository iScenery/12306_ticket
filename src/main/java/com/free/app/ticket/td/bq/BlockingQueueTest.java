package com.free.app.ticket.td.bq;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * Created by wyfengyuepan on 2015/2/16.
 */
public class BlockingQueueTest {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter base dictionary:");
		String directory = scanner.nextLine();
		System.out.print("Enter keyword:");
		String keyword = scanner.nextLine();
		final int FILE_QUEUE_SIZE = 10;
		final int SEARCH_THREAD = 100;

		BlockingQueue<File> queue = new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);

		FileEnumerationTask enumerator = new FileEnumerationTask(queue,new File(directory));
		new Thread(enumerator).start();

		for (int i = 1;i <SEARCH_THREAD;i++){
			new Thread(new SearchTask(queue,keyword)).start();
		}
	}
}
