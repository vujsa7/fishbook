package com.fishbook.storage.service;

import com.fishbook.storage.dto.EntityImageDto;
import com.fishbook.storage.model.EntityImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public interface StorageService {
    void uploadImage(MultipartFile file, EntityImage image);
    Path load(String filename);
    Resource loadAsResource(String filename);
    String getPriorityImageUrl(Set<EntityImage> images);
    List<String> getImageUrls(Set<EntityImage> images);
}
