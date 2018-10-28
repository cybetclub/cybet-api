package club.cybet.repository;

import club.cybet.domain.db.orm.UserAccount;
import club.cybet.utils.Constants;
import club.cybet.utils.http.ScedarHttpHandler;
import club.cybet.utils.memory.JvmManager;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.HashMap;
import java.util.List;

/**
 * sls-api (club.cybet.repository)
 * Created by: cybet
 * On: 04 Jul, 2018 7/4/18 9:56 PM
 **/
public class UserAccountRepository {

    public static UserAccount getByUsernameFields(String username){
        UserAccount user = (UserAccount) Repository
                .findByUniqueField(UserAccount.class, "email_address", username);

        if(user == null)
            user = (UserAccount) Repository
                    .findByUniqueField(UserAccount.class, "ethereum_wallet_address", username);

        if(user != null)
            if(user.getActivated().toLowerCase().equals("no"))
                return null;

        return user;
    }

    public static String getNextAccountId(){
        return Repository.getNextInt(Constants.USER_ACCOUNT_COUNTER, 1);
    }

    @SuppressWarnings("unchecked")
    public static double getShareCapital(String accountTypeCode){
        List<String> metaData = (List<String>) Repository.executeQuery("" +
                "select metaData from OrganisationAccountType where accountTypeCode = '"+accountTypeCode+"'");

        assert metaData != null;
        HashMap<String, String> metaDataMap = (HashMap<String, String>)
                ScedarHttpHandler.toHashMap(metaData.get(0),
                        new TypeReference<HashMap<String, String>>() {});
        double shareCapital = Double.parseDouble(metaDataMap.get(Constants.SHARE_CAPITAL));

        JvmManager.gc(metaData, metaDataMap);
        return shareCapital;
    }

}
