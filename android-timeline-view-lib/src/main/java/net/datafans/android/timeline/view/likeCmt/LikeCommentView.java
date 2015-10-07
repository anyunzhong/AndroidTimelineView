package net.datafans.android.timeline.view.likeCmt;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.datafans.android.timeline.R;
import net.datafans.android.timeline.item.BaseLineItem;
import net.datafans.android.timeline.item.LineCommentItem;
import net.datafans.android.timeline.item.LineLikeItem;

import java.util.List;

/**
 * Created by zhonganyun on 15/10/7.
 */
public class LikeCommentView extends FrameLayout {


    private Context context;

    private TextView likeView;

    private View likeLayout;

    private View dividerView;

    private TextView commentView;

    public LikeCommentView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public LikeCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        initView();
    }


    private void initView() {

        View view = LayoutInflater.from(context).inflate(R.layout.like_comment, null);
        addView(view);

        likeView = (TextView) view.findViewById(R.id.likes);
        likeView.setMovementMethod(LinkMovementMethod.getInstance());
        likeView.setClickable(false);
        likeView.setLinksClickable(true);

        likeLayout = view.findViewById(R.id.likeLayout);


        dividerView = view.findViewById(R.id.divider);


        commentView = (TextView) view.findViewById(R.id.comments);
        commentView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public void updateWithItem(BaseLineItem item) {


        List<LineLikeItem> likes = item.likes;
        List<LineCommentItem> comments = item.comments;

        if (likes.isEmpty()) {
            likeLayout.setVisibility(GONE);
            dividerView.setVisibility(GONE);
        } else {
            likeLayout.setVisibility(VISIBLE);

            if (comments.isEmpty()) {
                dividerView.setVisibility(GONE);
            } else {
                dividerView.setVisibility(VISIBLE);
            }


            if (item.likeSpanStr != null)
                likeView.setText(item.likeSpanStr);
        }

        if (comments.isEmpty()) {
            commentView.setVisibility(GONE);
        } else {
            commentView.setVisibility(VISIBLE);

            if (item.commentSpanStr != null)
                commentView.setText(item.commentSpanStr);
        }
    }

}
