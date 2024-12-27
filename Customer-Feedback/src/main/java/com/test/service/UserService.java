package com.test.service;

import com.test.config.JwtService;
import com.test.domain.entity.UserData;
import com.test.domain.exception.MasterNotFoundException;
import com.test.domain.exception.RowAlreadyExistsException;
import com.test.domain.request.AuthRequest;
import com.test.domain.request.UserRequest;
import com.test.domain.response.Response;
import com.test.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.test.constants.UAMConstants.GET_ROLE_USER;

@Service
public class UserService {


    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);


    @Transactional
    public String userLogin(AuthRequest authRequest) {

        String userName = authRequest.getUserName();
        String userPassword = authRequest.getPasssword();

        String response;
        try {
            UserData userData = userRepository.findByUserNameAndPassword(userName, userPassword);

            if (userData != null) {
                response = jwtService.generateToken(userData.getUserName()+"!"+userData.getEmail(),userData);
                logger.info("Login Successfully");
            } else {
                response = "User Not Found";
                logger.info(response);
            }

        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return response;
    }


    @Transactional
    public Response createUser(UserRequest userRequest) {
        Response response = new Response();

        try {
            Optional<UserData> flag = checkDuplicateRecord(userRequest);

            if (flag.isEmpty()) {
                UserData user = new UserData();
                user.setUserName(userRequest.getUserName());
                user.setEmail(userRequest.getEmail());
                user.setPassword(userRequest.getPasssword());
                user.setDateCreated(OffsetDateTime.now().toString());
                user.setDateEdited(OffsetDateTime.now().toString());
                user.setRole(GET_ROLE_USER);
                UserData userResponse = userRepository.save(user);

                // sent Response to the user
                response.setMassage("User Created Successfully");
                response.setDetail(userResponse);
            } else {
                // sent Response to the user
                response.setMassage("User Already Exist");
            }
            logger.info(response.getMassage());
        } catch (Exception exception) {
            throw new MasterNotFoundException(exception.getMessage());
        }
        return response;
    }


    public Response updateUser(Long id, UserRequest userRequest) {
        Response response = new Response();
        try {
            UserData user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user Not Found"));
            user.setFeedback(userRequest.getFeedback());
            user.setDateEdited(OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.of("+05:30")).toString());
            UserData userResponse = userRepository.save(user);

            // sent Response to the user
            response.setMassage("User Updated Successfully");
            userResponse.setPassword("");
            userResponse.setDateCreated(null);
            response.setDetail(userResponse);

            logger.info(response.getMassage());
        } catch (Exception exception) {
            throw new MasterNotFoundException(exception.getMessage());
        }
        return response;
    }

    public List<UserData> getAllUserData() throws RuntimeException {

        List<UserData> response=userRepository.findAll();
        List<UserData> sortedResponse = response.stream()
                .sorted(Comparator.comparing(UserData::getId)) // Sort by ID
                .peek(user -> user.setPassword("")) // Clear the password
                .collect(Collectors.toList());
        return sortedResponse;

    }

    public UserData getById(Long id) throws RuntimeException {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("user Not Found"));
    }

    @Transactional
    public Response deleteUserBYId(Long id) {

        Response response = new Response();
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                response.setMassage(" User " + id + " Deleted Successfully");
            } else {
                response.setMassage(" User " + id + " Not Found");
            }
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        logger.info(response.getMassage());
        return response;
    }


    public Optional<UserData> checkDuplicateRecord(UserRequest userRequest) {
        Response response = new Response();

        try {
            if (!(userRequest.getPasssword()).equals(userRequest.getConfirmPassword())) {
                response.setMassage("Password doesn't Match");
                throw new RowAlreadyExistsException(response.getMassage());
            }
            return userRepository.findByUserNameAndEmail(userRequest.getUserName(), userRequest.getEmail());
        } catch (Exception ex) {
            throw new RowAlreadyExistsException(ex.getMessage());
        }
    }



}
