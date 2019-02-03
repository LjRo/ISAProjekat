package isa.projekat.Projekat.aspects;

import isa.projekat.Projekat.exceptions.AdminEnabledException;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.UserRepository;
import isa.projekat.Projekat.security.TokenUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AdminEnabledCheckAspect {
    @Autowired
    private TokenUtils tUtils;

    @Autowired
    private UserRepository userRepository;

    @Before("@annotation(isa.projekat.Projekat.aspects.AdminEnabledCheck)")
    public void checkIfEnabled(JoinPoint jpoinPoint) throws AdminEnabledException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String email = tUtils.getUsernameFromToken(tUtils.getToken(request));
        User target = userRepository.findByUsername(email);
        if(!target.isEnabled()) {
            throw new AdminEnabledException("Password not changed!");
        }

    }
}
