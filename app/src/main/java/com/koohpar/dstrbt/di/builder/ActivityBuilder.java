package com.koohpar.dstrbt.di.builder;

import com.koohpar.dstrbt.ui.about.AboutUsActivity;
import com.koohpar.dstrbt.ui.about.AboutUsActivityModule;
import com.koohpar.dstrbt.ui.activation.ActivationActivity;
import com.koohpar.dstrbt.ui.activation.ActivationActivityModule;
import com.koohpar.dstrbt.ui.brand.BrandActivity;
import com.koohpar.dstrbt.ui.brand.BrandActivityModule;
import com.koohpar.dstrbt.ui.category.CategoryActivity;
import com.koohpar.dstrbt.ui.category.CategoryActivityModule;
import com.koohpar.dstrbt.ui.categoryStuff.CategoryStuffActivity;
import com.koohpar.dstrbt.ui.categoryStuff.CategoryStuffActivityModule;
import com.koohpar.dstrbt.ui.detailReportList.DetailReportListActivity;
import com.koohpar.dstrbt.ui.detailReportList.DetailReportListActivityModule;
import com.koohpar.dstrbt.ui.forgetPassword.ForgetPasswordActivity;
import com.koohpar.dstrbt.ui.forgetPassword.ForgetPasswordActivityModule;
import com.koohpar.dstrbt.ui.listSelectedStuff.ListSelectedStuffActivity;
import com.koohpar.dstrbt.ui.listSelectedStuff.ListSelectedStuffActivityModule;
import com.koohpar.dstrbt.ui.login.LoginActivity;
import com.koohpar.dstrbt.ui.login.LoginActivityModule;
import com.koohpar.dstrbt.ui.main.MainActivity;
import com.koohpar.dstrbt.ui.main.MainActivityModule;
import com.koohpar.dstrbt.ui.newPassword.NewPasswordActivity;
import com.koohpar.dstrbt.ui.newPassword.NewPasswordActivityModule;
import com.koohpar.dstrbt.ui.notifyInventory.ListNotifyInventoryActivity;
import com.koohpar.dstrbt.ui.notifyInventory.ListNotifyInventoryActivityModule;
import com.koohpar.dstrbt.ui.notifyInventory.ListNotifyInventoryViewModel;
import com.koohpar.dstrbt.ui.profile.ProfileActivity;
import com.koohpar.dstrbt.ui.profile.ProfileActivityModule;
import com.koohpar.dstrbt.ui.register.RegisterActivity;
import com.koohpar.dstrbt.ui.register.RegisterActivityModule;
import com.koohpar.dstrbt.ui.reportList.ReportListActivity;
import com.koohpar.dstrbt.ui.reportList.ReportListActivityModule;
import com.koohpar.dstrbt.ui.signIn.SignInActivity;
import com.koohpar.dstrbt.ui.signIn.SignInActivityModule;
import com.koohpar.dstrbt.ui.splash.SplashActivity;
import com.koohpar.dstrbt.ui.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = SignInActivityModule.class)
    abstract SignInActivity bindSignInActivity();

    @ContributesAndroidInjector(modules = ActivationActivityModule.class)
    abstract ActivationActivity bindActivationActivity();

    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity bindRegisterActivity();

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = ProfileActivityModule.class)
    abstract ProfileActivity bindProfileActivity();

    @ContributesAndroidInjector(modules = ForgetPasswordActivityModule.class)
    abstract ForgetPasswordActivity bindForgetPasswordActivity();

    @ContributesAndroidInjector(modules = CategoryActivityModule.class)
    abstract CategoryActivity bindCategoryActivity();

    @ContributesAndroidInjector(modules = CategoryStuffActivityModule.class)
    abstract CategoryStuffActivity bindCategoryStuffActivity();

    @ContributesAndroidInjector(modules = BrandActivityModule.class)
    abstract BrandActivity bindBrandActivity();

    @ContributesAndroidInjector(modules = ListSelectedStuffActivityModule.class)
    abstract ListSelectedStuffActivity bindListSelectedStuffActivity();

    @ContributesAndroidInjector(modules = AboutUsActivityModule.class)
    abstract AboutUsActivity bindAboutUsActivity();

    @ContributesAndroidInjector(modules = NewPasswordActivityModule.class)
    abstract NewPasswordActivity bindNewPasswordActivity();

    @ContributesAndroidInjector(modules = ReportListActivityModule.class)
    abstract ReportListActivity bindReportListActivity();

    @ContributesAndroidInjector(modules = DetailReportListActivityModule.class)
    abstract DetailReportListActivity bindDetailReportListActivity();

    @ContributesAndroidInjector(modules = ListNotifyInventoryActivityModule.class)
    abstract ListNotifyInventoryActivity bindListNotifyInventoryActivity();
}
