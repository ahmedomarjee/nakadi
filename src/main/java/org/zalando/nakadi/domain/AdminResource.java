package org.zalando.nakadi.domain;

import org.zalando.nakadi.plugin.api.authz.AuthorizationAttribute;
import org.zalando.nakadi.plugin.api.authz.AuthorizationService;
import org.zalando.nakadi.plugin.api.authz.Resource;

import java.util.List;
import java.util.Optional;

public class AdminResource implements Resource {

    public static final String ADMIN_RESOURCE = "nakadi";

    private final String name;
    private final AdminAuthorization etAuthorization;

    public AdminResource(final String name, final AdminAuthorization etAuthorization) {
        this.name = name;
        this.etAuthorization = etAuthorization;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return ADMIN_RESOURCE;
    }

    @Override
    public Optional<List<AuthorizationAttribute>> getAttributesForOperation(
            final AuthorizationService.Operation operation) {
        switch (operation) {
            case READ:
                return Optional.of(etAuthorization.getReaders());
            case WRITE:
                return Optional.of(etAuthorization.getWriters());
            case ADMIN:
                return Optional.of(etAuthorization.getAdmins());
            default:
                throw new IllegalArgumentException("Operation " + operation + " is not supported");
        }
    }

    public List<Permission> getPermissionsList() {
        return etAuthorization.toPermissionsList(name);
    }

    @Override
    public String toString() {
        return "AuthorizedResource{" + ADMIN_RESOURCE +"='" + name + "'}";
    }

}
