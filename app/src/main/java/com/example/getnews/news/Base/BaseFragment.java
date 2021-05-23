package com.example.getnews.news.Base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;

public class BaseFragment extends Fragment {


   protected BaseActivity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(BaseActivity)context;
    }

    public MaterialDialog showMessage(int titleResId, int messageRecId, int posRecTxt){

        return activity.showMessage(titleResId,messageRecId,posRecTxt);
    }
    public MaterialDialog showConfirmationMessage(int titleResId,int messageRecId,int posRecTxt,MaterialDialog.SingleButtonCallback onPosAction){


        return activity.showConfirmationMessage(titleResId,messageRecId,posRecTxt,onPosAction);

    }
    public MaterialDialog showMessage(String title,String message,String posTxt){

        return  activity.showMessage(title,message,posTxt);
    }
    public MaterialDialog showProgressBar(){

        return  activity.showProgressBar();
    }
    public MaterialDialog hideProgressBar(){

        return activity.hideProgressBar();
    }

}
