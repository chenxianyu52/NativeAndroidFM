package com.xianyu.common.ui.bottom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.xianyu.androidlibrary.utils.DisplayUtil;
import com.xianyu.common.nav.AppConfig;
import com.xianyu.common.nav.Destination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AppBottomBar extends BottomNavigationView {
    private final BottomBar config;
    private final HashMap<String, BottomBarTab> configTab;

    public AppBottomBar(Context context) {
        this(context, null);
    }

    public AppBottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("RestrictedApi")
    public AppBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        config = AppConfig.getBottomBarConfig();
        configTab = AppConfig.getTabConfig();
        int[][] state = new int[2][];
        state[0] = new int[]{android.R.attr.state_selected};
        state[1] = new int[]{};
        int[] colors = new int[]{Color.parseColor(config.activeColor), Color.parseColor(config.inActiveColor)};
        ColorStateList stateList = new ColorStateList(state, colors);
        setItemTextColor(stateList);
        setItemIconTintList(stateList);
        //LABEL_VISIBILITY_LABELED:设置按钮的文本为一直显示模式
        //LABEL_VISIBILITY_AUTO:当按钮个数小于三个时一直显示，或者当按钮个数大于3个且小于5个时，被选中的那个按钮文本才会显示
        //LABEL_VISIBILITY_SELECTED：只有被选中的那个按钮的文本才会显示
        //LABEL_VISIBILITY_UNLABELED:所有的按钮文本都不显示
        setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        List<BottomBarTab> bottomBarTabList = resortBottomTabFromIndex(configTab);
        for (int i = 0; i < bottomBarTabList.size(); i++) {
            BottomBarTab tab = bottomBarTabList.get(i);
            if (!tab.enable) {
                continue;
            }
            int itemId = getItemId(tab.pageUrl);
            if (itemId < 0) {
                continue;
            }
            Log.i("cxy2", tab.enable + " , " + tab.pageUrl + " , " + " , " + tab.icon);
            MenuItem menuItem = getMenu().add(0, itemId, i, tab.title);
            int drawableId = getResources().getIdentifier(tab.icon, "drawable", context.getPackageName());
            menuItem.setIcon(getResources().getDrawable(drawableId));

            //此处给按钮icon设置大小
            int iconSize = DisplayUtil.INSTANCE.dip2px(tab.size);
            Log.i("cxy", tab.pageUrl + " , " + tab.index);
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
            itemView.setIconSize(iconSize);
            if (TextUtils.isEmpty(tab.title)) {
                int tintColor = TextUtils.isEmpty(tab.tintColor) ? Color.parseColor("#ff678f") : Color.parseColor(tab.tintColor);
                itemView.setIconTintList(ColorStateList.valueOf(tintColor));
                //禁止掉点按时 上下浮动的效果
                itemView.setShifting(false);
            }
        }
    }

    // 重组index，可能某个模块删除导致crash。
    private List<BottomBarTab> resortBottomTabFromIndex(HashMap<String, BottomBarTab> configTab) {
        BottomBarTab[] bottomBarTabs = new BottomBarTab[10];
        for (Map.Entry<String, BottomBarTab> tabEntry : configTab.entrySet()) {
            bottomBarTabs[Integer.parseInt(tabEntry.getKey())] = tabEntry.getValue();
        }
        List<BottomBarTab> resultList = new ArrayList<>(Arrays.asList(bottomBarTabs));
        resultList.removeIf(Objects::isNull);
        return resultList;
    }

    private int getItemId(String pageUrl) {
        Destination destination = AppConfig.getDestConfig().get(pageUrl);
        if (destination == null)
            return -1;
        return destination.id;
    }
}
