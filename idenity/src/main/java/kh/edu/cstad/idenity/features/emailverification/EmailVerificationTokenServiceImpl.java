package kh.edu.cstad.idenity.features.emailverification;

import kh.edu.cstad.idenity.domain.EmailVerificationToken;
import kh.edu.cstad.idenity.domain.User;
import kh.edu.cstad.idenity.features.emailverification.dto.EmailVerifyRequest;
import kh.edu.cstad.idenity.features.user.UserRepository;
import kh.edu.cstad.idenity.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;


    @Override
    public void verify(EmailVerifyRequest emailVerifyRequest) {

        // check if user attempts to verify exists or not
        User foundUser = userRepository.findByUsernameAndIsEnabledTrue(emailVerifyRequest.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with corresponding verification token"));

        EmailVerificationToken foundToken = emailVerificationTokenRepository.getByToken(emailVerifyRequest.token())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Verification token is invalid"));

        if (this.isUsersToken(foundToken, foundUser)) {
            if (this.isExpired(foundToken)) {
                foundUser.setEmailVerified(true);
                userRepository.save(foundUser);
                emailVerificationTokenRepository.deleteByUser(foundUser);
                return;
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Verification token has expired");
    }

    @Override
    public void resend(String username) {

        // check if user attempts to verify exists or not
        User foundUser = userRepository.findByUsernameAndIsEnabledTrue(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unsuccessfully creation of confirmation link!"));

        emailVerificationTokenRepository.deleteByUser(foundUser);
        generate(foundUser);
    }

    @Override
    public boolean isUsersToken(EmailVerificationToken token, User user) {
        return Objects.equals(user.getId(), token.getUser().getId());
    }

    @Override
    public boolean isTokenInDb(EmailVerificationToken token, String tokenToVerify) {
        return token.getToken().equals(tokenToVerify);
    }

    @Override
    public void generate(User user) {

        LocalTime expiration = LocalTime.now().plusMinutes(3);

        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken(RandomUtil.generate6Digits());
        emailVerificationToken.setExpiration(expiration);
        emailVerificationToken.setUser(user);
        emailVerificationTokenRepository.save(emailVerificationToken);

        // after saved information into database, mail will be started to send
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject("Account Verification");
            mimeMessageHelper.setText(emailVerificationToken.getToken());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
    }

    @Override
    public boolean isExpired(EmailVerificationToken token) {
        return !token.getExpiration().isBefore(LocalTime.now());
    }

}
