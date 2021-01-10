package com.nana.imagerepository.Repository;

import com.nana.imagerepository.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Imagerepository extends JpaRepository<Image, Long> {


}
