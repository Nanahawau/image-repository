package com.nana.imagerepository.Repository;

import com.nana.imagerepository.Entity.Image;
import com.nana.imagerepository.Entity.User;
import com.nana.imagerepository.Model.PermissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {


    @Query(value = "select distinct(file_path) from images where is_private=false", nativeQuery = true)
    List<String> findPublicImages();

    @Query(value = "select file_path from images where is_private = true and user_id=:userId", nativeQuery = true)
    List<String> findAllPrivateImages(Long userId);

    @Query(value = "select file_path from images where is_private = false and user_id=:userId", nativeQuery = true)
    List<String> findAllPublicImagesForUser(Long userId);

    List<Image> findImageByFilePathAndUser(String filename, User user);

    Image findImageById(Long id);

    @Query(value = "select distinct(id) from images where file_path=:filePath and user_id=:userId", nativeQuery = true)
    Long findImageIdAndPermission(String filePath, Long userId);
}
