package net.datafans.android.timeline.view.user;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.timeline.R;
import net.datafans.android.timeline.item.user.BaseUserLineItem;
import net.datafans.android.timeline.item.user.TextImageUserLineItem;

/**
 * Created by zhonganyun on 15/10/17.
 */
public class TextImageUserLineCell extends BaseUserLineCell {


    private CommonImageView coverView;

    private TextView textView;

    private TextView photoCountView;

    public TextImageUserLineCell(int layout, Context context) {
        super(layout, context);

        View view = LayoutInflater.from(context).inflate(R.layout.user_text_image_cell, null);
        contentView.addView(view);

        coverView = (CommonImageView) view.findViewById(R.id.cover);

        textView = (TextView) view.findViewById(R.id.text);

        photoCountView = (TextView) view.findViewById(R.id.photoCount);
    }


    @Override
    protected void refresh(BaseUserLineItem item) {
        super.refresh(item);
        TextImageUserLineItem textImageUserLineItem = null;

        if (item instanceof TextImageUserLineItem) {
            textImageUserLineItem = (TextImageUserLineItem) item;
        }

        if (textImageUserLineItem == null) return;

        if (textImageUserLineItem.cover == null){
            coverView.setVisibility(View.GONE);
            photoCountView.setVisibility(View.GONE);

            textView.setMaxLines(4);

            contentView.setBackgroundColor(Color.rgb(240,240,240));
        }else{
            coverView.loadImage(textImageUserLineItem.cover);
            coverView.setVisibility(View.VISIBLE);
            photoCountView.setVisibility(View.VISIBLE);
            if (textImageUserLineItem.photoCount > 1){
                photoCountView.setText(String.format("共%d张",textImageUserLineItem.photoCount));
            }else{
                photoCountView.setText("");
            }

            textView.setMaxLines(3);
            contentView.setBackgroundColor(Color.TRANSPARENT);

        }

        textView.setText(textImageUserLineItem.text);

    }


}
