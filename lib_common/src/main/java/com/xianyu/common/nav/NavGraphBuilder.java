package com.xianyu.common.nav;

import android.content.ComponentName;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;

import com.xianyu.androidlibrary.AppGlobals;

import java.util.HashMap;
import java.util.Optional;


public class NavGraphBuilder {
    public static void build(FragmentActivity activity, NavController controller, int containerId) {
        NavigatorProvider provider = controller.getNavigatorProvider();
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        //FragmentNavigator fragmentNavigator = provider.getNavigator(FragmentNavigator.class);
        FixFragmentNavigator fragmentNavigator = new FixFragmentNavigator(activity, activity.getSupportFragmentManager(), containerId);
        provider.addNavigator(fragmentNavigator);
        ActivityNavigator activityNavigator = provider.getNavigator(ActivityNavigator.class);
        HashMap<String, Destination> destConfig = AppConfig.getDestConfig();
        for (Destination node : destConfig.values()) {
            if (node.isFragment) {
                FragmentNavigator.Destination destination = fragmentNavigator.createDestination();
                destination.setId(node.id);
                destination.setClassName(node.className);
                destination.addDeepLink(node.pageUrl);
                navGraph.addDestination(destination);
            } else {
                ActivityNavigator.Destination destination = activityNavigator.createDestination();
                destination.setId(node.id);
                destination.setComponentName(new ComponentName(AppGlobals.getApplication().getPackageName(), node.className));
                destination.addDeepLink(node.pageUrl);
                navGraph.addDestination(destination);
            }

            if (node.asStarter) {
                navGraph.setStartDestination(node.id);
            }
        }

        // 说明starter的被移除了，就随机默认
        if(navGraph.getStartDestination() == 0){
            if (destConfig.values().size() > 0) {
                Optional<Destination> optionalDestination = destConfig.values().stream().findFirst();
                optionalDestination.ifPresent(destination -> navGraph.setStartDestination(destination.id));
            }
        }

        if (destConfig.values().size() > 0) {
            controller.setGraph(navGraph);
        }
    }
}
