package com.doctor.dr.user.profile.service;

import com.doctor.dr.user.profile.dto.request.UserProfileRequestDTO;
import com.doctor.dr.user.profile.dto.response.UserProfileResponseDTO;
import com.doctor.dr.user.profile.entity.UserProfile;
import com.doctor.dr.user.profile.mapper.UserProfileMapper;
import com.doctor.dr.user.profile.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public List<UserProfileResponseDTO> getAll() {
        List<UserProfile> userProfileList = this.userProfileRepository.findAllByIsActiveTrue();
        return userProfileList.stream().map(this.userProfileMapper::toUserProfileResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void create(UserProfileRequestDTO userProfileRequestDTO) {
        UserProfile userProfile = this.userProfileMapper.toUserProfile(userProfileRequestDTO);
        userProfile.setActive(true);
        persistEntity(userProfile);
    }


    @Override
    public UserProfileResponseDTO getById(long id) {
        UserProfile userProfile = findById(id);
        return this.userProfileMapper.toUserProfileResponseDTO(userProfile);
    }


    @Override
    public void deleteById(long id) {
        UserProfile userProfile = this.findById(id);
        userProfile.setActive(false);
        this.persistEntity(userProfile);
    }

    private UserProfile findById(long id) {
        return this.userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
    }


    private void persistEntity(UserProfile userProfile) {
        this.userProfileRepository.save(userProfile);
    }
}
