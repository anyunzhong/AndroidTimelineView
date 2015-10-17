package net.datafans.android.timeline.item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonganyun on 15/10/6.
 */
public class TextImageLineItem extends BaseLineItem {

    public TextImageLineItem(){

        this.itemType = LineItemType.TextImage;
    }

    public String text;
    public String parsedText;
    public List<String> thumbImages = new ArrayList<>();
    public List<String> srcImages = new ArrayList<>();
    public int width;
    public int height;
}
