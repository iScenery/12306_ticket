package com.free.app.ticket.td.bq;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * 在文件中搜索指定关键词
 * Created by wyfengyuepan on 2015/2/16.
 */
public class SearchTask implements Runnable {

	private BlockingQueue<File> queue;
	private String keyword;

	/**
	 * 构造一个搜索任务
	 * @param queue 在这个队列中获取要操作的文件
	 * @param keyword 要搜索的关键词
	 */
	public SearchTask(BlockingQueue<File> queue,String keyword){
		this.queue = queue;
		this.keyword = keyword;
	}
	@Override
	public void run() {
		 try{
			 boolean done = false;
			 while (!done){
				 File file = queue.take();
				if (file == FileEnumerationTask.DUMMY){
					queue.put(file);
					done = true;
				}else
					search(file);

			 }
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 } catch (IOException e){
			 e.printStackTrace();
		 }
	}

	/**
	 * 在指定的文件中找到关键字，并且打印出来
	 * @param file
	 * @throws IOException
	 */
	public void search(File file) throws IOException{

			Scanner in = new Scanner(file);
			int lineNum = 0;
			while (in.hasNext()){
				lineNum ++;
				String line = in.nextLine();
				if(line.contains(keyword)){
					System.out.printf("%s:%d:%s%n",file.getPath(),lineNum,line);
				}
			}

	}
}
