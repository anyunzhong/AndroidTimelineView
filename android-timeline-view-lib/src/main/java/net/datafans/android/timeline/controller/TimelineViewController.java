package net.datafans.android.timeline.controller;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;
import net.datafans.android.timeline.adapter.BaseLineCellAdapter;
import net.datafans.android.timeline.adapter.CellAdapterManager;
import net.datafans.android.timeline.adapter.TextImageLineCellAdapter;
import net.datafans.android.timeline.event.CommentClickEvent;
import net.datafans.android.timeline.event.UserClickEvent;
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
public abstract class TimelineViewController extends BaseTimelineViewController<BaseLineItem> implements CommentInputView.Delegate, BaseLineCell.BaseLineCellDelegate {


    private View rootView;

    private List<BaseLineItem> items = new ArrayList<>();

    private CommentInputView inputView;

    private long currentItemId;

    private BaseLineCell currentOptCell;

    private Map<Long, BaseLineItem> itemMap = new HashMap<>();
    private Map<Long, LineCommentItem> commentItemMap = new HashMap<>();

    private boolean isKeyboardShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        super.onCreate(savedInstanceState);

        initAdapters();

        rootView = getRootView(this);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);


        tableView.getAdapter().getListView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        tableView.hideDivider();

        initCommentInputView();

        EventBus.getDefault().register(this);
    }


    private void initAdapters(){
        CellAdapterManager manager = CellAdapterManager.sharedInstance();

        TextImageLineCellAdapter imageLineCellAdapter = new TextImageLineCellAdapter();
        imageLineCellAdapter.setContext(this);
        manager.registerAdapter(LineItemType.TextImage, imageLineCellAdapter);
    }


    private void initCommentInputView() {
        inputView = new CommentInputView(this);
        inputView.setVisibility(View.GONE);
        inputView.setDelegate(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        containerParent.addView(inputView, params);
    }





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
            if (commentItem != null)
                inputView.setPlaceHolder("  回复:" + commentItem.userNick);
            currentItemId = commentClickEvent.itemId;
        } else if (event instanceof UserClickEvent) {
            UserClickEvent userClickEvent = (UserClickEvent) event;
            onUserClick(userClickEvent.userId);
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


    protected void addLikeItem(LineLikeItem likeItem, long itemId) {
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


    protected abstract void onLikeCreate(long itemId);


    @Override
    public void onAlbumOptViewClick(BaseLineCell cell) {
        currentOptCell = cell;
    }

    @Override
    public void onCommentClick(long itemId) {
        currentItemId = itemId;
        inputView.show();
        inputView.setCommentId(0);

    }

    @Override
    public void onCellClick() {
        if (currentOptCell != null)
            currentOptCell.hideAlbumOptView();
    }

    protected abstract void onUserClick(int userId);


    @Override
    protected void onClickHeaderUserAvatar() {
        onUserClick(userId);
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

    private ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

            int heightDiff = rootView.getHeight() - containerParent.getHeight();

            if (heightDiff > 300) {
                LogHelper.debug("键盘弹出状态");
                isKeyboardShow = true;
            } else {
                LogHelper.debug("键盘收起状态");
                if (isKeyboardShow)
                    inputView.hide();

                isKeyboardShow = false;

            }
        }
    };

    private static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

}

