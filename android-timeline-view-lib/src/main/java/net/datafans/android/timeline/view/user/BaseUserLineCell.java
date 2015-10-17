package net.datafans.android.timeline.view.user;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.timeline.R;
import net.datafans.android.timeline.item.user.BaseUserLineItem;

/**
 * Created by zhonganyun on 15/10/17.
 */
public class BaseUserLineCell extends TableViewCell<BaseUserLineItem> {


    private View topSpace;
    private TextView timeDayView;
    private TextView timeMonthView;
    protected LinearLayout contentView;


    private Delegate delegate;

    private BaseUserLineItem item;


    public BaseUserLineCell(int layout, Context context) {
        super(layout, context);

        topSpace = cell.findViewById(R.id.topSpace);

        timeDayView = (TextView) cell.findViewById(R.id.timeDay);

        timeMonthView = (TextView) cell.findViewById(R.id.timeMonth);

        contentView = (LinearLayout) cell.findViewById(R.id.content);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (delegate == null) return;
                delegate.onClickItem(item);
            }
        });


    }

    @Override
    protected void refresh(BaseUserLineItem item) {

        this.item = item;

        if (item.isShowTime) {
            topSpace.setVisibility(View.VISIBLE);
            if (item.day < 10) {
                timeDayView.setText(String.format("0%d", item.day));
            } else {
                timeDayView.setText(String.valueOf(item.day));
            }

            timeMonthView.setText(String.format("%d月", item.month));
        } else {
            topSpace.setVisibility(View.GONE);

            timeDayView.setText("");
            timeMonthView.setText("");
        }
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }


    public interface Delegate {
        void onClickItem(BaseUserLineItem item);
    }
}
