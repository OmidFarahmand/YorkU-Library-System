package model;

public class PayMobileWallet implements Payment {
	public double amountPay=0;
    @Override
    public void pay(double amount) {
    	amountPay+=amount;
    }

}
