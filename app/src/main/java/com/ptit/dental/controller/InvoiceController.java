//package com.ptit.dental.controller;
//
//import com.ptit.dental.base.BaseController;
//import com.ptit.dental.model.dao.InvoiceDAO;
//import com.ptit.dental.model.dao.PatientDAO;
//import com.ptit.dental.model.entity.Invoice;
//import com.ptit.dental.model.entity.Patient;
//import com.ptit.dental.utils.Database;
//import com.ptit.dental.view.InvoiceFormDialog;
//import com.ptit.dental.view.InvoiceView;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
//public class InvoiceController extends BaseController<InvoiceView> {
//    private InvoiceDAO invoiceDAO;
//    private PatientDAO patientDAO;
//    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
//    public InvoiceController(InvoiceView view) {
//        super(view);
//        try {
//            Connection conn = Database.getInstance().getConnection();
//            invoiceDAO = new InvoiceDAO(conn);
//            patientDAO = new PatientDAO(conn);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(view, "Lỗi kết nối database: " + e.getMessage(),
//                    "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//        initListeners();
//        loadInvoices();
//    }
//
//    private void initListeners() {
//        view.getSearchButton().addActionListener(e -> searchInvoices());
//        view.getAddButton().addActionListener(e -> addInvoice());
//        view.getEditButton().addActionListener(e -> editInvoice());
//        view.getDeleteButton().addActionListener(e -> deleteInvoice());
//    }
//
//    private void loadInvoices() {
//        try {
//            List<Invoice> invoices = invoiceDAO.getAll();
//            DefaultTableModel model = (DefaultTableModel) view.getInvoiceTable().getModel();
//            model.setRowCount(0);
//
//            for (Invoice invoice : invoices) {
//                String patientName = invoice.getMedicalRecord() != null
//                        && invoice.getMedicalRecord().getPatient() != null
//                                ? invoice.getMedicalRecord().getPatient().getFullname()
//                                : "N/A";
//                String serviceSummary = getServiceSummary(invoice);
//                model.addRow(new Object[] {
//                        invoice.getId(),
//                        dateFormat.format(invoice.getCreatedDate()),
//                        serviceSummary,
//                        String.format("%.2f", invoice.getTotal())
//                });
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + e.getMessage(),
//                    "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private String getServiceSummary(Invoice invoice) {
//        try {
//            Invoice fullInvoice = invoiceDAO.getById(invoice.getId());
//            if (fullInvoice.getItems() != null && !fullInvoice.getItems().isEmpty()) {
//                if (fullInvoice.getItems().size() == 1) {
//                    return fullInvoice.getItems().get(0).getServiceName();
//                } else {
//                    return fullInvoice.getItems().get(0).getServiceName() + " và " +
//                            (fullInvoice.getItems().size() - 1) + " dịch vụ khác";
//                }
//            }
//        } catch (SQLException e) {
//            // Ignore
//        }
//        return "Không có dịch vụ";
//    }
//
//    private void searchInvoices() {
//        String keyword = view.getSearchField().getText().trim();
//        if (keyword.isEmpty()) {
//            loadInvoices();
//            return;
//        }
//
//        try {
//            List<Invoice> invoices = invoiceDAO.searchByPatientName(keyword);
//            DefaultTableModel model = (DefaultTableModel) view.getInvoiceTable().getModel();
//            model.setRowCount(0);
//
//            for (Invoice invoice : invoices) {
//                String patientName = invoice.getMedicalRecord() != null
//                        && invoice.getMedicalRecord().getPatient() != null
//                                ? invoice.getMedicalRecord().getPatient().getFullname()
//                                : "N/A";
//                String serviceSummary = getServiceSummary(invoice);
//                model.addRow(new Object[] {
//                        invoice.getId(),
//                        dateFormat.format(invoice.getCreatedDate()),
//                        serviceSummary,
//                        String.format("%.2f", invoice.getTotal())
//                });
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm: " + e.getMessage(),
//                    "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void addInvoice() {
//        InvoiceFormDialog dialog = new InvoiceFormDialog(view, null);
//        dialog.setVisible(true);
//
//        if (dialog.isSaved()) {
//            Invoice invoice = dialog.getInvoice();
//            try {
//                invoiceDAO.insert(invoice);
//                JOptionPane.showMessageDialog(view, "Tạo hóa đơn thành công!",
//                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                loadInvoices();
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(view, "Lỗi tạo hóa đơn: " + e.getMessage(),
//                        "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
//
//    private void editInvoice() {
//        int selectedRow = view.getInvoiceTable().getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(view, "Vui lòng chọn hóa đơn để chỉnh sửa!",
//                    "Thông báo", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        try {
//            int invoiceId = (Integer) view.getInvoiceTable().getValueAt(selectedRow, 0);
//            Invoice invoice = invoiceDAO.getById(invoiceId);
//
//            if (invoice != null) {
//                InvoiceFormDialog dialog = new InvoiceFormDialog(view, invoice);
//                dialog.setVisible(true);
//
//                if (dialog.isSaved()) {
//                    Invoice updatedInvoice = dialog.getInvoice();
//                    if (invoiceDAO.update(updatedInvoice)) {
//                        JOptionPane.showMessageDialog(view, "Cập nhật hóa đơn thành công!",
//                                "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                        loadInvoices();
//                    } else {
//                        JOptionPane.showMessageDialog(view, "Cập nhật thất bại!",
//                                "Lỗi", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(view, "Lỗi: " + e.getMessage(),
//                    "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void deleteInvoice() {
//        int selectedRow = view.getInvoiceTable().getSelectedRow();
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(view, "Vui lòng chọn hóa đơn để xóa!",
//                    "Thông báo", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//
//        try {
//            int invoiceId = (Integer) view.getInvoiceTable().getValueAt(selectedRow, 0);
//
//            int confirm = JOptionPane.showConfirmDialog(view,
//                    "Bạn có chắc chắn muốn xóa hóa đơn này?",
//                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
//
//            if (confirm == JOptionPane.YES_OPTION) {
//                if (invoiceDAO.delete(invoiceId)) {
//                    JOptionPane.showMessageDialog(view, "Đã xóa hóa đơn thành công!",
//                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
//                    loadInvoices();
//                } else {
//                    JOptionPane.showMessageDialog(view, "Xóa thất bại!",
//                            "Lỗi", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(view, "Lỗi xóa hóa đơn: " + e.getMessage(),
//                    "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}


package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.model.dao.InvoiceDAO;
import com.ptit.dental.model.dao.PatientDAO;
import com.ptit.dental.model.entity.Invoice;
import com.ptit.dental.model.entity.Patient;
import com.ptit.dental.utils.Database;
import com.ptit.dental.view.InvoiceFormDialog;
import com.ptit.dental.view.InvoiceView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class InvoiceController extends BaseController<InvoiceView> {
    private InvoiceDAO invoiceDAO;
    private PatientDAO patientDAO;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public InvoiceController(InvoiceView view) {
        super(view);
        try {
            Connection conn = Database.getInstance().getConnection();
            invoiceDAO = new InvoiceDAO(conn);
            patientDAO = new PatientDAO(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi kết nối database: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        initListeners();
        loadInvoices();
    }

    private void initListeners() {
        view.getSearchButton().addActionListener(e -> searchInvoices());
        view.getAddButton().addActionListener(e -> addInvoice());
        view.getEditButton().addActionListener(e -> editInvoice());
        view.getDeleteButton().addActionListener(e -> deleteInvoice());
    }

    private void loadInvoices() {
        try {
            List<Invoice> invoices = invoiceDAO.getAll();
            DefaultTableModel model = (DefaultTableModel) view.getInvoiceTable().getModel();
            model.setRowCount(0);

            for (Invoice invoice : invoices) {
                Patient patient = patientDAO.getById(invoice.getPatientId());
                String patientName = patient != null ? patient.getFullname() : "N/A";
                String serviceSummary = getServiceSummary(invoice);
                model.addRow(new Object[]{
                        invoice.getId(),
                        dateFormat.format(invoice.getCreatedDate()),
                        serviceSummary,
                        String.format("%.2f", invoice.getTotal())
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getServiceSummary(Invoice invoice) {
        try {
            Invoice fullInvoice = invoiceDAO.getById(invoice.getId());
            if (fullInvoice.getItems() != null && !fullInvoice.getItems().isEmpty()) {
                if (fullInvoice.getItems().size() == 1) {
                    return fullInvoice.getItems().get(0).getServiceName();
                } else {
                    return fullInvoice.getItems().get(0).getServiceName() + " và " +
                            (fullInvoice.getItems().size() - 1) + " dịch vụ khác";
                }
            }
        } catch (SQLException e) {
            // Ignore
        }
        return "Không có dịch vụ";
    }

    private void searchInvoices() {
        String keyword = view.getSearchField().getText().trim();
        if (keyword.isEmpty()) {
            loadInvoices();
            return;
        }

        try {
            List<Invoice> invoices = invoiceDAO.searchByPatientName(keyword);
            DefaultTableModel model = (DefaultTableModel) view.getInvoiceTable().getModel();
            model.setRowCount(0);

            for (Invoice invoice : invoices) {
                Patient patient = patientDAO.getById(invoice.getPatientId());
                String patientName = patient != null ? patient.getFullname() : "N/A";
                String serviceSummary = getServiceSummary(invoice);
                model.addRow(new Object[]{
                        invoice.getId(),
                        dateFormat.format(invoice.getCreatedDate()),
                        serviceSummary,
                        String.format("%.2f", invoice.getTotal())
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addInvoice() {
        InvoiceFormDialog dialog = new InvoiceFormDialog(view, null);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            Invoice invoice = dialog.getInvoice();
            try {
                invoiceDAO.insert(invoice);
                JOptionPane.showMessageDialog(view, "Tạo hóa đơn thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadInvoices();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view, "Lỗi tạo hóa đơn: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editInvoice() {
        int selectedRow = view.getInvoiceTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn hóa đơn để chỉnh sửa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int invoiceId = (Integer) view.getInvoiceTable().getValueAt(selectedRow, 0);
            Invoice invoice = invoiceDAO.getById(invoiceId);

            if (invoice != null) {
                InvoiceFormDialog dialog = new InvoiceFormDialog(view, invoice);
                dialog.setVisible(true);

                if (dialog.isSaved()) {
                    Invoice updatedInvoice = dialog.getInvoice();
                    if (invoiceDAO.update(updatedInvoice)) {
                        JOptionPane.showMessageDialog(view, "Cập nhật hóa đơn thành công!",
                                "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadInvoices();
                    } else {
                        JOptionPane.showMessageDialog(view, "Cập nhật thất bại!",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteInvoice() {
        int selectedRow = view.getInvoiceTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn hóa đơn để xóa!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int invoiceId = (Integer) view.getInvoiceTable().getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(view,
                    "Bạn có chắc chắn muốn xóa hóa đơn này?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (invoiceDAO.delete(invoiceId)) {
                    JOptionPane.showMessageDialog(view, "Đã xóa hóa đơn thành công!",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadInvoices();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa thất bại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi xóa hóa đơn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

