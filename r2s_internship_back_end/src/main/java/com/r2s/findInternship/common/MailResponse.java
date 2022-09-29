package com.r2s.findInternship.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.r2s.findInternship.dto.ApplyListDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MailResponse {
	private ApplyListDTO apply;
	private String to;
	private String subject;
	private String mailTemplate;
	private String namePost;
	private String nameRecieve;
	private String cv;
	private TypeMail typeMail;
	public void createTemplate()
	{
		this.mailTemplate =  String.format("<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Document</title>\r\n"
				+ "    <style>\r\n"
				+ "        * {\r\n"
				+ "            padding: 0;\r\n"
				+ "            margin: 0;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        .email-container {\r\n"
				+ "            padding: 40px;\r\n"
				+ "        }\r\n"
				+ "        p {\r\n"
				+ "            line-height: 30px;\r\n"
				+ "        }\r\n"
				+ "        ul {\r\n"
				+ "            list-style-type: none;\r\n"
				+ "        }\r\n"
				+ "        li {\r\n"
				+ "            font-weight: bold;\r\n"
				+ "            line-height: 30px;\r\n"
				+ "        }\r\n"
				+ "        a {\r\n"
				+ "            color: #2d8ada;\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "    <div class=\"email-container\">\r\n"
				+ "        <p>\r\n"
				+ "            Thân chào " +apply.getJobApp().getHr().getUser().getLastName()+"<br/>\r\n"
				+ "             IT InternShip Jobs gửi đến bạn thông báo về ứng viên <b>"+apply.getCandidate().getUser().getLastName()+" </b> vừa ứng tuyển vào bài Tuyển dụng <b>"+apply.getJobApp().getName()+"</b> được đăng vào ngày: "+apply.getJobApp().getCreateDate()+" vào lúc "+apply.getCreateDate()+". <br/>\r\n"
				+ "            CV của ứng viên được gửi kèm theo Email này, HR cần thường xuyên cập nhật thông tin bài đăng trên trang website <br/>\r\n"
				+ "            Đây là email tự động, bạn không cần reply mail này.<br/>\r\n"
				+ "            Thân mến.<br/>\r\n"
				+ "        </p>\r\n"
				+ "        <div class=\"line\">______</div>\r\n"
				+ "        <ul>\r\n"
				+ "           <li>Công ty cổ phần R2S</li> \r\n"
				+ "           <li>Phone: 0919 365 363</li>\r\n"
				+ "           <li>Email: tuyendung@r2s.com.vn</li>\r\n"
				+ "           <li>Website: <a href=\"https://r2s.com.vn\">https://r2s.com.vn</a> ; <a href=\"https://r2s.edu.vn\">https://r2s.edu.vn</a> </li>\r\n"
				+ "        </ul>\r\n"
				+ "<br><img src='cid:R2S' width=175px height=120px/>"
				+ "    </div>\r\n"
				+ "</body>\r\n"
				+ "</html>", nameRecieve,namePost, LocalDate.now().getDayOfMonth(),LocalDate.now().getMonth().getValue(), LocalDate.now().getYear(),LocalDateTime.now().getHour(),LocalDateTime.now().getMinute());
			
	}

	public void createMailForgotPassword(String newPassword)
	{
		this.mailTemplate = "<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\r\n"
				+ "<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\r\n"
				+ "<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500&display=swap\" rel=\"stylesheet\">\r\n"
				+ "    <title>Email</title>\r\n"
				+ "    <style>\r\n"
				+ "        .email-container{\r\n"
				+ "            width: 50%;\r\n"
				+ "            padding: 30px 0px;\r\n"
				+ "            margin: 20px auto;\r\n"
				+ "            font-family: 'Poppins', sans-serif;\r\n"
				+ "            color: #555;\r\n"
				+ "            font-size: 15px\r\n"
				+ "        }\r\n"
				+ "        img {\r\n"
				+ "            width: 100%;\r\n"
				+ "            height: 80%;\r\n"
				+ "        }\r\n"
				+ "        .email-container__new-pass{\r\n"
				+ "            width: 60%;\r\n"
				+ "            margin: 20px auto;\r\n"
				+ "            text-align: center;\r\n"
				+ "            /* padding: 5px 0px; */\r\n"
				+ "            /* border: 2px solid #f4b459; */\r\n"
				+ "            background-color: #f4b459;\r\n"
				+ "            border-radius: 15px;\r\n"
				+ "            color: #fff;\r\n"
				+ "        }\r\n"
				+ "        h4{\r\n"
				+ "            font-size: 20px;\r\n"
				+ "            font-weight: bold\r\n"
				+ "        }\r\n"
				+ "        span{\r\n"
				+ "            font-weight: bold \r\n"
				+ "        }\r\n"
				+ "        h5{\r\n"
				+ "            line-height: 5px;\r\n"
				+ "            font-size: 15px;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        @media screen and (max-width: 750px) {\r\n"
				+ "            .email-container {\r\n"
				+ "                width: 90%;\r\n"
				+ "            }\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "    <div class=\"email-container\">\r\n"
				+ "        <div class=\"email-container__image\">\r\n"
				+ "            <img src=\"https://tlr.stripocdn.email/content/guids/CABINET_2af5bc24a97b758207855506115773ae/images/80731620309017883.png\" alt=\"image-forgot-password\">\r\n"
				+ "        </div>\r\n"
				+ "        <div class=\"email-container__content\">\r\n"
				+ "            <h1 class=\"email-container__title\">Bạn vừa gửi yêu cầu reset mật khẩu ?</h1>\r\n"
				+ "            <p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản InternshipJob được liên kết với email <b>"+to+"</b>. Mật khẩu đăng nhập mới của bạn là : </p>\r\n"
				+ "            <div class=\"email-container__new-pass\">\r\n"
				+ "                <h4>"+newPassword+"</h4>\r\n"
				+ "            </div>\r\n"
				+ "            \r\n"
				+ "\r\n"
				+ "            <p>Cảm ơn bạn đã sử dụng dịch vụ - R2S. </p>\r\n"
				+ "            <p>Thân mến </p>\r\n"
				+ "            <hr>\r\n"
				+ "            <h4>Công ty cổ phần R2S</h4>\r\n"
				+ "            <h5>Phone: 0901 250 813</h5>\r\n"
				+ "            <h5>Email: <a href=\"mailto:DoanhHn@r2s.vn\">DoanhHn@r2s.vn</a></h5>\r\n"
				+ "            <h5>Website: <a href=\"http://r2s.edu.vn\">r2s.edu.vn</a> </h5>\r\n"
				+ "            \r\n"
				+ "        </div>\r\n"
				+ "    </div>\r\n"
				+ "</body>\r\n"
				+ "</html>";
	}
	public void createTemplateHRApply()
	{
		this.mailTemplate =  String.format("<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Document</title>\r\n"
				+ "    <style>\r\n"
				+ "        * {\r\n"
				+ "            padding: 0;\r\n"
				+ "            margin: 0;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        .email-container {\r\n"
				+ "            padding: 40px;\r\n"
				+ "        }\r\n"
				+ "        p {\r\n"
				+ "            line-height: 30px;\r\n"
				+ "        }\r\n"
				+ "        ul {\r\n"
				+ "            list-style-type: none;\r\n"
				+ "        }\r\n"
				+ "        li {\r\n"
				+ "            font-weight: bold;\r\n"
				+ "            line-height: 30px;\r\n"
				+ "        }\r\n"
				+ "        a {\r\n"
				+ "            color: #2d8ada;\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "    <div class=\"email-container\">\r\n"
				+ "        <p>\r\n"
				+ "            Thân chào bạn <br/>\r\n"
				+ "             IT InternShip Jobs gửi đến bạn thông báo về HR <b> %s </b> vừa quan tâm đến bài Tuyển dụng <b> %s </b> được đăng vào ngày %d/%d/%d vào lúc %d: %d. <br/>\r\n"
				+ "            Danh sách sinh viên được gửi kèm theo Email này, Partner cần thường xuyên cập nhật thông tin bài đăng trên trang website <br/>\r\n"
				+ "            Đây là email tự động, bạn không cần reply mail này.<br/>\r\n"
				+ "            Thân mến.<br/>\r\n"
				+ "        </p>\r\n"
				+ "        <div class=\"line\">______</div>\r\n"
				+ "        <ul>\r\n"
				+ "           <li>Công ty cổ phần R2S</li> \r\n"
				+ "           <li>Phone: 0919 365 363</li>\r\n"
				+ "           <li>Email: tuyendung@r2s.com.vn</li>\r\n"
				+ "           <li>Website: <a href=\"https://r2s.com.vn\">https://r2s.com.vn</a> ; <a href=\"https://r2s.edu.vn\">https://r2s.edu.vn</a> </li>\r\n"
				+ "        </ul>\r\n"
				+ "<br><img src='cid:R2S'/>"
				+ "    </div>\r\n"
				+ "</body>\r\n"
				+ "</html>", nameRecieve,namePost, LocalDate.now().getDayOfMonth(),LocalDate.now().getMonth().getValue(), LocalDate.now().getYear(),LocalDateTime.now().getHour(),LocalDateTime.now().getMinute());
			
	}
	public void createTemplateActive()
	{
		String human = "";
		switch (typeMail)
		{
			case ActiveCompany: human = "HR"; break;
			case ActiveUniversity: human = "Partner";break;
		}
		this.mailTemplate = String.format("<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "\r\n"
				+ "<head>\r\n"
				+ "    <title></title>\r\n"
				+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
				+ "    <style type=\"text/css\">\r\n"
				+ "        @media screen {\r\n"
				+ "            @font-face {\r\n"
				+ "                font-family: 'Lato';\r\n"
				+ "                font-style: normal;\r\n"
				+ "                font-weight: 400;\r\n"
				+ "                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\r\n"
				+ "            }\r\n"
				+ "\r\n"
				+ "            @font-face {\r\n"
				+ "                font-family: 'Lato';\r\n"
				+ "                font-style: normal;\r\n"
				+ "                font-weight: 700;\r\n"
				+ "                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\r\n"
				+ "            }\r\n"
				+ "\r\n"
				+ "            @font-face {\r\n"
				+ "                font-family: 'Lato';\r\n"
				+ "                font-style: italic;\r\n"
				+ "                font-weight: 400;\r\n"
				+ "                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\r\n"
				+ "            }\r\n"
				+ "\r\n"
				+ "            @font-face {\r\n"
				+ "                font-family: 'Lato';\r\n"
				+ "                font-style: italic;\r\n"
				+ "                font-weight: 700;\r\n"
				+ "                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\r\n"
				+ "            }\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        /* CLIENT-SPECIFIC STYLES */\r\n"
				+ "        body,\r\n"
				+ "        table,\r\n"
				+ "        td,\r\n"
				+ "        a {\r\n"
				+ "            -webkit-text-size-adjust: 100%;\r\n"
				+ "            -ms-text-size-adjust: 100%;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        table,\r\n"
				+ "        td {\r\n"
				+ "            mso-table-lspace: 0pt;\r\n"
				+ "            mso-table-rspace: 0pt;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        img {\r\n"
				+ "            -ms-interpolation-mode: bicubic;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        /* RESET STYLES */\r\n"
				+ "        img {\r\n"
				+ "            border: 0;\r\n"
				+ "            height: auto;\r\n"
				+ "            line-height: 100%;\r\n"
				+ "            outline: none;\r\n"
				+ "            text-decoration: none;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        table {\r\n"
				+ "            border-collapse: collapse !important;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        body {\r\n"
				+ "            height: 100% !important;\r\n"
				+ "            margin: 0 !important;\r\n"
				+ "            padding: 0 !important;\r\n"
				+ "            width: 100% !important;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        /* iOS BLUE LINKS */\r\n"
				+ "        a[x-apple-data-detectors] {\r\n"
				+ "            color: inherit !important;\r\n"
				+ "            text-decoration: none !important;\r\n"
				+ "            font-size: inherit !important;\r\n"
				+ "            font-family: inherit !important;\r\n"
				+ "            font-weight: inherit !important;\r\n"
				+ "            line-height: inherit !important;\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        /* MOBILE STYLES */\r\n"
				+ "        @media screen and (max-width:600px) {\r\n"
				+ "            h1 {\r\n"
				+ "                font-size: 32px !important;\r\n"
				+ "                line-height: 32px !important;\r\n"
				+ "            }\r\n"
				+ "        }\r\n"
				+ "\r\n"
				+ "        /* ANDROID CENTER FIX */\r\n"
				+ "        div[style*=\"margin: 16px 0;\"] {\r\n"
				+ "            margin: 0 !important;\r\n"
				+ "        }\r\n"
				+ "    </style>\r\n"
				+ "</head>\r\n"
				+ "\r\n"
				+ "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\r\n"
				+ "    <!-- HIDDEN PREHEADER TEXT -->\r\n"
				+ "    <div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"> We're thrilled to have you here! Get ready to dive into your new account.\r\n"
				+ "    </div>\r\n"
				+ "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\r\n"
				+ "        <!-- LOGO -->\r\n"
				+ "        <tr>\r\n"
				+ "            <td bgcolor=\"#FFA73B\" align=\"center\">\r\n"
				+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </table>\r\n"
				+ "            </td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "            <td bgcolor=\"#FFA73B\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\r\n"
				+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\r\n"
				+ "                            <h1 style=\"font-size: 48px; font-weight: 400; margin: 2;\">Xin Chúc Mừng!</h1> <img src=\" https://img.icons8.com/clouds/100/000000/handshake.png\" width=\"125\" height=\"120\" style=\"display: block; border: 0px;\" />\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </table>\r\n"
				+ "            </td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\r\n"
				+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\r\n"
				+ "                            <p style=\"margin: 0;\">Tài khoản %s của bạn đã được kích hoạt, từ giờ bạn sẽ không bị giới hạn những tính năng dành riêng cho %s. </p>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\r\n"
				+ "                            <p style=\"margin: 0;\">Chúng tôi mong rằng bạn sẽ có những trải nghiệm tốt nhất khi sử dụng nền tảng <b>jobsit.com</b>.</p>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\r\n"
				+ "                            <p style=\"margin: 0;\">Mong rằng đôi bên sẽ có sự hợp tác lâu dài,<br>R2S Team</p>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </table>\r\n"
				+ "            </td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\r\n"
				+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td bgcolor=\"#FFECD1\" align=\"center\" style=\"padding: 30px 30px 30px 30px; border-radius: 4px 4px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\r\n"
				+ "                            <h2 style=\"font-size: 20px; font-weight: 400; color: #111111; margin: 0;\">Bạn cần sự hỗ trợ?</h2>\r\n"
				+ "                            <p style=\"margin: 0;\"><a href=\"#\" target=\"_blank\" style=\"color: #FFA73B;\">Liên hệ với chúng tôi tại đây</a></p>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </table>\r\n"
				+ "            </td>\r\n"
				+ "        </tr>\r\n"
				+ "        <tr>\r\n"
				+ "            <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\r\n"
				+ "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\r\n"
				+ "                    <tr>\r\n"
				+ "                        <td bgcolor=\"#f4f4f4\" align=\"left\" style=\"padding: 0px 30px 30px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;\"> <br>\r\n"
				+ "                            <p style=\"margin: 0;\">Đây là Email tự động vui lòng không reply lại mail này.</p>\r\n"
				+ "                        </td>\r\n"
				+ "                    </tr>\r\n"
				+ "                </table>\r\n"
				+ "            </td>\r\n"
				+ "        </tr>\r\n"
				+ "    </table>\r\n"
				+ "</body>\r\n"
				+ "\r\n"
				+ "</html>", human);
	}
}
