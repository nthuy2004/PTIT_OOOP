package com.ptit.dental.model.dao;

import com.ptit.dental.model.entity.Invoice;
import com.ptit.dental.model.entity.InvoiceItem;
import com.ptit.dental.model.entity.MedicalRecord;
import com.ptit.dental.model.entity.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceDAO {

    private final Connection connection;
    private MedicalRecordDAO medicalRecordDAO;
    private PatientDAO patientDAO;

    public InvoiceDAO(Connection connection) {
        this.connection = connection;
        this.medicalRecordDAO = new MedicalRecordDAO(connection);
        this.patientDAO = new PatientDAO(connection);
    }

    // ===========================================================
    // CREATE INVOICE
    // ===========================================================
    public int createInvoice(Invoice invoice) throws SQLException {
        // Tìm hoặc tạo medical record cho patient
        int recordId = getOrCreateMedicalRecord(invoice.getPatientId());
        
        String sql = "INSERT INTO invoice (record_id, created_time, status, total) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, recordId); // Dùng medical record ID thay vì patient ID
            ps.setTimestamp(2, new java.sql.Timestamp(invoice.getCreatedDate().getTime()));
            ps.setInt(3, 1); // Default status = 1 (đã thanh toán/active)
            ps.setDouble(4, invoice.getTotal());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int invoiceId = rs.getInt(1);
                invoice.setId(invoiceId);

                // Save items
                for (InvoiceItem item : invoice.getItems()) {
                    createInvoiceItem(invoiceId, item);
                }

                return invoiceId;
            }
        }

        return -1;
    }

    // ===========================================================
    // CREATE INVOICE ITEM
    // ===========================================================
    private void createInvoiceItem(int invoiceId, InvoiceItem item) throws SQLException {
        String sql = "INSERT INTO invoice_item (invoice_id, service_name, quantity, price, total) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            ps.setString(2, item.getServiceName());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getUnitPrice());
            ps.setDouble(5, item.getTotal());
            ps.executeUpdate();
        }
    }

    // ===========================================================
    // GET ALL INVOICES
    // ===========================================================
    public List<Invoice> getAll() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT i.*, mr.patient_id FROM invoice i " +
                     "INNER JOIN medical_records mr ON i.record_id = mr.id " +
                     "ORDER BY i.id DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("id"));
                invoice.setPatientId(rs.getInt("patient_id")); // Lấy từ medical_records
                invoice.setCreatedDate(new Date(rs.getTimestamp("created_time").getTime()));
                invoice.setTotal(rs.getDouble("total"));

                invoice.setItems(getInvoiceItems(invoice.getId()));

                list.add(invoice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===========================================================
    // GET INVOICE ITEMS
    // ===========================================================
    public List<InvoiceItem> getInvoiceItems(int invoiceId) {
        List<InvoiceItem> items = new ArrayList<>();

        String sql = "SELECT * FROM invoice_item WHERE invoice_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, invoiceId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                InvoiceItem item = new InvoiceItem();
                item.setServiceName(rs.getString("service_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("price"));
                item.setTotal(rs.getDouble("total"));

                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // ===========================================================
    // DELETE INVOICE
    // ===========================================================
    public boolean deleteInvoice(int id) {
        try {

            deleteInvoiceItems(id);

            String sql = "DELETE FROM invoice WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===========================================================
    // DELETE INVOICE ITEMS
    // ===========================================================
    private void deleteInvoiceItems(int invoiceId) throws SQLException {
        String sql = "DELETE FROM invoice_item WHERE invoice_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, invoiceId);
        ps.executeUpdate();
    }

    // Compatibility wrappers for older controller code
    public Invoice getById(int id) throws SQLException {
        String sql = "SELECT i.*, mr.patient_id FROM invoice i " +
                     "INNER JOIN medical_records mr ON i.record_id = mr.id " +
                     "WHERE i.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("id"));
                    // Lấy patient_id từ medical_records, không phải từ record_id
                    invoice.setPatientId(rs.getInt("patient_id"));
                    invoice.setCreatedDate(new Date(rs.getTimestamp("created_time").getTime()));
                    invoice.setTotal(rs.getDouble("total"));
                    invoice.setItems(getInvoiceItems(invoice.getId()));
                    return invoice;
                }
            }
        }
        return null;
    }

    public List<Invoice> searchByPatientName(String keyword) throws SQLException {
        List<Invoice> invoiceList = new ArrayList<>();
        String sql = "SELECT i.*, mr.patient_id FROM invoice i " +
                     "INNER JOIN medical_records mr ON i.record_id = mr.id " +
                     "INNER JOIN patients p ON mr.patient_id = p.id " +
                     "WHERE p.fullname LIKE ? ORDER BY i.id DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("id"));
                    invoice.setPatientId(rs.getInt("patient_id")); // Lấy từ medical_records
                    invoice.setCreatedDate(new Date(rs.getTimestamp("created_time").getTime()));
                    invoice.setTotal(rs.getDouble("total"));
                    invoice.setItems(getInvoiceItems(invoice.getId()));
                    invoiceList.add(invoice);
                }
            }
        }
        return invoiceList;
    }
    
    /**
     * Tìm kiếm hóa đơn theo tên bệnh nhân HOẶC tên dịch vụ
     */
    public List<Invoice> search(String keyword) throws SQLException {
        List<Invoice> invoiceList = new ArrayList<>();
        String sql = "SELECT DISTINCT i.*, mr.patient_id FROM invoice i " +
                     "INNER JOIN medical_records mr ON i.record_id = mr.id " +
                     "INNER JOIN patients p ON mr.patient_id = p.id " +
                     "LEFT JOIN invoice_item ii ON i.id = ii.invoice_id " +
                     "WHERE p.fullname LIKE ? OR ii.service_name LIKE ? " +
                     "ORDER BY i.id DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern); // Tìm theo tên bệnh nhân
            ps.setString(2, searchPattern); // Tìm theo tên dịch vụ
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("id"));
                    invoice.setPatientId(rs.getInt("patient_id"));
                    invoice.setCreatedDate(new Date(rs.getTimestamp("created_time").getTime()));
                    invoice.setTotal(rs.getDouble("total"));
                    invoice.setItems(getInvoiceItems(invoice.getId()));
                    invoiceList.add(invoice);
                }
            }
        }
        return invoiceList;
    }

    public void insert(Invoice invoice) throws SQLException {
        createInvoice(invoice);
    }

    public boolean update(Invoice invoice) throws SQLException {
        // Lấy invoice hiện tại để so sánh patient
        Invoice currentInvoice = getById(invoice.getId());
        if (currentInvoice == null) {
            throw new SQLException("Không tìm thấy hóa đơn với ID: " + invoice.getId());
        }
        
        int recordId;
        // Nếu patient thay đổi, cần tìm/tạo medical record mới
        if (currentInvoice.getPatientId() != invoice.getPatientId()) {
            recordId = getOrCreateMedicalRecord(invoice.getPatientId());
        } else {
            // Nếu patient không thay đổi, lấy record_id hiện tại
            String getRecordIdSql = "SELECT record_id FROM invoice WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(getRecordIdSql)) {
                ps.setInt(1, invoice.getId());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        recordId = rs.getInt("record_id");
                    } else {
                        throw new SQLException("Không tìm thấy record_id cho invoice ID: " + invoice.getId());
                    }
                }
            }
        }
        
        String sql = "UPDATE invoice SET record_id = ?, created_time = ?, total = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, recordId); // Dùng medical record ID
            ps.setTimestamp(2, new java.sql.Timestamp(invoice.getCreatedDate().getTime()));
            ps.setDouble(3, invoice.getTotal());
            ps.setInt(4, invoice.getId());

            // Replace items: delete and re-create
            deleteInvoiceItems(invoice.getId());
            for (InvoiceItem item : invoice.getItems()) {
                createInvoiceItem(invoice.getId(), item);
            }

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        return deleteInvoice(id);
    }
    
    // ===========================================================
    // HELPER: Get or Create Medical Record
    // ===========================================================
    /**
     * Tìm medical record của patient, nếu không có thì tạo mới
     * @param patientId ID của patient
     * @return ID của medical record
     */
    private int getOrCreateMedicalRecord(int patientId) throws SQLException {
        // Tìm medical record gần nhất của patient
        List<MedicalRecord> records = medicalRecordDAO.getByPatientId(patientId);
        
        if (records != null && !records.isEmpty()) {
            // Dùng medical record gần nhất
            return records.get(0).getId();
        }
        
        // Nếu không có, tạo medical record mới
        Patient patient = patientDAO.getById(patientId);
        if (patient == null) {
            throw new SQLException("Patient không tồn tại với ID: " + patientId);
        }
        
        MedicalRecord newRecord = new MedicalRecord(
            null, // id sẽ được generate
            patient,
            null, // doctor
            "Tự động tạo khi tạo hóa đơn", // diagnostic
            "", // plan
            "Đang điều trị", // status
            LocalDateTime.now() // time
        );
        
        medicalRecordDAO.insert(newRecord);
        return newRecord.getId();
    }
}
