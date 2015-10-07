package net.datafans.android.timeline.controller;

import android.util.Log;

import net.datafans.android.common.widget.controller.TableViewController;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;
import net.datafans.android.timeline.adapter.BaseLineCellAdapter;
import net.datafans.android.timeline.adapter.CellAdapterManager;
import net.datafans.android.timeline.item.BaseLineItem;
import net.datafans.android.timeline.item.LineItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonganyun on 15/10/6.
 */
public class TimelineViewController extends TableViewController<BaseLineItem> {


    private List<BaseLineItem> items = new ArrayList<>();

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


    protected void addItem(BaseLineItem item){
        items.add(item);
    }


}

