package com.koohpar.dstrbt.data.model.api;

public class ImageDetail {

    private int pic = -1 ;
    private String name = "" ;

    public ImageDetail(int pic, String name){
        this.pic = pic ;
        this.name = name ;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
