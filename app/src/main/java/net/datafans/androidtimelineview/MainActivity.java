package net.datafans.androidtimelineview;

import android.os.Bundle;
import android.util.Log;

import net.datafans.android.timeline.adapter.CellAdapterManager;
import net.datafans.android.timeline.adapter.TextImageLineCellAdapter;
import net.datafans.android.timeline.config.Config;
import net.datafans.android.timeline.controller.TimelineViewController;
import net.datafans.android.timeline.item.LineCommentItem;
import net.datafans.android.timeline.item.LineItemType;
import net.datafans.android.timeline.item.LineLikeItem;
import net.datafans.android.timeline.item.TextImageLineItem;

public class MainActivity extends TimelineViewController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        CellAdapterManager manager = CellAdapterManager.sharedInstance();

        TextImageLineCellAdapter imageLineCellAdapter = new TextImageLineCellAdapter();
        imageLineCellAdapter.setContext(this);
        manager.registerAdapter(LineItemType.TextImage, imageLineCellAdapter);


        addItems();


        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }

    @Override
    protected String getNavTitle() {
        return "朋友圈";
    }



    private TextImageLineItem textImageItem3;
    private void addItems() {


        TextImageLineItem textImageItem = new TextImageLineItem();

        textImageItem.itemId = 1;
        textImageItem.itemType = LineItemType.TextImage;
        textImageItem.userId = 10086;
        textImageItem.userAvatar = "http://file-cdn.datafans.net/avatar/1.jpeg";
        textImageItem.userNick = "Allen";
        textImageItem.title = "";
        textImageItem.text = "你是我的小苹果 小苹果 我爱你 就像老鼠爱大米 18680551720 [亲亲]";

        textImageItem.srcImages.add("http://file-cdn.datafans.net/temp/11.jpg");
        textImageItem.srcImages.add("http://file-cdn.datafans.net/temp/12.jpg");
        textImageItem.srcImages.add("http://file-cdn.datafans.net/temp/13.jpg");
        textImageItem.srcImages.add("http://file-cdn.datafans.net/temp/14.jpg");
        textImageItem.srcImages.add("http://file-cdn.datafans.net/temp/15.jpg");
        textImageItem.srcImages.add("http://file-cdn.datafans.net/temp/16.jpg");
        textImageItem.srcImages.add("http://file-cdn.datafans.net/temp/17.jpg");
        textImageItem.srcImages.add("http://file-cdn.datafans.net/temp/18.jpg");
        textImageItem.srcImages.add("http://file-cdn.datafans.net/temp/19.jpg");


        textImageItem.thumbImages.add("http://file-cdn.datafans.net/temp/11.jpg_160x160.jpeg");
        textImageItem.thumbImages.add("http://file-cdn.datafans.net/temp/12.jpg_160x160.jpeg");
        textImageItem.thumbImages.add("http://file-cdn.datafans.net/temp/13.jpg_160x160.jpeg");
        textImageItem.thumbImages.add("http://file-cdn.datafans.net/temp/14.jpg_160x160.jpeg");
        textImageItem.thumbImages.add("http://file-cdn.datafans.net/temp/15.jpg_160x160.jpeg");
        textImageItem.thumbImages.add("http://file-cdn.datafans.net/temp/16.jpg_160x160.jpeg");
        textImageItem.thumbImages.add("http://file-cdn.datafans.net/temp/17.jpg_160x160.jpeg");
        textImageItem.thumbImages.add("http://file-cdn.datafans.net/temp/18.jpg_160x160.jpeg");
        textImageItem.thumbImages.add("http://file-cdn.datafans.net/temp/19.jpg_160x160.jpeg");

        textImageItem.location = "中国 • 广州";
        textImageItem.ts = System.currentTimeMillis() - 10 * 60 * 1000;


        LineLikeItem likeItem1_1 = new LineLikeItem();
        likeItem1_1.userId = 10086;
        likeItem1_1.userNick = "Allen";
        textImageItem.likes.add(likeItem1_1);


        LineLikeItem likeItem1_2 = new LineLikeItem();
        likeItem1_2.userId = 10088;
        likeItem1_2.userNick = "奥巴马";
        textImageItem.likes.add(likeItem1_2);


        LineCommentItem commentItem1_1 = new LineCommentItem();
        commentItem1_1.userId = 10086;
        commentItem1_1.userNick = "习大大";
        commentItem1_1.text = "精彩 大家鼓掌";
        textImageItem.comments.add(commentItem1_1);


        LineCommentItem commentItem1_2 = new LineCommentItem();
        commentItem1_2.userId = 10088;
        commentItem1_2.userNick = "奥巴马";
        commentItem1_2.text = "欢迎来到美利坚";
        commentItem1_2.replyUserId = 10086;
        commentItem1_2.replyUserNick = "习大大";
        textImageItem.comments.add(commentItem1_2);


        LineCommentItem commentItem1_3 = new LineCommentItem();
        commentItem1_3.userId = 10010;
        commentItem1_3.userNick = "神雕侠侣";
        commentItem1_3.text = "呵呵";
        textImageItem.comments.add(commentItem1_3);

        addItem(textImageItem);


        TextImageLineItem textImageItem2 = new TextImageLineItem();
        textImageItem2.itemId = 2;
        textImageItem2.itemType = LineItemType.TextImage;
        textImageItem2.userId = 10088;
        textImageItem2.userAvatar = "http://file-cdn.datafans.net/avatar/2.jpg";
        textImageItem2.userNick = "奥巴马";
        textImageItem2.title = "发表了";
        textImageItem2.text = "京东JD.COM-专业的综合网上购物商城，销售超数万品牌、4020万种商品，http://jd.com 囊括家电、手机、电脑、服装、图书、母婴、个护、食品、旅游等13大品类。秉承客户为先的理念，京东所售商品为正品行货、全国联保、机打发票。@刘强东";


        textImageItem2.srcImages.add("http://file-cdn.datafans.net/temp/20.jpg");
        textImageItem2.srcImages.add("http://file-cdn.datafans.net/temp/21.jpg");
        textImageItem2.srcImages.add("http://file-cdn.datafans.net/temp/22.jpg");
        textImageItem2.srcImages.add("http://file-cdn.datafans.net/temp/23.jpg");

        textImageItem2.thumbImages.add("http://file-cdn.datafans.net/temp/20.jpg_160x160.jpeg");
        textImageItem2.thumbImages.add("http://file-cdn.datafans.net/temp/21.jpg_160x160.jpeg");
        textImageItem2.thumbImages.add("http://file-cdn.datafans.net/temp/22.jpg_160x160.jpeg");
        textImageItem2.thumbImages.add("http://file-cdn.datafans.net/temp/23.jpg_160x160.jpeg");


        LineLikeItem likeItem2_1 = new LineLikeItem();
        likeItem2_1.userId = 10086;
        likeItem2_1.userNick = "Allen";
        textImageItem2.likes.add(likeItem2_1);

        LineCommentItem commentItem2_1 = new LineCommentItem();
        commentItem2_1.userId = 10088;
        commentItem2_1.userNick = "奥巴马";
        commentItem2_1.text = "欢迎来到美利坚";
        commentItem2_1.replyUserId = 10086;
        commentItem2_1.replyUserNick = "习大大";
        textImageItem2.comments.add(commentItem2_1);

        LineCommentItem commentItem2_2 = new LineCommentItem();
        commentItem2_2.userId = 10010;
        commentItem2_2.userNick = "神雕侠侣";
        commentItem2_2.text = "大家好";
        textImageItem2.comments.add(commentItem2_2);


        addItem(textImageItem2);


        textImageItem3 = new TextImageLineItem();
        textImageItem3.itemId = 3;
        textImageItem3.itemType = LineItemType.TextImage;
        textImageItem3.userId = 10088;
        textImageItem3.userAvatar = "http://file-cdn.datafans.net/avatar/2.jpg";
        textImageItem3.userNick = "奥巴马";
        textImageItem3.title = "发表了";
        textImageItem3.text = "京东JD.COM-专业的综合网上购物商城";

        textImageItem3.srcImages.add("http://file-cdn.datafans.net/temp/21.jpg");

        textImageItem3.thumbImages.add("http://file-cdn.datafans.net/temp/21.jpg_480x270.jpeg");


        textImageItem3.width = 640;
        textImageItem3.height = 360;

        textImageItem3.location = "广州信息港";

        addItem(textImageItem3);
    }


    @Override
    protected String getCover(int width, int height) {
        String url = String.format("http://file-cdn.datafans.net/temp/12.jpg_%dx%d.jpeg", width, height);
        Log.e(Config.TAG, url);
        return url;
    }


    @Override
    protected String getUserAvatar(int width, int height) {
        return String.format("http://file-cdn.datafans.net/avatar/1.jpeg_%dx%d.jpeg", width, height);
    }


    @Override
    protected String getUserNick() {
        return "Allen";
    }

    @Override
    public void onRefresh() {
        super.onRefresh();

        onEnd();

    }


    @Override
    public void onLoadMore() {
        super.onLoadMore();


        addItem(textImageItem3);

        onEnd();

    }
}
