/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ad
 */
@Entity
@Table(name = "user_permission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserPermission.findAll", query = "SELECT u FROM UserPermission u"),
    @NamedQuery(name = "UserPermission.findById", query = "SELECT u FROM UserPermission u WHERE u.id = :id"),
    @NamedQuery(name = "UserPermission.findByCreatedDate", query = "SELECT u FROM UserPermission u WHERE u.createdDate = :createdDate"),
    @NamedQuery(name = "UserPermission.findByUpdatedTime", query = "SELECT u FROM UserPermission u WHERE u.updatedTime = :updatedTime"),
    @NamedQuery(name = "UserPermission.findByActive", query = "SELECT u FROM UserPermission u WHERE u.active = :active")})
public class UserPermission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private short active;
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Permission permissionId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;

    public UserPermission() {
    }

    public UserPermission(Integer id) {
        this.id = id;
    }

    public UserPermission(Integer id, Date createdDate, short active) {
        this.id = id;
        this.createdDate = createdDate;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    public Permission getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Permission permissionId) {
        this.permissionId = permissionId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserPermission)) {
            return false;
        }
        UserPermission other = (UserPermission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhp.pojo.UserPermission[ id=" + id + " ]";
    }
    
}
