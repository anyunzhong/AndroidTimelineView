package net.datafans.android.timeline.adapter.user;

import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.timeline.R;
import net.datafans.android.timeline.adapter.BaseLineCellAdapter;
import net.datafans.android.timeline.view.TextImageLineCell;
import net.datafans.android.timeline.view.user.TextImageUserLineCell;

/**
 * Created by zhonganyun on 15/10/6.
 */
public class TextImageUserLineCellAdapter extends BaseUserLineCellAdapter {


    @Override
    public TableViewCell getCell() {
        return new TextImageUserLineCell(R.layout.base_user_cell, getContext());
    }
}
