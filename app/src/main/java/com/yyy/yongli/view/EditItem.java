package com.yyy.yongli.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yyy.yongli.R;
import com.yyy.yongli.interfaces.OnChangedListener;
import com.yyy.yongli.interfaces.OnEntryListener;
import com.yyy.yongli.interfaces.OnSelectClickListener;

public class EditItem extends FrameLayout {
    Context context;
    EditText etContent;
    TextView tvTitle;
    OnSelectClickListener onSelectClickListener;
    OnChangedListener onChangedListener;
    OnEntryListener onEntryListener;

    public void setOnEntryListener(OnEntryListener onEntryListener) {
        this.onEntryListener = onEntryListener;
    }

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        this.onSelectClickListener = onSelectClickListener;
    }

    public void setOnChangedListener(OnChangedListener onChangedListener) {
        this.onChangedListener = onChangedListener;
    }

    public EditItem setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    public EditItem setContent(String content) {
        etContent.setText(content);
        return this;
    }

    public EditItem setHint(String hint) {
        etContent.setHint(hint);
        return this;
    }

    public EditItem requestFocus(boolean b) {
        if (b)
            etContent.requestFocus();
        else
            etContent.clearFocus();
        return this;
    }

    public EditItem setInputType(int type) {
        etContent.setInputType(type);
        return this;
    }

    public EditItem setDigits(String digits) {
        etContent.setKeyListener(DigitsKeyListener.getInstance(digits));
        return this;
    }

    public EditItem(@NonNull Context context) {
        this(context, null);
    }

    public EditItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_edit, this, true);
        setBackgroundColor(context.getResources().getColor(R.color.white));
        init();
    }

    private void init() {
        tvTitle = findViewById(R.id.tv_title);
        etContent = findViewById(R.id.et_content);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onChangedListener != null) {
                    onChangedListener.onTextChanged(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etContent.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (onEntryListener != null) {
                        onEntryListener.onEntry(v);
//                        Log.e("event", "entry");
                    }
                }
                return false;
            }
        });
    }

    public String getText() {
        return etContent.getText().toString();
    }
}
