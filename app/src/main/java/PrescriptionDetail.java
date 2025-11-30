/*
 * PTIT OOP
 * QUAN LY PHONG KHAM RANG
 */

/**
 *
 * @author Administrator
 */
public class PrescriptionDetail {
    private int drugId;
    private String drugName;
    private String drugType;
    private String drugDesc;
    private int quantity;
    private double drugPrice;
    private String usage;
    private double totalPrice;

    // Constructors
    public PrescriptionDetail() {
    }

    public PrescriptionDetail(int drugId, String drugName, String drugType, String drugDesc,
                              int quantity, double drugPrice, String usage) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.drugType = drugType;
        this.drugDesc = drugDesc;
        this.quantity = quantity;
        this.drugPrice = drugPrice;
        this.usage = usage;
        this.totalPrice = drugPrice * quantity;
    }

    // Getters and Setters
    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugDesc() {
        return drugDesc;
    }

    public void setDrugDesc(String drugDesc) {
        this.drugDesc = drugDesc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = drugPrice * quantity;
    }

    public double getDrugPrice() {
        return drugPrice;
    }

    public void setDrugPrice(double drugPrice) {
        this.drugPrice = drugPrice;
        this.totalPrice = drugPrice * quantity;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "PrescriptionDrugDetail{" +
                "drugName='" + drugName + '\'' +
                ", quantity=" + quantity +
                ", usage='" + usage + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}