package club.cybet.domain.db.orm;

import club.cybet.utils.Constants;
import club.cybet.utils.DateTime;
import club.cybet.utils.security.AccessTokenGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "access_tokens",
        indexes = {
                @Index(name = "index_access_token_access_token", columnList = "access_token"),
                @Index(name = "index_access_token_type", columnList = "type"),
                @Index(name = "index_access_token_user_id", columnList = "user_account_id")
            },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_access_token_access_token", columnNames = "access_token")
            }
        )
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "AccessToken")
@DynamicUpdate
@DynamicInsert
public class AccessToken implements Serializable {
    private Long id;
    private String accessToken;
    private Timestamp dateCreated;
    private Long expiry;
    private String timeUnit;
    private String type;
    private String deleted;
    private Long userAccountId;
    private UserAccount userAccountById;
    private UserAccount user;

    public AccessToken(Long userAccountId, String type, Long expiry, String timeUnit) {
        this();
        this.userAccountId = userAccountId;
        this.type = type;
        this.expiry = expiry;
        this.timeUnit = timeUnit.toLowerCase();
    }

    public AccessToken(Long userAccountId){
        this();
        this.userAccountId = userAccountId;
    }

    public AccessToken(){
        this.deleted = "NO";
        this.type = "Bearer";
        this.timeUnit = "seconds";
        this.expiry = Constants.getTokenExpiry();
        this.dateCreated = DateTime.getCurrentSqlTimestamp();
        this.accessToken = AccessTokenGenerator.getAccessToken();

    }

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, precision = 0)
    public Long getAccessTokenId() {
        return id;
    }

    public void setAccessTokenId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "access_token", nullable = false, length = 255)
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Basic
    @Column(name = "deleted", nullable = false)
    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "date_created", nullable = true)
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "expiry", nullable = false)
    public Long getExpiry() {
        return expiry;
    }

    public void setExpiry(Long expiry) {
        this.expiry = expiry;
    }

    @Basic
    @Column(name = "time_unit", nullable = false, length = 10)
    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 50)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "user_account_id", nullable = false)
    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessToken that = (AccessToken) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (accessToken != null ? !accessToken.equals(that.accessToken) : that.accessToken != null) return false;
        if (dateCreated != null ? !dateCreated.equals(that.dateCreated) : that.dateCreated != null) return false;
        if (expiry != null ? !expiry.equals(that.expiry) : that.expiry != null) return false;
        if (timeUnit != null ? !timeUnit.equals(that.timeUnit) : that.timeUnit != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (userAccountId != null ? !userAccountId.equals(that.userAccountId) : that.userAccountId != null)
            return false;
        return userAccountById != null ? userAccountById.equals(that.userAccountById) : that.userAccountById == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (expiry != null ? expiry.hashCode() : 0);
        result = 31 * result + (timeUnit != null ? timeUnit.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (userAccountId != null ? userAccountId.hashCode() : 0);
        result = 31 * result + (userAccountById != null ? userAccountById.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id",
            foreignKey = @ForeignKey(name = "fkey_access_token_user_id"),
            referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UserAccount getUserAccountById() {
        return userAccountById;
    }

    public void setUserAccountById(UserAccount userAccountById) {
        this.userAccountById = userAccountById;
    }

    @Transient
    public UserAccount getUser() {
        return user;
    }

    public AccessToken setUser(UserAccount user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                ", dateCreated=" + dateCreated +
                ", expiry=" + expiry +
                ", timeUnit='" + timeUnit + '\'' +
                ", type='" + type + '\'' +
                ", userAccountId=" + user +
                '}';
    }
}
