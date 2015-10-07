package net.datafans.android.timeline.controller;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.datafans.android.common.helper.DipHelper;
import net.datafans.android.common.widget.controller.TableViewController;
import net.datafans.android.common.widget.imageview.CommonImageView;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;
import net.datafans.android.timeline.R;
import net.datafans.android.timeline.adapter.BaseLineCellAdapter;
import net.datafans.android.timeline.adapter.CellAdapterManager;
import net.datafans.android.timeline.item.BaseLineItem;
import net.datafans.android.timeline.item.LineItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonganyun on 15/10/6.
 */
public abstract class TimelineViewController extends TableViewController<BaseLineItem> {


    private List<BaseLineItem> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        tableView.getAdapter().getListView().setSelector(new ColorDrawable(Color.TRANSPARENT));
        tableView.hideDivider();


        initHeaderView();
    }


    private void initHeaderView() {

        View header = getLayoutInflater().inflate(R.layout.header, null);
        tableView.getAdapter().getListView().addHeaderView(header);

        CommonImageView cover = (CommonImageView) header.findViewById(R.id.cover);


        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        cover.loadImage(getCover(width, DipHelper.dip2px(this, 200)));

        CommonImageView userAvatar = (CommonImageView) header.findViewById(R.id.userAvatar);
        userAvatar.loadImage(getUserAvatar(160, 160));

        TextView userNick = (TextView) header.findViewById(R.id.userNick);
        userNick.setText(getUserNick());
    }


    protected abstract String getCover(int width, int height);
    protected abstract String getUserAvatar(int width, int height);
    protected abstract String getUserNick();


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
        return adapter.getCell();
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


    private BaseLineCellAdapter getAdapter(int itemType) {
        CellAdapterManager manager = CellAdapterManager.sharedInstance();
        return manager.getAdapter(itemType);
    }


    protected void addItem(BaseLineItem item) {
        items.add(item);
    }


}

