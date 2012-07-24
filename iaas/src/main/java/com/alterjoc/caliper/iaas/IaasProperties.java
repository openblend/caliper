package com.alterjoc.caliper.iaas;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Matej Lazar
 */
public class IaasProperties {
    private String provider;
    private String providerUrl;
    private String imageId;
    private String userName;
    private String password;
    private String keyPairName;
    private String[] securityGroups;
    private String group;

    private static final Logger log = Logger.getLogger(IaasProperties.class.getName());

    private IaasProperties() throws IOException {

        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("iaas.properties");
            prop.load(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        setProperties(prop);
    }

    private void setProperties(Properties prop) {
        this.provider = prop.getProperty("provider");
        this.providerUrl = prop.getProperty("provider-url");
        this.imageId = prop.getProperty("image-id");
        this.userName = prop.getProperty("username");
        this.password = prop.getProperty("password");

        this.keyPairName = prop.getProperty("key-pair-name");
        this.securityGroups = prop.getProperty("security-groups").split(":");
        this.group = prop.getProperty("group");
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

    public static IaasProperties getInstance() {
        IaasProperties iaasProp;
        try {
            iaasProp = new IaasProperties();
            if (iaasProp.isValid()) {
                return iaasProp;
            } else {
                log.warning("Iaas properties are not complete.");
            }
        } catch (IOException e) {
            log.warning("Cannot read iaas properties.");
        }
        return null;
    }

    private boolean isValid() {
        if (provider == null || provider.equals("")) {
            return false;
        } else if (providerUrl == null || providerUrl.equals("")) {
            return false;
        } else if (imageId == null || imageId.equals("")) {
            return false;
        } else if (userName == null || userName.equals("")) {
            return false;
        } else if (password == null || password.equals("")) {
            return false;
        }
        return true;
    }
}
