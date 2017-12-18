package octapult.com.sazmaan.Pojo;

/**
 * Created by AQSA SHaaPARR on 12/12/2017.
 */

public class SalesReportPojo {

    public SalesReportPojo(String billNo, String saleDate, String clientName, String contact, String productName, String totalSaleItem, String gst, String unitPrice, String grandPrice) {
        this.billNo = billNo;
        this.saleDate = saleDate;
        this.clientName = clientName;
        this.contact = contact;
        this.productName = productName;
        this.totalSaleItem = totalSaleItem;
        this.gst = gst;
        this.unitPrice = unitPrice;
        this.grandPrice = grandPrice;
    }

    public SalesReportPojo() {
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTotalSaleItem() {
        return totalSaleItem;
    }

    public void setTotalSaleItem(String totalSaleItem) {
        this.totalSaleItem = totalSaleItem;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getGrandPrice() {
        return grandPrice;
    }

    public void setGrandPrice(String grandPrice) {
        this.grandPrice = grandPrice;
    }

    private String billNo,saleDate,clientName,contact,productName,totalSaleItem,gst,unitPrice,grandPrice;





}
