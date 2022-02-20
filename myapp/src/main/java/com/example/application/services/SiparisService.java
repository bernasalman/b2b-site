package com.example.application.services;

import com.example.application.models.Kategori;
import com.example.application.models.Kullanici;
import com.example.application.models.Siparis;
import com.example.application.models.Stok;

import java.util.Set;

public interface SiparisService {
    Set<Siparis> getList();
    Set<Siparis> getList(String filter);
    Set<Siparis> getList(String filter,Long systemUserId);
    Set<Siparis> getList(Kullanici kullanici);
    Set<Siparis> getList(Kategori kategori);
    Set<Siparis> getList(Stok stok);
    Siparis save(Siparis s);
    void delete(Siparis s);
    //Set<Siparis> getKategori();





}
