/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.text.method.KeyListener
 *  android.text.method.TextKeyListener
 *  android.view.KeyEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 *  com.unity3d.player.UnityPlayer
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 */
package com.unity3d.player;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.TextKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.unity3d.player.UnityPlayer;

public final class p
extends Dialog
implements TextWatcher,
View.OnClickListener {
    private Context a = null;
    private UnityPlayer b = null;

    /*
     * Enabled aggressive block sorting
     */
    public p(Context context, UnityPlayer unityPlayer, String string, int n2, boolean bl, boolean bl2, boolean bl3, String string2) {
        super(context);
        this.a = context;
        this.b = unityPlayer;
        this.getWindow().setGravity(80);
        this.getWindow().requestFeature(1);
        ColorDrawable colorDrawable = new ColorDrawable(0);
        this.getWindow().setBackgroundDrawable((Drawable)colorDrawable);
        this.setContentView(this.createSoftInputView());
        this.getWindow().clearFlags(2);
        EditText editText = (EditText)this.findViewById(1057292289);
        Button button = (Button)this.findViewById(1057292290);
        editText.setImeOptions(6);
        editText.setText((CharSequence)string);
        editText.setHint((CharSequence)string2);
        int n3 = bl ? 32768 : 0;
        int n4 = bl2 ? 131072 : 0;
        int n5 = n4 | n3;
        int n6 = 0;
        if (bl3) {
            n6 = 128;
        }
        int n7 = n6 | n5;
        if (n2 >= 0 && n2 <= 7) {
            n7 |= new int[]{1, 16385, 12290, 17, 2, 3, 97, 33}[n2];
        }
        editText.setInputType(n7);
        editText.addTextChangedListener((TextWatcher)this);
        int n8 = editText.getInputType();
        editText.setKeyListener((KeyListener)TextKeyListener.getInstance());
        editText.setRawInputType(n8);
        editText.setClickable(true);
        if (!bl2) {
            editText.selectAll();
        }
        button.setOnClickListener((View.OnClickListener)this);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener((p)this){
            private /* synthetic */ p a;
            {
                this.a = p2;
            }

            public final void onFocusChange(View view, boolean bl) {
                if (bl) {
                    this.a.getWindow().setSoftInputMode(5);
                }
            }
        });
    }

    private String a() {
        EditText editText = (EditText)this.findViewById(1057292289);
        if (editText == null) {
            return null;
        }
        return editText.getText().toString().trim();
    }

    public final void afterTextChanged(Editable editable) {
        this.b.reportSoftInputStr(editable.toString(), 0);
    }

    public final void beforeTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
    }

    protected final View createSoftInputView() {
        RelativeLayout relativeLayout = new RelativeLayout(this.a);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        EditText editText = new EditText(this, this.a){
            private /* synthetic */ p a;
            {
                this.a = p2;
                super(context);
            }

            public final boolean onKeyPreIme(int n2, KeyEvent keyEvent) {
                if (n2 == 4) {
                    this.a.b.reportSoftInputStr(this.a.a(), 1);
                }
                if (n2 == 84) {
                    return true;
                }
                return super.onKeyPreIme(n2, keyEvent);
            }

            public final void onWindowFocusChanged(boolean bl) {
                super.onWindowFocusChanged(bl);
                if (bl) {
                    ((InputMethodManager)this.a.a.getSystemService("input_method")).showSoftInput((View)this, 0);
                }
            }
        };
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(15);
        layoutParams.addRule(0, 1057292290);
        editText.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        editText.setId(1057292289);
        relativeLayout.addView((View)editText);
        Button button = new Button(this.a);
        button.setText(this.a.getResources().getIdentifier("ok", "string", "android"));
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(15);
        layoutParams2.addRule(11);
        button.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        button.setId(1057292290);
        relativeLayout.addView((View)button);
        ((EditText)relativeLayout.findViewById(1057292289)).setOnEditorActionListener(new TextView.OnEditorActionListener(this){
            private /* synthetic */ p a;
            {
                this.a = p2;
            }

            public final boolean onEditorAction(TextView textView, int n2, KeyEvent keyEvent) {
                if (n2 == 6) {
                    this.a.b.reportSoftInputStr(this.a.a(), 1);
                }
                return false;
            }
        });
        return relativeLayout;
    }

    public final void onBackPressed() {
        String string = this.a();
        this.b.reportSoftInputStr(string, 1);
    }

    public final void onClick(View view) {
        String string = p.super.a();
        this.b.reportSoftInputStr(string, 1);
    }

    public final void onTextChanged(CharSequence charSequence, int n2, int n3, int n4) {
    }

}

