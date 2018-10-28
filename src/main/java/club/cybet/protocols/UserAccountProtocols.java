package club.cybet.protocols;

import club.cybet.domain.db.orm.UserAccount;
import club.cybet.utils.Constants;
import club.cybet.utils.http.mail.CreateMail;
import club.cybet.utils.http.mail.Mailer;
import club.cybet.utils.memory.JvmManager;
import club.cybet.utils.security.Encryption;

/**
 * cybet-api (club.cybet.protocols)
 * Created by: cybet
 * On: 24 Oct, 2018 10/24/18 5:31 PM
 **/
public class UserAccountProtocols {

    private UserAccount userAccount;
    private Thread worker;

    public UserAccountProtocols(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public void run(){
        //TODO: Combine compatible / sequence protocols here
    }

    public void registration(){
        Runnable sendRegistrationEmail = this::sendRegistrationEmail;
        worker = new Thread(sendRegistrationEmail);
        worker.start();
    }

    public void resetPassword(){
        Runnable sendPasswordResetEmail = this::sendPasswordResetEmail;
        worker = new Thread(sendPasswordResetEmail);
        worker.start();
    }

    public void sendRegistrationEmail(){
        System.out.println("THREAD "+this.getClass().getName()+".sendRegistrationEmail() STARTED");

        String[] userHash = getUserHash();


        String redirect = Constants.getApiContextProtocol()+"://"
                +Constants.getApiServer()+":"
                +Constants.getApiContextPort()+"/"+
                "api/users/account-activation?id="+userHash[0]+"&hash="+userHash[1];
        String email = Constants.activateAccountEmail
                .replaceAll("%\\(redirect\\)%", redirect);

        Mailer mailer = new Mailer();
        mailer.setEmailSMTPHost(Constants.getMailerSmtpHost());
        mailer.setEmailSMTPStartTLSEnable(Boolean.getBoolean(Constants.getMailerSmtpEnableTls()));
        mailer.setEmailSMTPPort(Integer.parseInt(Constants.getMailerSmtpPort()));
        mailer.setFromEmailAddress(Constants.getMailerFromEmailAddress());
        mailer.setEmailPassword(Constants.getMailerPassword());
        mailer.setToEmailAddresses(userAccount.getEmailAddress());
        mailer.doSendMail("SilverSpoon - Account Activation", email);

        System.out.println("THREAD "+this.getClass().getName()+".sendRegistrationEmail() COMPLETED");

        JvmManager.gc(mailer, email, redirect);
    }

    public void sendPasswordResetEmail(){
        System.out.println("THREAD "+this.getClass().getName()+".sendPasswordResetEmail() STARTED");

        String[] userHash = getUserHash();

        String redirect = Constants.getPortalPassResetHost()+"?id="+userHash[0]+"&hash="+userHash[1];
        String email = Constants.passwordResetEmail
                .replaceAll("%\\(redirect\\)%", redirect);

        Mailer mailer = new Mailer();
        mailer.setEmailSMTPHost(Constants.getMailerSmtpHost());
        mailer.setEmailSMTPStartTLSEnable(Boolean.getBoolean(Constants.getMailerSmtpEnableTls()));
        mailer.setEmailSMTPPort(Integer.parseInt(Constants.getMailerSmtpPort()));
        mailer.setFromEmailAddress(Constants.getMailerFromEmailAddress());
        mailer.setEmailPassword(Constants.getMailerPassword());
        mailer.setToEmailAddresses(userAccount.getEmailAddress());
        mailer.doSendMail("SilverSpoon - Password Reset", email);

        System.out.println("THREAD "+this.getClass().getName()+".sendEmail() COMPLETED");

        JvmManager.gc(mailer, email, redirect);
    }

    private String[] getUserHash(){
        String id = Encryption.encrypto(userAccount.getId().toString());
        String hash = Encryption.encrypto(
                userAccount.getUserPassword()+
                        Constants.SKY_DELIMITER+
                        userAccount.getEmailAddress()+
                        Constants.SKY_DELIMITER+
                        userAccount.getId()
        );

        return new String[] {id, hash};
    }

}
