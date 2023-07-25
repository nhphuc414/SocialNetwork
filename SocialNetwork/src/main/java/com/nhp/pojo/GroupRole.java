/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhp.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ad
 */
@Entity
@Table(name = "group_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupRole.findAll", query = "SELECT g FROM GroupRole g"),
    @NamedQuery(name = "GroupRole.findById", query = "SELECT g FROM GroupRole g WHERE g.id = :id"),
    @NamedQuery(name = "GroupRole.findByName", query = "SELECT g FROM GroupRole g WHERE g.name = :name"),
    @NamedQuery(name = "GroupRole.findByCreatedDate", query = "SELECT g FROM GroupRole g WHERE g.createdDate = :createdDate"),
    @NamedQuery(name = "GroupRole.findByUpdatedDate", query = "SELECT g FROM GroupRole g WHERE g.updatedDate = :updatedDate")})
public class GroupRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupId", fetch = FetchType.LAZY)
    private Set<GroupUser> groupUserSet;

    public GroupRole() {
    }

    public GroupRole(Integer id) {
        this.id = id;
    }

    public GroupRole(Integer id, String name, Date createdDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @XmlTransient
    public Set<GroupUser> getGroupUserSet() {
        return groupUserSet;
    }

    public void setGroupUserSet(Set<GroupUser> groupUserSet) {
        this.groupUserSet = groupUserSet;
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
        if (!(object instanceof GroupRole)) {
            return false;
        }
        GroupRole other = (GroupRole) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nhp.pojo.GroupRole[ id=" + id + " ]";
    }
    
}
