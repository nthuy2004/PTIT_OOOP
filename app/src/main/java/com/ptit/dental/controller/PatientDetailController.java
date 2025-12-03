/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */
package com.ptit.dental.controller;

import com.ptit.dental.base.BaseController;
import com.ptit.dental.model.dao.AppointmentDAO;
import com.ptit.dental.model.dao.MedicalRecordDAO;
import com.ptit.dental.model.entity.Appointment;
import com.ptit.dental.model.entity.MedicalRecord;
import com.ptit.dental.model.entity.Patient;
import com.ptit.dental.utils.Injector;
import com.ptit.dental.utils.Util;
import com.ptit.dental.view.PatientDetailDialog;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class PatientDetailController extends BaseController<PatientDetailDialog> {
    
    private Patient patient;
    private MedicalRecordDAO mDAO;
    private AppointmentDAO aDAO;
    private List<MedicalRecord> medicalRecords;
    private List<Appointment> appointments;
    
    public PatientDetailController(PatientDetailDialog view, Patient patient) {
        super(view);
        this.patient = patient;
        this.mDAO = Injector.get(MedicalRecordDAO.class);
        this.aDAO = Injector.get(AppointmentDAO.class);
        initData();
    }
    
    private void initData()
    {
        view.setTitle("Hồ sơ bệnh án của bệnh nhân " + patient.getFullname());
        
        view.txtFullname.setText(patient.getFullname());
        view.txtBirthday.setText(patient.getBirthday().toString());
        view.txtGender.setText(patient.getGender().toString());
        view.txtPhone.setText(patient.getPhone());
        view.txtAddress.setText(patient.getAddress());
        
        try
        {
            medicalRecords = mDAO.getByPatientId(patient.getId());
            renderRecords(medicalRecords);
            
            appointments = aDAO.getByPatientId(patient.getId());
            renderApps(appointments);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    private void renderRecords(List<MedicalRecord> mrs)
    {
        DefaultTableModel model = (DefaultTableModel) view.tblRecords.getModel();
        model.setRowCount(0);

        for (MedicalRecord mr : mrs) {
            model.addRow(new Object[]{
                mr.getId(),
                mr.doctor.getFullname(),
                "ahihi",
                mr.time.toString(),
                mr.getDiagnostic(),
                mr.getPlan(),
                mr.getStatus()
            });
        }
    }
    
    private void renderApps(List<Appointment> list)
    {
        DefaultTableModel model = (DefaultTableModel) view.tblAppointments.getModel();
        model.setRowCount(0);

        for (Appointment a : list) {
                model.addRow(new Object[]{
                        a.getId(),
                        a.getPatientName(),
                        Util.formatDate(a.getDate()),
                        a.getTime(),
                        a.getService(),
                        a.getNote()
                });
            }
    }
    
}
