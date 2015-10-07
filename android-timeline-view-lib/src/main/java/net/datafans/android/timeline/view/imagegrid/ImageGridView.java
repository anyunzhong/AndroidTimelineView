package net.datafans.android.timeline.view.imagegrid;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.loopj.android.image.SmartImageView;

import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.timeline.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonganyun on 15/10/6.
 */
public class ImageGridView extends FrameLayout {

    private int maxWidth;

    private Context context;


    private List<CommonImageView> imageViews = new ArrayList<>();

    private SmartImageView imageViewOne;

    public ImageGridView(Context context, int maxWidth) {
        super(context);

        this.context = context;

        this.maxWidth = maxWidth;

        initView();
    }

    public ImageGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView() {

        View view = LayoutInflater.from(context).inflate(R.layout.nine_image_grid, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(view, params);

        for (int i = 0; i < 9; i++) {

            int id = getId("image_" + i);
            if (id != 0) {
                CommonImageView imageView = (CommonImageView) view.findViewById(id);
                imageView.setMinimumHeight((maxWidth - 6) / 3);
                imageView.setMinimumWidth((maxWidth - 6) / 3);
                imageViews.add(imageView);
            }
        }


        imageViewOne = (SmartImageView) view.findViewById(R.id.image_one);


    }

    public void updateWithImage(List<String> images) {

        if (images.size() == 1) {
            imageViewOne.setVisibility(VISIBLE);
            imageViewOne.setImageUrl(images.get(0));
        } else {
            imageViewOne.setVisibility(GONE);
        }


        for (int i = 0; i < imageViews.size(); i++) {
            CommonImageView imageView = imageViews.get(i);

            if (images.size() == 1) {
                imageView.setVisibility(GONE);
            } else {
                if (images.size() == 4) {
                    if (i == 0 || i == 1) {
                        imageView.setVisibility(VISIBLE);
                        imageView.loadImage(images.get(i));
                    } else if (i == 3 || i == 4) {
                        imageView.setVisibility(VISIBLE);
                        imageView.loadImage(images.get(i - 1));
                    } else {
                        imageView.setVisibility(GONE);
                    }
                } else {
                    if (i < images.size()) {
                        imageView.setVisibility(VISIBLE);
                        imageView.loadImage(images.get(i));
                    } else {
                        imageView.setVisibility(GONE);
                    }
                }
            }
        }

    }

    private int getId(String name) {
        try {

            Field field = R.id.class.getField(name);

            return field.getInt(new R.drawable());

        } catch (Exception e) {
            return 0;
        }
    }

}
