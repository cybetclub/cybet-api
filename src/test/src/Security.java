package src;

import club.cybet.domain.db.orm.UserAccount;
import club.cybet.repository.Repository;
import club.cybet.utils.Constants;
import club.cybet.utils.hibernate.HibernateSetup;
import club.cybet.utils.security.Encryption;
import org.junit.Test;

import java.util.Arrays;

/**
 * cybet-api (src)
 * Created by: cybet
 * On: 24 Oct, 2018 10/24/18 6:45 PM
 **/
public class Security {

    @Test public void getUserActivationHash(){

        new HibernateSetup();

        UserAccount userAccount = (UserAccount) Repository.findById(UserAccount.class, "1");

        String id = Encryption.encrypto(userAccount.getId().toString());
        String hash = Encryption.encrypto(
                userAccount.getUserPassword()+
                        Constants.SKY_DELIMITER+
                        userAccount.getEmailAddress()+
                        Constants.SKY_DELIMITER+
                        userAccount.getId()
        );

        System.out.println(id);
        System.out.println(hash);

        System.out.println(Encryption.decrypto(id));
        String[] userHash = Encryption.decrypto(hash).split(Constants.SKY_SPLITTER);
        System.out.println(Arrays.toString(userHash));
    }

    @Test public void confide(){
        System.out.println(Constants.getEthWalletFilePath());
    }

}
