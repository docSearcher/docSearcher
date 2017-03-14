package com.ustc.utils;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendEmail {

	private static final String HOST = "smtp.qq.com";
	private static final String PROTOCOL = "smtp";
	private static final int PORT = 25;
	private static final String FROM = "965477952@qq.com";// 发件人的email
	private static final String PWD = "tianfei19920627";// wvczckskpurhbaij

	private static Session getSession() throws MessagingException {
		Properties props = new Properties();
		MailSSLSocketFactory sf;
		try {
			sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		props.setProperty("mail.debug", "true");
		props.setProperty("mail.smtp.host", HOST);// 设置服务器地址
		props.setProperty("mail.transport.protocol", PROTOCOL);// 设置协议
		// props.put("mail.smtp.port", PORT);// 设置端口
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(props);
		/*
		 * Authenticator authenticator = new Authenticator() {
		 * 
		 * @Override protected PasswordAuthentication
		 * getPasswordAuthentication() { return new PasswordAuthentication(FROM,
		 * PWD); } };
		 */
		// Session session = Session.getDefaultInstance(props, authenticator);
		return session;
	}

	public static void send(String toEmail, String content) {
		Session session;
		try {
			session = getSession();
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(FROM));
			/*
			 * InternetAddress[] address = { new InternetAddress(toEmail) };
			 * msg.setRecipients(Message.RecipientType.TO, address);
			 */
			msg.setSubject("账号激活邮件");
			msg.setSentDate(new Date());
			msg.setContent(content, "text/html;charset=utf-8");// Send the
																// message
			Transport transport = session.getTransport();
			transport.connect(HOST, FROM, PWD);
			transport.sendMessage(msg, new Address[] { new InternetAddress(toEmail) });
			;
			transport.close();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
