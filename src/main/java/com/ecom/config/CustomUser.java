//package com.ecom.config;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.ecom.model.UserDtls;
//
//public class CustomUser implements UserDetails{
//	
//	private UserDtls user;
//	
//
//	public CustomUser(UserDtls user) {
//		super();
//		this.user = user;
//	}
//
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		SimpleGrantedAuthority authority=new SimpleGrantedAuthority(user.getRole());
//		return Arrays.asList(authority);
//	}
//
//
//	@Override
//	public String getPassword() {
//		
//		return getPassword();
//	}
//
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return getUsername();
//	}
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return user.getAccountNonLocked();
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return user.getIsEnable();
//	}
//
//
//
//	
//
//}


package com.ecom.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecom.model.UserDtls;

public class CustomUser implements UserDetails {

    private UserDtls user;

    public CustomUser(UserDtls user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // fixed
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // or user.getUsername() if you have that field
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
	@Override
	public boolean isAccountNonLocked() {
		return user.getAccountNonLocked();
	}

//    @Override
//    public boolean isAccountNonLocked() {
//        // Null-safe handling to avoid NPE
//        return user.getAccountNonLocked() != null ? user.getAccountNonLocked() : true;
//    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
	@Override
	public boolean isEnabled() {
		return user.getIsEnable();
	}

//    @Override
//    public boolean isEnabled() {
//        return user.getIsEnable() != null ? user.getIsEnable() : true;
//    }
}
