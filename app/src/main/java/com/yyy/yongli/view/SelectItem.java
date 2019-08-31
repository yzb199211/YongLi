package com.yyy.yongli.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yyy.yongli.R;
import com.yyy.yongli.interfaces.OnSelectClickListener;

public class SelectItem extends FrameLayout {
    Context context;
    TextView tvContent;
    TextView tvTitle;
    OnSelectClickListener onSelectClickListener;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        this.onSelectClickListener = onSelectClickListener;
    }

    public SelectItem setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    public SelectItem setContent(String content) {
        tvContent.setText(content);
        return this;
    }

    public SelectItem setHint(String hint) {
        tvContent.setHint(hint);
        return this;
    }

    public String getTitle() {
        return tvTitle.getText().toString();
    }

    public SelectItem(@NonNull Context context) {
        this(context, null);
    }

    public SelectItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_select, this, true);
        setBackgroundColor(context.getResources().getColor(R.color.white));
        init();
    }

    private void init() {
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        tvContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectClickListener != null) {
                    onSelectClickListener.onSelectClick(v);
                }
            }
        });
    }

    public String getText() {
        return tvContent.getText().toString();
    }
}
