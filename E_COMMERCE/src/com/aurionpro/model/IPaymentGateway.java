package com.aurionpro.model;

public interface IPaymentGateway {
	
	public void pay(double amount);
	public void refund(double amount);

}
