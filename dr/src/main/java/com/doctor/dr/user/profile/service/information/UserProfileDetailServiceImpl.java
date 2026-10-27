package com.doctor.dr.user.profile.service.information;

import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.user.profile.entity.UserProfile;
import com.doctor.dr.user.profile.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileDetailServiceImpl implements UserProfileDetailService{
    private final UserProfileRepository userProfileRepository;
    public UserProfileDetailServiceImpl(UserProfileRepository userProfileRepository){
        this.userProfileRepository =userProfileRepository;
    }

    @Override
    public UserProfile getUserProfileById(Long userProfileId) {
        return this.userProfileRepository.findById(userProfileId).orElseThrow(()->new ResourceNotFoundException(("User Profile Id not found.")));
    }
}
