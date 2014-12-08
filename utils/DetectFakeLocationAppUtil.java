package com.wink.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.wink.model.AndroidApp;

public class DetectFakeLocationAppUtil {

	static Context mContext;
	public static List<AndroidApp> mForbiddenPackages = new ArrayList<AndroidApp>();

	public static List<AndroidApp> hasForbiddenApps(Context context,
			List<AndroidApp> fakeAppList) {
		mContext = context;

		List<AndroidApp> installedList = getInstalledApps();
		List<AndroidApp> fakeApplist = new ArrayList<AndroidApp>();

		for (AndroidApp app : fakeAppList) {
			if (installedList.contains(app)){
				fakeApplist.add(app);
			}
		}
		return fakeApplist;
	}

	private static List<AndroidApp> getInstalledApps() {

		List<AndroidApp> appList = new ArrayList<AndroidApp>();

		List<PackageInfo> packs = mContext.getPackageManager()
				.getInstalledPackages(0);

		for (int i = 0; i < packs.size(); i++) {
			PackageInfo p = packs.get(i);
			if (p.versionName == null) {
				continue;
			}
			appList.add(new AndroidApp(p.packageName, p.applicationInfo
					.loadLabel(mContext.getPackageManager()).toString()));

		}
		return appList;
	}

	private static List<AndroidApp> getFakeApps() {

		List<AndroidApp> appList = new ArrayList<AndroidApp>();

		List<PackageInfo> packs = mContext.getPackageManager()
				.getInstalledPackages(0);

		for (int i = 0; i < 4; i++) {
			PackageInfo p = packs.get(i);
			if (p.versionName == null) {
				continue;
			}
			appList.add(new AndroidApp(p.packageName, p.applicationInfo
					.loadLabel(mContext.getPackageManager()).toString()));

		}
		return appList;
	}

}
