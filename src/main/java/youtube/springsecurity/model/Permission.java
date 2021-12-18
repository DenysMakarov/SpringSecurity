package youtube.springsecurity.model;

import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public enum Permission {
    DEVELOPER_READ("developers:read"),
    DEVELOPER_WRITE("developers:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
