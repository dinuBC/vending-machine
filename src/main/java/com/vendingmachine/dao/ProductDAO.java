package com.vendingmachine.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.vendingmachine.machine.product.ProductEntity;
import com.vendingmachine.utils.HibernateUtils;

import java.util.List;

public class ProductDAO {

	public void createProduct(ProductEntity product) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();
		session.save(product);
		session.getTransaction().commit();
		HibernateUtils.shutdown();
	}
	public void deleteProduct (ProductEntity product){
		Session session1 = HibernateUtils.getSessionFactory().openSession();
		session1.beginTransaction();
		session1.delete(product);
		session1.getTransaction().commit();
		HibernateUtils.shutdown();
	}
	public void updateProduct(ProductEntity newProduct){
		Session session2=HibernateUtils.getSessionFactory().openSession();
		session2.beginTransaction();
		session2.update(newProduct);
		session2.getTransaction().commit();
		HibernateUtils.shutdown();
	}
	public List<ProductEntity> getallProducts(){
		Session session3 = HibernateUtils.getSessionFactory().openSession();
		session3.beginTransaction();
		List<ProductEntity> products = session3.createQuery("from ProductEntity").list();
		for(ProductEntity product :products){
			System.out.println(product.toString());
		}
		session3.getTransaction().commit();
		HibernateUtils.shutdown();
		return products;
	}
}
