package net.datafans.android.timeline.item;

/**
 * Created by zhonganyun on 15/10/6.
 */
public class LineCommentItem {
    public long commentId;
    public int userId;
    public String userNick;
    public int replyUserId;
    public String replyUserNick;
    public String text;

    @Override
    public String toString() {
        return "LineCommentItem{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", userNick='" + userNick + '\'' +
                ", replyUserId=" + replyUserId +
                ", replyUserNick='" + replyUserNick + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
