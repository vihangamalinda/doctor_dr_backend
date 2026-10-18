package com.doctor.dr.submission.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUtils {
    String prepareImageForJson(MultipartFile file);
}
