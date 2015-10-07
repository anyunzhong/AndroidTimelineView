package net.datafans.android.timeline.controller;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import net.datafans.android.common.helper.DipHelper;
import net.datafans.android.common.widget.controller.TableViewController;
import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;
import net.datafans.android.timeline.R;
import net.datafans.android.timeline.adapter.BaseLineCellAdapter;
import net.datafans.android.timeline.adapter.CellAdapterManager;
import net.datafans.android.timeline.config.Config;
import net.datafans.android.timeline.item.BaseLineItem;
import net.datafans.android.timeline.item.LineCommentItem;
import net.datafans.android.timeline.item.LineItemType;
import net.datafans.android.timeline.item.LineLikeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonganyun on 15/10/6.
 */
public abstract class TimelineViewController extends TableViewController<BaseLineItem> {


    private List<BaseLineItem> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tableView.getAdapter().getListView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        tableView.hideDivider();


        initHeaderView();
    }


    private void initHeaderView() {

        View header = getLayoutInflater().inflate(R.layout.header, null);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
            tableView.getAdapter().getListView().addHeaderView(header);

        CommonImageView cover = (CommonImageView) header.findViewById(R.id.cover);


        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        cover.loadImage(getCover(width, DipHelper.dip2px(this, 200)));

        CommonImageView userAvatar = (CommonImageView) header.findViewById(R.id.userAvatar);
        userAvatar.loadImage(getUserAvatar(160, 160));

        TextView userNick = (TextView) header.findViewById(R.id.userNick);
        userNick.setText(getUserNick());
    }


    protected abstract String getCover(int width, int height);

    protected abstract String getUserAvatar(int width, int height);

    protected abstract String getUserNick();


    @Override
    protected RefreshControlType getRefreshControlType() {

        return RefreshControlType.BGANormal;
    }

    @Override
    public int getRows(int section) {

        return items.size();
    }

    @Override
    public TableViewCell<BaseLineItem> getTableViewCell(int section, int row) {

        BaseLineItem item = items.get(row);
        BaseLineCellAdapter adapter = getAdapter(item.itemType);
        return adapter.getCell();
    }


    @Override
    public int getItemViewType(int section, int row) {

        BaseLineItem item = getEntity(section, row);
        if (item.itemType == LineItemType.TextImage)
            return 0;
        return 100000;
    }

    @Override
    public int getItemViewTypeCount() {

        return 1;
    }

    @Override
    public BaseLineItem getEntity(int section, int row) {
        return items.get(row);
    }

    @Override
    public void onClickRow(int section, int row) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }


    private BaseLineCellAdapter getAdapter(int itemType) {
        CellAdapterManager manager = CellAdapterManager.sharedInstance();
        return manager.getAdapter(itemType);
    }


    protected void addItem(BaseLineItem item) {
        items.add(item);
        genLikeSpanStr(item);
        genCommentSpanStr(item);
    }


    private void genLikeSpanStr(BaseLineItem item) {

        List<LineLikeItem> likes = item.likes;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < likes.size(); i++) {
            LineLikeItem like = likes.get(i);
            builder.append(like.userNick);
            if (i != (likes.size() - 1))
                builder.append(", ");
        }


        SpannableString spannableString = new SpannableString(builder.toString());

        int position = 0;
        for (int i = 0; i < likes.size(); i++) {
            LineLikeItem like = likes.get(i);
            spannableString.setSpan(new ClickSpan(like.userId), position, position + like.userNick.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            position += like.userNick.length() + 2;
        }

        item.likeSpanStr = spannableString;

    }


    private void genCommentSpanStr(BaseLineItem item) {

        List<LineCommentItem> comments = item.comments;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < comments.size(); i++) {
            LineCommentItem comment = comments.get(i);
            builder.append(comment.userNick);
            if (comment.replyUserNick != null) {
                builder.append("回复");
                builder.append(comment.replyUserNick);
            }
            builder.append(": ");
            builder.append(comment.text);
            if (i != (comments.size() - 1))
                builder.append("\n");

        }

        SpannableString spannableString = new SpannableString(builder.toString());

        int position = 0;
        for (int i = 0; i < comments.size(); i++) {

            LineCommentItem comment = comments.get(i);
            spannableString.setSpan(new ClickSpan(comment.userId), position, position + comment.userNick.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            if (comment.replyUserNick == null) {
                position += comment.userNick.length() + 3 + comment.text.length(); //3=": "+"\n"
            } else {
                position += comment.userNick.length() + 2; //2="回复"
                spannableString.setSpan(new ClickSpan(comment.replyUserId), position, position + comment.replyUserNick.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                position += comment.text.length() + 3 + comment.replyUserNick.length(); //3=": "+"\n"
            }
        }

        item.commentSpanStr = spannableString;

    }


    private class ClickSpan extends ClickableSpan {

        private int userId;

        public ClickSpan(int userId) {
            this.userId = userId;
        }

        @Override
        public void onClick(View v) {

            Log.e(Config.TAG, "" + userId);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.hl));
            ds.setUnderlineText(false);
        }
    }


}

