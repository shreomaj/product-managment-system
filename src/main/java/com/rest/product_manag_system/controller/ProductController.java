package com.rest.product_manag_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.product_manag_system.dto.ProductDto;
import com.rest.product_manag_system.dto.ProductResponse;
import com.rest.product_manag_system.model.Product;
import com.rest.product_manag_system.service.ProductService;

//import com.ProductManagment.ProductManagmentSystem.entity.Product;

@RestController
public class ProductController{
	@Autowired
	private ProductService productService;
	
	@PostMapping("/save-product")
	public ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto) {
		try {
			Boolean saveProduct = productService.saveProduct(productDto);
			if (!saveProduct) {
				return new ResponseEntity<>("product not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("saved success", HttpStatus.CREATED);
	}

	@GetMapping("/products")
	public ResponseEntity<?> getProducts() {
		List<ProductDto> allProducts = null;
		try {
			allProducts = productService.getAllProducts();

			if (CollectionUtils.isEmpty(allProducts)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(allProducts, HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<?> getProducts(@PathVariable(name = "id") Integer id) {
		ProductDto product = null;
		try {
			product = productService.getProductById(id);

			if (ObjectUtils.isEmpty(product)) {
				return new ResponseEntity<>("Product not found with id=" + id, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Integer id) {
		Boolean deleteProduct = null;
		try {
			deleteProduct = productService.deleteProduct(id);

			if (!deleteProduct) {
				return new ResponseEntity<>("Product not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Delete success", HttpStatus.OK);
	}

	@GetMapping("/page-products")
	public ResponseEntity<?> getProductsPaginate(
	        @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
	        @RequestParam(name = "pageSize", defaultValue = "2") int pageSize,
	        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
	        @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir) {
	    try {
	        // Validate input
	        if (pageNo < 0 || pageSize <= 0) {
	            return ResponseEntity.badRequest().body("Invalid pagination parameters. Page number must be >= 0 and page size must be > 0.");
	        }
	        if (!sortDir.equalsIgnoreCase("asc") && !sortDir.equalsIgnoreCase("desc")) {
	            return ResponseEntity.badRequest().body("Invalid sort direction. Use 'asc' or 'desc'.");
	        }

	        ProductResponse productResponse = productService.getProductsWithPagination(pageNo, pageSize, sortBy, sortDir);

	        if (ObjectUtils.isEmpty(productResponse) || ObjectUtils.isEmpty(productResponse.getProducts())) {
	            return ResponseEntity.noContent().build();
	        }

	        return ResponseEntity.ok(productResponse);
	    } catch (Exception e) {
	        // Log the exception
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while fetching paginated products. Please try again.");
	    }
	}




}
