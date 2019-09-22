package com.vendingmachine.machine;

import com.vendingmachine.bank.Bank;
import com.vendingmachine.bank.Money;
import com.vendingmachine.exception.NotFullPaidException;
import com.vendingmachine.exception.NotSufficientChangeException;
import com.vendingmachine.exception.SoldOutException;
import com.vendingmachine.machine.product.Product;
import com.vendingmachine.machine.storage.Storage;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

public class VendingMachineService implements IVendingMachine {
	
	private Storage storage ;
	private Bank bank;
	
	public Storage getStorage() {
		return storage;
	}
	
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	public Bank getBank() {
		return bank;
	}
	
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	public Product dispenceProduct(int shelfCode) {
		Map<Integer, Queue<Product>> storageMap = storage.getStorageMap();
		Queue<Product> productQueue = storageMap.get(shelfCode);
		if (productQueue.isEmpty()) {
			throw new SoldOutException("Product is not available.");
		}
		
		Product head = productQueue.poll();
		return head;
	}
	
	@Override
	public boolean payProductPrice(int shelfCode, Queue<Money> amountPayed) {
		Map<Integer, Queue<Product>> storageMap = storage.getStorageMap();
		Queue<Product> productQueue = storageMap.get(shelfCode);
		double productPrice = productQueue.peek().getPrice();
		double amountPayedAsDouble = getTotalMoneyAsDouble(amountPayed);
		
		if (amountPayedAsDouble >= productPrice) {
			updateBank(amountPayed);
			Queue<Money> change = computeChange(amountPayedAsDouble, productPrice);
			updateBankWithChange(change);
			dispenceProduct(shelfCode);
			return true;
		} else {
			throw new NotFullPaidException("The amount of money introduced is insufficient");
		}
	}
	
	// todo refactorizam metoda data viitoare
	// metoda care ne calculeaza restul
	@Override
	// amountReceived = 11
	// amountExpected = 6
	// change = 5
	// firstCount = 5%5 = 0
	// 1 bancnota = 5/5
	public Queue<Money> computeChange(double amountReceived,
									  double amountExpected) {
		double totalChange = amountReceived - amountExpected;
		double change = totalChange % 5;
		double noOfCoins = totalChange / 5;
		Queue<Money> coins = new ArrayDeque<>();
		
		if (noOfCoins != 0 &&
				checkAvailableMoney(getBank().getFiveDollarStack(), noOfCoins)) {
			// create queue of coins to return
			for (int i = 0; i <= noOfCoins; i++) {
				coins.add(Money.FIVE_DOLLAR);
			}
		}
		if (change == 0) {
			return coins;
		} else {
			change = change % 1;
			noOfCoins = change / 1;
			if (noOfCoins != 0 &&
					checkAvailableMoney(getBank().getOneDollarStack(), noOfCoins)) {
				for (int i = 0; i <= noOfCoins; i++) {
					coins.add(Money.ONE_DOLLAR);
				}
			}
			if (change == 0) {
				return coins;
			} else {
				change = change % 0.5;
				noOfCoins = change / 0.5;
				
				if (noOfCoins != 0 &&
						checkAvailableMoney(getBank().getFiftyCentsStack(), noOfCoins)) {
					for (int i = 0; i <= noOfCoins; i++) {
						coins.add(Money.FIFTY_CENT);
					}
				}
				if (change == 0) {
					return coins;
				} else {
					change = change % 0.1;
					noOfCoins = change / 0.1;
					
					if (noOfCoins != 0 &&
							checkAvailableMoney(getBank().getTenCentsStack(), noOfCoins)) {
						for (int i = 0; i <= noOfCoins; i++) {
							coins.add(Money.TEN_CENT);
						}
					}
					if (change == 0) {
						return coins;
					}
				}
			}
			
		}
		return coins;
	}
	
	private boolean checkAvailableMoney(Queue<Money> moneyStack,
										double moneyIntroduced) {
		if (moneyStack.size() > moneyIntroduced) {
			return true;
		} else {
			throw new NotSufficientChangeException("The vending machine does not have enough change.");
		}
	}
	
	private void updateBank(Queue<Money> moneyReceived) {
		for (Money m : moneyReceived) {
			switch (m) {
				case FIVE_DOLLAR:
					getBank().getFiveDollarStack().add(m);
					break;
				case ONE_DOLLAR:
					getBank().getOneDollarStack().add(m);
					break;
				case FIFTY_CENT:
					getBank().getFiftyCentsStack().add(m);
					break;
				case TEN_CENT:
					getBank().getTenCentsStack().add(m);
					break;
			}
		}
		
	}
	
	private void updateBankWithChange(Queue<Money> moneyReceived) {
		for (Money m : moneyReceived) {
			switch (m) {
				case FIVE_DOLLAR:
					getBank().getFiveDollarStack().poll();
					break;
				case ONE_DOLLAR:
					getBank().getOneDollarStack().poll();
					break;
				case FIFTY_CENT:
					getBank().getFiftyCentsStack().poll();
					break;
				case TEN_CENT:
					getBank().getTenCentsStack().poll();
					break;
			}
		}
	}
	
	private double getTotalMoneyAsDouble(Queue<Money> money) {
		double total = 0;
		for (Money m : money) {
			switch (m) {
				case FIVE_DOLLAR:
					total += 5;
					break;
				case ONE_DOLLAR:
					total += 1;
					break;
				case FIFTY_CENT:
					total += 0.5;
					break;
				case TEN_CENT:
					total += 0.1;
					break;
			}
		}
		return total;
	}
	
}
