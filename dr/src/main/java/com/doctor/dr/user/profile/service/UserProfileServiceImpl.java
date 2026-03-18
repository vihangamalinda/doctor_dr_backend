package com.doctor.dr.user.profile.service;

import com.doctor.dr.user.profile.dto.request.UserInternalSystemInformationRequestDTO;
import com.doctor.dr.user.profile.dto.request.UserProfileRequestDTO;
import com.doctor.dr.user.profile.dto.response.UserProfileResponseDTO;
import com.doctor.dr.user.profile.entity.UserProfile;
import com.doctor.dr.user.profile.mapper.UserProfileMapper;
import com.doctor.dr.user.profile.repository.UserProfileRepository;
import com.doctor.dr.usercredential.dto.request.UserCredentialRegistrationRequestDTO;
import com.doctor.dr.usercredential.service.information.UserCredentialDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;
    private final UserCredentialDetailService userCredentialDetailService;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper, UserCredentialDetailService userCredentialDetailService) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
        this.userCredentialDetailService = userCredentialDetailService;
    }

    @Override
    public List<UserProfileResponseDTO> getAll() {
        List<UserProfile> userProfileList = this.userProfileRepository.findAllByIsActiveTrue();
        return userProfileList.stream().map(this.userProfileMapper::toUserProfileResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void create(UserProfileRequestDTO userProfileRequestDTO) {
        UserProfile userProfile = this.userProfileMapper.toUserProfile(userProfileRequestDTO);
        userProfile.setIsActive(true);
        userProfile = persistEntity(userProfile);

        UserInternalSystemInformationRequestDTO userInternalDTO = userProfileRequestDTO.getUserInternalSystemInformation();
        UserCredentialRegistrationRequestDTO userCredentialDTO = new UserCredentialRegistrationRequestDTO(userInternalDTO.getUserName(), userProfile, userInternalDTO.getUserRoleId());
        userCredentialDetailService.createUserCredentials(userCredentialDTO);

    }


    @Override
    public UserProfileResponseDTO getById(long id) {
        UserProfile userProfile = findById(id);
        return this.userProfileMapper.toUserProfileResponseDTO(userProfile);
    }


    @Override
    public void deleteById(long id) {
        UserProfile userProfile = this.findById(id);
        userProfile.setIsActive(false);
        this.persistEntity(userProfile);
    }

    private UserProfile findById(long id) {
        return this.userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
    }


    private UserProfile persistEntity(UserProfile userProfile) {
        return this.userProfileRepository.save(userProfile);
    }
}
