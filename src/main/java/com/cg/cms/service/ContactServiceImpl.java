package com.cg.cms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cg.cms.dao.ContactDAOImpl;
import com.cg.cms.dao.IContactDAO;
import com.cg.cms.entity.Contact;
import com.cg.cms.exception.CMSException;

public class ContactServiceImpl implements IContactService {
	
	private IContactDAO contactDAO;
	
	public ContactServiceImpl() {
		contactDAO = new ContactDAOImpl();
	}
	
	public boolean isValidContactId(Long contactId) {
		return contactId != null && contactId > 0;
	}
	
	public boolean isValidFullName(String fullName) {
		return fullName != null && (fullName.length() >= 3 || fullName.length() <= 20);
	}
	
	public boolean isValidDateOfBirth(LocalDate dob) {
		return dob != null && dob.isBefore(LocalDate.now());
	}
	
	public boolean isValidMobile(String mobile) {
		return mobile != null && mobile.matches("[1-9][0-9]{9}");
	}
	
	public boolean isValidContact(Contact contact) throws CMSException {
		List<String> errorMessages = new ArrayList<>();
		boolean isValid = true;
		
		if(contact != null) {
			if(!isValidContactId(contact.getContactId())) {
				isValid = false;
				errorMessages.add("Contact id cannot be blank and must be positive");
			}
			if(!isValidFullName(contact.getFullName())) {
				isValid = false;
				errorMessages.add("full name can't be blank and must be between 3 and 20 characters");
			}
			if(!isValidDateOfBirth(contact.getDateOfBirth())) {
				isValid = false;
				errorMessages.add("date of birth can't be blank or futuristic");
			}
			if(!isValidMobile(contact.getMobile())) {
				isValid = false;
				errorMessages.add("Mobile number can't be blank and mustbe of 10 digits");
			}
			if(!errorMessages.isEmpty()) {
				throw new CMSException("Invalid Contact : " +errorMessages);
			}
		} else {
				isValid = false;
				throw new CMSException("contact details are not supplied");
			}
			
		return isValid;
	}
	
	@Override
	public Contact add(Contact contact) throws CMSException {
		if (isValidContact(contact)) {
			contactDAO.add(contact);
		}
		return contact;
	}

	@Override
	public Contact save(Contact contact) throws CMSException {
		if (isValidContact(contact)) {
			contactDAO.add(contact);
		}
		return contact;
	}

	@Override
	public boolean deleteById(long contactId) throws CMSException {
		return contactDAO.deleteById(contactId);
	}

	@Override
	public Contact getById(long contactId) throws CMSException {
		return contactDAO.getById(contactId);
	}

	@Override
	public List<Contact> getAll() throws CMSException {
		return contactDAO.getAll();
	}

}
