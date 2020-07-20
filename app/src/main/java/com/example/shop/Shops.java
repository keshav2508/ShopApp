
package com.example.shop;

public class Shops {
    private String mshopname;

    private String mshopStatus;

    private String mshopid;

    private String mshopmobile;

    private String mshopaddress;

    private String mImageResourceId;

    public Shops(String mshopname, String mshopStatus, String mshopid, String mshopmobile, String mshopaddress, String mImageResourceId) {
        this.mshopname = mshopname;
        this.mshopStatus = mshopStatus;
        this.mshopid = mshopid;
        this.mshopmobile = mshopmobile;
        this.mshopaddress = mshopaddress;
        this.mImageResourceId = mImageResourceId;
    }

    public void setMshopname(String mshopname) {
        this.mshopname = mshopname;
    }

    public String getMshopname() {
        return mshopname;
    }

    public String getMshopStatus() {
        return mshopStatus;
    }

    public String getImageResourceId() {
        return mImageResourceId;
    }

    public String getMshopid() {
        return mshopid;
    }

    public String getMshopmobile() {
        return mshopmobile;
    }

    public String getMshopaddress() {
        return mshopaddress;
    }
}