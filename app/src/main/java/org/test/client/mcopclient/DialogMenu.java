/*
 *
 *  Copyright (C) 2018 Eduardo Zarate Lasurtegui
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
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Spinner;


public class DialogMenu extends DialogFragment {
    private final static String TAG = DialogMenu.class.getCanonicalName();
    private EditText dialog_new_account_EditText_name;
    private EditText dialog_new_account_EditText_UriSip;
    private Spinner dialog_new_account_Spinner_type;

    private static final String PARAMETER_MENU_ITEM=TAG+".PARAMETER_MENU_ITEM";
    private static final String PARAMETER_MENU_TITLE=TAG+".PARAMETER_MENU_TITLE";


    private String[] items;
    private String title;

    private OnClickListener onClickItemListener;

    public DialogMenu() {
    }

    public static DialogMenu newInstance(String[] items, String title) {
        DialogMenu f = new DialogMenu();

        Bundle args = new Bundle();
        args.putStringArray(PARAMETER_MENU_ITEM, items);
        args.putString(PARAMETER_MENU_TITLE, title);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.items = getArguments().getStringArray(PARAMETER_MENU_ITEM);
        this.title = getArguments().getString(PARAMETER_MENU_TITLE);

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogNewAccount();
    }

    /**
     * create new Dialog Fragment
     * @return new Dialog Fragment
     */
    public AlertDialog createDialogNewAccount() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String titleComplete="options";
        if(title!=null){
            titleComplete+="("+title+")";
        }

        builder.setTitle(titleComplete)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(onClickItemListener!=null)onClickItemListener.onClickItem(item);
                    }
                });

        return builder.create();
    }


    public interface OnClickListener{
        void onClickItem(int item);
    }

    public void setOnClickItemListener(OnClickListener onClickItemListener){
        this.onClickItemListener=onClickItemListener;
    }
}
