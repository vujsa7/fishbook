package com.fishbook.storage.service.impl;

import com.fishbook.boat.dao.BoatRepository;
import com.fishbook.boat.model.Boat;
import com.fishbook.storage.dao.EntityImageRepository;
import com.fishbook.storage.dto.EntityImageDto;
import com.fishbook.storage.model.EntityImage;
import com.fishbook.storage.model.StorageException;
import com.fishbook.storage.model.StorageFileNotFoundException;
import com.fishbook.storage.model.StorageProperties;
import com.fishbook.storage.service.StorageService;
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

@Service
public class StorageServiceImpl implements StorageService {

    private final EntityImageRepository entityImageRepository;
    private final Path rootLocation;
    private final BoatRepository boatRepository;

    @Autowired
    public StorageServiceImpl(EntityImageRepository entityImageRepository, StorageProperties properties, BoatRepository boatRepository) {
        this.entityImageRepository = entityImageRepository;
        this.rootLocation = Paths.get(properties.getLocation());
        this.boatRepository = boatRepository;
    }

    @Override
    public EntityImage uploadImage(MultipartFile file, EntityImageDto imageDto) {

        String imageName = RandomStringUtils.random(15, true, true)
                + file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4);

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

        EntityImage image = entityImageRepository.save(new EntityImage(imageName, imageDto.getPriority()));
        Boat boat = boatRepository.getById(imageDto.getEntityId());
        boat.addImage(image);
        boatRepository.save(boat);

        return image;

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
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }
}
