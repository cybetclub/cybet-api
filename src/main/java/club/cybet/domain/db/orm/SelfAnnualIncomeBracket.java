package club.cybet.domain.db.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * cybet-api (club.cybet.domain.db.orm)
 * Created by: cybet
 * On: 23 Oct, 2018 10/23/18 12:05 PM
 **/
@Entity
@Table(name = "self_annual_income_brackets",
        indexes = {
                @Index(name = "self_annual_income_bracket_created", columnList = "created"),
                @Index(name = "self_annual_income_bracket_updated", columnList = "updated"),
                @Index(name = "self_annual_income_bracket_deleted", columnList = "deleted")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_self_annual_income_bracket_name", columnNames = "self_annual_income_bracket_name")
        }
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "SelfAnnualIncomeBracket")
@DynamicUpdate
@DynamicInsert
public class SelfAnnualIncomeBracket {
    private Integer id;
    private String selfAnnualIncomeBracketName;
    private String description;
    private Timestamp created;
    private Timestamp updated;
    private String deleted;

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
    @Column(name = "self_annual_income_bracket_name", nullable = false, length = 150)
    public String getSelfAnnualIncomeBracketName() {
        return selfAnnualIncomeBracketName;
    }

    public void setSelfAnnualIncomeBracketName(String selfAnnualIncomeBracketName) {
        this.selfAnnualIncomeBracketName = selfAnnualIncomeBracketName;
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
    @Column(name = "updated", nullable = true)
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

        SelfAnnualIncomeBracket that = (SelfAnnualIncomeBracket) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (selfAnnualIncomeBracketName != null ? !selfAnnualIncomeBracketName.equals(that.selfAnnualIncomeBracketName) : that.selfAnnualIncomeBracketName != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (selfAnnualIncomeBracketName != null ? selfAnnualIncomeBracketName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }
}
