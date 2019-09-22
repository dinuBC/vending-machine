package com.vendingmachine.utils;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtils {

	private static final SessionFactory sessionFactory = createSessionFactory();

	private static SessionFactory createSessionFactory() {
		// TODO Auto-generated method stub
		try {
			return new AnnotationConfiguration().configure(
					new File().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("There was an error while creating the session factory " + e);
		}
		return null;
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static void shutdown() {
		getSessionFactory().close();
	}
}
