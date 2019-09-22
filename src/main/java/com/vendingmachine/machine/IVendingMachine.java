package com.vendingmachine.machine;

import com.vendingmachine.bank.Money;
import com.vendingmachine.machine.product.Product;

import java.util.Queue;

public interface IVendingMachine {
	public Product dispenceProduct(int shelfCode);
	
	public boolean payProductPrice(int shelfCode, Queue<Money> amountPayed);
	
	public Queue<Money> computeChange(double amountReceived, double amountExpected);
}
