package club.cybet.utils.http.mail;/*
 * REFERENCES ...
 * http://www.tutorialspoint.com/java/java_sending_email.htm
 * http://stackoverflow.com/questions/16117365/sending-mail-attachment-using-java
 */
// Send Mail using Mintel with attachment
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class Mailer {

	private String strEmailSMTPHost = "";
	private boolean bEmailSMTPStartTLSEnable = false;
	private int intEmailSMTPPort = 0;
	private String strFromEmailAddress = "";
	private String strEmailPassword = "";
	private String strEmailSubject = "";
	private String strToEmailAddresses = "";
	private String strCCEmailAddresses = "";
	private String strBCCEmailAddresses = "";
	
	public void setEmailSMTPHost(String theEmailSMTPHost){strEmailSMTPHost = theEmailSMTPHost;}
	public void setEmailSMTPStartTLSEnable(boolean theEmailSMTPStartTLSEnable){bEmailSMTPStartTLSEnable = theEmailSMTPStartTLSEnable;}
	public void setEmailSMTPPort(int theEmailSMTPPort){intEmailSMTPPort = theEmailSMTPPort;}
	public void setFromEmailAddress(String theFromEmailAddress){strFromEmailAddress = theFromEmailAddress;}
	public void setEmailPassword(String theEmailPassword){strEmailPassword = theEmailPassword;}
	public void setEmailSubject(String theEmailSubject){strEmailSubject = theEmailSubject;}
	public void setToEmailAddresses(String theToEmailAddresses){strToEmailAddresses = theToEmailAddresses;}
	public void setCCEmailAddresses(String theCCEmailAddresses){strCCEmailAddresses = theCCEmailAddresses;}
	public void setBCCEmailAddresses(String theBCCEmailAddresses){strBCCEmailAddresses = theBCCEmailAddresses;}
	
	public String getEmailSMTPHost(){return strEmailSMTPHost;}
	public boolean getEmailSMTPStartTLSEnable(){	return bEmailSMTPStartTLSEnable;}
	public int getEmailSMTPPort(){	return intEmailSMTPPort;}
	public String getFromEmailAddress(){return strFromEmailAddress;}
	public String getEmailPassword(){return strEmailPassword;}
	public String getEmailSubject(){return strEmailSubject;}
	public String getToEmailAddresses(){return strToEmailAddresses;}
	public String getCCEmailAddresses(){return strCCEmailAddresses;}
	public String getBCCEmailAddresses(){return strBCCEmailAddresses;}
	
	public boolean doSendMail(String strEmailSubject, String strEmailBodyMessage)  {

		// Get system properties
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", true);
	    
	    props.put("mail.smtp.starttls.enable", getEmailSMTPStartTLSEnable());
        props.put("mail.smtp.host", getEmailSMTPHost());
        props.put("mail.smtp.port", getEmailSMTPPort());
	
	    // Get the default Session object.
	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(getFromEmailAddress(), getEmailPassword());
	                }
	            });
	
	    try {
	    	
	    	// Create a default MimeMessage object
	        Message message = new MimeMessage(session);
	        
	        // Set From: header field of the header.
//	        System.out.println("Set From: header field of the header - " + getFromEmailAddress());
	        
	        if(isEmailAddressValid(getFromEmailAddress()))
			{
	        	message.setFrom(new InternetAddress(getFromEmailAddress()));
			}
	        
	        // Set To: header field of the header.
//	        System.out.println("Set To: header field of the header - " + getToEmailAddresses());
	        
	        String strToEmailAddresses[] = getToEmailAddresses().split(",");
			
	        for(int i =0; i < strToEmailAddresses.length; i++)
			{
				String strToEmailAddress = strToEmailAddresses[i].trim();
				
				if(isEmailAddressValid(strToEmailAddress))
				{
					message.addRecipients(Message.RecipientType.TO,
			                InternetAddress.parse(strToEmailAddress));
				}
			}
	        
	        // Set CC: header field of the header
	        /*
	        System.out.println("Set CC: header field of the header (Company Email Address) - " + ReportParameters.getCompanyEmailAddress());
	        if(Mail.isEmailAddressValid(ReportParameters.getCompanyEmailAddress()))
			{
				message.addRecipients(Message.RecipientType.CC,
		                InternetAddress.parse(ReportParameters.getCompanyEmailAddress()));
			}
	        */
	        
//	     	System.out.println("Set CC: header field of the header (Other Email Addresses) - " + getCCEmailAddresses());
	        
	     	String strCCEailAddresses[] = getCCEmailAddresses().split(",");
	        
			for(int i =0; i < strCCEailAddresses.length; i++)
			{
				String strCCEailAddress = strCCEailAddresses[i].trim();
				
				if(isEmailAddressValid(strCCEailAddress))
				{
					message.addRecipients(Message.RecipientType.CC,
			                InternetAddress.parse(strCCEailAddress));
				}
			}
			
			
			// Set BCC: header field of the header.
//			System.out.println("Set BCC: header field of the header - " + getBCCEmailAddresses());
			
	        String strBCCEailAddresses[] = getBCCEmailAddresses().split(",");
	        
			for(int i =0; i < strBCCEailAddresses.length; i++)
			{
				String strBCCEailAddress = strBCCEailAddresses[i].trim();
				
				if(isEmailAddressValid(strBCCEailAddress))
				{
					message.addRecipients(Message.RecipientType.BCC,
			                InternetAddress.parse(strBCCEailAddress));
				}
			}
	        
	        // Set Subject: header field
	        message.setSubject(strEmailSubject);
	        
	        // Create a multipart message
	        Multipart multipart = new MimeMultipart();
	        
	        // Create the message part - Part one is message
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        
	        // Fill the message part
	        messageBodyPart.setContent(strEmailBodyMessage,"text/html");
	
	        // Set text message part one
	        multipart.addBodyPart(messageBodyPart);
	        
	        // Part two is attachment
	        
	        /* NO attachment for this email
	        messageBodyPart = new MimeBodyPart();
	        
	        System.out.println ("\nReceipt Full Name: " + ReportParameters.getReportFullName());
			System.out.println ("Receipt Full Path: " + ReportParameters.getReportFullPath());
			
	        String file = ReportParameters.getReportFullPath();
	        String fileName = ReportParameters.getReportFullName();
	        
	        DataSource source = new FileDataSource(file);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(fileName);
	        multipart.addBodyPart(messageBodyPart);
	        */
	        //Send the complete message parts
	        message.setContent(multipart);
			
	        // Send message
//	        System.out.println("\nSending Mail ...");
	        Transport.send(message);
//	        System.out.println("\nMail sent successfully!!!");

	        return true;
	
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        return false;
	    }
		catch(Exception e) {
			e.printStackTrace();
			return true;
		} 
	}
	
	private boolean isEmailAddressValid(String strEmailAddress)  {
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		
		if (strEmailAddress.matches(emailreg)) {
//            System.out.println("The Email Address " + strEmailAddress + " is Valid");
            return true;
        }else {
//            System.err.println("The Email Address " + strEmailAddress + " is Invalid");
            return false;
        }
		
	}
	
	public String getHTMLEmailReceiptBody(String strURLParameters)  {
		
		String strReceipt = "";
		
		try
		{
			//This is not used on this setup
			String strReportURL = strURLParameters; //Parameters.getReportTemplateURL() + "?" + strURLParameters;
	        URL url = new URL(strReportURL);
	        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.connect();
	
	        InputStream stream = connection.getInputStream();

	        BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
	        
			String strLine = null;
			
			while ((strLine = rd.readLine()) != null) {
				//System.out.println(strLine.toString());
				strReceipt = strReceipt + strLine;
			}
		}
		catch (Exception e)
		{
			System.err.println ("Error message DB: " + e.getMessage ());
		}
		
		return strReceipt;
	}
}