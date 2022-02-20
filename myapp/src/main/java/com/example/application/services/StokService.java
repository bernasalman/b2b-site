package com.example.application.services;
import com.example.application.models.Kategori;
import com.example.application.models.Stok;

import java.util.Set;

public interface StokService {
    Set<Stok> getList();
    Set<Stok> getList(String stokKodu);
    Set<Stok> getList(Kategori kategori);
    Set<Stok> getList1(String  filter);
    Stok save (Stok s);
    void delete(Stok s);

}
