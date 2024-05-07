package com.mobiuso.controller;

import com.mobiuso.entity.*;
import com.mobiuso.jwt.JwtTokenProvider;
import com.mobiuso.repository.OrderItemsRepository;
import com.mobiuso.repository.ProductAsItIsRepository;
import com.mobiuso.repository.ProductRepository;
import com.mobiuso.repository.UserRepository;
import com.mobiuso.request.ProductDto;
import com.mobiuso.service.UserPossessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserPossessionController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserPossessionService userPossessionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private ProductAsItIsRepository productAsItIsRepository;

    @PostMapping("/saveTheProduct")
    public ResponseEntity<String> saveTheProduct (@RequestHeader("Authorization") String jwt, @RequestBody Product product) {
        String email = this.jwtTokenProvider.getEmailFromToken(jwt);
//        log.info("ProductRequest " + productDTO.toString());
//        Product product = this.userPossessionService.productRequestToEntity(productDTO);     //this works as DTO (Data Transfer Object)
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            product.setUser(user);
            this.productRepository.save(product);
            log.info("Product got saved "+ product.toString() + " with " + user.getName());    //this is logger
            return new ResponseEntity<>("Product with regards to this user "+ user.getName()+" got saved",HttpStatus.CREATED);
        }else {
            log.error("It seems as if there is no user to fetch data with");
            return new ResponseEntity<>("There is no user to map address with",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllSavedProducts(@RequestHeader("Authorization") String jwt){
        String email = this.jwtTokenProvider.getEmailFromToken(jwt);
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            List<Product> productList = this.productRepository.findAllByUser(user);
            if(productList.size() != 0){
                List<ProductDto> productDTOList = this.userPossessionService.productListEntityToRequest(productList);
                log.info("Products are available");
                log.info("Product entity : " + productList.toString());
                log.info("product Dto : " + productDTOList.toString());
                return ResponseEntity.ok(productDTOList);
            }else {
                log.warn("Products are not available");
                return new ResponseEntity<>("Products are not reginstered by this user "+user.getName(),HttpStatus.NOT_FOUND);
            }
        }
        log.error("User didn't found so is the products");
        return new ResponseEntity<>("User didn't found so is the products",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/placeTheOrderItems")
    public ResponseEntity<?> placeTheOrderItems (@RequestHeader("Authorization") String jwt, @RequestBody OrderItems orderItems){
        String email = this.jwtTokenProvider.getEmailFromToken(jwt);
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            List<Product> productList = orderItems.getProductList();

            List<String> productIdList = productList.stream().map(product -> product.getProductId().toString()).collect(Collectors.toList());   // this will abstract productIds from product list
            log.info("this will filter out only ListOfProductIds : " + productIdList);
            List<Product> products=productRepository.findAllById(productIdList);

            log.info("All the retrieved Products are : " + products.toString());
            orderItems.setProductList(products);
            orderItems.setUser(user);
            orderItems.setProductQuantity(orderItems.getProductList().size());
            this.orderItemsRepository.save(orderItems);
            return new ResponseEntity<>("order has been placed with regards to " + user.getName(),HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getAllUploadedProducts")
    public ResponseEntity<?> getAllUploadProducts(){
        List<ProductAsItIs> productAsItIsList = this.productAsItIsRepository.findAll();
        return ResponseEntity.ok(productAsItIsList);
    }

    @DeleteMapping("/deleteOrderItemById/{orderItemId}")
    public ResponseEntity<?> deleteOrderItem (@RequestHeader("Authorization") String jwt,@PathVariable String orderItemId){
        String email = this.jwtTokenProvider.getEmailFromToken(jwt);
        log.info("this is orderItemId : " +orderItemId);
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<OrderItems> optionalOrderItem = this.orderItemsRepository.findById(orderItemId);
            if (optionalOrderItem.isPresent()) {
                OrderItems orderItem = optionalOrderItem.get();
                this.orderItemsRepository.delete(orderItem);
                log.error("OrderItem with this credential : " + orderItemId + " got deleted");
                return new ResponseEntity<>("OrderItem with this credential " + orderItemId + " got deleted", HttpStatus.GONE);
            } else {
                log.error("OrderItem with the following credential " + orderItemId + " doesn't exist");
                return new ResponseEntity<>("OrderItem with the following credential " + orderItemId + " doesn't exist", HttpStatus.NOT_FOUND);
            }
        }
        log.error("User not found so is the orderItems --------- from delete method");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/getOrderItems")
    public ResponseEntity<?> getOrderItems(@RequestHeader("Authorization") String jwt){
        String email = this.jwtTokenProvider.getEmailFromToken(jwt);
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<List<OrderItems>> optionalOrderItemsList = this.orderItemsRepository.findAllByUser(user);
            if(optionalOrderItemsList.isPresent()){
                List<OrderItems> orderItemsList = optionalOrderItemsList.get();
                log.info("All the product with respect to the user "+ user.getName()+" are here");
                log.info(orderItemsList.toString());
                return ResponseEntity.ok(orderItemsList);
            }else {
                log.error("this user "+user.getName()+" doesn't have any orderitems yet");
                return ResponseEntity.notFound().build();
            }
        }
        log.error("User not found so it the orderItems--------------- from get method");
        return ResponseEntity.badRequest().build();
     }

//     @DeleteMapping("/deleteParticularProduct/{productId}")
//     public ResponseEntity<?> deleteParticularProduct (@RequestHeader("Authorization") String jwt,@PathVariable String productId){
//        String email = this.jwtTokenProvider.getEmailFromToken(jwt);
//     }

}
