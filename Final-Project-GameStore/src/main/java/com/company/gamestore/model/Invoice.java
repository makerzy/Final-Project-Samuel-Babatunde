package com.company.gamestore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "invoice")
public class Invoice implements Serializable {

    @Id
    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceId;
    @NotEmpty(message = "You must supply a value for name.")
    private String name;
    @NotEmpty(message = "You must supply a value for street.")
    private String street;
    @NotEmpty(message = "You must supply a value for city.")
    private String city;
    @NotEmpty(message = "You must supply a value for state.")
    private String state;
    @NotEmpty(message = "You must supply a value for zipcode.")
    private String zipcode;

    @NotEmpty(message = "You must supply a value for itemType.")
    @Column(name = "item_type")
    private String itemType;
    @NotEmpty(message = "You must supply a value for itemId.")
    @Column(name = "item_id")
    private int itemId;
    @NotEmpty(message = "You must supply a value for quantity.")
    private int  quantity;

    @NotEmpty(message = "You must supply a value for unitPrice.")
    @Column(name = "unit_price")
    private double unitPrice;
    @NotEmpty(message = "You must supply a value for subtotal.")
    private double subtotal;
    @NotEmpty(message = "You must supply a value for tax.")
    private double tax;
    @NotEmpty(message = "You must supply a value for processingFee.")
    @Column(name = "processing_fee")
    private double processingFee;
    @NotEmpty(message = "You must supply a value for total.")
    private double total;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return invoiceId == invoice.invoiceId && itemId == invoice.itemId && quantity == invoice.quantity && Double.compare(invoice.unitPrice, unitPrice) == 0 && Double.compare(invoice.subtotal, subtotal) == 0 && Double.compare(invoice.tax, tax) == 0 && Double.compare(invoice.processingFee, processingFee) == 0 && Double.compare(invoice.total, total) == 0 && Objects.equals(name, invoice.name) && Objects.equals(street, invoice.street) && Objects.equals(city, invoice.city) && Objects.equals(state, invoice.state) && Objects.equals(zipcode, invoice.zipcode) && Objects.equals(itemType, invoice.itemType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, name, street, city, state, zipcode, itemType, itemId, quantity, unitPrice, subtotal, tax, processingFee, total);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", itemType='" + itemType + '\'' +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + subtotal +
                ", tax=" + tax +
                ", processingFee=" + processingFee +
                ", total=" + total +
                '}';
    }
}
