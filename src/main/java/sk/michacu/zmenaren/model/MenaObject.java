package sk.michacu.zmenaren.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "mena")
public class MenaObject {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "currName")
    private String currName;
    @Column(name = "icon")
    private String icon;
    @Column(name = "description")
    private String description;
    @Column(name = "active")
    private boolean active;
    @Column(name = "created_at")
    private Date created_at;
    @Column(name = "updated_at")
    private Date updated_at;

    public MenaObject() {}

    public MenaObject(Long id, String currName, String icon, String description, boolean active, Date created_at, Date updated_at) {
        this.id = id;
        this.currName = currName;
        this.icon = icon;
        this.description = description;
        this.active = active;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrName() {
        return currName;
    }

    public void setCurrName(String currName) {
        this.currName = currName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
