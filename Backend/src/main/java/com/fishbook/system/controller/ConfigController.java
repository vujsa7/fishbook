package com.fishbook.system.controller;

import com.fishbook.system.model.GlobalConfig;
import com.fishbook.system.model.LoyaltyConfig;
import com.fishbook.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
    private final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService){
        this.configService = configService;
    }

    @PutMapping(value = "/loyalty/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateLoyaltyConfig(@RequestBody LoyaltyConfig config, @PathVariable Integer id){
        Optional<LoyaltyConfig> c = configService.findLoyaltyConfigById(id);
        if(c.isPresent()){
            if(c.get().getLoyaltyType().equals(config.getLoyaltyType()) && c.get().getId().equals(config.getId())){
                configService.update(config);
                return new ResponseEntity<>("Success", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/global/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateGlobalConfig(@RequestBody GlobalConfig config, @PathVariable Integer id){
        Optional<GlobalConfig> c = configService.findGlobalConfigById(id);
        if(c.isPresent()){
            if(c.get().getId().equals(config.getId())){
                configService.update(config);
                return new ResponseEntity<>("Success", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }
}
