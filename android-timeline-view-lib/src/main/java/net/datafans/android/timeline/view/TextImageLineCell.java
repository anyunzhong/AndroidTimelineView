package net.datafans.android.timeline.view;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import net.datafans.android.common.helper.DipHelper;
import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.timeline.R;
import net.datafans.android.timeline.config.Config;
import net.datafans.android.timeline.item.BaseLineItem;
import net.datafans.android.timeline.item.TextImageLineItem;
import net.datafans.android.timeline.view.imagegrid.ImageGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhonganyun on 15/10/6.
 */
public class TextImageLineCell extends BaseLineCell {


    private TextView textView;

    private ImageGridView imageGridView;

    public TextImageLineCell(int layout, Context context) {
        super(layout, context);

        textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setLineSpacing(0f, 1.2f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, DipHelper.dip2px(context, 7));
        contentView.addView(textView, params);


        imageGridView = new ImageGridView(context, DipHelper.dip2px(context, 220));
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentView.addView(imageGridView, imageParams);


    }


    @Override
    protected void refresh(BaseLineItem item) {
        super.refresh(item);

        TextImageLineItem textImageLineItem = (TextImageLineItem) item;


        if (textImageLineItem == null) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(textImageLineItem.text);
        }

        if (textImageLineItem != null)
            imageGridView.updateWithImage(textImageLineItem.thumbImages);




    }
}
