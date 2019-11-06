/*
 *
 *   Copyright (C) 2018, University of the Basque Country (UPV/EHU)
 *
 *  Contact for licensing options: <licensing-mcpttclient(at)mcopenplatform(dot)com>
 *
 *  This file is part of MCOP MCPTT Client
 *
 *  This is free software: you can redistribute it and/or modify it under the terms of
 *  the GNU General Public License as published by the Free Software Foundation, either version 3
 *  of the License, or (at your option) any later version.
 *
 *  This is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.test.client.mcopclient;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;


public class DialogAlert extends DialogFragment {
    private final static String TAG = Utils.getTAG(DialogAlert.class.getCanonicalName());

    private static final String PARAMETER_TITLE=TAG+".PARAMETER_TITLE";
    private static final String PARAMETER_DATA=TAG+".PARAMETER_DATA";
    private static final String PARAMETER_BUTTON_OK=TAG+".PARAMETER_BUTTON_OK";
    private static final String PARAMETER_BUTTON_CANCEL=TAG+".PARAMETER_BUTTON_CANCEL";
    private static final String PARAMETER_IS_BUTTON_CANCEL=TAG+".PARAMETER_IS_BUTTON_CANCEL";


    private String data;
    private String buttonOk;
    private String buttonCancel;
    private String[] groups;
    private OnClickOkButtonListener onClickOkButtonListener;
    private String title;
    private ArrayList<String> groupsSelect;
    private boolean isButtonCancel;


    public DialogAlert() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.data = getArguments().getString(PARAMETER_DATA);
        this.title=getArguments().getString(PARAMETER_TITLE);
        this.title=getArguments().getString(PARAMETER_TITLE);
        this.buttonOk=getArguments().getString(PARAMETER_BUTTON_OK);
        this.buttonCancel=getArguments().getString(PARAMETER_BUTTON_CANCEL);
        this.isButtonCancel=getArguments().getBoolean(PARAMETER_IS_BUTTON_CANCEL);
        if(buttonOk==null)
            buttonOk=getString(R.string.OK);
        if(buttonCancel==null)
            buttonCancel=getString(R.string.Cancel);
        groupsSelect=new ArrayList<>();
    }

    public static DialogAlert newInstance(String data, String title,boolean isButtonCancel) {
        DialogAlert f = new DialogAlert();

        Bundle args = new Bundle();
        args.putString(PARAMETER_DATA, data);
        args.putString(PARAMETER_TITLE, title);
        args.putBoolean(PARAMETER_IS_BUTTON_CANCEL, isButtonCancel);
        f.setArguments(args);

        return f;
    }

    public static DialogAlert newInstance(String title, String buttonOk, String buttonCancel,String data,boolean isButtonCancel) {
        DialogAlert f = new DialogAlert();

        Bundle args = new Bundle();
        args.putString(PARAMETER_DATA,data);
        args.putString(PARAMETER_TITLE, title);
        args.putString(PARAMETER_BUTTON_OK, buttonOk);
        args.putString(PARAMETER_BUTTON_CANCEL, buttonCancel);
        args.putBoolean(PARAMETER_IS_BUTTON_CANCEL, isButtonCancel);
        f.setArguments(args);
        return f;
    }

    /**
     * Display the dialog, adding the fragment to the given FragmentManager.  This
     * is a convenience for explicitly creating a transaction, adding the
     * fragment to it with the given tag, and committing it.  This does
     * <em>not</em> add the transaction to the back stack.  When the fragment
     * is dismissed, a new transaction will be executed to remove it from
     * the activity.
     * @param manager The FragmentManager whom fragment will be added to.
     * @param tag The tag for this fragment
     */
    public void show(FragmentManager manager, String tag) {
        try{
            super.show(manager,tag);
        }catch (Exception e){
            Log.e(TAG,"Error in dialog: "+e.getMessage());
        }

    }

    public static DialogAlert newInstance() {
        return newInstance(null,null,false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogSelfAffiliation();
    }

    /**
     * Create new Dialog Fragment
     * @return new Dialog Fragment
     */
    public AlertDialog createDialogSelfAffiliation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMessage(data)
                .setPositiveButton(buttonOk, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(onClickOkButtonListener!=null)
                            onClickOkButtonListener.onClickOkButton();
                    }
                });

        if(isButtonCancel){
            builder.setNegativeButton(buttonCancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    if (BuildConfig.DEBUG)Log.d(TAG,"Cancel dialog.");

                }
            });
        }
        return builder.create();
    }



    public interface OnClickOkButtonListener{
        void onClickOkButton();
    }

    public void setOnClickOkButtonListener(OnClickOkButtonListener onClickOkButtonListener){
        this.onClickOkButtonListener=onClickOkButtonListener;
    }
}
