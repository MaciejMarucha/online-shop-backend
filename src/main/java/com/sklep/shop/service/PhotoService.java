package com.sklep.shop.service;

import com.sklep.shop.model.Photo;
import com.sklep.shop.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public ResponseMetadata save(MultipartFile file) throws IOException {

        Photo photo = new Photo();
//        photo.setFile(file.getBytes());
        photo.setFile(Base64.getEncoder().encode(file.getBytes()));
        photo.setName(file.getOriginalFilename());
        photoRepository.save(photo);

        ResponseMetadata responseMetadata = new ResponseMetadata();
        responseMetadata.setMessage("success");
        responseMetadata.setStatus(200);

        return responseMetadata;
    }

    public ResponseMetadata save(Photo photo) throws IOException {

        photo.setFile(Base64.getEncoder().encode(photo.getFile()));
        photo.setName(photo.getName());
        photoRepository.save(photo);

        ResponseMetadata responseMetadata = new ResponseMetadata();
        responseMetadata.setMessage("success");
        responseMetadata.setStatus(200);

        return responseMetadata;
    }

    public byte[] getDocumentFile(Long id) {
        return photoRepository.findById(id).get().getFile();
    }

    public Optional<Photo> findByName(String name) {
        return photoRepository.findByName(name);
    }

    public List<Photo> findAll() {
        return photoRepository.findAll();
    }
}
