package net.datafans.android.timeline.controller;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import net.datafans.android.timeline.event.CommentClickEvent;
import net.datafans.android.timeline.item.BaseLineItem;
import net.datafans.android.timeline.item.LineCommentItem;
import net.datafans.android.timeline.item.LineItemType;
import net.datafans.android.timeline.item.LineLikeItem;
import net.datafans.android.timeline.view.BaseLineCell;
import net.datafans.android.timeline.view.commentInput.CommentInputView;
import net.datafans.android.timeline.view.span.TouchSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by zhonganyun on 15/10/6.
 */
public abstract class TimelineViewController extends TableViewController<BaseLineItem> implements CommentInputView.Delegate, BaseLineCell.BaseLineCellDelegate {


    private List<BaseLineItem> items = new ArrayList<>();

    private CommentInputView inputView;

    private long currentItemId;

    private Map<Long, BaseLineItem> itemMap = new HashMap<>();
    private Map<Long, LineCommentItem> commentItemMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        super.onCreate(savedInstanceState);


        tableView.getAdapter().getListView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        tableView.hideDivider();


        initHeaderView();
        initCommentInputView();

        EventBus.getDefault().register(this);
    }





    @Override
    protected int getStatusBarColor() {
        return Color.rgb(30, 35, 46);
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

    private void initCommentInputView() {
        inputView = new CommentInputView(this);
        inputView.setVisibility(View.GONE);
        inputView.setDelegate(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        containerParent.addView(inputView, params);
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
        BaseLineCell cell = (BaseLineCell) adapter.getCell();
        cell.setDelegate(this);
        return cell;
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






    @SuppressWarnings("unused")
    public void onEvent(Object event) {
        if (event instanceof CommentClickEvent) {
            CommentClickEvent commentClickEvent = (CommentClickEvent) event;
            inputView.show();
            inputView.setCommentId(commentClickEvent.uniqueId);
            LineCommentItem commentItem = commentItemMap.get(commentClickEvent.uniqueId);
            if (commentItem!=null)
            inputView.setPlaceHolder("回复:"+commentItem.userNick);
            currentItemId = commentClickEvent.itemId;


        }
    }



    private BaseLineCellAdapter getAdapter(int itemType) {
        CellAdapterManager manager = CellAdapterManager.sharedInstance();
        return manager.getAdapter(itemType);
    }


    protected void addItem(BaseLineItem item) {
        items.add(item);
        itemMap.put(item.itemId, item);
        genLikeSpanStr(item);
        genCommentSpanStr(item);
    }


    protected void addLikeItem(LineLikeItem likeItem, long itemId){
        BaseLineItem item = itemMap.get(itemId);
        if (item == null) return;

        item.likes.add(0, likeItem);
        genLikeSpanStr(item);
        tableView.reloadData();
    }


    protected void addCommentItem(LineCommentItem commentItem, long itemId, long replyCommentId) {
        BaseLineItem item = itemMap.get(itemId);
        if (item == null) return;

        if (replyCommentId > 0) {
            LineCommentItem replyCommentItem = commentItemMap.get(replyCommentId);
            if (replyCommentItem == null) return;
            commentItem.replyUserNick = replyCommentItem.userNick;
            commentItem.replyUserId = replyCommentItem.userId;
        }

        item.comments.add(commentItem);
        genCommentSpanStr(item);
    }

    @Override
    public void onCommentCreate(long commentId, String text) {
        //Log.e(Config.TAG, "commentId: " +commentId  +  "  itemId: " + currentItemId);
        onCommentCreate(currentItemId, commentId, text);
    }

    protected abstract void onCommentCreate(long itemId, long commentId, String text);


    @Override
    public void onLikeClick(long itemId) {
        onLikeCreate(itemId);
    }


    protected abstract void  onLikeCreate(long itemId);


    @Override
    public void onCommentClick(long itemId) {
        currentItemId = itemId;
        inputView.show();
        inputView.setCommentId(0);

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
            spannableString.setSpan(new TouchSpan(this, like.userId), position, position + like.userNick.length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            position += like.userNick.length() + 2;
        }

        item.likeSpanStr = spannableString;

    }


    private void genCommentSpanStr(BaseLineItem item) {


        item.commentSpanStrs.clear();

        List<LineCommentItem> comments = item.comments;

        for (int i = 0; i < comments.size(); i++) {

            LineCommentItem comment = comments.get(i);

            commentItemMap.put(comment.commentId, comment);

            StringBuilder builder = new StringBuilder();
            builder.append(comment.userNick);
            if (comment.replyUserNick != null) {
                builder.append("回复");
                builder.append(comment.replyUserNick);
            }
            builder.append(": ");
            builder.append(comment.text);

            SpannableString spannableString = new SpannableString(builder.toString());


            if (comment.replyUserNick == null) {

                spannableString.setSpan(new TouchSpan(this, comment.userId), 0, comment.userNick.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {

                int position = 0;
                spannableString.setSpan(new TouchSpan(this, comment.userId), position, position + comment.userNick.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                position += comment.userNick.length() + 2; //2="回复"
                spannableString.setSpan(new TouchSpan(this, comment.replyUserId), position, position + comment.replyUserNick.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            item.commentSpanStrs.add(spannableString);
        }
    }

}

