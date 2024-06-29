package model;

public class PayDebit implements Payment {
	public  double amountPay=0;
    @Override
    public void pay(double amount) {
    	amountPay+=amount;
    }

}
