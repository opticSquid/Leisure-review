package com.cts.interim.beta.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cts.interim.beta.entities.PlaceType;

@Service
public class FileUploadUtil {
	public void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}

	public byte[] fetchSingleImage(PlaceType placeType, String vendorId, String imageName) throws IOException {
		byte[] image = new byte[0];
		try {
			Path uploadedPath = Paths.get("vendor-photos/" + placeType.toString() + "/" + vendorId);
			if (Files.exists(uploadedPath)) {
				Path filePath = uploadedPath.resolve(imageName);
				image = FileUtils.readFileToByteArray(filePath.toFile());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public List<URI> getAllPhotos(PlaceType placeType, String vendorId) {
		File folder = new File("vendor-photos/" + placeType.toString() + "/" + vendorId);
		String contents[] = folder.list();
		List<URI> listOfImages = new ArrayList<>();
		for (String image : contents) {
			String dynamicPart = vendorId + "/" + image;
			URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/vendors/images/{dynamicPart}")
					.buildAndExpand(dynamicPart).toUri();
			listOfImages.add(location);
		}
		return listOfImages;
	}
}
