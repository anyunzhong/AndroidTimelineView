package net.datafans.android.timeline.controller;

import android.os.Bundle;

import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;
import net.datafans.android.timeline.adapter.user.BaseUserLineCellAdapter;
import net.datafans.android.timeline.adapter.user.TextImageUserLineCellAdapter;
import net.datafans.android.timeline.adapter.user.UserCellAdapterManager;
import net.datafans.android.timeline.item.user.BaseUserLineItem;
import net.datafans.android.timeline.item.user.UserLineItemType;
import net.datafans.android.timeline.view.user.BaseUserLineCell;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhonganyun on 15/10/6.
 */
public abstract class UserTimelineViewController extends BaseTimelineViewController<BaseUserLineItem> implements BaseUserLineCell.Delegate {

    private List<BaseUserLineItem> items = new ArrayList<>();

    private int currentYear;

    private int currentMonth;

    private int currentDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initAdapters();

        super.onCreate(savedInstanceState);

    }


    private void initAdapters(){
        UserCellAdapterManager manager = UserCellAdapterManager.sharedInstance();
        TextImageUserLineCellAdapter imageLineCellAdapter = new TextImageUserLineCellAdapter();
        imageLineCellAdapter.setContext(this);
        manager.registerAdapter(UserLineItemType.TextImage, imageLineCellAdapter);
    }


    @Override
    protected RefreshControlType getRefreshControlType() {

        return RefreshControlType.BGANormal;
    }

    @Override
    public int getRows(int section) {

        return items.size();
    }

    @Override
    public TableViewCell<BaseUserLineItem> getTableViewCell(int section, int row) {

        BaseUserLineItem item = items.get(row);
        BaseUserLineCellAdapter adapter = getAdapter(item.itemType);
        BaseUserLineCell cell = (BaseUserLineCell) adapter.getCell();
        cell.setDelegate(this);
        return cell;
    }


    @Override
    public int getItemViewType(int section, int row) {

        BaseUserLineItem item = getEntity(section, row);
        if (item.itemType == UserLineItemType.TextImage)
            return 0;
        return 100000;
    }

    @Override
    public int getItemViewTypeCount() {

        return 1;
    }

    @Override
    public BaseUserLineItem getEntity(int section, int row) {
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


    private BaseUserLineCellAdapter getAdapter(int itemType) {
        UserCellAdapterManager manager = UserCellAdapterManager.sharedInstance();
        return manager.getAdapter(itemType);
    }


    protected void addItem(BaseUserLineItem item) {
        items.add(item);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(item.ts));

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)-1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        item.year = year;
        item.month = month;
        item.day = day;

        if (currentYear == year && currentMonth == month && currentDay == day){
            item.isShowTime = false;
        }else{
            item.isShowTime = true;
        }

        currentYear = year;
        currentMonth = month;
        currentDay = day;
    }

    @Override
    protected void onClickHeaderUserAvatar() {

    }
}

