package com.zebra.das.model.api;

import java.util.Set;

import com.zebra.constant.web.WebElementType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Cacheable
@Table(name = "principal")
public class Principal implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    private String name;

    @ElementCollection
    @CollectionTable(name = "principalRights")
    @Enumerated(EnumType.STRING)
    private Set<WebElementType> rights;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "principal", fetch = FetchType.LAZY)
    private List<User> users;

    public Principal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<WebElementType> getRights() {
        return rights;
    }

    public void setRights(Set<WebElementType> rights) {
        this.rights = rights;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
