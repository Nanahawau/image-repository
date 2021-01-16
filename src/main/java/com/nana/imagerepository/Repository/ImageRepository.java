package com.nana.imagerepository.Repository;

import com.nana.imagerepository.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "select id, permission, data, size, title from image", nativeQuery = true)
    List<Image> findAllImages();

    @Query(value = "select id, data from image where permissions", nativeQuery = true)
    List<Image> findPublicImages();

    @Query(value = "select id, permissions, data from image where permissions ", nativeQuery = true)
    List<Image> findAllPrivateImages();

}
