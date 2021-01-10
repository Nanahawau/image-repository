package com.nana.imagerepository.Service;

import com.nana.imagerepository.Entity.Image;
import com.nana.imagerepository.Repository.Imagerepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    Imagerepository imagerepository;
        public Image save (MultipartFile file) throws IOException {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Image image = new Image(fileName, String.valueOf(file.getSize()), "", file.getBytes());
            return imagerepository.save(image);
        }

}
