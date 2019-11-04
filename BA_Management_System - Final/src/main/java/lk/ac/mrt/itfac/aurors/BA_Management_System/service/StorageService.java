package lk.ac.mrt.itfac.aurors.BA_Management_System.service;

import com.itextpdf.text.DocumentException;
import lk.ac.mrt.itfac.aurors.BA_Management_System.controller.EmployeeController;
import lk.ac.mrt.itfac.aurors.BA_Management_System.dto.*;
import lk.ac.mrt.itfac.aurors.BA_Management_System.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import static com.itextpdf.text.pdf.BaseFont.EMBEDDED;
import static com.itextpdf.text.pdf.BaseFont.IDENTITY_H;

@Service
public class StorageService {

	@Autowired
	EmployeeSearchRepository employeeSearchRepository;

	@Autowired
	EmployeeMongoRepository employeeMongoRepository;

	@Autowired
	OverallSearchRepository overallSearchRepository;

	@Autowired
	OverallMongoRepository overallMongoRepository;

	@Autowired
	InterfaceDesignMongoRepository interfaceDesignMongoRepository;

	@Autowired
	InterfaceDesignSearchRepository interfaceDesignSearchRepository;

	@Autowired
	SRSSearchRepository srsSearchRepository;

	@Autowired
	SpringTemplateEngine templateEngine;

	@Autowired
	ProjectSearchRepository projectSearchRepository;

	@Autowired
	CustomerSearchRepository customerSearchRepository;

	@Autowired
	IntroductionSearchRepository introductionSearchRepository;

	@Autowired
	NonFunctionalSearchRepository nonFunctionalSearchRepository;

	@Autowired
	SystemFeaturesSearchRepository systemFeaturesSearchRepository;

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("I:\\Final Software Project\\Final\\New folder\\src\\assets\\");

	public void store(MultipartFile file,String name) {
//		deleteAll(name);
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(name+".jpg"));
			Collection<Employee> c= employeeSearchRepository.searchUserId(name);
			ArrayList<Employee> a = new ArrayList<>(c);
			a.get(0).setImage(name);
			employeeMongoRepository.save(a.get(0));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeOverall(MultipartFile file,String name) {
		String project1 = name.substring(0,8);
		String project2 = name.substring(0,15);
		System.out.println(project1);
		System.out.println(project2);
//		deleteAll(project2);
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(name));
			System.out.println(name);
			Collection<SRS> c = srsSearchRepository.getFromProjectid(project1);
			ArrayList<SRS> a = new ArrayList<>(c);
			Collection<Overall> co = overallSearchRepository.getFromSRSId(a.get(0).getSRSid());
			ArrayList<Overall> ao = new ArrayList<>(co);
			ao.get(0).setDiagram1("available");
			overallMongoRepository.save(ao.get(0));
 		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void storeDisplay(MultipartFile file,String name) {
//		deleteAll(name);
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(name));
			String project = name.substring(0,8);
			Collection<SRS> c = srsSearchRepository.getFromProjectid(project);
			ArrayList<SRS> a = new ArrayList<>(c);
			Collection<InterfaceDesign> co = interfaceDesignSearchRepository.getFromSRSId(a.get(0).getSRSid());
			ArrayList<InterfaceDesign> ao = new ArrayList<>(co);
			ao.get(0).setAttachment("available");
			interfaceDesignMongoRepository.save(ao.get(0));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void storePDF(Project p) throws IOException, DocumentException {
		Collection<Project> pc = projectSearchRepository.projectDetailsId(p.getProjectid());
		ArrayList<Project> pa = new ArrayList<>(pc);
		Collection<SRS> sc = srsSearchRepository.getFromProjectid(p.getProjectid());
		ArrayList<SRS> sa = new ArrayList<>(sc);
		Collection<Introdution> ic = introductionSearchRepository.getFromSRSId(sa.get(0).getSRSid());
		ArrayList<Introdution> ia = new ArrayList<>(ic);
		Collection<InterfaceDesign> inc = interfaceDesignSearchRepository.getFromSRSId(sa.get(0).getSRSid());
		ArrayList<InterfaceDesign> ina = new ArrayList<>(inc);
		Collection<Overall> oc = overallSearchRepository.getFromSRSId(sa.get(0).getSRSid());
		ArrayList<Overall> oa = new ArrayList<>(oc);
		Collection<NonFunctional> nc = nonFunctionalSearchRepository.getFromSRSId(sa.get(0).getSRSid());
		ArrayList<NonFunctional> na = new ArrayList<>(nc);
		Collection<SystemFeatures> sfc = systemFeaturesSearchRepository.getFromSRSId(sa.get(0).getSRSid());
		ArrayList<SystemFeatures> sfa = new ArrayList<>(sfc);
		Collection<Customer> cc = customerSearchRepository.searchCusID(pa.get(0).getCustomerid());
		ArrayList<Customer> ca = new ArrayList<>(cc);
		Collection<Employee> ec = employeeSearchRepository.searchUserId(pa.get(0).getPm());
		ArrayList<Employee> ea = new ArrayList<>(ec);

		Context context = new Context();

		context.setVariable("customername",ca.get(0).getName());
		context.setVariable("customercontact",ca.get(0).getContact1());
		context.setVariable("customeremail",ca.get(0).getEmail());
		context.setVariable("pmname",ea.get(0).getFname()+" "+ea.get(0).getLname());
		context.setVariable("pmcontact",ea.get(0).getContact1());
		context.setVariable("pmemail",ea.get(0).getEmail());
		context.setVariable("projectid",pa.get(0).getProjectid());
		context.setVariable("projectname",pa.get(0).getName());
		context.setVariable("projectdate",pa.get(0).getStartdate());
		context.setVariable("purpose",ia.get(0).getPurpose());
		context.setVariable("ispecification",ia.get(0).getSpecifications());
		context.setVariable("suggestion",ia.get(0).getSuggestions());
		context.setVariable("audience",ia.get(0).getAudience());
		context.setVariable("benefits",ia.get(0).getBenefits());
		context.setVariable("goal",ia.get(0).getGoal());
		context.setVariable("objectives",ia.get(0).getObjectives());
		context.setVariable("ireference",ia.get(0).getReference());
		context.setVariable("type",oa.get(0).getType());
		context.setVariable("odescription",oa.get(0).getDescription());
		context.setVariable("ospecification",oa.get(0).getSpecification());
		context.setVariable("usercase",oa.get(0).getUsercase());
		context.setVariable("software",oa.get(0).getSoftware());
		context.setVariable("hardware",oa.get(0).getHardware());
		context.setVariable("constraints",oa.get(0).getConstraints());
		context.setVariable("documents",oa.get(0).getDocuments());
		context.setVariable("assumptions",oa.get(0).getAssumptions());
		context.setVariable("display",ina.get(0).getDisplay());
		context.setVariable("dspecifications",ina.get(0).getSpecifications());
		context.setVariable("connections",ina.get(0).getConnections());
		context.setVariable("network",ina.get(0).getNetwork());
		context.setVariable("encryption",ina.get(0).getEncryption());
		context.setVariable("performance",na.get(0).getPerformance());
		context.setVariable("damages",na.get(0).getDamages());
		context.setVariable("safety",na.get(0).getSafety());
		context.setVariable("security",na.get(0).getSecurity());
		context.setVariable("attribute",na.get(0).getAttribute());
		context.setVariable("ndescription",na.get(0).getDescription());
		context.setVariable("nreference",na.get(0).getReference());
		context.setVariable("features",sfa.get(0).getFeatureset());
		String renderedHtmlContent = templateEngine.process("pdf-template", context);
		String xHtml = convertToXhtml(renderedHtmlContent);

		System.out.println("step 1");

		ITextRenderer renderer = new ITextRenderer();
//		renderer.getFontResolver().addFont("Code39.ttf", IDENTITY_H, EMBEDDED);

		String baseUrl = FileSystems
				.getDefault()
				.getPath("src", "main", "resources","templates")
				.toUri()
				.toURL()
				.toString();
		renderer.setDocumentFromString(xHtml, baseUrl);
		renderer.layout();

		System.out.println("step 2");

		OutputStream outputStream = new FileOutputStream("I:\\Final Software Project\\Final\\New folder\\src\\assets\\" +p.getProjectid()+ ".pdf");
		System.out.println("creating");
		renderer.createPDF(outputStream);
		System.out.println("created");
		outputStream.close();
	}

	private String convertToXhtml(String html) throws UnsupportedEncodingException {
		Tidy tidy = new Tidy();
		tidy.setInputEncoding("UTF-8");
		tidy.setOutputEncoding("UTF-8");
		tidy.setXHTML(true);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes("UTF-8"));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		tidy.parseDOM(inputStream, outputStream);
		return outputStream.toString("UTF-8");
	}

	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll(String name) {
		File file=new File("I:\\Final Software Project\\Final\\New folder\\src\\assets\\"+name+".jpg");
		if(file.exists())
			file.delete();
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}
