package net.datafans.android.timeline.adapter;

import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.timeline.R;
import net.datafans.android.timeline.view.TextImageLineCell;

/**
 * Created by zhonganyun on 15/10/6.
 */
public class TextImageLineCellAdapter extends BaseLineCellAdapter {


    @Override
    public TableViewCell getCell() {
        return new TextImageLineCell(R.layout.base_cell, getContext());
    }
}
