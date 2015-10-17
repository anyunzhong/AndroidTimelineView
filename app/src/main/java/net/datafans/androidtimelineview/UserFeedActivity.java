package net.datafans.androidtimelineview;

import android.os.Bundle;

import net.datafans.android.timeline.controller.UserTimelineViewController;
import net.datafans.android.timeline.item.user.BaseUserLineItem;
import net.datafans.android.timeline.item.user.TextImageUserLineItem;

public class UserFeedActivity extends UserTimelineViewController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        addItems();
        super.onCreate(savedInstanceState);


        setHeader();
    }

    @Override
    protected String getNavTitle() {
        return "Allen的相册";
    }


    private void setHeader() {
        String coverUrl = String.format("http://file-cdn.datafans.net/temp/12.jpg_%dx%d.jpeg", coverWidth, coverHeight);
        setCover(coverUrl);


        String userAvatarUrl = String.format("http://file-cdn.datafans.net/avatar/1.jpeg_%dx%d.jpeg", userAvatarSize, userAvatarSize);
        setUserAvatar(userAvatarUrl);


        setUserNick("Allen");

        setUserId(123456);
    }



    private void addItems() {

        TextImageUserLineItem item = new TextImageUserLineItem();
        item.itemId = 1111;
        item.ts = 1444902955586L;
        item.cover = "http://file-cdn.datafans.net/temp/11.jpg_200x200.jpeg";
        item.photoCount = 5;
        item.text = "我说你二你就二";
        addItem(item);


        TextImageUserLineItem item2 = new TextImageUserLineItem();
        item2.itemId = 11222;
        item2.ts = 1444902951586L;
        item2.text = "阿里巴巴（1688.com）是全球企业间电子商务的著名品牌，为数千万网商提供海量商机信息和便捷安全的在线交易市场，也是商人们以商会友、真实互动的社区平台 ...";

        addItem(item2);


        TextImageUserLineItem item3 = new TextImageUserLineItem();
        item3.itemId = 22221111;
        item3.ts = 1444102855586L;
        item3.cover = "http://file-cdn.datafans.net/temp/15.jpg_200x200.jpeg";
        item3.photoCount = 8;
        addItem(item3);


        TextImageUserLineItem item4 = new TextImageUserLineItem();
        item4.itemId = 7771111;
        item4.ts = 1442912955586L;
        item4.cover = "http://file-cdn.datafans.net/temp/19.jpg_200x200.jpeg";
        item4.photoCount = 6;
        addItem(item4);


        TextImageUserLineItem item5 = new TextImageUserLineItem();
        item5.itemId = 9991111;
        item5.ts = 1442912945586L;
        item5.cover = "http://file-cdn.datafans.net/temp/14.jpg_200x200.jpeg";
        item5.photoCount = 2;
        item5.text = "京东JD.COM-专业的综合网上购物商城，销售超数万品牌、4020万种商品，http://jd.com 囊括家电、手机、电脑、服装、图书、母婴、个护、食品、旅游等13大品类。秉承客户为先的理念，京东所售商品为正品行货、全国联保、机打发票。@刘强东";
        addItem(item5);


    }


    @Override
    public void onRefresh() {
        super.onRefresh();

        onEnd();
    }


    @Override
    public void onLoadMore() {
        super.onLoadMore();

        onEnd();
    }

    @Override
    public void onClickItem(BaseUserLineItem item) {

    }
}
