package com.example.application.services;

import com.example.application.models.Kategori;
import com.example.application.models.Stok;
import com.example.application.repostories.StokRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StokServiceImpl implements StokService {

    private final StokRepository stokRepository;
    public StokServiceImpl(StokRepository stokRepository) {
        this.stokRepository = stokRepository;
    }

    @Override
    public Set<Stok> getList() {
        Set<Stok> stokSet =new HashSet<>();
        stokRepository.findAll().iterator().forEachRemaining(stokSet::add);
        return stokSet;
    }

    @Override
    public Set<Stok> getList(String stokKodu) {
        Set<Stok> stokSet = new HashSet<>();
        stokRepository.findByStokKoduContaining(stokKodu).iterator().forEachRemaining(stokSet::add);
        return stokSet;
    }

    @Override
    public Set<Stok> getList(Kategori kategori) {
        Set<Stok> stokSet = new HashSet<>();
        stokRepository.findByKategoriIsLike(kategori).iterator().forEachRemaining(stokSet::add);
        return stokSet;
    }

    @Override
    public Set<Stok> getList1(String filter) {
        Set<Stok> stokSet = new HashSet<>();
        stokRepository.findByAciklamaContaining(filter).iterator().forEachRemaining(stokSet::add);
        return stokSet;
    }

    @Override
    public Stok save(Stok s) {
        return stokRepository.save(s);
    }

    @Override
    public void delete(Stok s) {stokRepository.delete(s);    }

}
