package com.fishbook.system.controller;

import com.fishbook.exception.ApiRequestException;
import com.fishbook.system.dto.DiscountAndFeesDto;
import com.fishbook.system.model.GlobalConfig;
import com.fishbook.system.model.LoyaltyConfig;
import com.fishbook.system.service.ConfigService;
import com.fishbook.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
    private final ConfigService configService;
    private final UserService userService;

    @Autowired
    public ConfigController(ConfigService configService, UserService userService){
        this.configService = configService;
        this.userService = userService;
    }

    @GetMapping(value = "/loyalty")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoyaltyConfig>> getLoyaltyConfig(){
        return new ResponseEntity<>(configService.getLoyaltyConfig(), HttpStatus.OK);
    }

    @GetMapping(value = "/global")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GlobalConfig> getGlobalConfig(){
        return new ResponseEntity<>(configService.getGlobalConfig(), HttpStatus.OK);
    }

    @PutMapping(value = "/loyalty/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateLoyaltyConfig(@RequestBody LoyaltyConfig config, @PathVariable Integer id){
        Optional<LoyaltyConfig> c = configService.findLoyaltyConfigById(id);
        if(c.isPresent()){
            if(c.get().getLoyaltyType().equals(config.getLoyaltyType()) && c.get().getId().equals(config.getId())){
                configService.update(config);
                return new ResponseEntity<>(config, HttpStatus.OK);
            }
        }
        throw new ApiRequestException("Unable to update loyalty configuration.");
    }

    @PutMapping(value = "/global/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateGlobalConfig(@RequestBody GlobalConfig config, @PathVariable Integer id){
        Optional<GlobalConfig> c = configService.findGlobalConfigById(id);
        if(c.isPresent()){
            if(c.get().getId().equals(config.getId())){
                configService.update(config);
                return new ResponseEntity<>(config, HttpStatus.OK);
            }
        }
        throw new ApiRequestException("Unable to update global configuration.");
    }

    @GetMapping(value = "/clientLevelMarks")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity getClientLevelMarks(){
        List<Integer> marks = configService.getClientLevelMarks();
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @GetMapping(value = "/sellerLevelMarks")
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'INSTRUCTOR', 'HOUSE_OWNER')")
    public ResponseEntity getSellerLevelMarks(){
        List<Integer> marks = configService.getSellerLevelMarks();
        return new ResponseEntity<>(marks, HttpStatus.OK);
    }

    @GetMapping(value = "/discountAndFees/{entityId}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity getClientDiscount(Principal principal, @PathVariable Long entityId){
        Double clientDiscount = configService.getClientDiscountPercentageForPoints(userService.findByEmail(principal.getName()).getPoints());
        Double sellerExtraRevenue = configService.getSellerExtraRevenueForEntity(entityId);
        if(sellerExtraRevenue == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        Double systemFee = configService.getGlobalConfig().getSystemFee();
        return new ResponseEntity( new DiscountAndFeesDto(clientDiscount, sellerExtraRevenue, systemFee), HttpStatus.OK);
    }
}
