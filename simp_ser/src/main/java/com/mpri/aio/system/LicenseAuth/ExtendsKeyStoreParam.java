package com.mpri.aio.system.LicenseAuth;

import de.schlichtherle.license.KeyStoreParam;

import java.io.IOException;
import java.io.InputStream;

public class ExtendsKeyStoreParam implements KeyStoreParam {

    private final String alias, storePwd, keyPwd;
    private final Class clazz;
    private final InputStream resource;

    public ExtendsKeyStoreParam(final Class clazz, final InputStream resource, final String alias, final String storePwd, final String keyPwd) {
        if (null == clazz || null == resource || null ==alias  || null ==storePwd)
            throw new NullPointerException();
        this.clazz = clazz;
        this.resource=resource;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }

    @Override
    public InputStream getStream() throws IOException {
        return resource;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePwd;
    }

    @Override
    public String getKeyPwd() {
        return keyPwd;
    }

    public final boolean equals(final Object object) {
        if (!(object instanceof ExtendsKeyStoreParam))
            return false;
        final ExtendsKeyStoreParam that = (ExtendsKeyStoreParam) object;
        return this.clazz.equals(that.clazz)
                && this.resource.equals(that.resource)
                && this.getAlias().equals(that.getAlias());
    }

    /**
     * Returns a hash code which is consistent with {@link #equals(Object)}.
     *
     * @return A hash code which is consistent with {@link #equals(Object)}.
     */
    public final int hashCode() {
        int c = 17;
        c = 37 * c + this.clazz.hashCode();
        c = 37 * c + this.resource.hashCode();
        c = 37 * c + this.getAlias().hashCode();
        return c;
    }
}
