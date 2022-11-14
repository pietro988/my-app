package com.example.application.views.masterdetail;

import com.example.application.data.entity.Misurazione;
import com.example.application.data.service.MisurazioneService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Master-Detail")
@Route(value = "master-detail/:misurazioneID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class MasterDetailView extends Div implements BeforeEnterObserver {

    private final String MISURAZIONE_ID = "misurazioneID";
    private final String MISURAZIONE_EDIT_ROUTE_TEMPLATE = "master-detail/%s/edit";

    private final Grid<Misurazione> grid = new Grid<>(Misurazione.class, false);

    private TextField idSensore;
    private TextField potenzaConsumata;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Misurazione> binder;

    private Misurazione misurazione;

    private final MisurazioneService misurazioneService;

    @Autowired
    public MasterDetailView(MisurazioneService misurazioneService) {
        this.misurazioneService = misurazioneService;
        addClassNames("master-detail-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("idSensore").setAutoWidth(true);
        grid.addColumn("potenzaConsumata").setAutoWidth(true);
        grid.setItems(query -> misurazioneService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(MISURAZIONE_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(MasterDetailView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Misurazione.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(idSensore).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("idSensore");
        binder.forField(potenzaConsumata).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("potenzaConsumata");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.misurazione == null) {
                    this.misurazione = new Misurazione();
                }
                binder.writeBean(this.misurazione);
                misurazioneService.update(this.misurazione);
                clearForm();
                refreshGrid();
                Notification.show("Misurazione details stored.");
                UI.getCurrent().navigate(MasterDetailView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the misurazione details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UUID> misurazioneId = event.getRouteParameters().get(MISURAZIONE_ID).map(UUID::fromString);
        if (misurazioneId.isPresent()) {
            Optional<Misurazione> misurazioneFromBackend = misurazioneService.get(misurazioneId.get());
            if (misurazioneFromBackend.isPresent()) {
                populateForm(misurazioneFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested misurazione was not found, ID = %s", misurazioneId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(MasterDetailView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        idSensore = new TextField("Id Sensore");
        potenzaConsumata = new TextField("Potenza Consumata");
        formLayout.add(idSensore, potenzaConsumata);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Misurazione value) {
        this.misurazione = value;
        binder.readBean(this.misurazione);

    }
}
