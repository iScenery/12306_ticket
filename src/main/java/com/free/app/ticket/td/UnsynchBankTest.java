package com.free.app.ticket.td;

/**
 * Created by wyfengyuepan on 2015/2/12.
 */
public class UnsynchBankTest {
	public static final int ACCONTS = 10;
	public static final Double INITAL_BALANCE = new Double(1000);
	public static void main(String[] args) {
		Bank b = new Bank(ACCONTS,INITAL_BALANCE);
		int i;
		for (i = 0;i<ACCONTS;i++){
			TransferRunnable r = new TransferRunnable(b,i,2*INITAL_BALANCE);
			Thread t = new Thread(r);
			t.start();
		}
	}

}
