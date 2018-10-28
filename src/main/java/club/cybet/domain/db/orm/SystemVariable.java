package club.cybet.domain.db.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * cybet-api (club.cybet.domain.orm)
 * Created by: cybet
 * On: 14 Aug, 2018 8/14/18 11:24 PM
 **/
@Entity
@Table(name = "system_variables",
        indexes = {
                @Index(name = "index_system_variables_deleted", columnList = "deleted"),
                @Index(name = "index_system_variables_created", columnList = "created"),
                @Index(name = "index_system_variables_updated", columnList = "updated"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_system_variables_identifier", columnNames = "identifier")
        }
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SystemVariable")
@DynamicUpdate
@DynamicInsert
public class SystemVariable implements Serializable {
    private Long id;
    private String deleted;
    private String key;
    private String value;
    private Timestamp created;
    private Timestamp updated;
    private Long createdById;
    private Long updatedById;
    private String isSystemProtected;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @JsonIgnore
    @Column(name = "deleted", nullable = false)
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "identifier", nullable = false, length = 256)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Lob
    @Basic
    @Column(name = "value", nullable = true)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    @Basic
    @Column(name = "created_by_id", nullable = true)
    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    @Basic
    @Column(name = "updated_by_id", nullable = true)
    public Long getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
    }

    @Basic
    @JsonIgnore
    @Column(name = "is_system_protected", nullable = true)
    public String getSystemProtected() {
        return isSystemProtected == null ? "NO" : isSystemProtected;
    }

    public void setSystemProtected(String systemProtected) {
        isSystemProtected = systemProtected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemVariable that = (SystemVariable) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        return result;
    }
}
