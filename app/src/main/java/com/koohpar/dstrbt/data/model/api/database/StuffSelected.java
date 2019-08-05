package com.koohpar.dstrbt.data.model.api.database;

import android.util.Base64;
import android.util.Log;

import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.data.model.api.Picture;
import com.koohpar.dstrbt.data.model.api.SpecialOfferResponse;
import com.orm.SugarRecord;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by cmos on 04/05/2018.
 */

public class StuffSelected extends SugarRecord<StuffSelected> {

    private String GUID;
    private String BrandName;
    private String StuffName;
    private String Price;
    private String SumPrice;
    private int NumberSelected;
    private String Picture;
    private String ConsumerPrice;
    private String UnitPrice;
    private String Offer;

    public String getOffer() {
        return Offer;
    }

    public void setOffer(String offer) {
        Offer = offer;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getConsumerPrice() {
        return ConsumerPrice;
    }

    public void setConsumerPrice(String consumerPrice) {
        ConsumerPrice = consumerPrice;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getStuffName() {
        return StuffName;
    }

    public void setStuffName(String stuffName) {
        StuffName = stuffName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getNumberSelected() {
        return NumberSelected;
    }

    public void setNumberSelected(int numberSelected) {
        NumberSelected = numberSelected;
    }

    public String getSumPrice() {
        return SumPrice;
    }

    public void setSumPrice(String sumPrice) {
        SumPrice = sumPrice;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public StuffSelected(String offer, String guid, String brandName, String stuffName, String price, String sumPrice, int numberSelected, String picture, String consumerPrice, String unitPrice) {
        this.Offer = offer;
        this.GUID = guid;
        this.BrandName = brandName;
        this.StuffName = stuffName;
        this.Price = price;
        if (offer != null && Integer.parseInt(offer) > 0)
            this.SumPrice = String.valueOf(Integer.parseInt(sumPrice) - (((numberSelected * Integer.parseInt(price)) / 100) * Integer.parseInt(offer)));
        else
            this.SumPrice = sumPrice;
        this.NumberSelected = numberSelected;
        this.Picture = picture;
        this.ConsumerPrice = consumerPrice;
        this.UnitPrice = unitPrice;
    }

    public StuffSelected() {

    }

    public static void fromJson(List<StuffSelected> stuffSelecteds)
            throws JSONException, IOException {
        StuffSelected.deleteAll(StuffSelected.class);
        setSequenceForTable();
        for (int i = 0; i < stuffSelecteds.size(); i++) {
            StuffSelected object = stuffSelecteds.get(i);
            StuffSelected stuffSelected = new StuffSelected();
//            stuffSelected.value = object.getvValue();
//            stuffSelected.name = object.getvKey();
//            stuffSelected.type = object.getLanguageTypeName();
            stuffSelected.save();
        }
    }

    public static void deleteAll()
            throws JSONException, IOException {
        StuffSelected.deleteAll(StuffSelected.class);
        setSequenceForTable();
    }

    public static void setToDB(CategoryStuffResponse categoryStuffResponse)
            throws JSONException, IOException {
        String encodedImage = Base64.encodeToString(categoryStuffResponse.getPicture().getData(), Base64.DEFAULT);
        StuffSelected stuffSelected = new StuffSelected(
                categoryStuffResponse.getOffer(),
                categoryStuffResponse.getID(), categoryStuffResponse.getBrandName(), categoryStuffResponse.getStuffName(), categoryStuffResponse.getPrice(), categoryStuffResponse.getPrice(), 1, encodedImage, categoryStuffResponse.getConsumerPrice(), categoryStuffResponse.getUnitName());
        stuffSelected.save();
    }

    public static void setToDB(SpecialOfferResponse specialOfferResponse)
            throws JSONException, IOException {
        String encodedImage = Base64.encodeToString(specialOfferResponse.getPicture().getData(), Base64.DEFAULT);
        StuffSelected stuffSelected = new StuffSelected(
                specialOfferResponse.getOffer(),
                specialOfferResponse.getID(), specialOfferResponse.getBrandName(), specialOfferResponse.getStuffName(), specialOfferResponse.getPrice(), specialOfferResponse.getPrice(), 1, encodedImage, specialOfferResponse.getConsumerPrice(), specialOfferResponse.getUnitName());
        stuffSelected.save();
    }


    private static void setSequenceForTable() {
        StuffSelected.executeQuery("UPDATE SQLITE_SEQUENCE SET seq = 1 WHERE name = 'StuffSelected';");
    }

    public static int updateByGUID(StuffSelected stuffSelected) {
        List<StuffSelected> stuffSelecteds = StuffSelected.findByGUID(stuffSelected.GUID);
        stuffSelecteds.get(0).setNumberSelected(stuffSelecteds.get(0).getNumberSelected() + 1);
        if(Integer.parseInt(stuffSelecteds.get(0).getOffer()) == 0)
        stuffSelecteds.get(0).setSumPrice(String.valueOf(stuffSelecteds.get(0).getNumberSelected() * Integer.parseInt(stuffSelecteds.get(0).getPrice())));
        else
            stuffSelecteds.get(0).setSumPrice(String.valueOf(
                    (stuffSelecteds.get(0).getNumberSelected() * Integer.parseInt(stuffSelecteds.get(0).getPrice()))-
                    ((stuffSelecteds.get(0).getNumberSelected() * Integer.parseInt(stuffSelecteds.get(0).getPrice()))/100)*Integer.parseInt(stuffSelecteds.get(0).getOffer())));

        StuffSelected.saveInTx(stuffSelecteds);
        return stuffSelecteds.get(0).getNumberSelected();
    }

    public static int updateDecreaseByGUID(StuffSelected stuffSelected) {
        List<StuffSelected> stuffSelecteds = StuffSelected.findByGUID(stuffSelected.GUID);
        if (stuffSelecteds.get(0).getNumberSelected() > 1) {
            stuffSelecteds.get(0).setNumberSelected(stuffSelecteds.get(0).getNumberSelected() - 1);

            if(Integer.parseInt(stuffSelecteds.get(0).getOffer()) == 0)
                stuffSelecteds.get(0).setSumPrice(String.valueOf(stuffSelecteds.get(0).getNumberSelected() * Integer.parseInt(stuffSelecteds.get(0).getPrice())));
            else
                stuffSelecteds.get(0).setSumPrice(String.valueOf(
                        (stuffSelecteds.get(0).getNumberSelected() * Integer.parseInt(stuffSelecteds.get(0).getPrice()))-
                                ((stuffSelecteds.get(0).getNumberSelected() * Integer.parseInt(stuffSelecteds.get(0).getPrice()))/100)*Integer.parseInt(stuffSelecteds.get(0).getOffer())));

            StuffSelected.saveInTx(stuffSelecteds);
            return stuffSelecteds.get(0).getNumberSelected();
        } else {
            stuffSelecteds.get(0).delete();
            StuffSelected.saveInTx(stuffSelecteds);
            return 0;
        }

    }


    public static List<StuffSelected> findAll() {
        List<StuffSelected> list = StuffSelected.listAll(StuffSelected.class);
        return list.size() > 0 ? list : null;
    }

    public static List<StuffSelected> findByGUID(String guid) {
        List<StuffSelected> list = (StuffSelected.find(StuffSelected.class, "GUID=? ", guid));
        return list.size() > 0 ? list : null;
    }
}
