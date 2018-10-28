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
 * cybet-api (club.cybet.domain.db.orm)
 * Created by: cybet
 * On: 23 Oct, 2018 10/23/18 12:05 PM
 **/
@Entity
@Table(name = "identification_types",
        indexes = {
                @Index(name = "identification_types_created", columnList = "created"),
                @Index(name = "identification_types_updated", columnList = "updated"),
                @Index(name = "identification_types_deleted", columnList = "deleted")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_identification_types_name_and_code", columnNames = {
                        "identification_name", "identification_code"
                })
        }
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "IdentificationTypes")
@DynamicUpdate
@DynamicInsert
public class IdentificationType {
    private Integer id;
    private String identificationCode;
    private String identificationName;
    private Timestamp created;
    private Timestamp updated;
    private String deleted;
    private Collection<Attachment> attachmentsById;

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
    @Column(name = "identification_code", nullable = false, length = 50)
    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    @Basic
    @Column(name = "identification_name", nullable = false, length = 150)
    public String getIdentificationName() {
        return identificationName;
    }

    public void setIdentificationName(String identificationName) {
        this.identificationName = identificationName;
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

        IdentificationType that = (IdentificationType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (identificationCode != null ? !identificationCode.equals(that.identificationCode) : that.identificationCode != null)
            return false;
        if (identificationName != null ? !identificationName.equals(that.identificationName) : that.identificationName != null)
            return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (identificationCode != null ? identificationCode.hashCode() : 0);
        result = 31 * result + (identificationName != null ? identificationName.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "identificationTypesByIdentificationTypeId", fetch = FetchType.LAZY)
    public Collection<Attachment> getAttachmentsById() {
        return attachmentsById;
    }

    public void setAttachmentsById(Collection<Attachment> attachmentsById) {
        this.attachmentsById = attachmentsById;
    }
}
