package lk.ac.mrt.itfac.aurors.BA_Management_System.controller;

import lk.ac.mrt.itfac.aurors.BA_Management_System.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class UploadController {

	@Autowired
	StorageService storageService;

	List<String> files = new ArrayList<String>();

	@PostMapping("/post")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			File deleteFile = new File("I:\\Final Software Project\\Final\\New folder\\src\\assets\\"+ EmployeeController.image +".jpg");
			deleteFile.delete();
			storageService.store(file,EmployeeController.image);
			files.add(file.getOriginalFilename());

			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@PostMapping("/postoverall")
	public ResponseEntity<String> handleFileUploadOverall(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			File deleteFile = new File("I:\\Final Software Project\\Final\\New folder\\src\\assets\\"+ EmployeeController.overallpdf);
			deleteFile.delete();
			storageService.storeOverall(file,EmployeeController.overallpdf);
			files.add(file.getOriginalFilename());

			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@PostMapping("/postdisplay")
	public ResponseEntity<String> handleFileUploadDisplay(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			File deleteFile = new File("I:\\Final Software Project\\Final\\New folder\\src\\assets\\"+ EmployeeController.displaypdf);
			deleteFile.delete();
			storageService.storeDisplay(file,EmployeeController.displaypdf);
			files.add(file.getOriginalFilename());

			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@GetMapping("/getallfiles")
	public ResponseEntity<List<String>> getListFiles(Model model) {
		List<String> fileNames = files
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(fileNames);
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
}
