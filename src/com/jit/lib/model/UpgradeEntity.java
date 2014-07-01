package com.jit.lib.model;

import com.google.gson.annotations.SerializedName;

public class UpgradeEntity {
	@SerializedName(value = "AppID")
	private int AppID;
	@SerializedName(value = "AppCode")
	private String AppCode;
	@SerializedName(value = "Description")
	private String Description;
	@SerializedName(value = "Version")
	private String Version;
	@SerializedName(value = "PackageUrl")
	private String PackageUrl;
	
	public int getAppID() {
		return AppID;
	}
	public void setAppID(int appID) {
		AppID = appID;
	}
	public String getAppCode() {
		return AppCode;
	}
	public void setAppCode(String appCode) {
		AppCode = appCode;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getPackageUrl() {
		return PackageUrl;
	}
	public void setPackageUrl(String packageUrl) {
		PackageUrl = packageUrl;
	}

    @Override
    public String toString() {
        return "UpgradeEntity{" +
                "AppID=" + AppID +
                ", AppCode=" + AppCode +
                ", Description='" + Description + '\'' +
                ", Version=" + Version +
                ", PackageUrl='" + PackageUrl + '\'' +
                '}';
    }
}
