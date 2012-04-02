package com.alterjoc.caliper.iaas;

/**
 * @author Matej Lazar
 */
public class IaasProperties {
    private String provider;
    private String providerUrl;
    private String imageId;
    private String userName;
    private String password;
    private String keyPairName = "caliperKey";
    private String[] securityGroups = new String[]{"default"};
    private String group = "caliper";

    public IaasProperties(String provider, String providerUrl, String imageId, String userName, String password) {
        super();
        this.provider = provider;
        this.providerUrl = providerUrl;
        this.imageId = imageId;
        this.userName = userName;
        this.password = password;
    }

    public IaasProperties(String provider, String providerUrl, String imageId, String userName, String password, String keyPairName, String[] securityGroups, String group) {
        super();
        this.provider = provider;
        this.providerUrl = providerUrl;
        this.imageId = imageId;
        this.userName = userName;
        this.password = password;
        this.keyPairName = keyPairName;
        this.securityGroups = securityGroups;
        this.group = group;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderUrl() {
        return providerUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getKeyPairName() {
        return keyPairName;
    }

    public String[] getSecurityGroups() {
        return securityGroups;
    }

    public String getGroup() {
        return group ;
    }
}
