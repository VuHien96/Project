package com.vuhien.application.repository;

import com.vuhien.application.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Images,Integer> {

}
