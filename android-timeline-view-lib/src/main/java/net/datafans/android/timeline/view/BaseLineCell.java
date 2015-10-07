package net.datafans.android.timeline.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.TimeHelper;
import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.timeline.R;
import net.datafans.android.timeline.item.BaseLineItem;

/**
 * Created by zhonganyun on 15/10/6.
 */
public abstract class BaseLineCell extends TableViewCell<BaseLineItem> {


    private CommonImageView userAvatarView;
    private TextView userNickView;
    private TextView titleView;
    private TextView locationView;
    private TextView timeView;

    protected LinearLayout contentView;


    public BaseLineCell(int layout, Context context) {
        super(layout, context);

        userAvatarView = (CommonImageView) cell.findViewById(R.id.userAvatar);
        userNickView = (TextView) cell.findViewById(R.id.userNick);
        titleView = (TextView) cell.findViewById(R.id.title);

        contentView = (LinearLayout) cell.findViewById(R.id.content);


        locationView = (TextView) cell.findViewById(R.id.location);
        timeView = (TextView) cell.findViewById(R.id.time);
    }

    @Override
    protected void refresh(BaseLineItem item) {

        userAvatarView.loadImage(item.userAvatar);
        userNickView.setText(item.userNick);
        if (item.title == null){
            titleView.setVisibility(View.GONE);
        }else{
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(item.title);
        }

        if (item.location == null){
            locationView.setVisibility(View.GONE);
        }else{
            locationView.setVisibility(View.VISIBLE);
            locationView.setText(item.location);
        }

        timeView.setText(TimeHelper.prettyTime(item.ts));
    }
}
