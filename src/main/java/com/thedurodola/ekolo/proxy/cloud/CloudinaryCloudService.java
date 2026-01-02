package com.thedurodola.ekolo.proxy.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thedurodola.ekolo.dtos.responses.CloudServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class CloudinaryCloudService implements CloudService {

    private final Cloudinary cloudinary;

    @Override
    public CloudServiceResponse upload(MultipartFile file) throws IOException {
        Map params = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true,
                "resource_type", "image"
        );
        Map<?,?> result = cloudinary.uploader().upload(file.getBytes(), params);
        String imageUrl = result.get("secure_url").toString();

        return null;
    }
}
