package net.datafans.android.timeline.adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhonganyun on 15/10/06.
 */
public class CellAdapterManager {

    private static final CellAdapterManager manager = new CellAdapterManager();

    private Map<Integer, BaseLineCellAdapter> adapterMap = new HashMap<>();

    public static CellAdapterManager sharedInstance() {
        return manager;
    }

    public void registerAdapter(Integer type, BaseLineCellAdapter adapter) {
        adapterMap.put(type, adapter);
    }

    public BaseLineCellAdapter getAdapter(Integer type) {
        return adapterMap.get(type);
    }


}
