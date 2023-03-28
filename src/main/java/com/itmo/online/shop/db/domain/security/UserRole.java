package com.itmo.online.shop.db.domain.security;

import com.itmo.online.shop.db.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_role")
public class UserRole {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "user_role_id")
	private Long userRoleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="role_id")
//	@Column(name = "role_id")	// todo do we need this?
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
//	@Column(name = "user_id")	// todo do we need this?
	private User user;

//	public UserRole() {
//
//	}

	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}

//	@Override
//	public boolean equals(Object o) {
//		if (o == this) return true;
//		if (!(o instanceof UserRole)) {
//			return false;
//		}
//		UserRole userRole = (UserRole) o;
//		return userRole.getUserRoleId().equals(userRoleId)
//				&& userRole.getRole().equals(role)
//				&& userRole.getUser().equals(user);
//	}
//
//	@Override
//	public int hashCode() {
//		int result = 17;
//		result = 31 * result + userRoleId.hashCode();
//		result = 31 * result + role.hashCode();
//		result = 31 * result + user.hashCode();
//		return result;
//	}

//	public Long getUserRoleId() {
//		return userRoleId;
//	}
//
//	public void setUserRoleId(Long userRoleId) {
//		this.userRoleId = userRoleId;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	public Role getRole() {
//		return role;
//	}
//
//	public void setRole(Role role) {
//		this.role = role;
//	}
}
