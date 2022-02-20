package com.example.application.services;

import com.example.application.models.Kategori;
import com.example.application.repostories.KategoriRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class KategoriServiceImpl implements KategoriService {
        private final KategoriRepository kategoriRepository;

    public KategoriServiceImpl(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    @Override
    public Set<Kategori> getList() {
        Set<Kategori> kategoriSet =new HashSet<>();
        kategoriRepository.findAll().iterator().forEachRemaining(kategoriSet::add);
        return kategoriSet;
    }

    @Override
    public Set<Kategori> getList(String filter) {
        Set<Kategori> kategoriSet =new HashSet<>();
        kategoriRepository.findByKategoriContaining(filter).iterator().forEachRemaining(kategoriSet::add);
        return kategoriSet;
    }

    @Override
    public Kategori save(Kategori k) {
        return kategoriRepository.save(k);
    }

    @Override
    public void delete(Kategori k) { kategoriRepository.delete(k);

    }
}
