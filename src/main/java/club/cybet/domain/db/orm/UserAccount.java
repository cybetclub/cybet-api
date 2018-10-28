package club.cybet.domain.db.orm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * cybet-api (club.cybet.domain.db.orm)
 * Created by: cybet
 * On: 23 Oct, 2018 10/23/18 12:05 PM
 **/
@Entity
@Table(name = "user_accounts",
        indexes = {
                @Index(name = "user_accounts_created", columnList = "created"),
                @Index(name = "user_accounts_updated", columnList = "updated"),
                @Index(name = "user_accounts_deleted", columnList = "deleted")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_user_accounts_email_address", columnNames = "email_address")
        }
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "UserAccount")
@DynamicUpdate
@DynamicInsert
public class UserAccount {
    private Long id;
    private String firstName;
    private String lastName;
    private String otherNames;
    private String emailAddress;
    private String ethereumWalletAddress;
    private String userPassword;
    private String gender;
    private Timestamp dateOfBirth;
    private String mobileNumber;
    private String countryOfResidence;
    private String cityOfResidence;
    private String stateOrProvinceOfResidence;
    private Integer postalCode;
    private Integer userAccountTypeId;
    private Integer boxNumber;
    private String streetOrResidenceAddress;
    private String identificationNumbers;
    private String idIssuingCountry;
    private String displayPicture;
    private Date idExpirationDate;
    private Integer employmentStatusId;
    private Integer employmentIndustryId;
    private Integer jobTitleId;
    private Integer sourceOfFundsId;
    private Integer selfAnnualIncomeBracketId;
    private Timestamp created;
    private Timestamp updated;
    private String deleted;
    private String activated;
    private String completedKyc;
    private UserAccountType userAccountTypesByUserAccountTypeId;
    private Collection<AccessToken> accessTokensByUserAccountId;
    private Collection<Attachment> attachmentsById;

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
    @Column(name = "first_name", nullable = true, length = 150)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 150)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "other_names", nullable = true, length = 300)
    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    @Basic
    @Column(name = "email_address", nullable = false, length = 150)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Column(name = "ethereum_wallet_address", nullable = true, length = 100)
    public String getEthereumWalletAddress() {
        return ethereumWalletAddress;
    }

    public void setEthereumWalletAddress(String ethereumWalletAddress) {
        this.ethereumWalletAddress = ethereumWalletAddress;
    }

    @Basic
    @JsonIgnore
    @JsonProperty(value = "userPassword")
    @Column(name = "user_password", nullable = false, length = 150)
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "user_account_type_id", nullable = false)
    public Integer getUserAccountTypeId() {
        return userAccountTypeId == null ? 3 : userAccountTypeId;
    }

    public void setUserAccountTypeId(Integer userAccountTypeId) {
        this.userAccountTypeId = userAccountTypeId;
    }


    @Basic
    @Column(name = "gender", nullable = true, length = 10)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "date_of_birth", nullable = true)
    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "mobile_number", nullable = true, length = 50)
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Basic
    @Column(name = "country_of_residence", nullable = true, length = 100)
    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    @Basic
    @Column(name = "city_of_residence", nullable = true, length = 100)
    public String getCityOfResidence() {
        return cityOfResidence;
    }

    public void setCityOfResidence(String cityOfResidence) {
        this.cityOfResidence = cityOfResidence;
    }

    @Basic
    @Column(name = "state_or_province_of_residence", nullable = true, length = 100)
    public String getStateOrProvinceOfResidence() {
        return stateOrProvinceOfResidence;
    }

    public void setStateOrProvinceOfResidence(String stateOrProvinceOfResidence) {
        this.stateOrProvinceOfResidence = stateOrProvinceOfResidence;
    }

    @Basic
    @Column(name = "postal_code", nullable = true)
    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "box_number", nullable = true)
    public Integer getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(Integer boxNumber) {
        this.boxNumber = boxNumber;
    }

    @Basic
    @Column(name = "street_or_residence_address", nullable = true)
    public String getStreetOrResidenceAddress() {
        return streetOrResidenceAddress;
    }

    public void setStreetOrResidenceAddress(String streetOrResidenceAddress) {
        this.streetOrResidenceAddress = streetOrResidenceAddress;
    }

    @Basic
    @Column(name = "identification_numbers", nullable = true, length = 256)
    public String getIdentificationNumbers() {
        return identificationNumbers;
    }

    public void setIdentificationNumbers(String identificationNumbers) {
        this.identificationNumbers = identificationNumbers;
    }

    @Basic
    @Column(name = "id_issuing_country", nullable = true, length = 100)
    public String getIdIssuingCountry() {
        return idIssuingCountry;
    }

    public void setIdIssuingCountry(String idIssuingCountry) {
        this.idIssuingCountry = idIssuingCountry;
    }

    @Basic
    @Column(name = "id_expiration_date", nullable = true)
    public Date getIdExpirationDate() {
        return idExpirationDate;
    }

    public void setIdExpirationDate(Date idExpirationDate) {
        this.idExpirationDate = idExpirationDate;
    }

    @Basic
    @Column(name = "employment_status_id", nullable = true)
    public Integer getEmploymentStatusId() {
        return employmentStatusId;
    }

    public void setEmploymentStatusId(Integer employmentStatusId) {
        this.employmentStatusId = employmentStatusId;
    }

    @Basic
    @Column(name = "employment_industry_id", nullable = true)
    public Integer getEmploymentIndustryId() {
        return employmentIndustryId;
    }

    public void setEmploymentIndustryId(Integer employmentIndustryId) {
        this.employmentIndustryId = employmentIndustryId;
    }

    @Basic
    @Column(name = "job_title_id", nullable = true)
    public Integer getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(Integer jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    @Basic
    @Column(name = "source_of_funds_id", nullable = true)
    public Integer getSourceOfFundsId() {
        return sourceOfFundsId;
    }

    public void setSourceOfFundsId(Integer sourceOfFundsId) {
        this.sourceOfFundsId = sourceOfFundsId;
    }

    @Basic
    @Column(name = "self_annual_income_bracket_id", nullable = true)
    public Integer getSelfAnnualIncomeBracketId() {
        return selfAnnualIncomeBracketId;
    }

    public void setSelfAnnualIncomeBracketId(Integer selfAnnualIncomeBracketId) {
        this.selfAnnualIncomeBracketId = selfAnnualIncomeBracketId;
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

    @Basic
    @Column(name = "activated", nullable = false)
    public String getActivated() {
        return activated == null ? "NO" : activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    @Basic
    @Column(name = "completed_kyc", nullable = false)
    public String getCompletedKyc() {
        return completedKyc == null ? "NO" : completedKyc;
    }

    public void setCompletedKyc(String completedKyc) {
        this.completedKyc = completedKyc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (otherNames != null ? !otherNames.equals(that.otherNames) : that.otherNames != null) return false;
        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;
        if (ethereumWalletAddress != null ? !ethereumWalletAddress.equals(that.ethereumWalletAddress) : that.ethereumWalletAddress != null)
            return false;
        if (userPassword != null ? !userPassword.equals(that.userPassword) : that.userPassword != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(that.dateOfBirth) : that.dateOfBirth != null) return false;
        if (mobileNumber != null ? !mobileNumber.equals(that.mobileNumber) : that.mobileNumber != null) return false;
        if (countryOfResidence != null ? !countryOfResidence.equals(that.countryOfResidence) : that.countryOfResidence != null)
            return false;
        if (cityOfResidence != null ? !cityOfResidence.equals(that.cityOfResidence) : that.cityOfResidence != null)
            return false;
        if (stateOrProvinceOfResidence != null ? !stateOrProvinceOfResidence.equals(that.stateOrProvinceOfResidence) : that.stateOrProvinceOfResidence != null)
            return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (boxNumber != null ? !boxNumber.equals(that.boxNumber) : that.boxNumber != null) return false;
        if (streetOrResidenceAddress != null ? !streetOrResidenceAddress.equals(that.streetOrResidenceAddress) : that.streetOrResidenceAddress != null)
            return false;
        if (identificationNumbers != null ? !identificationNumbers.equals(that.identificationNumbers) : that.identificationNumbers != null)
            return false;
        if (idIssuingCountry != null ? !idIssuingCountry.equals(that.idIssuingCountry) : that.idIssuingCountry != null)
            return false;
        if (idExpirationDate != null ? !idExpirationDate.equals(that.idExpirationDate) : that.idExpirationDate != null)
            return false;
        if (employmentStatusId != null ? !employmentStatusId.equals(that.employmentStatusId) : that.employmentStatusId != null)
            return false;
        if (employmentIndustryId != null ? !employmentIndustryId.equals(that.employmentIndustryId) : that.employmentIndustryId != null)
            return false;
        if (jobTitleId != null ? !jobTitleId.equals(that.jobTitleId) : that.jobTitleId != null) return false;
        if (sourceOfFundsId != null ? !sourceOfFundsId.equals(that.sourceOfFundsId) : that.sourceOfFundsId != null)
            return false;
        if (selfAnnualIncomeBracketId != null ? !selfAnnualIncomeBracketId.equals(that.selfAnnualIncomeBracketId) : that.selfAnnualIncomeBracketId != null)
            return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        if (deleted != null ? !deleted.equals(that.deleted) : that.deleted != null) return false;
        if (activated != null ? !activated.equals(that.activated) : that.activated != null) return false;
        if (completedKyc != null ? !completedKyc.equals(that.completedKyc) : that.completedKyc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (otherNames != null ? otherNames.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        result = 31 * result + (ethereumWalletAddress != null ? ethereumWalletAddress.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (mobileNumber != null ? mobileNumber.hashCode() : 0);
        result = 31 * result + (countryOfResidence != null ? countryOfResidence.hashCode() : 0);
        result = 31 * result + (cityOfResidence != null ? cityOfResidence.hashCode() : 0);
        result = 31 * result + (stateOrProvinceOfResidence != null ? stateOrProvinceOfResidence.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (boxNumber != null ? boxNumber.hashCode() : 0);
        result = 31 * result + (streetOrResidenceAddress != null ? streetOrResidenceAddress.hashCode() : 0);
        result = 31 * result + (identificationNumbers != null ? identificationNumbers.hashCode() : 0);
        result = 31 * result + (idIssuingCountry != null ? idIssuingCountry.hashCode() : 0);
        result = 31 * result + (idExpirationDate != null ? idExpirationDate.hashCode() : 0);
        result = 31 * result + (employmentStatusId != null ? employmentStatusId.hashCode() : 0);
        result = 31 * result + (employmentIndustryId != null ? employmentIndustryId.hashCode() : 0);
        result = 31 * result + (jobTitleId != null ? jobTitleId.hashCode() : 0);
        result = 31 * result + (sourceOfFundsId != null ? sourceOfFundsId.hashCode() : 0);
        result = 31 * result + (selfAnnualIncomeBracketId != null ? selfAnnualIncomeBracketId.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (activated != null ? activated.hashCode() : 0);
        result = 31 * result + (completedKyc != null ? completedKyc.hashCode() : 0);
        return result;
    }

    @Transient
    public String getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(String displayPicture) {
        this.displayPicture = displayPicture;
    }


    @JsonIgnore
    @OneToMany(mappedBy = "userAccountsByUserAccountId", fetch = FetchType.LAZY)
    public Collection<Attachment> getAttachmentsById() {
        return attachmentsById;
    }

    public void setAttachmentsById(Collection<Attachment> attachmentsById) {
        this.attachmentsById = attachmentsById;
    }

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userAccountById")
    public Collection<AccessToken> getAccessTokensByUserAccountId() {
        return accessTokensByUserAccountId;
    }

    public void setAccessTokensByUserAccountId(Collection<AccessToken> accessTokensByUserAccountId) {
        this.accessTokensByUserAccountId = accessTokensByUserAccountId;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_type_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public UserAccountType getUserAccountTypesByUserAccountTypeId() {
        return userAccountTypesByUserAccountTypeId;
    }

    public void setUserAccountTypesByUserAccountTypeId(UserAccountType userAccountTypesByUserAccountTypeId) {
        this.userAccountTypesByUserAccountTypeId = userAccountTypesByUserAccountTypeId;
    }
}
