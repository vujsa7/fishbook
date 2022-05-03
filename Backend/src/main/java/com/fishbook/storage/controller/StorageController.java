package com.fishbook.storage.controller;

import com.fishbook.entity.model.Entity;
import com.fishbook.entity.dao.EntityRepository;
import com.fishbook.storage.dto.EntityImageDto;
import com.fishbook.storage.model.EntityImage;
import com.fishbook.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class StorageController {

    private final StorageService storageService;
    private final EntityRepository entityRepository;

    @Autowired
    public StorageController(StorageService storageService, EntityRepository entityRepository) {
        this.storageService = storageService;
        this.entityRepository = entityRepository;
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadImage(@RequestPart("file") MultipartFile file, @RequestPart("image") EntityImageDto imageDto) {
        try {
            Entity entity = entityRepository.getById(imageDto.getEntityId());
            EntityImage image = new EntityImage("", imageDto.getPriority(), entity);
            storageService.uploadImage(file, image);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
