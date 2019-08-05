package com.koohpar.dstrbt.ui.category;

import com.koohpar.dstrbt.data.model.api.CategoryResponse;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cmos on 13/04/2018.
 */

public interface CategoryNavigator {
   void setCategory(List<CategoryResponse> categoryResponses);
}
