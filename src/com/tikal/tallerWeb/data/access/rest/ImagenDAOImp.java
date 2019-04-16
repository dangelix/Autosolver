/**
 *   Copyright 2013-2015 TIKAL-TECHNOLOGY
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.tikal.tallerWeb.data.access.rest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.tikal.tallerWeb.data.access.ImageDAO;
import com.tikal.tallerWeb.modelo.upload.ImagenMetadata;
//import com.tikal.tallerWeb.rest.util.RestTemplateFactory;

/**
 * @author Nekorp
 */
@Service
public class ImagenDAOImp implements ImageDAO {

//    @Autowired
//    @Qualifier("taller-RestTemplateFactory")
//    private RestTemplateFactory factory;
//    
//    @Override
//    public ImagenMetadata saveImage(BufferedImage image) {
//        try {
//            ImagenMetadata r = factory.getTemplate().getForObject(factory.getRootUlr() + "/upload/url", ImagenMetadata.class);
//            File file = new File("data/upload.jpg");
//            ImageIO.write(image, "jpg", file);
//            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
//            form.add("myFile", new FileSystemResource(file));
//            r = factory.getTemplate().postForObject(r.getUploadUrl(), form, ImagenMetadata.class);
//            File cache = new File("data/" + r.getRawBlobKey());
//            file.renameTo(cache);
//            return r;
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    @Override
//    public void deleteImage(ImagenMetadata data) {
//        factory.getTemplate().delete(factory.getRootUlr() + "/upload/imagenes/" + data.getRawBlobKey());
//        File cache = new File("data/" + data.getRawBlobKey());
//        if (cache.exists()) {
//            cache.delete();
//        }
//    }
//
//    @Override
//    public BufferedImage loadImage(ImagenMetadata data) {
//        try {
//            File file = new File("data/" + data.getRawBlobKey());
//            if (file.exists()) {
//                return ImageIO.read(file);
//            }
//            BufferedImage img = factory.getTemplate().getForObject(factory.getRootUlr() + "/upload/imagenes/" + data.getRawBlobKey(), BufferedImage.class);
//            ImageIO.write(img, "jpg", file);
//            return img;
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }

}
