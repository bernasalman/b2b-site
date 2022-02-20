package com.example.application.repostories;

import com.example.application.models.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KategoriRepository extends CrudRepository<Kategori,Long>,JpaRepository<Kategori,Long>{
    List<Kategori> findByKategoriContaining(String kategori);
}