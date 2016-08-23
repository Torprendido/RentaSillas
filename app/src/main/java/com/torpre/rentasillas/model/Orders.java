package com.torpre.rentasillas.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@DatabaseTable
public class Orders implements Serializable {

    public static final byte STATUS_TO_BRING = 1;
    public static final byte STATUS_TO_COLLEGE = 2;

    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField
    private Integer rectangularTables;
    @DatabaseField
    private Integer rectangularCloths;
    @DatabaseField
    private Integer roundTables;
    @DatabaseField
    private Integer roundCloths;
    @DatabaseField
    private Integer pinkCoverCloths;
    @DatabaseField
    private Integer blueCoverCloths;
    @DatabaseField
    private Integer orangeCoverCloths;
    @DatabaseField
    private Integer chairs;
    @DatabaseField(canBeNull = false)
    private String address;
    @DatabaseField(canBeNull = false)
    private Date date;
    @DatabaseField
    private String payment;
    @DatabaseField(defaultValue = "1")
    private Byte status;

    public Orders() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getRectangularTables() {
        return rectangularTables;
    }

    public void setRectangularTables(Integer rectangularTables) {
        this.rectangularTables = rectangularTables;
    }

    public Integer getRectangularCloths() {
        return rectangularCloths;
    }

    public void setRectangularCloths(Integer rectangularCloths) {
        this.rectangularCloths = rectangularCloths;
    }

    public Integer getRoundTables() {
        return roundTables;
    }

    public void setRoundTables(Integer roundTables) {
        this.roundTables = roundTables;
    }

    public Integer getRoundCloths() {
        return roundCloths;
    }

    public void setRoundCloths(Integer roundCloths) {
        this.roundCloths = roundCloths;
    }

    public Integer getPinkCoverCloths() {
        return pinkCoverCloths;
    }

    public void setPinkCoverCloths(Integer pinkCoverCloths) {
        this.pinkCoverCloths = pinkCoverCloths;
    }

    public Integer getBlueCoverCloths() {
        return blueCoverCloths;
    }

    public void setBlueCoverCloths(Integer blueCoverCloths) {
        this.blueCoverCloths = blueCoverCloths;
    }

    public Integer getOrangeCoverCloths() {
        return orangeCoverCloths;
    }

    public void setOrangeCoverCloths(Integer orangeCoverCloths) {
        this.orangeCoverCloths = orangeCoverCloths;
    }

    public Integer getChairs() {
        return chairs;
    }

    public void setChairs(Integer chairs) {
        this.chairs = chairs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String header = date == null ? "" : new SimpleDateFormat("dd-MMMM-yyyy").format(date);
        header = header + "\nDirección: " + address;
        header = payment == null ? header : header + "\nPago: " + payment;
        String toString = "";
        toString = chairs == 0 ? header : header + "\n" + chairs + " Sillas";

        toString = rectangularTables == 0 ? toString : toString + "\n" + rectangularTables + " Mesas Rectangulares";
        toString = rectangularCloths == 0 ? toString : toString + "\n" + rectangularCloths + " Manteles Rectangulares";

        toString = chairs/10.0 == rectangularTables  & chairs/10.0 == rectangularCloths & rectangularTables != 0
                ? header + "\n" + rectangularTables + " Juegos Rectangulares"
                : toString;

        toString = roundTables == 0 ? toString : toString + "\n" + roundTables + " Mesas Redondas";
        toString = roundCloths == 0 ? toString : toString + "\n" + roundCloths + " Manteles Redondos";

        toString = chairs/10.0 == roundTables & chairs/10.0 == roundCloths & roundTables != 0
                ? header + "\n" + roundTables + " Juegos Rectangulares"
                : toString;
        toString = pinkCoverCloths == 0 ? toString : toString + "\n" + pinkCoverCloths + "Cubremanteles Rosas";
        toString = blueCoverCloths == 0 ? toString : toString + "\n" + blueCoverCloths + "Cubremanteles Azules";
        toString = orangeCoverCloths == 0 ? toString : toString + "\n" + orangeCoverCloths + "Cubremanteles Naranjas";

        return toString;
    }
}
