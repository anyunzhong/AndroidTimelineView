package net.datafans.android.timeline.view.imagegrid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(maxWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(view, params);

        for (int i = 0; i < 9; i++) {

            int id = getId("image_" + i);
            if (id != 0) {
                CommonImageView imageView = (CommonImageView) view.findViewById(id);
                imageView.setMinimumHeight((maxWidth-6)/3);
                imageViews.add(imageView);
            }
        }


    }

    public void updateWithImage(List<String> images) {

        for (int i = 0; i < 9; i++) {
            CommonImageView imageView = imageViews.get(i);
            if (images.size() - 1 < i) break;
            imageView.loadImage(images.get(i));
        }
    }

    private int getId(String name) {
        try {

            Field field = R.id.class.getField(name);

            int i = field.getInt(new R.drawable());
            return i;

        } catch (Exception e) {
            return 0;
        }
    }

}
