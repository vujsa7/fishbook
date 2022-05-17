package com.fishbook.storage.service.impl;

import com.fishbook.storage.dao.EntityImageRepository;
import com.fishbook.storage.model.EntityImage;
import com.fishbook.storage.model.StorageException;
import com.fishbook.storage.model.StorageFileNotFoundException;
import com.fishbook.storage.model.StorageProperties;
import com.fishbook.storage.service.StorageService;
import com.fishbook.user.dao.UserRepository;
import com.fishbook.user.model.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StorageServiceImpl implements StorageService {

    private final EntityImageRepository entityImageRepository;
    private final UserRepository userRepository;
    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl(EntityImageRepository entityImageRepository, StorageProperties properties, UserRepository userRepository) {
        this.entityImageRepository = entityImageRepository;
        this.userRepository = userRepository;
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void uploadEntityImage(MultipartFile file, EntityImage image) {
        String imageName = generateUniqueImageName(file);
        uploadImageToFileSystem(file, imageName);
        image.setName(imageName);
        entityImageRepository.save(image);
    }

    @Override
    public void uploadProfileImage(MultipartFile file, String email) {
        String imageName = generateUniqueImageName(file);
        User user = userRepository.findByEmail(email);
        user.setProfileImage(imageName);
        userRepository.save(user);
        uploadImageToFileSystem(file, imageName);
    }

    private String generateUniqueImageName(MultipartFile file){
        return RandomStringUtils.random(15, true, true)  + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);
    }

    @Override
    public void uploadImageToFileSystem(MultipartFile file, String imageName) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(imageName))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public String getPriorityImageUrl(Set<EntityImage> images) {
        Optional<EntityImage> image = images.stream().filter(i -> i.getPriority().equals(1)).findFirst();
        return image.map(entityImage -> "http://localhost:8080/api/files/" + entityImage.getName())
                .orElse("");
    }

    @Override
    public List<String> getImageUrls(Set<EntityImage> images){
        return images.stream().map(image -> "http://localhost:8080/api/files/" + image.getName()).collect(Collectors.toList());
    }

    @Override
    public String getImageUrl(String image) {
        return "http://localhost:8080/api/files/" + image;
    }
}
