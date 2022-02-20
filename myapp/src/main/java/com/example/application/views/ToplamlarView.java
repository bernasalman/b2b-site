package com.example.application.views;

import com.example.application.models.Kategori;
import com.example.application.models.Siparis;
import com.example.application.services.KategoriService;
import com.example.application.services.KullaniciService;
import com.example.application.services.SiparisService;
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

@Route(value = "Toplamlar", layout = MainView.class)
@PageTitle("Toplamlar")
public class ToplamlarView extends VerticalLayout {
    private final SiparisService siparisService;
    private final KullaniciService kullaniciService;
    private final KategoriService kategoriService;

    Grid<Siparis> grid = new Grid<>(Siparis.class);
    Grid<Siparis> grid1 = new Grid<>(Siparis.class);

    public ToplamlarView(SiparisService siparisService, KullaniciService kullaniciService, KategoriService kategoriService) {
        this.siparisService = siparisService;
        this.kullaniciService = kullaniciService;
        this.kategoriService = kategoriService;

        ComboBox<Kategori> siparisKategoriAra = new ComboBox<>();
        siparisKategoriAra.setPlaceholder("kategori seçin");
        siparisKategoriAra.setItems(this.kategoriService.getList());
        siparisKategoriAra.setItemLabelGenerator(Kategori::getKategori);
        Button btnFilterSiparis = new Button("kategori raporla", VaadinIcon.SEARCH.create());
        btnFilterSiparis.addClickListener(buttonClickEvent -> {
            refreshSiparisKategori(siparisKategoriAra.getValue());

        });
        HorizontalLayout filterGroupSiparis = new HorizontalLayout();
        filterGroupSiparis.add(siparisKategoriAra, btnFilterSiparis);

        grid1.removeColumnByKey("id");
        grid1.addColumn(Siparis::getAdet).setHeader("Kodu");
        grid1.addColumn(Siparis::getAdet).setHeader("Adet");
        //grid1.setColumns("stok","kategori","adet","tarih","kullanici");
        grid1.setColumns("tarih","adet" );
        grid1.addColumn(kategori -> kategori.getKategori().kategori).setHeader("Kategori");
        grid1.addColumn(stokKodu -> stokKodu.getStok().stokKodu).setHeader("Stok Kodu");
        grid1.addColumn(kullanici -> kullanici.getKullanici().adSoyad).setHeader("Kullanici");
        refreshData1();

        /*Collection adet = ((ListDataProvider)grid.getDataProvider()).getItems();
        for(Siparis sipari:adet){
            int total=0:
            total += adet.getValue();}
        footer.getCell(footerCell).setText(format(total));*/

        refreshData();
        grid.removeColumnByKey("id");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumns("stok","kategori","adet","tarih","kullanici");
        grid.setColumns("tarih","adet" );
        grid.addColumn(kategori -> kategori.getKategori().kategori).setHeader("Kategori");
        grid.addColumn(stokKodu -> stokKodu.getStok().stokKodu).setHeader("Stok Kodu");
        grid.addColumn(kullanici -> kullanici.getKullanici().adSoyad).setHeader("Kullanici");
        //grid.setHeightByRows(true);
        //grid.getHeight();

        add(new H1("TOPLAMLAR"),new H2("Stok Şipariş Toplamları"),grid,new H3("Kategori Şipariş Toplamları"),filterGroupSiparis,grid1);
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

   /* private void refreshKategori() {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getKategori());
        grid.setItems(siparisList);

    }
    */

    private void refreshSiparisKategori(Kategori kategori) {
        List<Siparis> siparisList = new ArrayList<>();
        siparisList.addAll(siparisService.getList(kategori));
        grid1.setItems(siparisList);
    }


}
