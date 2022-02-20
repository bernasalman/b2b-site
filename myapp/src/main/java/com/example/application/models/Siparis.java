package com.example.application.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Siparis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int adet;
    private LocalDate tarih;

    @ManyToOne
    public Kullanici kullanici;

    @ManyToOne
    public Stok stok;

    @ManyToOne
    public Kategori kategori;


}
