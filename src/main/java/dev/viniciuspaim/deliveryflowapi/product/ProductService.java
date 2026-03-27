package dev.viniciuspaim.deliveryflowapi.product;

import dev.viniciuspaim.deliveryflowapi.product.dto.ProductRequest;
import dev.viniciuspaim.deliveryflowapi.product.exception.ProductCategoryNotFoundException;
import dev.viniciuspaim.deliveryflowapi.product.exception.ProductNotFoundException;
import dev.viniciuspaim.deliveryflowapi.restaurant.Restaurant;
import dev.viniciuspaim.deliveryflowapi.restaurant.RestaurantRepository;
import dev.viniciuspaim.deliveryflowapi.restaurant.exception.RestaurantNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    final
    ProductRepository productRepository;
    final
    RestaurantRepository restaurantRepository;
    final
    ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductRepository productRepository,
                          RestaurantRepository restaurantRepository,
                          ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.restaurantRepository = restaurantRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public Product createProduct(ProductRequest request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Invalid Restaurant Id: " + request.getRestaurantId()));

        ProductCategory productCategory = productCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ProductCategoryNotFoundException("Invalid ProductCategory Id: " + request.getCategoryId()));

        Product product = Product.builder()
                .name(request.getName())
                .restaurant(restaurant)
                .category(productCategory)
                .status(ProductStatusEnum.UNAVAILABLE)
                .build();

                return productRepository.save(product);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Invalid Product Id: " + productId));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
