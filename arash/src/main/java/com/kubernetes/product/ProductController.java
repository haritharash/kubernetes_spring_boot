package com.kubernetes.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping("/products")
	public ResponseEntity<Product> addNewProduct(@RequestBody Product data) {
		return new ResponseEntity<>(productRepository.save(data), HttpStatus.CREATED);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {

		Optional<Product> foundProduct = productRepository.findById(productId);
		if (!foundProduct.isEmpty()) {
			return new ResponseEntity<>(foundProduct.get(), HttpStatus.OK);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

	}

	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product data, @PathVariable Integer productId) {

		Optional<Product> foundProduct = productRepository.findById(productId);

		if (!foundProduct.isEmpty()) {
			Product updatedProduct = foundProduct.get();
			updatedProduct.setDescription(data.getDescription());
			updatedProduct.setId(productId);
			updatedProduct.setName(data.getName());
			return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(updatedProduct));
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {

		Optional<Product> foundProduct = productRepository.findById(productId);

		if (foundProduct.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		productRepository.delete(foundProduct.get());

		return new ResponseEntity<>("Resource with ID " + productId + " deleted successfully.", HttpStatus.NO_CONTENT);

	}

}
