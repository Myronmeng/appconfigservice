package com.myron.codetest.app.configuration.appconfigservice.JPA;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class AppConfigIdentity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6349779031503208617L;

	@NotNull
	@Column(name="appcode")
    private String appCode;

	@NotNull
	@Column(name="version")
    private String version;    

	
	public AppConfigIdentity() {
		super();
	}

	public AppConfigIdentity(String appCode, String version) {
		super();
		this.appCode = appCode;
		this.version = version;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppConfigIdentity that = (AppConfigIdentity) o;

        if (!appCode.equals(that.appCode)) return false;
        return version.equals(that.version);
    }

    @Override
    public int hashCode() {
        int result = appCode.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }

	@Override
	public String toString() {
		return "AppConfigIdentity [appCode=" + appCode + ", version=" + version + "]";
	}
    
}