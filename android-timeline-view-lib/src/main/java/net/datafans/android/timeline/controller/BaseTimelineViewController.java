package net.datafans.android.timeline.controller;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import net.datafans.android.common.helper.DipHelper;
import net.datafans.android.common.widget.controller.TableViewController;
import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.timeline.R;

/**
 * Created by zhonganyun on 15/10/17.
 */
public abstract class BaseTimelineViewController<T> extends TableViewController<T> {

    private CommonImageView coverView;

    protected CommonImageView userAvatarView;

    protected TextView userNickView;

    protected TextView signView;

    protected int coverWidth;
    protected int coverHeight;

    protected int userAvatarSize;


    protected int userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tableView.getAdapter().getListView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        tableView.hideDivider();
    }

    @Override
    protected int getStatusBarColor() {
        return Color.rgb(30, 35, 46);
    }


    @Override
    protected View getTableHeaderView() {
        View header = getLayoutInflater().inflate(R.layout.header, null);
        coverView = (CommonImageView) header.findViewById(R.id.cover);


        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        coverWidth = width;
        coverHeight = DipHelper.dip2px(this, 200);


        userAvatarView = (CommonImageView) header.findViewById(R.id.userAvatar);

        userAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHeaderUserAvatar();
            }
        });
        userAvatarSize = 160;

        userNickView = (TextView) header.findViewById(R.id.userNick);
        signView = (TextView) header.findViewById(R.id.sign);
        return header;
    }

    protected abstract void onClickHeaderUserAvatar();


    protected void setCover(String url) {
        coverView.loadImage(url);
    }

    protected void setUserAvatar(String url) {
        userAvatarView.loadImage(url);
    }

    protected void setUserNick(String nick) {
        userNickView.setText(nick);
    }

    protected void setUserId(int userId){
        this.userId = userId;
    }


    protected void setUserSign(String sign) {
        signView.setText(sign);
    }


}
