package com.example.application.views;

import com.example.application.models.Kullanici;
import com.example.application.models.Siparis;
import com.example.application.models.Stok;
import com.example.application.services.KullaniciService;
import com.example.application.services.SiparisService;
import com.example.application.services.StokService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "Raporlar", layout = MainView.class)
@PageTitle("Raporlar")
public class RaporlarView extends VerticalLayout {
    private final SiparisService siparisService;
    private final KullaniciService kullaniciService;
    private final StokService stokService;

    Grid<Siparis> grid = new Grid<>(Siparis.class);
    Grid<Siparis> grid1 = new Grid<>(Siparis.class);

    public RaporlarView(SiparisService siparisService, KullaniciService kullaniciService, StokService stokService) {
        this.siparisService = siparisService;
        this.kullaniciService = kullaniciService;
        this.stokService = stokService;

        ComboBox<Kullanici> kullaniciAra = new ComboBox<>();
        kullaniciAra.setPlaceholder("kullanıcı seçin");
        kullaniciAra.setItems(kullaniciService.getList());
        kullaniciAra.setItemLabelGenerator(Kullanici::getAdSoyad);

        Button btnFilter = new Button("kullanıcı raporla", VaadinIcon.SEARCH.create());
        btnFilter.addClickListener(buttonClickEvent -> {

            refreshKullanici(kullaniciAra.getValue());
        });
        HorizontalLayout filterGroup = new HorizontalLayout();
        filterGroup.add( kullaniciAra, btnFilter);

        ComboBox<Stok> stokAra = new ComboBox<>();
        stokAra.setPlaceholder("stok kodunu seçin");
        stokAra.setItems(stokService.getList());
        //stokAra.setItemLabelGenerator(kategori -> kategori.getKategori().kategori);
        stokAra.setItemLabelGenerator(Stok::getStokKodu);

        Button btnStokAra = new Button("stok raporla", VaadinIcon.SEARCH.create());
        btnStokAra.addClickListener(buttonClickEvent -> {

            refreshStok(stokAra.getValue());
        });
        HorizontalLayout filterStok = new HorizontalLayout();
        filterStok.add(stokAra, btnStokAra);

        refreshData();
        grid1.removeColumnByKey("id");
        //grid1.setColumns("stok", "kategori", "adet", "tarih", "kullanici");
        grid1.setColumns("tarih","adet" );
        grid1.addColumn(kategori -> kategori.getKategori().kategori).setHeader("Kategori");
        grid1.addColumn(stokKodu -> stokKodu.getStok().stokKodu).setHeader("Stok Kodu");
        grid1.addColumn(kullanici -> kullanici.getKullanici().adSoyad).setHeader("Kullanici");
        refreshData1();

        refreshData();
        grid.removeColumnByKey("id");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        //grid.setColumns("stok", "kategori", "tarih", "adet", "kullanici");
        grid.setColumns("tarih","adet" );
        grid.addColumn(kategori -> kategori.getKategori().kategori).setHeader("Kategori");
        grid.addColumn(stokKodu -> stokKodu.getStok().stokKodu).setHeader("Stok Kodu");
        grid.addColumn(kullanici -> kullanici.getKullanici().adSoyad).setHeader("Kullanici");

        add(new H1("RAPORLAR"), new H2("Firma Sipariş Raporu"), filterGroup, grid, new H3("Stok Sipariş Raporu"), filterStok, grid1);
    }

    private void refreshData() {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getList());
        grid.setItems(siparisList);
    }

    private void refreshData1() {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getList());
        grid1.setItems(siparisList);
    }

    private void refreshData(String filter) {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getList(filter));
        grid.setItems(siparisList);
    }

    private void refreshKullanici(Kullanici kullanici) {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getList(kullanici));
        grid.setItems(siparisList);
    }

    private void refreshStok(Stok stok) {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getList(stok));
        grid1.setItems(siparisList);
    }

}