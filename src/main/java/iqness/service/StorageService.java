package iqness.service;

import java.io.IOException;
import java.net.URL;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void uploadToStorage(MultipartFile file, String fileName) throws IOException;

    URL createLink(String fileName, Long expiryTime);

}
