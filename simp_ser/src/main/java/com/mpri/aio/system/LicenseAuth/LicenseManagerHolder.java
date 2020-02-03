package com.mpri.aio.system.LicenseAuth;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=100)
public class LicenseManagerHolder {

    private LicenseManager licenseManager;

    public  synchronized LicenseManager getLicenseManager(LicenseParam licenseParams) {
        if (licenseManager == null) {
            licenseManager = new LicenseManager(licenseParams);
        }
        return licenseManager;
    }
}
