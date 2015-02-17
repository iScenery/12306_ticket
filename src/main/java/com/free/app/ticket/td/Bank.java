package com.free.app.ticket.td;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wyfengyuepan on 2015/2/12.
 */
public class Bank {
	private final Double[] accounts;
	private Lock bankLock ;
	private ReentrantReadWriteLock bankLock2 = new ReentrantReadWriteLock();

	private Condition sufficientFunds ;
	public static final ThreadLocal<SimpleDateFormat> dateFormat =
			new ThreadLocal<SimpleDateFormat>(){
				protected SimpleDateFormat initialValue(){
					return  new SimpleDateFormat("yyyy-MM-dd");
				}
			};
	/**
	 * Bank构造器 初始化每个账户的金额
	 * @param n
	 * @param initBalance
	 */
	public Bank(int n ,Double initBalance) {
		accounts = new Double[n];
		for (int i = 0;i<n;i++){
			accounts[i] = initBalance;
		}
		bankLock = new ReentrantLock();
		sufficientFunds = bankLock.newCondition();
	}

	/**
	 * 将一个账户amount金额从from 转移 到 to
	 * @param from
	 * @param to
	 * @param amount
	 */
	public void transfer(int from ,int to, Double amount){
		dateFormat.get().format(new Date());
		bankLock.lock();
		try{
			while (accounts[from] < amount)
				sufficientFunds.await();
			System.out.println(Thread.currentThread());
			accounts[from] -= amount;
			System.out.printf(" %10.2f from %d to %d",amount,from,to);
			accounts[to] += amount;
			System.out.printf("Total balance %10.2f%n",getTotalBalance());
			sufficientFunds.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bankLock.unlock();
		}


	}

	/**
	 * 获取所有账户的所有金额
	 * @return the total balance
	 */
	public Double getTotalBalance(){
		bankLock.lock();
		try{
			Double sum = new Double(0);
			for(Double amount : accounts){
				sum += amount;
			}
			return sum;
		}finally {
			bankLock.unlock();
		}

	}

	/**
	 * 获取银行的账户数
	 * @return 所有用户数
	 */
	public int size(){
		return accounts.length;
	}
}
