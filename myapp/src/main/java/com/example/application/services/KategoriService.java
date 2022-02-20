package com.example.application.services;

import com.example.application.models.Kategori;

import java.util.Set;

public interface KategoriService {
    Set<Kategori> getList();

    Set<Kategori> getList(String filter);

    Kategori save(Kategori k);

    void delete(Kategori k);
}
