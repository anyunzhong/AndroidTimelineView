package net.datafans.android.timeline.item;

import android.text.SpannableString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonganyun on 15/10/6.
 */
public abstract class BaseLineItem {

    public long itemId;
    public int itemType;

    public int userId;
    public String userNick;
    public String userAvatar;

    public String title;

    public String location;

    public long ts;

    public List<LineLikeItem> likes = new ArrayList<>();
    public List<LineCommentItem> comments = new ArrayList<>();

    public SpannableString likeSpanStr;

    public List<SpannableString> commentSpanStrs = new ArrayList<>();

}
