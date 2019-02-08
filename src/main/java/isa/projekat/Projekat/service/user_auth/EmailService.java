package isa.projekat.Projekat.service.user_auth;

import isa.projekat.Projekat.model.airline.Flight;
import isa.projekat.Projekat.model.airline.Order;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.model.user.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Service
public class EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    /*
     * Reads from application.properties file
     */
    @Autowired
    private Environment environment;


    @Async
    public void sendEmailAsync(User user, VerificationToken token) throws MailException, MessagingException {


        System.out.println("Sending mail... to :" + user.getUsername() + "!");



        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        mimeMessage.setContent( htmlMessage(user,token), "text/html");
        helper.setTo(user.getUsername());
        helper.setSentDate(new Date());
        helper.setSubject("eJourney account verification email");
        helper.setFrom(environment.getProperty("spring.mail.username"));

        javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendInviteMail(User sender, User recipient) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(recipient.getUsername());
        mailMessage.setSubject("eJourney trip invite");
        mailMessage.setFrom(environment.getProperty("spring.mail.username"));
        mailMessage.setSentDate(new Date());


        String text = "";
        text += recipient.getFirstName()+ ",\r\n\r\nYou have been invited to a trip by your friend "+ sender.getFirstName() +", you just need to verify your invitation on the link below.\r\n\r\n";
        text += "https://isa-tim-11.herokuapp.com/";
        text += "\r\n\r\nIf you have problems feel free to contact us at ejourneyofficial@gmail.com\r\nBest regards\r\neJourney Team";
        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }

    @Async
    public void sendOrderMail(User user, Order order, Flight fl) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(user.getUsername());
        mailMessage.setSubject("Order information");
        mailMessage.setFrom(environment.getProperty("spring.mail.username"));
        mailMessage.setSentDate(new Date());

        String text = "";
        text += user.getFirstName()+ ",\r\n\r\nYour order has been completed!\r\n";
        text += "You have purchased "+ order.getReservations().size() +" tickets, for the flight from "+ fl.getStart().getCity() +" to " + fl.getFinish().getCity() +".\r\n";
        text += "Your flight takes off at "+ fl.getStartTime() +" and will land around " + fl.getLandTime() +".\r\n\r\n";
        text += getRentACarText(order);
        text += getHotelText(order);
        text += "\r\n\r\nIf you have problems feel free to contact us at ejourneyofficial@gmail.com\r\nBest regards\r\neJourney Team";
        mailMessage.setText(text);

        javaMailSender.send(mailMessage);
    }

    private String getRentACarText(Order order) {
        String rez = "";

        if(order.getRentReservation() != null) {
            rez += "In addition you have rented a " + order.getRentReservation().getRentedCar().getMark() + " " + order.getRentReservation().getRentedCar().getModel() + " " + order.getRentReservation().getRentedCar().getName() + ".\r\n";
            rez += "Your reservation will last from "+ order.getRentReservation().getStartDate() +" till " + order.getRentReservation().getEndDate() +".\r\n\r\n";
        }

        return rez;
    }

    private String getHotelText(Order order) {
        String rez = "";

        if(order.getReservationHotel() != null) {
            rez += "Further, you have booked the room " + order.getReservationHotel().getRoom().getName() + " at the " + order.getReservationHotel().getRoom().getHotel().getName()+ " Hotel.\r\n";
            rez += "Your reservation will last from "+ order.getReservationHotel().getArrivalDate() +" till " + order.getReservationHotel().getDepartureDate() +".\r\n";
            boolean first = true;
            for(int i = 0; i < order.getReservationHotel().getServices().size(); i++) {
                if(first) {
                    rez += "Additional services: ";
                } else {
                    rez += ", ";
                }
                rez+=order.getReservationHotel().getServices().get(i).getName();
            }
            rez+="\r\n\r\n";
        }

        return rez;
    }


    private String htmlMessage(User user, VerificationToken token){
        String address = "http://localhost:8080/confirm.html?token=" + token.getToken();

        // BuildMyString.com generated code. Please enjoy your string responsibly.

        StringBuilder msg = new StringBuilder();

        msg.append("<!doctype html>");
        msg.append("<html>");
        msg.append("  <head>");
        msg.append("    <meta name=\"viewport\" content=\"width=device-width\">");
        msg.append("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        msg.append("    <title>Simple Transactional Email</title>");
        msg.append("    <style>");
        msg.append("    @media only screen and (max-width: 620px) {");
        msg.append("      table[class=body] h1 {");
        msg.append("        font-size: 28px !important;");
        msg.append("        margin-bottom: 10px !important;");
        msg.append("      }");
        msg.append("      table[class=body] p,");
        msg.append("            table[class=body] ul,");
        msg.append("            table[class=body] ol,");
        msg.append("            table[class=body] td,");
        msg.append("            table[class=body] span,");
        msg.append("            table[class=body] a {");
        msg.append("        font-size: 16px !important;");
        msg.append("      }");
        msg.append("      table[class=body] .wrapper,");
        msg.append("            table[class=body] .article {");
        msg.append("        padding: 10px !important;");
        msg.append("      }");
        msg.append("      table[class=body] .content {");
        msg.append("        padding: 0 !important;");
        msg.append("      }");
        msg.append("      table[class=body] .container {");
        msg.append("        padding: 0 !important;");
        msg.append("        width: 100% !important;");
        msg.append("      }");
        msg.append("      table[class=body] .main {");
        msg.append("        border-left-width: 0 !important;");
        msg.append("        border-radius: 0 !important;");
        msg.append("        border-right-width: 0 !important;");
        msg.append("      }");
        msg.append("      table[class=body] .btn table {");
        msg.append("        width: 100% !important;");
        msg.append("      }");
        msg.append("      table[class=body] .btn a {");
        msg.append("        width: 100% !important;");
        msg.append("      }");
        msg.append("      table[class=body] .img-responsive {");
        msg.append("        height: auto !important;");
        msg.append("        max-width: 100% !important;");
        msg.append("        width: auto !important;");
        msg.append("      }");
        msg.append("    }");
        msg.append("    @media all {");
        msg.append("      .ExternalClass {");
        msg.append("        width: 100%;");
        msg.append("      }");
        msg.append("      .ExternalClass,");
        msg.append("            .ExternalClass p,");
        msg.append("            .ExternalClass span,");
        msg.append("            .ExternalClass font,");
        msg.append("            .ExternalClass td,");
        msg.append("            .ExternalClass div {");
        msg.append("        line-height: 100%;");
        msg.append("      }");
        msg.append("      .apple-link a {");
        msg.append("        color: inherit !important;");
        msg.append("        font-family: inherit !important;");
        msg.append("        font-size: inherit !important;");
        msg.append("        font-weight: inherit !important;");
        msg.append("        line-height: inherit !important;");
        msg.append("        text-decoration: none !important;");
        msg.append("      }");
        msg.append("      .btn-primary table td:hover {");
        msg.append("        background-color: #34495e !important;");
        msg.append("      }");
        msg.append("      .btn-primary a:hover {");
        msg.append("        background-color: #34495e !important;");
        msg.append("        border-color: #34495e !important;");
        msg.append("      }");
        msg.append("    }");
        msg.append("    </style>");
        msg.append("  </head>");
        msg.append("  <body class=\"\" style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">");
        msg.append("    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">");
        msg.append("      <tr>");
        msg.append("        <td class=\"container\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">");
        msg.append("          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">");
        msg.append(" <span class=\"preheader\" style=\"color: transparent; display: none; height: 0; max-height: 0; max-width: 0; opacity: 0; overflow: hidden; mso-hide: all; visibility: hidden; width: 0;\">Confirm you account</span>");
        msg.append("            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\">");
        msg.append("              <tr>");
        msg.append("                <td class=\"wrapper\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\">");
        msg.append("                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">");
        msg.append("                    <tr>");
        msg.append("                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">");
        msg.append("                        <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">Hi "+user.getFirstName() + ",</p>");
        msg.append("                        <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">You are almost done, just verify your email adress by clicking the button below</p>");
        msg.append("                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\">");
        msg.append("                          <tbody>");
        msg.append("                            <tr>");
        msg.append("                              <td align=\"left\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 15px;\">");
        msg.append("                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\">");
        msg.append("                                  <tbody>");
        msg.append("                                    <tr>");
        msg.append("                                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; background-color: #3498db; border-radius: 5px; text-align: center;\"> <a href=\""+ address+"\" target=\"_blank\" style=\"display: inline-block; color: #ffffff; background-color: #3498db; border: solid 1px #3498db; border-radius: 5px; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 14px; font-weight: bold; margin: 0; padding: 12px 25px; text-transform: capitalize; border-color: #3498db;\">Confirm your email address</a> </td>");
        msg.append("                                    </tr>");
        msg.append("                                  </tbody>");
        msg.append("                                </table>");
        msg.append("                              </td>");
        msg.append("                            </tr>");
        msg.append("                          </tbody>");
        msg.append("                        </table>");
        msg.append("                      </td>");
        msg.append("                    </tr>");
        msg.append("                  </table>");
        msg.append("                </td>");
        msg.append("              </tr>");
        msg.append("            </table>");
        msg.append("            <div class=\"footer\" style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\">");
        msg.append("              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">");
        msg.append("                <tr>");
        msg.append("                  <td class=\"content-block powered-by\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">");
        msg.append("                    @ 2018 <a href=\"http://ejourneyofficial.com\" style=\"color: #999999; font-size: 12px; text-align: center; text-decoration: none;\">eJourney</a>.");
        msg.append("                  </td>");
        msg.append("                </tr>");
        msg.append("              </table>");
        msg.append("            </div>");
        msg.append("          </div>");
        msg.append("        </td>");
        msg.append("      </tr>");
        msg.append("    </table>");
        msg.append("  </body>");
        msg.append("</html>");
        return msg.toString();
    }


    public static BigInteger getBigInteger(Object value) {
        BigInteger ret = null;
        if ( value != null ) {
            if ( value instanceof BigInteger ) {
                ret = (BigInteger) value;
            } else if ( value instanceof String ) {
                ret = new BigInteger( (String) value );
            } else if ( value instanceof BigDecimal) {
                ret = ((BigDecimal) value).toBigInteger();
            } else if ( value instanceof Number ) {
                ret = BigInteger.valueOf( ((Number) value).longValue() );
            } else {
                throw new ClassCastException( "Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigInteger." );
            }
        }
        return ret;
    }
}
