package com.aurionpro.model;

public class UPIPayment implements IPaymentGateway {

	@Override
	public void pay(double amount)
	{
		System.out.println("Processing UPI payment of: " + amount);
	}

	@Override
	public void refund(double amount)
	{
		System.out.println("Cashback amount:  " + amount + " to UPI ID");
		System.out.println("-------------------------------------------------");
	}

}
