package net.datafans.android.timeline.adapter;

import android.content.Context;

import net.datafans.android.common.widget.table.TableViewCell;

/**
 * Created by zhonganyun on 15/10/6.
 */
public abstract class BaseLineCellAdapter {

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract TableViewCell getCell();
}
