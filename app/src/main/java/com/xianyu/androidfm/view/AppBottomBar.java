package com.xianyu.androidfm.view;

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
import com.xianyu.androidfm.AppConfig;
import com.xianyu.androidfm.R;
import com.xianyu.androidfm.model.BottomBar;
import com.xianyu.androidfm.model.Destination;
import com.xianyu.basemodule.utils.ScreenUtil;

import java.util.List;

public class AppBottomBar extends BottomNavigationView {
    private static int[] sIcons = new int[]{R.drawable.ic_home_black_24dp, R.drawable.ic_home_black_24dp,R.drawable.ic_home_black_24dp};

    private final BottomBar config;

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
        List<BottomBar.Tab> tabs = config.tabs;
        for (BottomBar.Tab tab : tabs) {
            if (!tab.enable) {
                continue;
            }
            int itemId = getItemId(tab.pageUrl);
            if (itemId < 0) {
                continue;
            }
            Log.i("cxy2",tab.enable + " , " + tab.pageUrl + " , ");
            MenuItem menuItem = getMenu().add(0, itemId, tab.index, tab.title);
            menuItem.setIcon(sIcons[tab.index]);
        }

        //此处给按钮icon设置大小
        for (BottomBar.Tab tab : config.tabs) {
            int iconSize = ScreenUtil.dp2Px(context,tab.size);
            Log.i("cxy",tab.pageUrl + " , " + tab.index);
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(tab.index);
            itemView.setIconSize(iconSize);
            if (TextUtils.isEmpty(tab.title)) {
                int tintColor = TextUtils.isEmpty(tab.tintColor) ? Color.parseColor("#ff678f") : Color.parseColor(tab.tintColor);
                itemView.setIconTintList(ColorStateList.valueOf(tintColor));
                //禁止掉点按时 上下浮动的效果
                itemView.setShifting(false);
            }
        }
    }

    private int getItemId(String pageUrl) {
        Destination destination = AppConfig.getDestConfig().get(pageUrl);
        if (destination == null)
            return -1;
        return destination.id;
    }
}
