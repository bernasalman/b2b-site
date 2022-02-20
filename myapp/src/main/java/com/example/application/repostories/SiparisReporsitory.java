package com.example.application.repostories;

import com.example.application.models.Kategori;
import com.example.application.models.Kullanici;
import com.example.application.models.Siparis;
import com.example.application.models.Stok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SiparisReporsitory extends CrudRepository<Siparis,Long>, JpaRepository<Siparis,Long> {
    List<Siparis> findByStokContaining(String filter);
    List<Siparis> findByStok_StokKoduContainingAndKullanici_Id(String filter, Long systemUserId);
    List<Siparis> findByKullaniciIsLike(Kullanici kullanici);
    List<Siparis> findByKategoriIsLike(Kategori kategori);
    List<Siparis> findByStokIsLike(Stok stok);
    //List<Siparis> countByKategori_KategoriAndAdet();

 
}
