package com.free.app.ticket.td;

import java.util.Map;

/**
 * 一个将钱从一个用户转移到另外一个用户里的线程
 * Created by wyfengyuepan on 2015/2/12.
 */
public class TransferRunnable implements Runnable{
	private Bank bank;
	private int fromAccount;
	private Double maxAmount;
	private int DELAY = 10;

	/**
	 *
	 * @param b 交易发生在哪个银行
	 * @param from 从哪个账户转出
	 * @param max 最大可以转成金额
	 */
	public TransferRunnable(Bank b, int from , Double max){
		bank = b;
		fromAccount = from;
		maxAmount = max;
	}
	@Override
	public void run() {

		try {
			while (true){
				int toAccount = (int) (bank.size()* Math.random());
				Double amount = maxAmount * Math.random();
				bank.transfer(fromAccount,toAccount,amount);
				Thread.sleep((int)(DELAY * Math.random()));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
