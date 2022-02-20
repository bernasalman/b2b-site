package com.example.application.repostories;

import com.example.application.models.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KullaniciRepository extends CrudRepository<Kullanici,Long>, JpaRepository<Kullanici,Long>{
    List<Kullanici> findByAdSoyadContainingAndSystemUserId(String adSoyad, Long systemUserId);
    List<Kullanici> findByAdSoyadContaining(String adSoyad);
    List<Kullanici> findByEmailAndSifre(String adSoyad, Long systemUserId);
    List<Kullanici> findByIdEquals(Long id);
}