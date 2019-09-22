package com.vendingmachine.machine;

import com.vendingmachine.bank.Bank;
import com.vendingmachine.bank.Money;
import com.vendingmachine.exception.SoldOutException;
import com.vendingmachine.machine.product.CocaCola;
import com.vendingmachine.machine.product.Product;
import com.vendingmachine.machine.storage.Storage;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class VendingMachineServiceTest {
	@Test
	public void testDispenceProduct_success() {
		Bank bank = initBank();
		Storage storage = initStorage();
		VendingMachineService service = new VendingMachineService();
		
		service.setBank(bank);
		service.setStorage(storage);
		
		Product product = service.dispenceProduct(3);
		assertEquals(1, product.getId());
		assertEquals(1, storage.getStorageMap().size());
	}
	
	@Test (expected = SoldOutException.class)
	public void testDispenceProduct_exception(){
		Bank bank = initBank();
		Storage storage = initStorage();
		
		VendingMachineService service = new VendingMachineService();
		service.setStorage(storage);
		
		service.dispenceProduct(3);
		service.dispenceProduct(3);
		service.dispenceProduct(3);
	}
	
	public Bank initBank() {
		Bank bank = new Bank();
		
		Queue<Money> fiveDollarStack = new ArrayDeque<Money>();
		fiveDollarStack.add(Money.FIVE_DOLLAR);
		fiveDollarStack.add(Money.FIVE_DOLLAR);
		fiveDollarStack.add(Money.FIVE_DOLLAR);
		bank.setFiveDollarStack(fiveDollarStack);
		
		Queue<Money> oneDollarStack = new ArrayDeque<Money>();
		oneDollarStack.add(Money.ONE_DOLLAR);
		oneDollarStack.add(Money.ONE_DOLLAR);
		oneDollarStack.add(Money.ONE_DOLLAR);
		oneDollarStack.add(Money.ONE_DOLLAR);
		oneDollarStack.add(Money.ONE_DOLLAR);
		bank.setOneDollarStack(oneDollarStack);
		
		Queue<Money> fiftyCentsStack = new ArrayDeque<Money>();
		fiftyCentsStack.add(Money.FIFTY_CENT);
		fiftyCentsStack.add(Money.FIFTY_CENT);
		bank.setFiftyCentsStack(fiftyCentsStack);
		
		Queue<Money> tenCentsStack = new ArrayDeque<Money>();
		tenCentsStack.add(Money.TEN_CENT);
		bank.setTenCentsStack(tenCentsStack);
		
		return bank;
	}
	
	public Storage initStorage() {
		Storage storage = new Storage();
		Map<Integer, Queue<Product>> storageMap = new HashMap<>();
		Queue productQueue = new ArrayDeque();
		
		Product p1 = new CocaCola();
		p1.setId(1);
		p1.setPrice(3.5);
		Product p2 = new CocaCola();
		p2.setId(2);
		p2.setPrice(5);
		productQueue.add(p1);
		productQueue.add(p2);
		storageMap.put(3, productQueue);
		
	//	storage.setStorageMap(storageMap);
		
		return storage;
	}
}
