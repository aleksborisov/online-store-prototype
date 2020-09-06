package com.marsarmy.service.impl;

import com.marsarmy.converter.ProductConverter;
import com.marsarmy.dao.interf.ProductDao;
import com.marsarmy.dto.ProductDto;
import com.marsarmy.service.interf.JmsService;
import com.marsarmy.statistics.ProductStatistics;
import com.marsarmy.model.Product;
import com.marsarmy.service.interf.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service responsible for operations on products
 */
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ProductConverter productConverter;
    private final JmsService jmsService;

    private static final Logger LOGGER = Logger.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductDao productDao, ProductConverter productConverter, JmsService jmsService) {
        this.productDao = productDao;
        this.productConverter = productConverter;
        this.jmsService = jmsService;
    }

    /**
     * Creates a new product
     *
     * @param product Product entity to be created
     */
    @Override
    @Transactional
    public void create(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product can't be null");
        }

        productDao.create(product);
        LOGGER.info("Product #" + product.getUpc() + " was created");
    }

    /**
     * Updates transmitted product
     *
     * @param product Product entity to be updated
     */
    @Override
    @Transactional
    public void update(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product can't be null");
        }

        productDao.update(product);
        jmsService.sendUpdate();
        LOGGER.info("Product #" + product.getUpc() + " was updated");
    }

    /**
     * Returns the list of all products
     *
     * @return {@link List} of {@link Product}
     */
    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    /**
     * Returns the product by upc
     *
     * @param upc UPC of product to get
     * @return {@link Product}
     */
    @Override
    public Product getOne(long upc) {
        if (upc < 1) {
            throw new IllegalArgumentException("Product UPC can't be less than 1");
        }

        return productDao.getOne(upc);
    }

    /**
     * Filters products by their fields
     *
     * @param category Category name
     * @param name Product name or its part
     * @param minPrice Minimum price include minPrice
     * @param maxPrice Maximum price include maxPrice
     * @param brand Product brand
     * @param color Product color
     * @return {@link List} of {@link Product}
     */
    @Override
    public List<Product> filter(String category, String name, int minPrice,
                                int maxPrice, String brand, String color) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Minimal price and maximum price can't be less than 0");
        }

        return productDao.filter(category, name, minPrice, maxPrice, brand, color);
    }

    /**
     * Returns the list of the top ten products
     *
     * @return {@link List} of {@link ProductStatistics}
     */
    @Override
    public List<ProductStatistics> getTopTenProducts() {
        return productDao.getTopTenProducts();
    }

    /**
     * Returns the map of products and its quantity in the cart at the moment. If cart is empty, returns an empty map.
     *
     * @param cart Map of product UPCs and its quantity in the cart
     * @return {@link Map} of {@link ProductDto}, {@link Integer}
     */
    @Override
    public Map<ProductDto, Integer> getProductsFromCart(Map<Long, Integer> cart) {
        if (cart == null) {
            return new HashMap<>();
        }

        Map<ProductDto, Integer> products = new HashMap<>();

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            products.put(productConverter.convertToDto(productDao.getOne(entry.getKey())), entry.getValue());
        }

        return products;
    }

    /**
     * Returns true if the requested quantity of product is equals or greater than the quantity in stock.
     * Otherwise returns false.
     *
     * @param upc Product UPC to check quantity
     * @param quantity Requested quantity of product
     * @return Boolean
     */
    @Override
    public boolean checkNumberOfProducts(long upc, int quantity) {
        if (upc < 1 || quantity < 1) {
            throw new IllegalArgumentException("Product UPC and quantity can't be less than 1");
        }

        return productDao.getOne(upc).getInStock() >= quantity;
    }
}
