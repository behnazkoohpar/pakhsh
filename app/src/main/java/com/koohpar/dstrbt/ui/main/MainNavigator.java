package com.koohpar.dstrbt.ui.main;

import com.koohpar.dstrbt.data.model.api.BannerResponse;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.data.model.api.SpecialOfferResponse;

import java.util.List;

/**
 * Created by cmos on 05/04/2018.
 */

public interface MainNavigator {
    void setBanner(List<BannerResponse> bannerResponses);
    void setSpecialOffer(List<SpecialOfferResponse> specialOfferResponses);
    void callSearch();
}
