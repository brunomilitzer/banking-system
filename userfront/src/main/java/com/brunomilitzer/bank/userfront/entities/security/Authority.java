package com.brunomilitzer.bank.userfront.entities.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority  implements GrantedAuthority {

    private static final long serialVersionUID = 7887559691042623893L;

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
