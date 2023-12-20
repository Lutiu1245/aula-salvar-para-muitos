package com.devsuperior.aula.services;

import com.devsuperior.aula.DTO.CategoryDTO;
import com.devsuperior.aula.DTO.ProductDTO;
import com.devsuperior.aula.entities.Category;
import com.devsuperior.aula.entities.Product;
import com.devsuperior.aula.repositories.CategoryRepository;
import com.devsuperior.aula.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDTO insert(ProductDTO dto) {
        Product product = new Product();
        copy(product, dto);
        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    public void copy(Product product, ProductDTO productDTO) {
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        for (CategoryDTO categoryDTO : productDTO.getCategories()) {
            Category category = categoryRepository.getReferenceById(categoryDTO.getId());
            category.setId(categoryDTO.getId());
            product.getCategories().add(category);
        }
    }
}
