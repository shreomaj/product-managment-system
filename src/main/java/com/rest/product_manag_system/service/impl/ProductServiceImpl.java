package com.rest.product_manag_system.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import com.rest.product_manag_system.dto.ProductDto;
import com.rest.product_manag_system.dto.ProductResponse;
import com.rest.product_manag_system.model.Product;
import com.rest.product_manag_system.repo.ProductRepository;
import com.rest.product_manag_system.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveProduct(ProductDto productDto) {

		//but we need to save the value in the product entity

//		Product product = new Product();
//		product.setId(productDto.getId());
//		product.setName(productDto.getName());
//		product.setDescription(productDto.getDescription());
//		product.setPrice(productDto.getPrice());
//		product.setQuantity(productDto.getQuantity());
		//always save entity class not dto
		
		Product product = mapper.map(productDto, Product.class);
		Product save = productRepository.save(product);
		if (ObjectUtils.isEmpty(save)) // save=!nulll
		{
			return false;
		}
		return true;
	}

	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> productList = productRepository.findAll();
		List<ProductDto> productDtoList = productList.stream()
									.map(product ->mapper.map(product, ProductDto.class))
									.collect(Collectors.toList());
		return productDtoList;
	}

	@Override
	public ProductDto getProductById(Integer id) {
		Optional<Product> findByProduct = productRepository.findById(id);
		if (findByProduct.isPresent()) {
			Product product = findByProduct.get();
			ProductDto productDto = mapper.map(product, ProductDto.class);
			return productDto;
		}
		return null;
	}

	@Override
	public Boolean deleteProduct(Integer id) {
		Optional<Product> findByProduct = productRepository.findById(id);
		if (findByProduct.isPresent()) {
			Product product = findByProduct.get();
			productRepository.delete(product);
			return true;
		}
		return false;
	}

	@Override
	public ProductResponse getProductsWithPagination(int pageNo, int pageSize, String sortBy, String sortDir) 
	{
//		Sort sort = Sort.by(sortBy).ascending();
//		Sort sort2 = Sort.by(sortBy).descending();
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
		Page<Product> page = productRepository.findAll(pageable);
		List<Product> products = page.getContent();
		
		List<ProductDto> productsDtos = products.stream()
				.map(prod -> mapper.map(prod, ProductDto.class)).toList();
		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();
		boolean first = page.isFirst();
		boolean last = page.isLast();
		
		//Replacement of Builder
//		ProductResponse productResponse=new ProductResponse();
//		productResponse.setProducts(productsDtos);
//		productResponse.setTotalElements(totalElements);
		
		ProductResponse productResponse =new ProductResponse.Builder()
				.products(productsDtos).totalElements(totalElements)
				.totalPages(totalPages).isFirst(first).isLast(last).pageNo(pageNo).pageSize(pageSize).build();
		return productResponse;

	}

}
