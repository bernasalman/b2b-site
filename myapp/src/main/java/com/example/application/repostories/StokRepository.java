package com.example.application.repostories;

import com.example.application.models.Kategori;
import com.example.application.models.Stok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StokRepository extends CrudRepository<Stok,Long>, JpaRepository<Stok,Long> {
        List<Stok> findByStokKoduContaining(String stokKodu);
        List<Stok> findByKategoriIsLike(Kategori kategori);
        List<Stok> findByAciklamaContaining(String filter);
}

