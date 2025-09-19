###  **Giới thiệu phần mềm quản lý hồ sơ bệnh án** {#giới-thiệu-phần-mềm-quản-lý-hồ-sơ-bệnh-án}

Phần mềm quản lý hồ sơ bệnh án là một ứng dụng được xây dựng bằng
**Java** nhằm hỗ trợ bệnh viện, phòng khám quản lý dữ liệu bệnh nhân một
cách hiệu quả, an toàn và tiện lợi.

Chức năng chính:

### **1. Quản lý hồ sơ bệnh nhân** {#quản-lý-hồ-sơ-bệnh-nhân}

- Tạo hồ sơ mới nhanh (scan CCCD, nhập tay).

- Lưu lịch sử khám, kết quả X-quang, đơn thuốc, hình ảnh.

- Gắn tag: chỉnh nha, nhổ răng khôn, implant... để tìm nhanh.

- Tìm kiếm bệnh nhân theo tên, số điện thoại, mã hồ sơ.

### **2. Quản lý lịch hẹn** {#quản-lý-lịch-hẹn}

- Xem lịch hẹn của từng bác sĩ, từng phòng ghế.

- Thông báo khi có bệnh nhân đến.

- Cho phép dời lịch, gộp lịch, hủy lịch.

- Hiển thị lịch theo dạng **calendar** hoặc **timeline**.  
  > **3. Quản lý điều trị**

<!-- -->

- Ghi kế hoạch điều trị cho từng bệnh nhân.

- Tick từng bước đã hoàn thành (vd: niềng răng -- buổi 1, buổi 2...).

- Upload ảnh so sánh trước -- sau để theo dõi tiến trình.

- Nhắc bác sĩ về các ca cần tái khám.

### **4. Tài chính & thanh toán** {#tài-chính-thanh-toán}

- Tạo hóa đơn trực tiếp khi bệnh nhân điều trị.

- Theo dõi thanh toán (một lần hoặc nhiều đợt).

- Báo cáo doanh thu theo ngày/tháng/năm.

- Báo cáo dịch vụ nào mang lại doanh thu nhiều nhất.

### **5. Quản lý kho & vật tư nha khoa** {#quản-lý-kho-vật-tư-nha-khoa}

- Quản lý vật tư: thuốc tê, chỉ khâu, implant, dây cung niềng...

- Cảnh báo khi vật tư sắp hết.

- Ghi lại số lượng vật tư đã dùng cho từng ca điều trị.

### **6. Hỗ trợ nội bộ** {#hỗ-trợ-nội-bộ}

- Chat nhanh giữa bác sĩ -- y tá -- lễ tân.

- Giao việc: "chuẩn bị hồ sơ cho ca X", "gửi nhắc nhở bệnh nhân Y".

- Thông báo nội bộ (họp, ca khẩn, ca VIP).

### **7. Phân tích & báo cáo** {#phân-tích-báo-cáo}

- Thống kê số lượng bệnh nhân theo bác sĩ.

- Tỉ lệ tái khám, tỷ lệ hủy lịch.

- Hiệu suất sử dụng ghế nha khoa (giờ hoạt động).

- Tổng hợp review/hài lòng từ bệnh nhân (nhập từ lễ tân).

**class Patient {**

**- patientID: String**

**- name: String**

**- dob: Date**

**- phone: String**

**- address: String**

**+ addRecord()**

**+ searchRecord()**

**}**

**class MedicalRecord {**

**- recordID: String**

**- diagnosis: String**

**- prescriptions: List**

**- tags: List**

**+ addPrescription()**

**}**

**class Appointment {**

**- appointmentID: String**

**- dateTime: DateTime**

**- status: String**

**- note: String**

**+ reschedule()**

**+ cancel()**

**}**

**class Doctor {**

**- doctorID: String**

**- name: String**

**- specialty: String**

**+ viewAppointments()**

**+ updateTreatment()**

**}**

**class TreatmentPlan {**

**- planID: String**

**- description: String**

**- steps: List**

**- progress: Double**

**+ addStep()**

**+ markStepDone()**

**}**

**class Invoice {**

**- invoiceID: String**

**- amount: Double**

**- status: String**

**- paymentDate: Date**

**+ generate()**

**+ pay()**

**}**

**class Inventory {**

**- itemID: String**

**- itemName: String**

**- quantity: int**

**- threshold: int**

**+ addItem()**

**+ useItem()**

**+ checkStock()**

**}**

**class Report {**

**- reportID: String**

**- type: String**

**- date: Date**

**- data: Object**

**+ generateRevenueReport()**

**+ generateEfficiencyReport()**

**}**

**Patient \"1\" \-- \"\*\" MedicalRecord**

**Patient \"1\" \-- \"\*\" Appointment**

**Doctor \"1\" \-- \"\*\" Appointment**

**Patient \"1\" \-- \"\*\" TreatmentPlan**

**TreatmentPlan \"1\" \-- \"\*\" Invoice**

**TreatmentPlan \"1\" \-- \"\*\" Inventory**

**Doctor \"1\" \-- \"\*\" Message**

**Report ..\> Appointment**

**Report ..\> Invoice**

**Report ..\> TreatmentPlan**
