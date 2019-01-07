package com.purnima.zuulgatwayproxy.filters;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.purnima.zuulgatwayproxy.dtos.Application;
import com.purnima.zuulgatwayproxy.dtos.ApplicationDetailsVO;
import com.purnima.zuulgatwayproxy.dtos.Business;
import com.purnima.zuulgatwayproxy.dtos.CompanyAddress;
import com.purnima.zuulgatwayproxy.dtos.DefaultValues;
import com.purnima.zuulgatwayproxy.model.ClientRequest;
import com.purnima.zuulgatwayproxy.model.UpdateBusinessRequest;
import com.purnima.zuulgatwayproxy.repo.ConfigRepo;
import com.purnima.zuulgatwayproxy.util.Utility;
import com.purnima.zuulgatwayproxy.validators.PartnerValidator;

public class PreFilter extends ZuulFilter {

	@Autowired
	private DefaultValues def;

	@Autowired
	private ConfigRepo config;

	@Autowired
	private PartnerValidator partnerValidator;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		
		HttpServletRequest request = ctx.getRequest();
		
//		System.out.println("Request Method : " + request.getMethod() + " Request URL : "
//				+ request.getRequestURL().toString() + "request >>");
		InputStream in = (InputStream) ctx.get("requestEntity");
		
		try {
			if (in == null) {

				in = ctx.getRequest().getInputStream();

			}

			String url = request.getRequestURL().toString()
					.split("/")[request.getRequestURL().toString().split("/").length - 1];
			
			System.out.println(url);
			
			Gson g = new Gson();

			String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));

			ClientRequest req = g.fromJson(body, ClientRequest.class);
			if (!(url.equals("initializeToken") || url.equals("loginViaPin") || url.equals("registerReferral")
					|| url.equals("addReferral") || url.equals("uploadBankStatementToTIORA") || url.equals("setBankUploadStatus"))) {
			String res=validateRequestAsPerPartner(req, url, ctx, request);
			ctx = updateRequestAsPerPartner(req, url, ctx, g);
            if(res.equals("Failed")) {
            	return null;
            }
			
			
				
		}

		} catch (Exception e) {
			System.out.println("Inside log1" + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return "hi";
	}

	private String validateRequestAsPerPartner(ClientRequest req, String url, RequestContext ctx,
			HttpServletRequest request) {
		List<String> fieldsList = new ArrayList<>();
		 ApplicationDetailsVO applicationDetailVo;
		 applicationDetailVo = Utility.extractObject(req.getData(), ApplicationDetailsVO.class);
		if(applicationDetailVo.getMyBusinessType()==null && !url.equals("createApplication")) {
			ctx.setResponseBody("Business can not be blank");
			ctx.setSendZuulResponse(false);
			return "Failed";
		}else if(url.equals("createApplication") && applicationDetailVo.getType()==null) {
			ctx.setResponseBody("Business can not be blank");
			ctx.setSendZuulResponse(false);
			return "Failed";
			
		}else {
			if(url.equals("createApplication"))
			applicationDetailVo.setMyBusinessType(applicationDetailVo.getType());
			
		}
		String mapedBusinessValue=	getBusinessTypeMapping(applicationDetailVo.getMyBusinessType());
		System.out.println(request.getHeader("Authorization"));
		Object[][] configEntity = config.findFieldsByApiNameAndPartner(request.getHeader("Authorization"), url,mapedBusinessValue);
		fieldsList = generateMandatFieldList(configEntity);
		
		
	

		Errors er = new BeanPropertyBindingResult(applicationDetailVo, applicationDetailVo.getClass().getName());


		if (configEntity == null) {
			ctx.setResponseBody("partner is not exist in system");
			ctx.setSendZuulResponse(false);
			return "Failed";
		}

		applicationDetailVo.setRequiredFields(fieldsList);
		partnerValidator.validate(applicationDetailVo, er);
		String s = "";
		int i = 0;
		if (er.hasErrors()) {
			for (FieldError fe : er.getFieldErrors()) {
				s += er.getFieldErrors().get(i).getCode() + "\n";
				i++;

			}
			ctx.setResponseBody(s);
			ctx.setSendZuulResponse(false);
			return "Failed";
		}
		

		return "Success";
	}

	private String getBusinessTypeMapping(String type) {

		String mapedVal=null;
		switch (type) {
		case "Self Employed Professional":
			mapedVal="BR03";
			break;
		case "Proprietorship":
			mapedVal="BR04";
			break;	

		case "Partnership":
			mapedVal="BR05";
			break;	
		case "Limited Liability Partnership(LLP)":
			mapedVal="BR06";
			break;	
		case "Private Limited":
			mapedVal="BR07";
			break;
		case "Public Limited":
			mapedVal="BR08";
			break;
		case "Advance against Receivables":
			mapedVal="BPT01";
			break;
		case "Purchase Stock":
			mapedVal="BPT02";
			break;
		case "Pay other Loans/ Obligations":
			mapedVal="BPT03";
			break;
		case "Purchase Equipment/ Other Asset":
			mapedVal="BPT04";
			break;
		case "Renovation/ Upgradation":
			mapedVal="BPT05";
			break;
		case "Other Business Loans":
			mapedVal="BPT06";
			break;
		default:
			break;
		}
		
		return mapedVal;
	}

	private List<String> generateMandatFieldList(Object[][] configEntity) {
		List<String> list = new ArrayList<>();

		for (int i = 0; i < configEntity.length; i++) {
			list.add(configEntity[i][1] + "");
		}
		// TODO Auto-generated method stub
		return list;
	}

	private RequestContext updateRequestAsPerPartner(ClientRequest req, String url, RequestContext ctx, Gson g) {
		try {
			ApplicationDetailsVO applicationDetailVo;
			applicationDetailVo = Utility.extractObject(req.getData(), ApplicationDetailsVO.class);
			String mapedPurposeValue="";
			String mapedBusinessValue=	"";
			if (url.equals("createApplication")) {
				mapedBusinessValue=getBusinessTypeMapping(applicationDetailVo.getType());
				  if(applicationDetailVo.getPurpose()!=null)
					 mapedPurposeValue=	getBusinessTypeMapping(applicationDetailVo.getPurpose());
				Application app = Utility.extractObject(req.getData(), Application.class);
				Object[][] defualtValues=config.getDefaultValuesByApiName(url);
				
				for (int i = 0; i < defualtValues.length; i++) {
					if(defualtValues[i][1].equals("use")) {
						System.out.println(defualtValues[i][2].toString());
						app.setUse(defualtValues[i][2].toString());
					}
				}
				
				
				app.setType(mapedBusinessValue);
				app.setPurpose(mapedPurposeValue);
				req.setData(app);
			} else if (url.equals("createBusiness1")) {
				mapedBusinessValue=getBusinessTypeMapping(applicationDetailVo.getMyBusinessType());
				Business business=new Business();
				CompanyAddress companyAddress=generateAddressObject(applicationDetailVo);
				companyAddress.setState(applicationDetailVo.getState());
				HashMap<String,Object> businessDetails=new HashMap<>();
				businessDetails=createBusinessDetailObj(applicationDetailVo,mapedBusinessValue);
				business.setBusinessDetails(businessDetails);
				business.getBusinessDetails().put("companyAddress", companyAddress);
				req.setData(business);
			}else if(url.equals("updateBusiness")) {
				mapedBusinessValue=getBusinessTypeMapping(applicationDetailVo.getMyBusinessType());
				CompanyAddress companyAddress=generateAddressObject(applicationDetailVo);
				UpdateBusinessRequest updateBusinessRequest=new UpdateBusinessRequest();
				
				updateBusinessRequest.setKey(applicationDetailVo.getKey());
				HashMap<String,Object> map=setParamToModel(applicationDetailVo);
				map.put("currentAddress", companyAddress);
				map.put("permamentAddressSame", true);
				updateBusinessRequest.setData(map);
				req.setData(updateBusinessRequest);
			}
			
			byte[] bytes = g.toJson(req).getBytes("UTF-8");

			ctx.setRequest(new HttpServletRequestWrapper(ctx.getRequest()) {
				@Override
				public ServletInputStream getInputStream() throws IOException {
					return new ServletInputStreamWrapper(bytes);
				}

				@Override
				public int getContentLength() {
					return bytes.length;
				}

				@Override
				public long getContentLengthLong() {
					return bytes.length;
				}
			});
			// ctx.set("requestEntity", new
			// ByteArrayInputStream("Purnima".getBytes("UTF-8")));

			// System.out.println(body+" >>>>>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ctx;
	}
	private HashMap<String,Object> setParamToModel(ApplicationDetailsVO applicationDetailVo) {
		
		HashMap<String,Object> map=new  HashMap<>();
		
		if(applicationDetailVo.getPan()!=null) {
			map.put("pan", applicationDetailVo.getPan());
			
		}
		if(applicationDetailVo.getOfficialMailId()!=null) {
			map.put("officialMailId", applicationDetailVo.getOfficialMailId());
			
		}
		if(applicationDetailVo.getPhone()!=null) {
			map.put("phone", applicationDetailVo.getPhone());
			
		}
		
		if(applicationDetailVo.getBirthDate()!=null) {
			map.put("birthDate", applicationDetailVo.getBirthDate());
			
		}
		if(applicationDetailVo.getIsFormSubmittedAtLeastOnce()!=null) {
			map.put("isFormSubmittedAtLeastOnce", applicationDetailVo.getIsFormSubmittedAtLeastOnce());
			
		}
		
		if(applicationDetailVo.getPermamentAddressSame()!=null) {
			map.put("permamentAddressSame", applicationDetailVo.getPermamentAddressSame());
			
		}
		
		if(applicationDetailVo.getName()!=null) {
			map.put("name", applicationDetailVo.getName());
			
		}
		if(applicationDetailVo.getGender()!=null) {
			map.put("gender", applicationDetailVo.getGender());
			
		}
		
		if(applicationDetailVo.getMaritalStatus()!=null) {
			map.put("maritalStatus", applicationDetailVo.getMaritalStatus());
			
		}
		
		if(applicationDetailVo.getNoOfDependents()!=null) {
			map.put("noOfDependents", applicationDetailVo.getNoOfDependents());
			
		}
		if(applicationDetailVo.getFathersName()!=null) {
			map.put("fathersName", applicationDetailVo.getFathersName());
			
		}
		if(applicationDetailVo.getMothersName()!=null) {
			map.put("mothersName", applicationDetailVo.getMothersName());
			
		}
		
		if(applicationDetailVo.getEduQualification()!=null) {
			map.put("eduQualification", applicationDetailVo.getEduQualification());
			
		}
		if(applicationDetailVo.getCategory()!=null) {
			map.put("category", applicationDetailVo.getCategory());
			
		}
		
		if(applicationDetailVo.getReligion()!=null) {
			map.put("religion", applicationDetailVo.getReligion());
			
		}
		
		if(applicationDetailVo.getIsDisable()!=null) {
			map.put("isDisable", applicationDetailVo.getIsDisable());
			
		}
		
		if(applicationDetailVo.getHasRelative()!=null) {
			map.put("hasRelative", applicationDetailVo.getHasRelative());
			
		}
		
		if(applicationDetailVo.getRelativeBankName()!=null) {
			map.put("relativeBankName", applicationDetailVo.getRelativeBankName());
			
		}
		
		if(applicationDetailVo.getBankRelativeDesignation()!=null) {
			map.put("bankRelativeDesignation", applicationDetailVo.getBankRelativeDesignation());
			
		}
		if(applicationDetailVo.getBankRelativeRelationship()!=null) {
			map.put("bankRelativeRelationship", applicationDetailVo.getBankRelativeRelationship());
			
		}
		if(applicationDetailVo.getIndianNational()!=null) {
			map.put("indianNational", applicationDetailVo.getIndianNational());
			
		}
		if(applicationDetailVo.getNameAsPerPan()!=null) {
			map.put("nameAsPerPan", applicationDetailVo.getNameAsPerPan());
			
		}
		
	
		
		if(applicationDetailVo.getApplyingLoanAs()!=null) {
			map.put("applyingLoanAs", applicationDetailVo.getApplyingLoanAs());
			
		}
		
		return map;
		
		
	}

	private HashMap<String, Object> createBusinessDetailObj(ApplicationDetailsVO applicationDetailVo,String mapedBusinessValue) {
		HashMap<String, Object> businessDetail=new HashMap<>();
		businessDetail.put("dateOfIncorporation", applicationDetailVo.getDateOfIncorporation());
		if(applicationDetailVo.getOfficialMailId()!=null)
		businessDetail.put("officialMailId", applicationDetailVo.getOfficialMailId());
		businessDetail.put("gstIn", applicationDetailVo.getGstIn());
		businessDetail.put("businessInOperationSince", applicationDetailVo.getBusinessInOperationSince());
		businessDetail.put("companyName", applicationDetailVo.getCompanyName());
		businessDetail.put("locality", applicationDetailVo.getLocality());
		businessDetail.put("businessType", applicationDetailVo.getBusinessType());
		businessDetail.put("subIndustry", applicationDetailVo.getSubIndustry());
		businessDetail.put("industry", applicationDetailVo.getIndustry());
		businessDetail.put("businessPanNumber", applicationDetailVo.getBusinessPanNumber());
		if(mapedBusinessValue.equals("BR06")) {
			businessDetail.put("llpinNumber", applicationDetailVo.getLlpinNumber());
		    businessDetail.put("noOfPartner", applicationDetailVo.getNoOfPartner());
			
		}
		// TODO Auto-generated method stub
		return businessDetail;
	}

	// TODO Auto-generated method stub

	private CompanyAddress generateAddressObject(ApplicationDetailsVO applicationDetailVo) {
		
		CompanyAddress companyAddress=new CompanyAddress();
		if(applicationDetailVo.getState()!=null) {
		 companyAddress.setState(applicationDetailVo.getState());
		}
		if(applicationDetailVo.getCity()!=null)
		companyAddress.setCity(applicationDetailVo.getCity());
		
		if(applicationDetailVo.getLine1()!=null)
		companyAddress.setLine1(applicationDetailVo.getLine1());
		
		if(applicationDetailVo.getLine2()!=null)
			companyAddress.setLine2(applicationDetailVo.getLine2());
		
		if(applicationDetailVo.getPostalCode()!=null)
		companyAddress.setPostalCode(applicationDetailVo.getPostalCode());
		
		if(applicationDetailVo.getStdCode()!=null)
		companyAddress.setStdCode(applicationDetailVo.getStdCode());
		
		if(applicationDetailVo.getOwnershipStatus()!=null)
			companyAddress.setOwnershipStatus(applicationDetailVo.getOwnershipStatus());
		
		companyAddress.setLocality(applicationDetailVo.getLocality());
		
		return companyAddress;
	}

}
