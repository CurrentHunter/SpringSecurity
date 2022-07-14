package com.example.security.secconfig;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;
import static com.example.security.secconfig.UserPermissions.*;



public enum Roles {
	STUDENT(Sets.newHashSet()),
	ADMIN_TRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ)),
	ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE));

private final Set<UserPermissions> permissions;

	Roles(Set<UserPermissions> permissions) {
		this.permissions = permissions;
	}

public Set<UserPermissions> getPermissions() {
	return permissions;
}

public Set<SimpleGrantedAuthority> getGrantedAuthority(){
	Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toSet());
	permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
	return permissions;
}

}
