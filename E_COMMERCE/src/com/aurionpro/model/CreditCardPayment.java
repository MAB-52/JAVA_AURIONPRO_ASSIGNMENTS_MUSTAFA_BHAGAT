package com.aurionpro.model;

public class CreditCardPayment implements IPaymentGateway{

	@Override
	public void pay(double amount)
	{
		System.out.println("Processing credit card payment of: " + amount);
	}

	@Override
	public void refund(double amount)
	{
		System.out.println("Cashback amount:  " + amount + " to credit card");
		System.out.println("-------------------------------------------------");
	}



}
