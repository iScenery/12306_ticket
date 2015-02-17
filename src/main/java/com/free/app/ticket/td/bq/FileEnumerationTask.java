package com.free.app.ticket.td.bq;

import java.io.File;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * 列举出所有的在给定文件夹和其子文件夹中的所有文件
 * Created by wyfengyuepan on 2015/2/16.
 */
public class FileEnumerationTask implements Runnable{
	public static File DUMMY = new File("");
	private BlockingQueue<File> queue;
	private File startingDirctory;

	public FileEnumerationTask(BlockingQueue<File> queue,File startingDirctory){
		this.queue = queue;
		this.startingDirctory = startingDirctory;
	}
	@Override
	public void run() {

			try {
				enumerate(startingDirctory);
				queue.put(DUMMY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

	}
	public void enumerate(File directory) throws InterruptedException {
		File[] files = directory.listFiles();
		for (File file : files){
			if (file.isDirectory()){
				enumerate(file);
			}else
				queue.put(file);
		}
	}
}
