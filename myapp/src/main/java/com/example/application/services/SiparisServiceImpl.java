package com.example.application.services;

import com.example.application.models.Kategori;
import com.example.application.models.Kullanici;
import com.example.application.models.Siparis;
import com.example.application.models.Stok;
import com.example.application.repostories.SiparisReporsitory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SiparisServiceImpl implements SiparisService {
    private final SiparisReporsitory siparisReporsitory;
    public SiparisServiceImpl(SiparisReporsitory siparisReporsitory) {
        this.siparisReporsitory = siparisReporsitory;

    }

    @Override
    public Set<Siparis> getList() {
        Set<Siparis> siparisSet = new HashSet<>();
        siparisReporsitory.findAll().iterator().forEachRemaining(siparisSet::add);
        return siparisSet;
    }

    @Override
    public Set<Siparis> getList(String filter) {
        Set<Siparis> siparisSet = new HashSet<>();
        siparisReporsitory.findByStokContaining(filter).iterator().forEachRemaining(siparisSet::add);
        return siparisSet;
    }


    @Override
    public Set<Siparis> getList(String filter, Long systemUserId) {
       Set<Siparis> siparisSet = new HashSet<>();
       siparisReporsitory.findByStok_StokKoduContainingAndKullanici_Id(filter,systemUserId).iterator().forEachRemaining(siparisSet::add);
       return siparisSet;
    }

    @Override
    public Set<Siparis> getList(Kullanici kullanici) {
        Set<Siparis> siparisSet = new HashSet<>();
        siparisReporsitory.findByKullaniciIsLike(kullanici).iterator().forEachRemaining(siparisSet::add);
        return siparisSet;
    }

    @Override
    public Set<Siparis> getList(Stok stok) {
        Set<Siparis> siparisSet = new HashSet<>();
        siparisReporsitory.findByStokIsLike(stok).iterator().forEachRemaining(siparisSet::add);
        return siparisSet;
    }

    @Override
    public Set<Siparis> getList(Kategori kategori) {
        Set<Siparis> siparisSet = new HashSet<>();
        siparisReporsitory.findByKategoriIsLike(kategori).iterator().forEachRemaining(siparisSet::add);
        return siparisSet;
    }

    @Override
    public Siparis save(Siparis s) {
        return siparisReporsitory.save(s);
    }

    @Override
    public void delete(Siparis s) {siparisReporsitory.delete(s);

    }

   /*@Override
    public Set<Siparis> getKategori() {
        Set<Siparis> siparisSet = new HashSet<>();
        siparisReporsitory.countByKategori_KategoriAndAdet().iterator().forEachRemaining(siparisSet::add);
        return siparisSet;
    }

    */




}
