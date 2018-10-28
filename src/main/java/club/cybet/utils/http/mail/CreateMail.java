package club.cybet.utils.http.mail;

import club.cybet.utils.Constants;

import java.util.List;

public class CreateMail {

    private Thread sendMailThread;
    private Thread sendMailsThread;
    private String recipient;
    private String email;
    private String subject;
    private List<String> recipients;
    private List<String> emails;
    private List<String> subjects;

    public CreateMail(String recipient, String subject, String email){
        this.email = email;
        this.subject = subject;
        this.recipient = recipient;
        sendMail();
    }

    public CreateMail(List<String> recipients, List<String> subjects, List<String> emails){
        this.emails = emails;
        this.subjects = subjects;
        this.recipients = recipients;
        sendMails();
    }

    public CreateMail() {
    }

    public void sendMails(){
//        System.out.println("Initiating send mails thread...");
        Runnable sendEmails = () -> {
            int counter = 0;
            for(String email : this.emails){
                Mailer mailer = new Mailer();

                mailer.setEmailSMTPHost(
                        Constants.getMailerSmtpHost());
                mailer.setEmailSMTPStartTLSEnable(
                        Boolean.parseBoolean(
                                Constants.getMailerSmtpEnableTls()));
                mailer.setEmailSMTPPort(
                        Integer.parseInt(
                                Constants.getMailerSmtpPort()));
                mailer.setFromEmailAddress(
                        Constants.getMailerFromEmailAddress());
                mailer.setEmailPassword(
                        Constants.getMailerPassword());
                mailer.setToEmailAddresses(this.recipients.get(counter));
                mailer.doSendMail(this.subjects.get(counter),email);
                counter++;
            }
        };
//        System.out.println("Dispatching send mails thread...");
        this.sendMailsThread = new Thread(sendEmails);
        this.sendMailsThread.start();
//        System.out.println("Dispatched send mails thread...");
    }

    public void sendMail(){
        System.out.println("Initiating send mail thread...");

        Runnable sendEmail = () -> {
            Mailer mailer = new Mailer();
            mailer.setEmailSMTPHost(
                    Constants.getMailerSmtpHost());
            mailer.setEmailSMTPStartTLSEnable(
                    Boolean.getBoolean(
                            Constants.getMailerSmtpEnableTls()));
            mailer.setEmailSMTPPort(
                    Integer.parseInt(
                            Constants.getMailerSmtpPort()));
            mailer.setFromEmailAddress(
                    Constants.getMailerFromEmailAddress());
            mailer.setEmailPassword(
                    Constants.getMailerPassword());
            mailer.setToEmailAddresses(this.recipient);
            mailer.doSendMail(this.subject,this.email);
        };

        System.out.println("Dispatching send mail thread...");
        this.sendMailThread = new Thread(sendEmail);
        this.sendMailThread.start();
        System.out.println("Dispatched send mail thread...");
    }

    public Thread getSendMailThread() {
        return sendMailThread;
    }

    public void setSendMailThread(Thread sendMailThread) {
        this.sendMailThread = sendMailThread;
    }

    public Thread getSendMailsThread() {
        return sendMailsThread;
    }

    public void setSendMailsThread(Thread sendMailsThread) {
        this.sendMailsThread = sendMailsThread;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "CreateMail{" +
                "sendMailThread=" + sendMailThread +
                ", sendMailsThread=" + sendMailsThread +
                ", recipient='" + recipient + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", recipients=" + recipients +
                ", emails=" + emails +
                ", subjects=" + subjects +
                '}';
    }
}
