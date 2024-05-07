package com.mobiuso.controller;

import com.mobiuso.entity.Address;
import com.mobiuso.entity.OrderItems;
import com.mobiuso.entity.User;
import com.mobiuso.jwt.JwtTokenProvider;
import com.mobiuso.repository.AddressRepository;
import com.mobiuso.repository.UserRepository;
import com.mobiuso.request.AddressDto;
import com.mobiuso.service.UserAboutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserAboutController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserAboutService userAboutService;

    @PostMapping("/saveTheAddress")
    public ResponseEntity<String> saveTheAddress(@RequestHeader("Authorization") String jwt, @RequestBody AddressDto addressDTO){
        String email = this.jwtTokenProvider.getEmailFromToken(jwt);
        Address address = this.userAboutService.addressRequestToEntity(addressDTO);  //this works as DTO (Data Transfer Object)
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            address.setUser(user);
            log.info("Address getting saved" + address.toString() + "with" + user.getName());        //this is logger
            this.addressRepository.save(address);
            return new ResponseEntity<>("Address got saved with regards to "+user.getName(), HttpStatus.CREATED);
        }else {
            log.error("It seems as if there is no user to fetch data with");            //logger to print message in console
            return new ResponseEntity<>("There is no user to map address with",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAddress")
    public ResponseEntity<?> getAddressOfUser(@RequestHeader("Authorization") String jwt){
        String email = this.jwtTokenProvider.getEmailFromToken(jwt);
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            Address address = this.addressRepository.findByUser(user);
            if(address != null){
                AddressDto addressRequest = this.userAboutService.addressEntityToRequest(address);  //converting entity to request
                return ResponseEntity.ok(addressRequest);
            }else {
                log.warn("This user hasn't registered his/her address");        //logger
                return new ResponseEntity<>("This user hasn't registered his/her address",HttpStatus.NOT_FOUND);
            }
        }
        log.error("this part will only run when we didn't find user");  //this is logger
        return new ResponseEntity<>("User didn't found so is the address",HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/modifyTheAddress")
    public ResponseEntity<?> modifyAddress(@RequestHeader("Authorization") String jwt,@RequestBody AddressDto addressDTO){
        String email = this.jwtTokenProvider.getEmailFromToken(jwt);
        Address address = this.userAboutService.addressRequestToEntity(addressDTO);  //converting request to entity
        log.info(address.toString());
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            this.userAboutService.modifyTheAddress(user,address);
            log.info("Address has got modified");               //logger to print message in console
            return new ResponseEntity<>("Address has been modified for the user "+user.getName(),HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    //        OrderItems orderItems=new OrderItems();
//        orderItems.getProductList().stream().map(p->p.getProductId()).collect(Collectors.toList());
//        List<Product> products productRepo.findAllById(productIds)
//        orderItems.setProductList(products)
//        ;

}
