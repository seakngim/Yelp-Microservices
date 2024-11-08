package kh.edu.cstad.idenity.features.user;

import kh.edu.cstad.idenity.domain.Authority;
import kh.edu.cstad.idenity.domain.BusinessOwner;
import kh.edu.cstad.idenity.domain.User;
import kh.edu.cstad.idenity.domain.UserAuthority;
import kh.edu.cstad.idenity.features.authority.AuthorityRepository;
import kh.edu.cstad.idenity.features.businessowner.BusinessOwnerRepository;
import kh.edu.cstad.idenity.features.emailverification.EmailVerificationTokenService;
import kh.edu.cstad.idenity.features.user.dto.*;
import kh.edu.cstad.idenity.features.user.UserMapper;
import kh.edu.cstad.idenity.util.RandomTokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final BusinessOwnerRepository businessOwnerRepository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final UserAuthorityRepository userAuthorityRepository;


    @Override
    public UserResponse updateBasicInfo(String username, UserBasicInfoRequest userBasicInfoRequest) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        userMapper.mapUserBasicInfoRequestToUser(userBasicInfoRequest, user);

        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserPasswordResetResponse resetPassword(String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        String newPassword = RandomTokenGenerator.generate(8);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return new UserPasswordResetResponse(newPassword);
    }

    @Override
    public void enable(String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        user.setIsEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disable(String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        user.setIsEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void createNewSubscriber(UserCreateRequest userCreateRequest) {

        this.existsByUsername(userCreateRequest.username());
        this.existsByEmail(userCreateRequest.email());

        User user = userMapper.fromUserCreationRequest(userCreateRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfileImage("users/business-owner-icon.png");
        user.setEmailVerified(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setIsEnabled(true);

        UserAuthority defaultUserAuthority = new UserAuthority();
        defaultUserAuthority.setUser(user);
        defaultUserAuthority.setAuthority(authorityRepository.AUTH_USER());

        UserAuthority subscriberAuthority = new UserAuthority();
        subscriberAuthority.setUser(user);
        subscriberAuthority.setAuthority(authorityRepository.AUTH_SUBSCRIBER());

        user.setUserAuthorities(Set.of(defaultUserAuthority, subscriberAuthority));

        BusinessOwner businessOwner = new BusinessOwner();
        businessOwner.setUser(user);
        businessOwnerRepository.save(businessOwner);

        // generate email verification token
        emailVerificationTokenService.generate(user);
    }

    @Override
    public void createNewBusinessOwner(UserCreateRequest userCreateRequest) {

        this.existsByUsername(userCreateRequest.username());
        this.existsByEmail(userCreateRequest.email());

        User user = userMapper.fromUserCreationRequest(userCreateRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfileImage("users/business-owner-icon.png");
        user.setEmailVerified(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setIsEnabled(true);

        UserAuthority defaultUserAuthority = new UserAuthority();
        defaultUserAuthority.setUser(user);
        defaultUserAuthority.setAuthority(authorityRepository.AUTH_USER());

        UserAuthority businessOwnerAuthority = new UserAuthority();
        businessOwnerAuthority.setUser(user);
        businessOwnerAuthority.setAuthority(authorityRepository.AUTH_BUSINESS_OWNER());

        user.setUserAuthorities(Set.of(defaultUserAuthority, businessOwnerAuthority));

        BusinessOwner businessOwner = new BusinessOwner();
        businessOwner.setUser(user);
        businessOwnerRepository.save(businessOwner);

        // generate email verification token
        emailVerificationTokenService.generate(user);

    }

    @Override
    public void createNewUser(UserCreateRequest userCreateRequest) {

        this.existsByUsername(userCreateRequest.username());
        this.existsByEmail(userCreateRequest.email());

        User user = userMapper.fromUserCreationRequest(userCreateRequest);
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfileImage("users/user-icon.png");
        user.setCoverImage("users/cover.png");
        user.setEmailVerified(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setIsEnabled(true);

        user = userRepository.save(user);

        // Set up default authority - USER
        UserAuthority defaultUserAuthority = new UserAuthority();
        defaultUserAuthority.setUser(user);
        defaultUserAuthority.setAuthority(authorityRepository.AUTH_USER());

        user.setUserAuthorities(new HashSet<>());
        user.getUserAuthorities().add(defaultUserAuthority);

        // Set up custom authority - ADMIN/EDITOR
        if (userCreateRequest.authorities() != null) {
            final User finalUser = user;
            System.out.println(finalUser);
            Set<UserAuthority> customAuthorities = userCreateRequest
                    .authorities()
                    .stream()
                    .map(name -> {
                        Authority authority = authorityRepository
                                .findByName(name)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Authority has not been found"));
                        UserAuthority userAuthority = new UserAuthority();
                        userAuthority.setUser(finalUser);
                        userAuthority.setAuthority(authority);
                        return userAuthority;
                    })
                    .collect(Collectors.toSet());
            user.getUserAuthorities().addAll(customAuthorities);
        }

        userAuthorityRepository.saveAll(user.getUserAuthorities());

    }

    @Override
    public Page<UserResponse> findList(int pageNumber, int pageSize) {

        log.info("List<UserResponse> findList(int pageNumber={}, int pageSize={})", pageNumber, pageSize);

        Sort sortByCreatedDate = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortByCreatedDate);

        // retrieve all users from db
        Page<User> users = userRepository.findAll(pageRequest);

        // map from user entities to user response list and return
        return users.map(userMapper::toUserResponse);
    }

    @Override
    public UserResponse findByUsername(String username) {

        log.info("Username: {}", username);

        // retrieve user by username from db
        User user = userRepository.findByUsernameAndIsEnabledTrue(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        return userMapper.toUserResponse(user);
    }

    @Override
    public void checkForPasswords(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password doesn't match!");
        }
    }

    @Override
    public void checkForOldPassword(String username, String oldPassword) {

        // retrieve user by username from db
        User user = userRepository.findByUsernameAndIsEnabledTrue(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong old password");
        }
    }

    @Override
    public void checkTermsAndConditions(String value) {
        if (!value.equals("true") && !value.equals("false")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal value!");
        } else if (value.equals("false")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Terms and Conditions must be accepted in order to register!");
        }
    }

    @Override
    public void existsByUsername(String username) {
        // check if username already exists (validation)
        if (userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists!");
        }
    }

    @Override
    public void existsByEmail(String email) {
        // check if email already exists (validation)
        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!");
        }
    }

    @Transactional
    @Override
    public void verifyEmail(User user) {
        user.setEmailVerified(true);
        userRepository.save(user);
    }


}
