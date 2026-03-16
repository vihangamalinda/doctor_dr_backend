package com.doctor.dr.user.profile.service.information;

import com.doctor.dr.user.profile.entity.UserProfile;
import com.doctor.dr.user.profile.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileDetailServiceImpl implements UserProfileDetailService{
    private UserProfileRepository userProfileRepository;
    @Override
    public UserProfile getUserProfileById(Long userProfileId) {
        return this.userProfileRepository.findById(userProfileId).orElseThrow(()->new RuntimeException(("User Profile Id not found.")));
    }
}
