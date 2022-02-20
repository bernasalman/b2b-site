package com.example.application.views;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;

import java.util.Optional;


@Route
@PWA(name = "My App", shortName = "My App", enableInstallPrompt = false)
@Theme(themeFolder = "myapp")
public class MainView extends AppLayout {
    Long loggedInSystemUserId;

    private final Tabs menu;
    Button btnLogOut = new Button("Çıkış", VaadinIcon.CLOSE_CIRCLE.create());
    Icon logo = new Icon(VaadinIcon.HANDSHAKE);
    H1 isim = new H1("B2B-ŞİPARİŞ");

    public MainView() {
        if (VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId")==null){
            UI.getCurrent().getPage().setLocation("/login");
        }else {

            System.out.println("Logedin User ID");
            System.out.println(VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId").toString());
            loggedInSystemUserId=Long.valueOf(VaadinSession.getCurrent().getSession().getAttribute("LoggedInSystemUserId").toString());
        }
        HorizontalLayout header = createHeader(logo,isim);
        menu = createMenuTabs();
        addToNavbar(createTopBar(header, btnLogOut, menu));

        btnLogOut.addClickListener(buttonClickEvent -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().getPage().setLocation("/login");
        });

    }

    private VerticalLayout createTopBar(HorizontalLayout header, Button btnLogOut, Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.getThemeList();
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setHorizontalComponentAlignment(FlexComponent.Alignment.END,btnLogOut);
        layout.add(header,btnLogOut, menu);
        return layout;
    }

    private HorizontalLayout createHeader(Icon logo, H1 isim) {
        HorizontalLayout header = new HorizontalLayout();
        header.setClassName("topmenu-header");
        header.setPadding(false);
        header.setSpacing(false);
        header.setWidthFull();
        header.setHeight("10px");
        header.setAlignItems(FlexComponent.Alignment.AUTO);


        logo.setSize("70px");
        logo.setColor("red");
        header.add(logo,isim);

        return header;
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.getStyle().set("max-width", "90%");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        return new Tab[]{
                createTab("Kullanıcı Ekleme", KullanicilarView.class),
                createTab("Kategori Ekleme", KategorilerView.class),
                createTab("Stok Kartı Ekleme", StokKartıView.class),
                createTab("Raporlar", RaporlarView.class),
                createTab("Toplamlar", ToplamlarView.class),


        };
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }
}