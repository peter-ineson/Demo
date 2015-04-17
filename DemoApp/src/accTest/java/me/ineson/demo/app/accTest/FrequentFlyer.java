package me.ineson.demo.app.accTest;

public class FrequentFlyer {

	int balance;
	int kms = 0;
	
	public FrequentFlyer(int balance) {
		super();
		this.balance = balance;
	}

	public static FrequentFlyer withInitialBalanceOf(int initialBalance) {
		// TODO Auto-generated method stub
		return new FrequentFlyer(initialBalance);
	}

	public FrequentFlyer flies(int distance) {
		kms = distance;
		balance += (distance / 10);
		return this;
	}

	public int kilometers() {
		return kms;
	}

	public int getBalance() {
		return balance;
	}

	public Status getStatus() {
		return balance > 1000 ? Status.Gold : Status.Silver;
	}

}
