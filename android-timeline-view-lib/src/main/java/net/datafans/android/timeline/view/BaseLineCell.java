package net.datafans.android.timeline.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.TimeHelper;
import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.timeline.R;
import net.datafans.android.timeline.event.UserClickEvent;
import net.datafans.android.timeline.item.BaseLineItem;
import net.datafans.android.timeline.view.likeCmt.LikeCommentView;

import de.greenrobot.event.EventBus;

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


    private LikeCommentView likeCommentView;


    private ImageView albumOptView;

    private LinearLayout albumToolbar;

    private boolean isToolbarShow = false;

    private View toolLikeView;
    private View toolCommentView;

    private BaseLineCellDelegate delegate;


    private BaseLineItem item;


    public BaseLineCell(int layout, Context context) {
        super(layout, context);

        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (delegate != null)
                    delegate.onCellClick();
            }
        });
        userAvatarView = (CommonImageView) cell.findViewById(R.id.userAvatar);
        userAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new UserClickEvent(BaseLineCell.this.item.userId));
            }
        });
        userNickView = (TextView) cell.findViewById(R.id.userNick);
        titleView = (TextView) cell.findViewById(R.id.title);

        contentView = (LinearLayout) cell.findViewById(R.id.content);


        locationView = (TextView) cell.findViewById(R.id.location);

        timeView = (TextView) cell.findViewById(R.id.time);


        likeCommentView = (LikeCommentView) cell.findViewById(R.id.likeCmt);


        albumOptView = (ImageView) cell.findViewById(R.id.album_opt);
        albumToolbar = (LinearLayout) cell.findViewById(R.id.album_toolbar);

        albumOptView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swicthToolbar();
                if (delegate != null)
                    delegate.onAlbumOptViewClick(BaseLineCell.this);
            }
        });


        toolLikeView = cell.findViewById(R.id.toolbarLike);

        toolLikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swicthToolbar();
                if (delegate != null) {
                    delegate.onLikeClick(item.itemId);
                }
            }
        });

        toolCommentView = cell.findViewById(R.id.toolbarComment);
        toolCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swicthToolbar();
                if (delegate != null) {
                    delegate.onCommentClick(item.itemId);
                }
            }
        });


    }


    private void swicthToolbar() {
        if (!isToolbarShow)
            albumToolbar.setVisibility(View.VISIBLE);
        else
            albumToolbar.setVisibility(View.GONE);

        isToolbarShow = !isToolbarShow;
    }


    @Override
    protected void refresh(BaseLineItem item) {

        this.item = item;


        userAvatarView.loadImage(item.userAvatar);
        userNickView.setText(item.userNick);
        if (item.title == null) {
            titleView.setVisibility(View.GONE);
        } else {
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(item.title);
        }

        if (item.location == null) {
            locationView.setVisibility(View.GONE);
        } else {
            locationView.setVisibility(View.VISIBLE);
            locationView.setText(item.location);
        }

        timeView.setText(TimeHelper.prettyTime(item.ts));

        if (item.likes.isEmpty() && item.comments.isEmpty()) {
            likeCommentView.setVisibility(View.GONE);
        } else {
            likeCommentView.setVisibility(View.VISIBLE);
            likeCommentView.updateWithItem(item);
        }

    }

    public void setDelegate(BaseLineCellDelegate delegate) {
        this.delegate = delegate;
    }


    public void hideAlbumOptView() {
        if (!isToolbarShow) return;
        swicthToolbar();
    }


    public interface BaseLineCellDelegate {

        void onAlbumOptViewClick(BaseLineCell cell);

        void onLikeClick(long itemId);

        void onCommentClick(long itemId);

        void onCellClick();
    }
}