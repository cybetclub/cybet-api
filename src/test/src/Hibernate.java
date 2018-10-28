package src;

import club.cybet.domain.beans.DbTransactionWrapper;
import club.cybet.domain.db.orm.AttachmentType;
import club.cybet.repository.Repository;
import club.cybet.utils.Constants;
import club.cybet.utils.hibernate.HibernateSetup;
import club.cybet.utils.security.Passwords;
import org.junit.Test;

import java.util.List;

/**
 * cybet-api (src)
 * Created by: cybet
 * On: 23 Oct, 2018 10/23/18 9:04 PM
 **/
public class Hibernate {

    @Test
    public void testingAutoIncrement(){
        new HibernateSetup();

        AttachmentType attachmentType = new AttachmentType();
        attachmentType.setAttachmentTypeName("Test");
        attachmentType.setDescription("Test");

        DbTransactionWrapper wrapper = Repository.save(attachmentType);

        attachmentType = (AttachmentType) wrapper.getData();
        System.out.println("ID: "+attachmentType.getId());
    }

    @Test
    public void createPassword(){
        String pwd = "S1lverSp00n18@";

        String pwdHash = Passwords.getHash(pwd);
        System.out.println(pwdHash);
        System.out.println(Passwords.check(pwd, pwdHash));
    }

    @Test
    public void testingNativeQuery(){
        new HibernateSetup();

        String dp = Constants.getPublicAssetsServer();

        List<String> dpNames = (List<String>)Repository.executeQuery(
                Constants.getDpNameQuery(1, 1)
        );

        if(dpNames == null || dpNames.size() < 1) dp = "No display picture set";
        else dp+=dpNames.get(0);

        System.out.println(dp);
    }

}
