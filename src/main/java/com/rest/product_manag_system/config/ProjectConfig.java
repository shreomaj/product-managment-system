package com.rest.product_manag_system.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rest.product_manag_system.dto.ProductDto;
import com.rest.product_manag_system.model.Product;

@Configuration
public class ProjectConfig {

//	@Bean
//	public ModelMapper mapper()
//	{
//		return new ModelMapper();
//	}
	@Bean
	public ModelMapper mapper() {
	    ModelMapper mapper = new ModelMapper();

	    // Customize mappings if needed
	    mapper.typeMap(ProductDto.class, Product.class)
	          .addMappings(mapping -> {
	              mapping.skip(Product::setId); // Example: Skipping the ID during mapping
	          });

	    return mapper;
	}
}
