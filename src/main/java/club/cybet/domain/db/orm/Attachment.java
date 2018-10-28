package club.cybet.domain.db.orm;

import club.cybet.utils.security.Encryption;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * cybet-api (club.cybet.domain.db.orm)
 * Created by: cybet
 * On: 23 Oct, 2018 10/23/18 12:05 PM
 **/
@Entity
@Table(name = "attachments",
        indexes = {
                @Index(name = "index_attachments_user_account_id", columnList = "user_account_id"),
                @Index(name = "index_attachments_attachment_type_id", columnList = "attachment_type_id"),
                @Index(name = "index_attachments_created", columnList = "created"),
                @Index(name = "index_attachments_updated", columnList = "updated"),
                @Index(name = "index_attachments_deleted", columnList = "deleted")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_user_attachment_type", columnNames = {
                        "user_account_id", "attachment_type_id"
                })
        }
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Attachment")
@DynamicUpdate
@DynamicInsert
public class Attachment {
    private Long id;
    private Long userAccountId;
    private Integer attachmentTypeId;
    private Integer identificationTypeId;
    private Long attachmentActualSize;
    private String attachmentPrettySize;
    private String docName;
    private String docPath;
    private String exposedPath;
    private Byte[] docBytea;
    private Timestamp created;
    private Timestamp updated;
    private String deleted;
    private UserAccount userAccountsByUserAccountId;
    private AttachmentType attachmentTypesByAttachmentTypeId;
    private IdentificationType identificationTypesByIdentificationTypeId;

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
    @Column(name = "user_account_id", nullable = true)
    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    @Basic
    @Column(name = "attachment_type_id", nullable = true)
    public Integer getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(Integer attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }

    @Basic
    @Column(name = "identification_type_id", nullable = false)
    public Integer getIdentificationTypeId(){
        return identificationTypeId;
    }

    public void setIdentificationTypeId(Integer identificationTypeId) {
        this.identificationTypeId = identificationTypeId;
    }

    @Basic
    @JsonIgnore
    @Column(name = "doc_path", nullable = false, length = 500)
    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    @Basic
    @Column(name = "attachment_actual_size", nullable = false)
    public Long getAttachmentActualSize() {
        return attachmentActualSize;
    }

    public void setAttachmentActualSize(Long attachmentActualSize) {
        this.attachmentActualSize = attachmentActualSize;
    }

    @Basic
    @Column(name = "attachment_actual_pretty_size", nullable = false)
    public String getAttachmentPrettySize() {
        return attachmentPrettySize;
    }

    public void setAttachmentPrettySize(String attachmentPrettySize) {
        this.attachmentPrettySize = attachmentPrettySize;
    }

    @Basic
    @Column(name = "doc_name", nullable = false)
    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    @Basic
    @Column(name = "doc_bytea", nullable = true)
    public Byte[] getDocBytea() {
        return docBytea;
    }

    public void setDocBytea(Byte[] docBytea) {
        this.docBytea = docBytea;
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

        Attachment that = (Attachment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userAccountId != null ? !userAccountId.equals(that.userAccountId) : that.userAccountId != null)
            return false;
        if (attachmentTypeId != null ? !attachmentTypeId.equals(that.attachmentTypeId) : that.attachmentTypeId != null)
            return false;
        if (docPath != null ? !docPath.equals(that.docPath) : that.docPath != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(docBytea, that.docBytea)) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userAccountId != null ? userAccountId.hashCode() : 0);
        result = 31 * result + (attachmentTypeId != null ? attachmentTypeId.hashCode() : 0);
        result = 31 * result + (docPath != null ? docPath.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(docBytea);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }

    @Transient
    public String getExposedPath() {

        if(docPath == null) return null;
        return Encryption.encrypto(docPath);
    }

    public void setExposedPath(String exposedPath) {
        this.exposedPath = exposedPath;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id", insertable = false, updatable = false)
    public UserAccount getUserAccountsByUserAccountId() {
        return userAccountsByUserAccountId;
    }

    public void setUserAccountsByUserAccountId(UserAccount userAccountsByUserAccountId) {
        this.userAccountsByUserAccountId = userAccountsByUserAccountId;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    public AttachmentType getAttachmentTypesByAttachmentTypeId() {
        return attachmentTypesByAttachmentTypeId;
    }

    public void setAttachmentTypesByAttachmentTypeId(AttachmentType attachmentTypesByAttachmentTypeId) {
        this.attachmentTypesByAttachmentTypeId = attachmentTypesByAttachmentTypeId;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identifiaction_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    public IdentificationType getIdentificationTypesByIdentificationTypeId() {
        return identificationTypesByIdentificationTypeId;
    }

    public void setIdentificationTypesByIdentificationTypeId(IdentificationType identificationTypesByIdentificationTypeId) {
        this.identificationTypesByIdentificationTypeId = identificationTypesByIdentificationTypeId;
    }
}
