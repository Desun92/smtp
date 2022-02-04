package practica1.castro.adrian;

import java.io.File;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EnviarMail {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		String[] destinatario = {"adri.villa92@gmail.com"}; //A quienes queremos escribir.
		String[] adjuntos = {"./recursos/queTalMundo.txt"};
		String remitente = "secundariaacv";
		String asunto = "Hola";
		String cuerpo = "Qué tal?";
		String clave = "";
		
		/*System.out.println("Indica los destinatarios, separados por comas");
		String[] destinatario = sc.nextLine().split(",");
		
		System.out.println("Indica la ruta de los archivos adjuntos, separados por comas");
		String[] adjuntos = sc.nextLine().split(",");
		
	    System.out.println("Indica el correo remitente sin '@gmail.com'");
	    String remitente = sc.nextLine();
	    
		System.out.println("Indica el asunto del mensaje");
		String asunto = sc.nextLine();
		
		System.out.println("Indica el cuerpo del mensaje");
	    String cuerpo = sc.nextLine();
	    
	    System.out.println("Introduce la contraseña del correo remitente");
	    String clave = sc.nextLine();*/
	    
	    enviarConGmail(destinatario, adjuntos, remitente, asunto, cuerpo, clave);
	    
	    sc.close();
	}
	
	public static void enviarConGmail(String[] destinatario, String[] adjuntos, String remitente, String asunto, String cuerpo, String clave) {
		
		// Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
	    String remitenteA = remitente;  //Para la dirección nomcuenta@gmail.com

	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitenteA);
	    props.put("mail.smtp.clave", clave);    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    MimeBodyPart mimeBodyPart;
	    Multipart multipart = new MimeMultipart();

	    try {
	        message.setFrom(new InternetAddress(remitente));
	      //Añadir varios destinatarios
	        for(int i=0;i<destinatario.length;i++) {
		        InternetAddress[] iaArray=InternetAddress.parse(destinatario[i]);
		        InternetAddress ia = iaArray[0];
		        message.addRecipient(Message.RecipientType.TO, ia);
	        }
	        if(adjuntos.length>0) {
		        for(int i=0;i<adjuntos.length;i++) {
		        	mimeBodyPart = new MimeBodyPart();
		        	mimeBodyPart.attachFile(new File(adjuntos[i]));
		        	multipart.addBodyPart(mimeBodyPart);
		        }
		        message.setContent(cuerpo,"text/html; charset=utf-8");
		        message.setContent(multipart);
	        }
		    
	        message.setSubject(asunto);
	        
	        if(adjuntos.length<1)
	        	message.setText(cuerpo,"UTF-8");
	        
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", remitente, clave);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
		
	}

}
