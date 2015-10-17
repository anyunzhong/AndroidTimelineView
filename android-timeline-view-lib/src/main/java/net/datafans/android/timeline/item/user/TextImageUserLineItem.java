package net.datafans.android.timeline.item.user;

import net.datafans.android.timeline.item.LineItemType;

/**
 * Created by zhonganyun on 15/10/17.
 */
public class TextImageUserLineItem extends BaseUserLineItem {


    public TextImageUserLineItem(){

        this.itemType = UserLineItemType.TextImage;
    }


    public String cover;
    public String text;
    public int photoCount;
}
