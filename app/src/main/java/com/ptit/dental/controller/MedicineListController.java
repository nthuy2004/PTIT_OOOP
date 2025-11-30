package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.view.MedicineListView;

public class MedicineListController extends BaseController<MedicineListView> {

    public MedicineListController(MedicineListView view) {
        super(view);
        initListeners();
    }

    private void initListeners() {
        view.getSearchButton().addActionListener(e -> searchMedicine());
        view.getAddButton().addActionListener(e -> addMedicine());
        view.getDeleteButton().addActionListener(e -> deleteMedicine());
        view.getEditButton().addActionListener(e -> editMedicine());
    }

    private void searchMedicine() {
        String query = view.getSearchField().getText();
        // Implement search logic
    }

    private void addMedicine() {
        // Implement add medicine logic
    }

    private void deleteMedicine() {
        // Implement delete medicine logic
    }

    private void editMedicine() {
        // Implement edit medicine logic
    }

    // Example usage
    private void loadSampleData() {
        view.addMedicineToTable("1", "Peniciline", "27-05-2024", "27-05-2025", 12000.00, 77);
        view.addMedicineToTable("4", "Amociline", "27-05-2024", "27-05-2025", 12000.00, 29);
        view.addMedicineToTable("5", "Vitamin C", "28-05-2024", "28-05-2027", 15000.00, 1);
    }
}