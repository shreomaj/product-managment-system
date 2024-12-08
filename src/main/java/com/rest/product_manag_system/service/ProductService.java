package com.rest.product_manag_system.service;

import java.util.List;

import com.rest.product_manag_system.dto.ProductDto;
import com.rest.product_manag_system.dto.ProductResponse;
import com.rest.product_manag_system.model.Product;

public interface ProductService {
	public Boolean saveProduct(ProductDto productDto);

	public List<ProductDto> getAllProducts();

	public ProductDto getProductById(Integer id);

	public Boolean deleteProduct(Integer id);

	public ProductResponse getProductsWithPagination(int pageNo, int pageSize, String sortBy, String sortDir);


}
