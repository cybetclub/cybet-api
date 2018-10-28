package club.cybet.domain.db.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * cybet-api (club.cybet.domain.orm)
 * Created by: cybet
 * On: 14 Aug, 2018 8/14/18 11:24 PM
 **/
@Entity
@Table(name = "user_account_types",
        indexes = {
                @Index(name = "index_user_account_types_created", columnList = "created"),
                @Index(name = "index_user_account_types_updated", columnList = "updated"),
                @Index(name = "index_user_account_types_deleted", columnList = "deleted")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_user_account_types_units_ac_type_name", columnNames = "ac_type_name")
        }
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "UserAccountType")
@DynamicUpdate
@DynamicInsert
public class UserAccountType {
    private Integer id;
    private String acTypeName;
    private String description;
    private String deleted;
    private Timestamp created;
    private Timestamp updated;
    private Collection<UserAccount> userAccountsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ac_type_name", nullable = false, length = 256)
    public String getAcTypeName() {
        return acTypeName;
    }

    public void setAcTypeName(String acTypeName) {
        this.acTypeName = acTypeName;
    }

    @Lob
    @Basic
    @Column(name = "description", nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @JsonIgnore
    @Column(name = "deleted", nullable = false, length = 3)
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccountType that = (UserAccountType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (acTypeName != null ? !acTypeName.equals(that.acTypeName) : that.acTypeName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (acTypeName != null ? acTypeName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "userAccountTypesByUserAccountTypeId", fetch = FetchType.LAZY)
    public Collection<UserAccount> getUserAccountsById() {
        return userAccountsById;
    }

    public void setUserAccountsById(Collection<UserAccount> userAccountsById) {
        this.userAccountsById = userAccountsById;
    }
}
