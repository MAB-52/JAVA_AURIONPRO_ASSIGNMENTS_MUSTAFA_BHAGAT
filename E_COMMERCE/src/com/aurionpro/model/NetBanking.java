package com.aurionpro.model;

public class NetBanking implements IPaymentGateway {

	@Override
	public void pay(double amount)
	{
		System.out.println("Processing net banking payment of: " + amount);
	}

	@Override
	public void refund(double amount)
	{
		System.out.println("Cashback amount:  " + amount + " through net banking");
		System.out.println("-------------------------------------------------");
	}
	
}
