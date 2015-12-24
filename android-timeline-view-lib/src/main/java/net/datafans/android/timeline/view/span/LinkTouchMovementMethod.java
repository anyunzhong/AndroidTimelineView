package net.datafans.android.timeline.view.span;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

import net.datafans.android.common.helper.LogHelper;
import net.datafans.android.timeline.event.CommentClickEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by zhonganyun on 15/10/12.
 */
public class LinkTouchMovementMethod extends LinkMovementMethod {

    private TouchSpan mPressedSpan;

    private long uniqueId;
    private long itemId;

    @Override
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPressedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null) {
                mPressedSpan.setPressed(true);
                Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan),
                        spannable.getSpanEnd(mPressedSpan));
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            TouchSpan touchedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null && touchedSpan != mPressedSpan) {
                mPressedSpan.setPressed(false);
                mPressedSpan = null;
                Selection.removeSelection(spannable);
            }
        } else {
            if (mPressedSpan != null) {
                mPressedSpan.setPressed(false);
                super.onTouchEvent(textView, spannable, event);
            }else{
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    LogHelper.debug("点击了label其它地方: " + uniqueId);
                    CommentClickEvent commentClickEvent = new CommentClickEvent();
                    commentClickEvent.uniqueId = uniqueId;
                    commentClickEvent.itemId = itemId;
                    EventBus.getDefault().post(commentClickEvent);
                }
            }
            mPressedSpan = null;
            Selection.removeSelection(spannable);
        }
        return true;
    }

    TouchSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();

        x += textView.getScrollX();
        y += textView.getScrollY();

        Layout layout = textView.getLayout();
        int line = layout.getLineForVertical(y);
        int off = layout.getOffsetForHorizontal(line, x);

        TouchSpan[] link = spannable.getSpans(off, off, TouchSpan.class);
        TouchSpan touchedSpan = null;
        if (link.length > 0) {
            touchedSpan = link[0];
        }
        return touchedSpan;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
