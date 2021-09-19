package com.infoworks.lab.services.iServices;

import com.fasterxml.jackson.core.type.TypeReference;
import com.infoworks.lab.services.impl.ResourceService;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface iResourceService {
    InputStream createStream(File file);
    String readAsString(String filename);
    String readAsString(InputStream ios);
    byte[] readAsBytes(InputStream ios);
    List<Map<String, Object>> readAsJsonObject(String json);
    <T> T readAsJsonObject(String json, TypeReference<T> typeReference);
    byte[] readImageAsBytes(BufferedImage img, ResourceService.Format format) throws IOException;
    String readImageAsBase64(BufferedImage img, ResourceService.Format format) throws IOException;
    BufferedImage readImageFromBase64(String content) throws IOException;
    BufferedImage readAsImage(InputStream ios, int imgType) throws IOException;
    BufferedImage createCopyFrom(Image originalImage, int scaledWidth, int scaledHeight, int imageType);
}
