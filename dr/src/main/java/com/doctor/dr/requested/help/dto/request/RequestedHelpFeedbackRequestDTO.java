package com.doctor.dr.requested.help.dto.request;

public class RequestedHelpFeedbackRequestDTO {
    private final String feedback;
    private final Long contactId;

    private final Long reviewedByUserProfileId;

    public RequestedHelpFeedbackRequestDTO(String feedback, Long contactId, Long reviewedByUserProfileId) {
        this.feedback = feedback;
        this.contactId = contactId;
        this.reviewedByUserProfileId = reviewedByUserProfileId;
    }

    public String getFeedback() {
        return feedback;
    }

    public Long getContactId() {
        return contactId;
    }

    public Long getReviewedByUserProfileId() {
        return reviewedByUserProfileId;
    }
}
