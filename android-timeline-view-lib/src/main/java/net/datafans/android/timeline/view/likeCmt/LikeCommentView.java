package net.datafans.android.timeline.view.likeCmt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.DipHelper;
import net.datafans.android.timeline.R;
import net.datafans.android.timeline.item.BaseLineItem;
import net.datafans.android.timeline.item.LineCommentItem;
import net.datafans.android.timeline.item.LineLikeItem;
import net.datafans.android.timeline.view.span.LinkTouchMovementMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonganyun on 15/10/7.
 */
public class LikeCommentView extends FrameLayout {


    private Context context;

    private TextView likeView;

    private View likeLayout;

    private View dividerView;

    private LinearLayout commentLayout;

    private List<TextView> commentViews = new ArrayList<>();

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
        likeView.setMovementMethod(new LinkTouchMovementMethod());
        likeView.setClickable(false);
        likeView.setLinksClickable(true);

        likeLayout = view.findViewById(R.id.likeLayout);


        dividerView = view.findViewById(R.id.divider);

        commentLayout = (LinearLayout) view.findViewById(R.id.commentLayout);

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

            commentLayout.setVisibility(GONE);


            for (TextView textView : commentViews) {
                textView.setText("");
                textView.setVisibility(GONE);
            }
        } else {

            commentLayout.setVisibility(VISIBLE);


            int textViewCount = commentViews.size();
            for (int i=0; i<textViewCount; i++) {
                TextView textView = commentViews.get(i);
                textView.setText("");
                if (i<item.comments.size()){
                    textView.setVisibility(VISIBLE);
                }else{
                    textView.setVisibility(GONE);
                }
            }

            for (int i=0;i<item.comments.size();i++) {

                TextView textView;

                if ( textViewCount > 0 && i < textViewCount) {
                    textView = commentViews.get(i);
                }else{

                    textView = new TextView(context);
                    textView.setMovementMethod(new LinkTouchMovementMethod());
                    textView.setClickable(false);
                    textView.setLinksClickable(true);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0,0,0, DipHelper.dip2px(context,2));
                    commentLayout.addView(textView, params);
                    commentViews.add(textView);
                }


                LineCommentItem commentItem = item.comments.get(i);

                LinkTouchMovementMethod method = (LinkTouchMovementMethod) textView.getMovementMethod();
                method.setUniqueId(commentItem.commentId);
                method.setItemId(item.itemId);

                textView.setVisibility(VISIBLE);
                textView.setText(item.commentSpanStrs.get(i));
            }

        }
    }

}
