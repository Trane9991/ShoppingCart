package com.shoppingcart.manager;

import com.shoppingcart.dao.ProductDAO;
import com.shoppingcart.dao.impl.ProductDAOImpl;
import com.shoppingcart.domain.Product;

public class ProductManager {

	ProductDAO productDAO = new ProductDAOImpl();

	public Product get(String productNumber) {
		return productDAO.get(productNumber);
	}

	public Product generate() {
		return productDAO.generate();
	}

	public Product generateProductWithSkew() {
		return productDAO.generateProductWithSku();
	}

	public double getVariablePrice(Product product) {
		PriceManager priceMgr = new PriceManager();
		Object priceObj = priceMgr.getVariablePrice(product);
		Double price = (Double) priceObj;
		return price.doubleValue();
	}

	public void update(Product product) {
		// TODO Auto generated
	}
}
