package com.thedurodola.ekolo.proxy.cloud;

import com.thedurodola.ekolo.dtos.responses.CloudServiceResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudService {
    CloudServiceResponse upload(MultipartFile file) throws IOException;
}
