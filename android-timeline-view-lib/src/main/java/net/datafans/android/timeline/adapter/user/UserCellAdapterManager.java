package net.datafans.android.timeline.adapter.user;

import net.datafans.android.timeline.adapter.BaseLineCellAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhonganyun on 15/10/06.
 */
public class UserCellAdapterManager {

    private static final UserCellAdapterManager manager = new UserCellAdapterManager();

    private Map<Integer, BaseUserLineCellAdapter> adapterMap = new HashMap<>();

    public static UserCellAdapterManager sharedInstance() {
        return manager;
    }

    public void registerAdapter(Integer type, BaseUserLineCellAdapter adapter) {
        adapterMap.put(type, adapter);
    }

    public BaseUserLineCellAdapter getAdapter(Integer type) {
        return adapterMap.get(type);
    }


}
